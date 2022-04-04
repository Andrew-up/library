package com.netcracker.ageev.library.dto;

import lombok.Data;

@Data
public class RefreshTokenDTO {
    private Long idToken;
    private String token;
    private String expiryDateToken;
}
