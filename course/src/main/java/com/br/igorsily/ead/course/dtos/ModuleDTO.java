package com.br.igorsily.ead.course.dtos;

import jakarta.validation.constraints.NotBlank;

import java.util.UUID;

public record ModuleDTO(

        UUID id,

        @NotBlank
        String title,
        @NotBlank
        String description
) {
}
