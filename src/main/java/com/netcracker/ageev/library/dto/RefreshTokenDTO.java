package com.netcracker.ageev.library.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RefreshTokenDTO {

    private Long idToken;
    private String token;
    private String expiryDateToken;

}
