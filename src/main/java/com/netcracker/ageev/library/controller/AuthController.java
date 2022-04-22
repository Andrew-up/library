package com.netcracker.ageev.library.controller;

import com.netcracker.ageev.library.dto.RefreshTokenDTO;
import com.netcracker.ageev.library.exception.TokenRefreshException;
import com.netcracker.ageev.library.facade.RefreshTokenFacade;
import com.netcracker.ageev.library.model.RefreshToken;
import com.netcracker.ageev.library.model.enums.ERole;
import com.netcracker.ageev.library.model.enums.Status;
import com.netcracker.ageev.library.model.users.Users;
import com.netcracker.ageev.library.payload.request.LoginRequest;
import com.netcracker.ageev.library.payload.request.SignupRequest;
import com.netcracker.ageev.library.payload.responce.SuccessResponse;
import com.netcracker.ageev.library.payload.responce.MessageResponse;
import com.netcracker.ageev.library.payload.responce.TokenRefreshResponse;
import com.netcracker.ageev.library.security.SecutiryConstants;
import com.netcracker.ageev.library.security.jwt.JWTProvider;
import com.netcracker.ageev.library.service.UsersService;
import com.netcracker.ageev.library.service.token.RefreshTokenService;
import com.netcracker.ageev.library.validators.ResponseErrorValidator;
import com.netcracker.ageev.library.validators.TokenRefreshRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Base64;

@CrossOrigin
@RestController
@RequestMapping("/api/auth")
@PreAuthorize("permitAll()")
public class AuthController {

    private static final Logger LOG = LoggerFactory.getLogger(AuthController.class);

    private final JWTProvider jwtUtils;
    private final AuthenticationManager authenticationManager;
    private final ResponseErrorValidator responseErrorValidator;
    private final UsersService usersService;
    private final RefreshTokenService refreshTokenService;
    private final JWTProvider jwtProvider;
    private final RefreshTokenFacade refreshTokenFacade;

    @Autowired
    public AuthController(JWTProvider jwtUtils,
                          AuthenticationManager authenticationManager,
                          ResponseErrorValidator responseErrorValidator,
                          UsersService usersService,
                          RefreshTokenService refreshTokenService,
                          JWTProvider jwtProvider,
                          RefreshTokenFacade refreshTokenFacade) {
        this.jwtUtils = jwtUtils;
        this.authenticationManager = authenticationManager;
        this.responseErrorValidator = responseErrorValidator;
        this.usersService = usersService;
        this.refreshTokenService = refreshTokenService;
        this.jwtProvider = jwtProvider;
        this.refreshTokenFacade = refreshTokenFacade;
    }

    @PostMapping("/signup")
    public ResponseEntity<Object> registerUser(@Valid @RequestBody SignupRequest signupRequest, BindingResult bindingResult) {
        ResponseEntity<Object> listErrors = responseErrorValidator.mappedValidatorService(bindingResult);
        if (!ObjectUtils.isEmpty(listErrors)) return listErrors;
        usersService.createUser(signupRequest);
        return ResponseEntity.ok((new MessageResponse("Registration successfully completed")));
    }

    //Авторизация
    @PostMapping("/signin")
    public ResponseEntity<Object> authenticateUser(@Valid @RequestBody LoginRequest loginRequest, BindingResult bindingResult) {
        ResponseEntity<Object> listErrors = responseErrorValidator.mappedValidatorService(bindingResult);
        if (!ObjectUtils.isEmpty(listErrors)) {
            return listErrors;
        }
        if (block(loginRequest.getEmail())) {
            LOG.info("login attempt. account is blocked: " + loginRequest.getEmail());
            return ResponseEntity.ok(new SuccessResponse(false, "Учетная запись заблокирована", new RefreshTokenDTO(), ""));
        }

        byte[] decodePassword = Base64.getDecoder().decode(loginRequest.getPassword());
        String decodedString = new String(decodePassword);

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), decodedString));
        Users usersDetails = (Users) authentication.getPrincipal();
        RefreshToken refreshToken = refreshTokenService.createRefreshToken(usersDetails.getId());
        RefreshTokenDTO refreshTokenDTO = refreshTokenFacade.refreshTokenDTO(refreshToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = SecutiryConstants.TOKEN_PREFIX + jwtProvider.generateToken(usersDetails);
        if (usersDetails.getERole().equals(ERole.ROLE_ADMIN)) {
            LOG.info("Nickname user: " + usersDetails.getEmail() + "logged in. ROLE: " + usersDetails.getERole());
        }
        return ResponseEntity.ok(new SuccessResponse(true, jwt, refreshTokenDTO, usersDetails.getERole().name()));
    }

    @PostMapping("/refreshtoken")
    public ResponseEntity<?> refreshToken(@Valid @RequestBody TokenRefreshRequest request) {
        String requestRefreshToken = request.getRefreshToken();
        return refreshTokenService.findByToken(requestRefreshToken)
                .map(refreshTokenService::verifyExpiration)
                .map(RefreshToken::getUser)
                .map(user -> {
                    String token = SecutiryConstants.TOKEN_PREFIX + jwtUtils.generateToken(user);
                    return ResponseEntity.ok(new TokenRefreshResponse(token, requestRefreshToken));
                })
                .orElseThrow(() -> new TokenRefreshException(requestRefreshToken,
                        "Refresh token is not in database!"));
    }

    private boolean block(String email) {
        if (usersService.findUsersByEmail(email).getStatus() == Status.ACTIVE) {
            return false;
        } else return true;
    }

}
