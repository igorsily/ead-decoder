package com.br.igorsily.ead.course.dtos;

import java.util.UUID;

public record CourseUserDTO(
        UUID courseId,
        UUID userId

) {
}
