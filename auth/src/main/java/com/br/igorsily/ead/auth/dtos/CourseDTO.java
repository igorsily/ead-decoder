package com.br.igorsily.ead.auth.dtos;

import com.br.igorsily.ead.auth.enums.CourseLevel;
import com.br.igorsily.ead.auth.enums.CourseStatus;

import java.util.UUID;

public record CourseDTO(
        UUID courseId,
        String name,
        String description,
        String imageUrl,
        CourseStatus courseStatus,
        UUID instructor,
        CourseLevel courseLevel
) {
}
