package com.netcracker.ageev.library.payload.responce;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SuccessResponse {
   private boolean success;
   private String token;
   private String refreshToken;
   private String role;
}
