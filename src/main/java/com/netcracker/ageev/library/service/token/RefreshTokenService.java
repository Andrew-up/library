package com.netcracker.ageev.library.service.token;

import com.netcracker.ageev.library.exception.ErrorMessage;
import com.netcracker.ageev.library.exception.TokenRefreshException;
import com.netcracker.ageev.library.model.RefreshToken;
import com.netcracker.ageev.library.repository.RefreshTokenRepository;
import com.netcracker.ageev.library.repository.users.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import static com.netcracker.ageev.library.security.SecutiryConstants.REFRESH_TOKEN_EXPIRATION_TIME;

@Service
public class RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;
    private final UsersRepository usersRepository;

    @Autowired
    public RefreshTokenService(RefreshTokenRepository refreshTokenRepository, UsersRepository usersRepository) {
        this.refreshTokenRepository = refreshTokenRepository;
        this.usersRepository = usersRepository;
    }


    public Optional<RefreshToken> findByToken(String token){
        return refreshTokenRepository.findByToken(token);
    }

    public RefreshToken createRefreshToken(Long userId) {
        RefreshToken refreshToken = new RefreshToken();

        RefreshToken refreshToken1 = null;
        try {
            refreshToken1  = refreshTokenRepository.findByUserId(userId).orElseThrow(() -> new NullPointerException("Юзер не найден"));
        }
        catch (NullPointerException e){
            e.printStackTrace();
        }
        if(refreshToken1!=null){
            refreshToken1.setUser(usersRepository.findById(userId).get());
            refreshToken1.setExpiryDate(Instant.now().plusMillis(REFRESH_TOKEN_EXPIRATION_TIME));
            refreshToken1.setToken(UUID.randomUUID().toString());
            return refreshTokenRepository.save(refreshToken1);
        }
        else {
            refreshToken.setUser(usersRepository.findById(userId).get());
            refreshToken.setExpiryDate(Instant.now().plusMillis(REFRESH_TOKEN_EXPIRATION_TIME));
            refreshToken.setToken(UUID.randomUUID().toString());
        }
        return refreshTokenRepository.save(refreshToken);
    }

    public RefreshToken verifyExpiration(RefreshToken token) {
        if (token.getExpiryDate().compareTo(Instant.now()) < 0) {
            refreshTokenRepository.delete(token);
            throw new TokenRefreshException(token.getToken(), "Refresh token was expired. Please make a new signin request");
        }
        return token;
    }


    @Transactional
    public int deleteByUserId(Long userId) {
        return refreshTokenRepository.deleteByUser(usersRepository.findById(userId).get());
    }


}
