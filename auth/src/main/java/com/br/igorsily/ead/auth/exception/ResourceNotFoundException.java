package com.br.igorsily.ead.auth.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.Serial;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;

    public ResourceNotFoundException(String exception) {
        super(exception);
    }

    public ResourceNotFoundException(String exception, Throwable cause) {
        super(exception, cause);
    }
}
