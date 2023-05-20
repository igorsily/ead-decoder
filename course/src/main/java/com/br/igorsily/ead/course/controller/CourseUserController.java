package com.br.igorsily.ead.course.controller;

import com.br.igorsily.ead.course.clients.AuthUserClient;
import com.br.igorsily.ead.course.dtos.SubscriptionDTO;
import com.br.igorsily.ead.course.dtos.UserDTO;
import com.br.igorsily.ead.course.service.CourseUserService;
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
@Tag(name = "Courses", description = "Courses API")
public class CourseUserController {

    private final AuthUserClient authUserClient;

    private final CourseUserService courseUserService;

    @GetMapping("/courses/{courseId}/users")
    public ResponseEntity<Page<UserDTO>> getAllUsersByCourse(@PageableDefault(page = 0, size = 10, sort = "userId", direction = Sort.Direction.ASC) Pageable pageable,
                                                             @PathVariable(value = "courseId") UUID courseId) {

        Page<UserDTO> users = authUserClient.getAllUsersByCourse(courseId, pageable);

        return ResponseEntity.status(HttpStatus.OK).body(users);
    }

    @PostMapping("/courses/{courseId}/users/subscription")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Object> saveSubscriptionUserInCourse(@PathVariable(value = "courseId") UUID courseId,
                                                               @RequestBody @Valid SubscriptionDTO subscriptionDTO) {

        var courseUser = courseUserService.saveAndSendSubscriptionUserInCourse(courseId, subscriptionDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(courseUser);
    }

    @DeleteMapping("/courses/users/{userId}")
    public ResponseEntity<Void> deleteCourseUserByUser(@PathVariable(value = "userId") UUID userId) {

        courseUserService.deleteCourseUserByUser(userId);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
