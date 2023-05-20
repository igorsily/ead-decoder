package com.br.igorsily.ead.course.controller;

import com.br.igorsily.ead.course.dtos.CourseDTO;
import com.br.igorsily.ead.course.exception.ExceptionResponse;
import com.br.igorsily.ead.course.service.CourseService;
import com.br.igorsily.ead.course.specification.SpecificationTemplate;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Log4j2
@RestController
@RequestMapping("/api/v1/courses")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Tag(name = "Courses", description = "Courses API")
public class CourseController {

    private final CourseService courseService;

    @Operation(summary = "Busca todos os cursos ", description = "Busca todos os cursos paginados", tags = {"Courses"},
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Lista os cursos paginados",
                            content = @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = List.class))
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Bad Request",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ExceptionResponse.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Unauthorized",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ExceptionResponse.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = " not found",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ExceptionResponse.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Internal Server Error",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ExceptionResponse.class)
                            )
                    ),
            })
    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Page<CourseDTO>> getAllCourses(SpecificationTemplate.CourseSpec spec,
                                                         @PageableDefault(page = 0, size = 10, sort = "courseId", direction = Sort.Direction.ASC) Pageable pageable,
                                                         @RequestParam(required = false) UUID userId) {

        var specCourse = userId != null ? SpecificationTemplate.courseUserId(userId).and(spec) : spec;

        Page<CourseDTO> page = courseService.findAll(specCourse, pageable);

        if (!page.isEmpty()) {
            page.toList().forEach(courseDTO -> {
                courseDTO.add(linkTo(methodOn(CourseController.class).getById(courseDTO.getId())).withSelfRel());
            });
        }

        return ResponseEntity.ok(page);
    }

    @GetMapping("/{courseId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<CourseDTO> getById(@PathVariable("courseId") UUID courseId) {
        var course = courseService.findById(courseId);
        return ResponseEntity.ok(course);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<CourseDTO> save(@RequestBody @Validated CourseDTO courseDTO) {

        CourseDTO course = courseService.save(courseDTO);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri()
                .path("/{id}")
                .buildAndExpand(course.getId())
                .toUri();

        return ResponseEntity.created(uri).body(course);
    }

    @PutMapping("/{courseId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<CourseDTO> update(@PathVariable("courseId") UUID courseId, @RequestBody @Valid CourseDTO courseDTO) {
        var course = courseService.update(courseId, courseDTO);

        return ResponseEntity.ok(course);
    }

    @DeleteMapping("/{courseId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<?> delete(@PathVariable("courseId") UUID courseId) {
        courseService.delete(courseId);

        return ResponseEntity.noContent().build();
    }
}
