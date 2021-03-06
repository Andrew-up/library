package com.netcracker.ageev.library.service.token;

import com.netcracker.ageev.library.exception.TokenRefreshException;
import com.netcracker.ageev.library.model.RefreshToken;
import com.netcracker.ageev.library.repository.RefreshTokenRepository;
import com.netcracker.ageev.library.repository.users.UsersRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import static com.netcracker.ageev.library.security.SecutiryConstants.REFRESH_TOKEN_EXPIRATION_TIME;

@Service
public class RefreshTokenService {
    private static final Logger LOG = LoggerFactory.getLogger(RefreshTokenService.class);
    private final RefreshTokenRepository refreshTokenRepository;
    private final UsersRepository usersRepository;

    @Autowired
    public RefreshTokenService(RefreshTokenRepository refreshTokenRepository, UsersRepository usersRepository) {
        this.refreshTokenRepository = refreshTokenRepository;
        this.usersRepository = usersRepository;
    }


    public Optional<RefreshToken> findByToken(String token) {
        return refreshTokenRepository.findByToken(token);
    }

    public RefreshToken createRefreshToken(Long userId) {
        RefreshToken refreshToken = null;
        Optional<RefreshToken> refreshToken1 = refreshTokenRepository.findByUserId(userId);
        if (refreshToken1.isPresent()) {
            refreshToken = refreshToken1.get();
            refreshToken.setUser(usersRepository.findById(userId).orElse(null));
            refreshToken.setExpiryDate(Instant.now().plusMillis(REFRESH_TOKEN_EXPIRATION_TIME));
            refreshToken.setToken(UUID.randomUUID().toString());
            LOG.debug("refresh token at the user: " + refreshToken.getUser().getEmail() + " replaced");
            return refreshTokenRepository.save(refreshToken);
        }
        refreshToken = new RefreshToken();
        refreshToken.setUser(usersRepository.findById(userId).orElse(null));
        refreshToken.setExpiryDate(Instant.now().plusMillis(REFRESH_TOKEN_EXPIRATION_TIME));
        refreshToken.setToken(UUID.randomUUID().toString());
        LOG.debug("refresh token at the user: " + refreshToken.getUser().getEmail() + " added");
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
