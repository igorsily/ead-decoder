package com.br.igorsily.ead.course.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class CustomizedResponseExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<ExceptionResponse> handleAllException(Exception e, WebRequest request) {
        ExceptionResponse exceptionResponse = ExceptionResponse.builder()
                .details(request.getDescription(false))
                .message(e.getMessage())
                .build();
        return new ResponseEntity<>(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ConflictException.class)
    public final ResponseEntity<ExceptionResponse> handleConflictException(Exception e, WebRequest request) {
        ExceptionResponse exceptionResponse = ExceptionResponse.builder()
                .details(request.getDescription(false))
                .message(e.getMessage())
                .build();
        return new ResponseEntity<>(exceptionResponse, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public final ResponseEntity<ExceptionResponse> handleResourceNotFoundException(Exception e, WebRequest request) {
        ExceptionResponse exceptionResponse = ExceptionResponse.builder()
                .details(request.getDescription(false))
                .message(e.getMessage())
                .build();
        return new ResponseEntity<>(exceptionResponse, HttpStatus.NOT_FOUND);
    }

//    @ExceptionHandler(BadRequestException.class)
//    public final ResponseEntity<ExceptionResponse> handleBadRequestException(Exception e, WebRequest request) {
//        ExceptionResponse exceptionResponse = ExceptionResponse.builder()
//                .details(request.getDescription(false))
//                .message(e.getMessage())
//                .build();
//        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
//    }

//    @ExceptionHandler(InvalidJwtAuthenticationException.class)
//    public final ResponseEntity<ExceptionResponse> handleInvalidJwtAuthenticationException(Exception e, WebRequest request) {
//        ExceptionResponse exceptionResponse = ExceptionResponse.builder()
//                .details(request.getDescription(false))
//                .message(e.getMessage())
//                .build();
//        return new ResponseEntity<>(exceptionResponse, HttpStatus.FORBIDDEN);
//    }


//    @ExceptionHandler(BadCredentialsException.class)
//    @ResponseStatus(HttpStatus.UNAUTHORIZED)
//    public final ResponseEntity<ExceptionResponse> handleBadCredentialsException(Exception e, WebRequest request) {
//        ExceptionResponse exceptionResponse = ExceptionResponse.builder()
//                .details(request.getDescription(false))
//                .message(e.getMessage())
//                .build();
//        return new ResponseEntity<>(exceptionResponse, HttpStatus.UNAUTHORIZED);

//    }

    @Override
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {

        List<ErrorValidation> errorValidationList = new ArrayList<>();

        ex.getBindingResult().getFieldErrors().forEach(error -> {
            errorValidationList.add(ErrorValidation.builder()
                    .field(error.getField())
                    .message(error.getDefaultMessage())
                    .build());
        });

        ExceptionResponse exceptionResponse = ExceptionResponse.builder()
                .details(request.getDescription(false))
                .message("Validation error")
                .errorValidationList(errorValidationList)
                .build();

        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }
}
