package com.br.igorsily.ead.course.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.Serial;

@ResponseStatus(HttpStatus.CONFLICT)
public class ConflictException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;

    public ConflictException(String exception) {
        super(exception);
    }

    public ConflictException(String exception, Throwable cause) {
        super(exception, cause);
    }
}
