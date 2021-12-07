package com.netcracker.ageev.library.payload.responce;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class JWTSuccessResponse {
   private boolean success;
   private String token;
}
