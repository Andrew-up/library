package com.netcracker.ageev.library.payload.responce;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNAUTHORIZED)
public class RegistrationException extends RuntimeException {

}

