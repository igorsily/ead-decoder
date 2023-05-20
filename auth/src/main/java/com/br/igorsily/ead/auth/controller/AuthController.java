package com.br.igorsily.ead.auth.controller;


import com.br.igorsily.ead.auth.dtos.UserDTO;
import com.br.igorsily.ead.auth.exception.ExceptionResponse;
import com.br.igorsily.ead.auth.service.UserService;
import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@Log4j2
@RestController
@RequestMapping("/api/v1/auths")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Tag(name = "Auth", description = "Auth API")
public class AuthController {

    private final UserService userService;


    @Operation(summary = "Cria um Usuario", description = "Cria um Usuario", tags = {"Auth"},
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "User created",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = UserDTO.class),
                                    examples = @ExampleObject(
                                            value = """
                                                    {
                                                        "id": "1a2b3c4d-5e6f-7g8h-9i10-j1k2l3m4n5o6",
                                                        "username": "johndoe",
                                                        "email": "johndoe@example.com",
                                                        "fullName": "John Doe",
                                                        "status": "ACTIVE",
                                                        "type": "STUDENT",
                                                        "phoneNumber": "555-5555",
                                                        "document": "1234567890",
                                                        "imageUrl": "http://example.com/profile.jpg"
                                                    }"""
                                    )
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
    @PostMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseStatus(HttpStatus.CREATED)
    @JsonView(UserDTO.UserDTOView.ResponseUser.class)
    public ResponseEntity<UserDTO> save(@RequestBody
                                        @Validated(UserDTO.UserDTOView.CreateUser.class)
                                        @JsonView(UserDTO.UserDTOView.CreateUser.class) UserDTO userDTO) {
        log.debug("POST registerUser userDto received {} ", userDTO.toString());

        var user = userService.saveUser(userDTO);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri()
                .path("/{id}")
                .buildAndExpand(user.getId())
                .toUri();


        return ResponseEntity.created(uri).body(user);
    }
}
