package com.br.igorsily.ead.auth.dtos;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record InstructorDTO(
        @NotNull
        UUID userId
) {
}
