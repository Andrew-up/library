package com.netcracker.ageev.library.security.jwt;


import com.netcracker.ageev.library.model.users.Users;
import com.netcracker.ageev.library.security.SecutiryConstants;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.*;

import static com.netcracker.ageev.library.security.SecutiryConstants.EXPIRATION_TIME;
import static com.netcracker.ageev.library.security.SecutiryConstants.SECRET_KEY;


@Component
public class JWTProvider {


    public static final Logger LOG = LoggerFactory.getLogger(JWTProvider.class);

    public String generateToken(Users user) {
        Date now = new Date(System.currentTimeMillis());
        Date expirationTime = new Date(now.getTime() + EXPIRATION_TIME);
        Calendar calendar = new GregorianCalendar();
//        Users user = (Users) authentication.getPrincipal();
        String userId = Long.toString(user.getId());

        Map<String, Object> claimsMap = new HashMap<>();
        claimsMap.put("id", userId);
//    claimsMap.put("email",user.getEmail());
        claimsMap.put("firstname", user.getFirstname());
        claimsMap.put("lastname", user.getLastname());
        claimsMap.put("status", user.getStatus());

        String token = Jwts.builder()
                .setSubject(userId)
                .addClaims(claimsMap)
                .setIssuedAt(now)
                .setExpiration(expirationTime)
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
                .compact();
        return token;
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser()
                    .setSigningKey(SECRET_KEY)
                    .parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException |
                MalformedJwtException |
                SignatureException |
                UnsupportedJwtException |
                IllegalArgumentException exception) {
            LOG.error(exception.getMessage());
            return false;
        }

    }

    public Long getUserIdFromToken(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();
        String userId = (String) claims.get("id");
         return Long.parseLong(userId);
    }

//    public String generateTokenFromUsername(Users user) {
////        return Jwts.builder().setSubject(username).setIssuedAt(new Date())
////                .setExpiration(new Date((new Date()).getTime() + EXPIRATION_TIME)).signWith(SignatureAlgorithm.HS512, SECRET_KEY)
////                .compact();
//    }

}

