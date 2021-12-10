package com.netcracker.ageev.library.security.jwt;


import com.netcracker.ageev.library.model.users.Users;
import com.netcracker.ageev.library.security.SecutiryConstants;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;


@Component
    public class JWTProvider {
        public static final Logger LOG = LoggerFactory.getLogger(JWTProvider.class);

public String generateToken(Authentication authentication){
    Users user = (Users) authentication.getPrincipal();

    Date now = new Date(System.currentTimeMillis());

    Date expirationTime = new Date(now.getTime()+ SecutiryConstants.EXPIRATION_TIME);

    String userId = Long.toString(user.getId());

    Map<String,Object> claimsMap =  new HashMap<>();
    claimsMap.put("id", userId);
    claimsMap.put("email",user.getEmail());
    claimsMap.put("firstname",user.getFirstname());
    claimsMap.put("lastname",user.getLastname());
    claimsMap.put("status",user.getStatus());

    String token  = Jwts.builder()
            .setSubject(userId)
            .addClaims(claimsMap)
            .setIssuedAt(now)
            .signWith(SignatureAlgorithm.HS512, SecutiryConstants.SECRET_KEY)
            .compact();
    return token;
}

public boolean validateToken(String token){
    try {
        Jwts.parser()
                .setSigningKey(SecutiryConstants.SECRET_KEY)
                .parseClaimsJws(token);
        return true;
    }
    catch (ExpiredJwtException |
            MalformedJwtException |
            SignatureException |
            UnsupportedJwtException |
            IllegalArgumentException exception){
        LOG.error(exception.getMessage());
        return false;
    }

}

public Long getUserIdFromToken(String token){
    Claims claims = Jwts.parser()
            .setSigningKey(SecutiryConstants.SECRET_KEY)
            .parseClaimsJws(token)
            .getBody();
    String userId = (String) claims.get("id");
    return Long.parseLong(userId);
  }

}

