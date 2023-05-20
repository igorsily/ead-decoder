package com.br.igorsily.ead.course.dtos;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record SubscriptionDTO(
        @NotNull
        UUID userId
) {
}
