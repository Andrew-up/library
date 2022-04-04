package com.netcracker.ageev.library.payload.responce;

import com.netcracker.ageev.library.dto.RefreshTokenDTO;
import com.netcracker.ageev.library.model.RefreshToken;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SuccessResponse {
   private boolean success;
   private String accessToken;
   private RefreshTokenDTO refreshToken;
   private String role;
}
