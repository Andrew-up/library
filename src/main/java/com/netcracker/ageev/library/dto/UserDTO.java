package com.netcracker.ageev.library.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class UserDTO {

    private Long id;
    private String firstname;
    private String lastname;
    private String username;
    private String info;
    private String status;
    private String newStatus;
    private String Role;
    private String Phone;
    private String address;
    private String dateOfBirth;
    private List<BasketUsersDTO> basketUser;
    private String newRole;

}

