package com.br.igorsily.ead.auth.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ErrorValidation implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private String field;

    private String message;
}
