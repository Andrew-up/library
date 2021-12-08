package com.netcracker.ageev.library.payload.request;


import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
public class SignupRequest {

    @Email(message = "it should be email format")
    @NotBlank(message = "User email is request")
    private String email;

    @NotEmpty(message = "Please enter your name")
    private String name;

    @NotEmpty(message = "Please enter your lastname")
    private String surname;

    @NotEmpty(message = "Please enter your password")
    @Size(min = 5, max = 50)
    private String password;

}
