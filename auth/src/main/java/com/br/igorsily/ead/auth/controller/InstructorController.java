package com.br.igorsily.ead.auth.controller;

import com.br.igorsily.ead.auth.dtos.InstructorDTO;
import com.br.igorsily.ead.auth.dtos.UserDTO;
import com.br.igorsily.ead.auth.exception.ExceptionResponse;
import com.br.igorsily.ead.auth.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Log4j2
@RestController
@RequestMapping("/api/v1/instructors")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Tag(name = "Instructor", description = "Instructor API")
public class InstructorController {

    private final UserService userService;

    @Operation(summary = "Atualiza usuario para ser Instrutor", description = "Atualiza usuario para ser Instrutor", tags = {"Instructor"},
            responses = {
                    @ApiResponse(
                            responseCode = "204",
                            description = "Usuario Atualizado com sucesso",
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
                            description = " not found",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ExceptionResponse.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "409",
                            description = "Conflict",
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
    @PostMapping("/subscription")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<?> saveSubscriptionInstructor(@RequestBody @Valid InstructorDTO instructorDTO) {

        userService.saveInstructor(instructorDTO);
        return ResponseEntity.noContent().build();
    }


}
