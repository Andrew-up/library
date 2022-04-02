package com.netcracker.ageev.library.controller;

import com.netcracker.ageev.library.dto.UserDTO;
import com.netcracker.ageev.library.facade.UserFacade;
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
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/user")
@CrossOrigin
public class UsersController {

    @Autowired
    private UserFacade userFacade;
    @Autowired
    private UsersService userService;
    @Autowired
    private ResponseErrorValidator responseErrorValidator;

    @GetMapping("/")
    public ResponseEntity<UserDTO> getCurrentUser(Principal principal) {
        Users user = userService.getCurrentUser(principal);
        UserDTO userDTO = userFacade.userToUserDTO(user);
        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }
    @GetMapping("/all")
    public ResponseEntity<List<UserDTO>> getAllUsers(){
        List<UserDTO> userDTOS = userService.getAllUsers()
                .stream()
                .map(userFacade::userToUserDTO)
                .collect(Collectors.toList());
        return new ResponseEntity<>(userDTOS,HttpStatus.OK);
    }
    @GetMapping("/allRequestCreatedToUser")
    public ResponseEntity<List<UserDTO>> getAllUsersRequestCreated(){
        List<UserDTO> userDTOS = userService.getAllUsersRequestCreated()
                .stream()
                .map(userFacade::userToUserDTO2)
                .collect(Collectors.toList());
        return new ResponseEntity<>(userDTOS,HttpStatus.OK);
    }


    @PostMapping("/update")
    public ResponseEntity<Object> updateUser(@Valid @RequestBody UserDTO userDTO,
                                             BindingResult bindingResult, Principal principal) {

        ResponseEntity<Object> listErrors = responseErrorValidator.mappedValidatorService(bindingResult);
        if (!ObjectUtils.isEmpty(listErrors)) return listErrors;
        Users user = userService.updateUser(userDTO, principal);
        UserDTO userUpdated = userFacade.userToUserDTO(user);
        return new ResponseEntity<>(userUpdated, HttpStatus.OK);
    }

}
