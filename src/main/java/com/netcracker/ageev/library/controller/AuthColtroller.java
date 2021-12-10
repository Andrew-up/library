package com.netcracker.ageev.library.controller;

import com.netcracker.ageev.library.model.enums.Status;
import com.netcracker.ageev.library.model.users.Users;
import com.netcracker.ageev.library.payload.request.LoginRequest;
import com.netcracker.ageev.library.payload.request.SignupRequest;
import com.netcracker.ageev.library.payload.responce.SuccessResponse;
import com.netcracker.ageev.library.payload.responce.MessageResponse;
import com.netcracker.ageev.library.security.SecutiryConstants;
import com.netcracker.ageev.library.security.jwt.JWTProvider;
import com.netcracker.ageev.library.service.UsersService;
import com.netcracker.ageev.library.validators.ResponseErrorValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.persistence.Basic;
import javax.validation.Valid;
import java.security.Principal;

@CrossOrigin
@RestController
@RequestMapping("/api/auth")
@PreAuthorize("permitAll()")
public class AuthColtroller {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private ResponseErrorValidator responseErrorValidator;

    @Autowired
    private UsersService usersService;


    @Autowired
    private JWTProvider jwtProvider;


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
        if(block(loginRequest.getEmail())){
            return ResponseEntity.ok(new SuccessResponse(false,"Учетная запись заблокирована"));
        }
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(),loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = SecutiryConstants.TOKEN_PREFIX+ jwtProvider.generateToken(authentication);
        return  ResponseEntity.ok(new SuccessResponse(true,jwt));

    }


    private boolean block(String email){
        if(usersService.findUsersByEmail(email).getStatus() == Status.ACTIVE){
            return false;
        }
        else return true;
    }

}
