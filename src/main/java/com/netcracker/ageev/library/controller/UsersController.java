package com.netcracker.ageev.library.controller;

import com.netcracker.ageev.library.dto.UserDTO;
import com.netcracker.ageev.library.facade.UserFacade;
import com.netcracker.ageev.library.model.enums.ERole;
import com.netcracker.ageev.library.model.enums.Status;
import com.netcracker.ageev.library.model.users.Users;
import com.netcracker.ageev.library.service.UsersService;
import com.netcracker.ageev.library.validators.ResponseErrorValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class UsersController {

    private final UserFacade userFacade;
    private final UsersService userService;
    private final ResponseErrorValidator responseErrorValidator;

    @Autowired
    public UsersController(UserFacade userFacade, UsersService userService, ResponseErrorValidator responseErrorValidator) {
        this.userFacade = userFacade;
        this.userService = userService;
        this.responseErrorValidator = responseErrorValidator;
    }

    @GetMapping("/user/")
    public ResponseEntity<UserDTO> getCurrentUser(Principal principal) {
        Users user = userService.getCurrentUser(principal);
        UserDTO userDTO = userFacade.userToUserDTO(user);
        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }

    @GetMapping("/user/all")
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<UserDTO> userDTOS = userService.getAllUsers()
                .stream()
                .map(userFacade::userToUserDTO)
                .collect(Collectors.toList());
        return new ResponseEntity<>(userDTOS, HttpStatus.OK);
    }

    @GetMapping("/staff/user/allRequestCreatedToUser")
    public ResponseEntity<List<UserDTO>> getAllUsersRequestCreated() {
        List<UserDTO> userDTOS = userService.getAllUsersRequestCreated()
                .stream()
                .map(userFacade::userToUserDTO2)
                .collect(Collectors.toList());
        System.out.println(userDTOS);
        return new ResponseEntity<>(userDTOS, HttpStatus.OK);
    }

    @PostMapping("/user/update")
    public ResponseEntity<Object> updateUser(@Valid @RequestBody UserDTO userDTO,
                                             BindingResult bindingResult, Principal principal) {

        ResponseEntity<Object> listErrors = responseErrorValidator.mappedValidatorService(bindingResult);
        if (!ObjectUtils.isEmpty(listErrors)) return listErrors;
        Users user = userService.updateUser(userDTO, principal);
        UserDTO userUpdated = userFacade.userToUserDTO(user);
        return new ResponseEntity<>(userUpdated, HttpStatus.OK);
    }

    @GetMapping("/user/roles")
    public ResponseEntity<List<ERole>> getAllRoleUsers() {
        List<ERole> eRolesList = new ArrayList<>(Arrays.asList(ERole.values()));
        return new ResponseEntity<>(eRolesList, HttpStatus.OK);
    }

    @GetMapping("/user/status")
    public ResponseEntity<List<Status>> getAllStatusUsers() {
        List<Status> statusList = new ArrayList<>(Arrays.asList(Status.values()));
        return new ResponseEntity<>(statusList, HttpStatus.OK);
    }

    @PostMapping("/admin/user/update")
    public ResponseEntity<Object> updateUserRoleOrActive(@Valid @RequestBody UserDTO userDTO,
                                                         BindingResult bindingResult) {

        ResponseEntity<Object> listErrors = responseErrorValidator.mappedValidatorService(bindingResult);
        if (!ObjectUtils.isEmpty(listErrors)) return listErrors;
        Users user = userService.updateUserRoleOrActive(userDTO);
        UserDTO userUpdated = userFacade.userToUserDTO(user);
        return new ResponseEntity<>(userUpdated, HttpStatus.OK);
    }

}
