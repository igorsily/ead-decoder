package com.br.igorsily.ead.course.dtos;

import com.br.igorsily.ead.course.enums.CourseLevel;
import com.br.igorsily.ead.course.enums.CourseStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.hateoas.RepresentationModel;

import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CourseDTO extends RepresentationModel<CourseDTO> implements Serializable {

    private UUID id;

    @NotBlank
    private String name;

    @NotBlank
    private String description;

    private String imageUrl;

    @NotNull
    private CourseStatus status;

    @NotNull
    private CourseLevel level;

    @NotNull
    private UUID instructor;
}
