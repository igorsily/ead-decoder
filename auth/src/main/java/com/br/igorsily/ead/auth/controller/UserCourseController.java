package com.br.igorsily.ead.auth.controller;


import com.br.igorsily.ead.auth.clients.CourseClient;
import com.br.igorsily.ead.auth.dtos.CourseDTO;
import com.br.igorsily.ead.auth.dtos.UserCourseDTO;
import com.br.igorsily.ead.auth.entity.UserCourse;
import com.br.igorsily.ead.auth.exception.ExceptionResponse;
import com.br.igorsily.ead.auth.service.UserCourseService;
import io.swagger.v3.oas.annotations.Operation;
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
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Log4j2
@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Tag(name = "UserCourse", description = "UserCourse API")
public class UserCourseController {


    private final CourseClient courseClient;


    private final UserCourseService userCourseService;

    @Operation(summary = "Busca todos os curso do usuario ", description = "Busca todos os curso do usuario paginados", tags = {"UserCourse"},
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Retorna os curso do usuario",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = Page.class)
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
                            description = "Usuario nao encontrado",
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
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/users/{userId}/courses")
    public ResponseEntity<Page<CourseDTO>> getAllCoursesByUser(@PageableDefault(page = 0, size = 10, sort = "courseId", direction = Sort.Direction.ASC) Pageable pageable,
                                                               @PathVariable(value = "userId") UUID userId) {
        return ResponseEntity.status(HttpStatus.OK).body(courseClient.getAllCoursesByUser(userId, pageable));
    }


    @Operation(summary = "Vincula um usuario a um curso", description = "Vincula um usuario a um curso", tags = {"UserCourse"},
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Usuario vinculado ao curso com sucesso",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = UserCourse.class)
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
                            description = "Usuario nao encontrado",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ExceptionResponse.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "409",
                            description = "Subscription already exists!",
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
    @PostMapping("/users/{userId}/courses/subscription")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<UserCourse> saveSubscriptionUserInCourse(@PathVariable(value = "userId") UUID userId,
                                                                   @RequestBody @Valid UserCourseDTO userCourseDTO) {

        var userCourse = userCourseService.saveUserInCourse(userId, userCourseDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(userCourse);
    }


    @Operation(summary = "Delete a ", description = "Delete a ", tags = {""},
            responses = {
                    @ApiResponse(
                            responseCode = "204",
                            description = "Delete ",
                            content = @Content()
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
                            description = "Person not found",
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
    @DeleteMapping("/users/courses/{courseId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> delete(@PathVariable("courseId") UUID courseId) {
        this.userCourseService.deleteUserCourseByCourse(courseId);

       return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
