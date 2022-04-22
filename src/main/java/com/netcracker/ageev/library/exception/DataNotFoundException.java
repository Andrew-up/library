package com.netcracker.ageev.library.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DataNotFoundException extends RuntimeException {

    private static final Logger LOG = LoggerFactory.getLogger(DataNotFoundException.class);

    public DataNotFoundException(String message) {
        super(message);
        LOG.error(message);
    }

    public DataNotFoundException() {
        super();
    }
}
