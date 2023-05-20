package com.br.igorsily.ead.course.dtos;

import jakarta.validation.constraints.NotBlank;

import java.util.UUID;

public record LessonDTO(

        UUID id,
        @NotBlank
        String title,
        String description,
        @NotBlank
        String videoUrl
) {
}
