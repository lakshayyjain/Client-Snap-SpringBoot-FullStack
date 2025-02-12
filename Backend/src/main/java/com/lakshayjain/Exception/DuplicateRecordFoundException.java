package com.lakshayjain.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.CONFLICT)
public class DuplicateRecordFoundException extends RuntimeException{
    public DuplicateRecordFoundException(String message) {
        super(message);
    }
}
