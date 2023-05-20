package com.br.igorsily.ead.auth.controller;

import com.br.igorsily.ead.auth.dtos.UserDTO;
import com.br.igorsily.ead.auth.service.UserService;
import com.br.igorsily.ead.auth.specification.SpecificationTemplate;
import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Log4j2
@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Tag(name = "User", description = "User API")
public class UserController {

    private final UserService userService;


    public ResponseEntity<Page<UserDTO>> getAllUsers(
            SpecificationTemplate.UserSpec spec,
            @PageableDefault(page = 0, size = 10, sort = "username", direction = Sort.Direction.ASC) Pageable pageable
    ) {

        Page<UserDTO> userDTOS = userService.findAll(spec, pageable);

        if (!userDTOS.isEmpty()) {
            userDTOS.toList().forEach(userDTO -> userDTO.add(linkTo(methodOn(UserController.class).getById(userDTO.getId())).withSelfRel()));
        }

        return ResponseEntity.ok(userDTOS);
    }


    public ResponseEntity<UserDTO> getById(@PathVariable(value = "userId") UUID userId) {
        UserDTO user = userService.findUserById(userId);
        return ResponseEntity.status(HttpStatus.OK).body(user);
    }


    public void delete(@PathVariable("userId") UUID userId) {
        log.debug("DELETE deleteUser userId received {} ", userId);
        this.userService.delete(userId);
    }


    public ResponseEntity<UserDTO> updateUser(@PathVariable("userId") UUID id, @RequestBody @Validated(UserDTO.UserDTOView.UpdateUser.class) UserDTO userDTO) {
        log.debug("PUT updateUser userDto received {} ", userDTO.toString());
        var user = this.userService.update(id, userDTO);
        return ResponseEntity.status(HttpStatus.OK).body(user);

    }


    public Object updatePassword(@PathVariable(value = "userId") UUID userId,
                                 @RequestBody @Validated(UserDTO.UserDTOView.UpdatePasswordUser.class)
                                 @JsonView(UserDTO.UserDTOView.UpdatePasswordUser.class) UserDTO userDTO) {
        var user = this.userService.updatePassword(userId, userDTO);

        return ResponseEntity.status(HttpStatus.OK).body(user);
    }


}

