package com.netcracker.ageev.library.payload.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class LoginRequest {

    @NotNull(message = "Username cannon be empty")
    private String email;
    @NotNull(message = "Password cannon be empty")
    private String password;

}
