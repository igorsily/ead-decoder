package com.br.igorsily.ead.auth.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ExceptionResponse implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Builder.Default
    private LocalDateTime timestamp = LocalDateTime.now();

    private String message;

    private String details;

    private List<ErrorValidation> errorValidationList;

}

