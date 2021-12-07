package com.netcracker.ageev.library.payload.responce;

import lombok.Getter;

@Getter
public class InvalidLoginResponse {

    private String email;
    private String password;

    public InvalidLoginResponse() {
        this.email = "Invalid username";
        this.password =  "Invalid password";
    }
}
