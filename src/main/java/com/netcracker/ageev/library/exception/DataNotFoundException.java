package com.netcracker.ageev.library.exception;

public class DataNotFoundException  extends  RuntimeException{

    public DataNotFoundException(String message) {
      super(message);
    }

    public DataNotFoundException() {
        super();
    }
}
