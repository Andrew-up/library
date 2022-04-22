package com.netcracker.ageev.library.exception;

import com.netcracker.ageev.library.service.token.RefreshTokenService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


@ResponseStatus(HttpStatus.FORBIDDEN)
public class TokenRefreshException extends RuntimeException {
    private static final Logger LOG = LoggerFactory.getLogger(TokenRefreshException.class);

    private static final long serialVersionUID = 1L;

    public TokenRefreshException(String token, String message) {
        super(String.format("Failed for [%s]: %s", token, message));
        LOG.error("Failed for [%s]: %s "+ token +" "+ message);
    }
}
