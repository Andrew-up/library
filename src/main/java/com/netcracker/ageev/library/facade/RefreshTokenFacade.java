package com.netcracker.ageev.library.facade;

import com.netcracker.ageev.library.dto.PublisherDTO;
import com.netcracker.ageev.library.dto.RefreshTokenDTO;
import com.netcracker.ageev.library.model.RefreshToken;
import com.netcracker.ageev.library.model.books.Publisher;
import org.springframework.stereotype.Component;

@Component
public class RefreshTokenFacade {

    public RefreshTokenDTO refreshTokenDTO(RefreshToken refreshToken) {
        RefreshTokenDTO refreshTokenDTO = new RefreshTokenDTO();
        refreshTokenDTO.setToken(refreshToken.getToken());
        refreshTokenDTO.setExpiryDateToken(refreshToken.getExpiryDate().toString());
        refreshTokenDTO.setIdToken(refreshToken.getId());
        return refreshTokenDTO;
    }
}
