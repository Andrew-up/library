package com.netcracker.ageev.library.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class UserDTO {

    private Long id;
    private String firstname;
    private String lastname;
    private String username;
    private String info;
    private String status;
    private String Role;
    private String Phone;
    private String address;
    private String dateOfBirth;

}
