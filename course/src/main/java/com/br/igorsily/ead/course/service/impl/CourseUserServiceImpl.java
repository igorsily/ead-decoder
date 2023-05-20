package com.br.igorsily.ead.course.service.impl;

import com.br.igorsily.ead.course.clients.AuthUserClient;
import com.br.igorsily.ead.course.dtos.CourseDTO;
import com.br.igorsily.ead.course.dtos.SubscriptionDTO;
import com.br.igorsily.ead.course.dtos.UserDTO;
import com.br.igorsily.ead.course.entity.CourseUser;
import com.br.igorsily.ead.course.enums.UserStatus;
import com.br.igorsily.ead.course.exception.ConflictException;
import com.br.igorsily.ead.course.exception.ResourceNotFoundException;
import com.br.igorsily.ead.course.mapper.CourseMapper;
import com.br.igorsily.ead.course.repository.CourseUserRepository;
import com.br.igorsily.ead.course.service.CourseService;
import com.br.igorsily.ead.course.service.CourseUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpStatusCodeException;

import java.util.Objects;
import java.util.UUID;

import static java.lang.String.format;

@Log4j2
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CourseUserServiceImpl implements CourseUserService {

    private final CourseUserRepository courseUserRepository;

    private final CourseService courseService;

    private final AuthUserClient authUserClient;

    private final CourseMapper courseMapper;

    @Override
    public boolean existsByCourseAndUserId(UUID courseId, UUID userId) {
        return courseUserRepository.existsByUserAndCourseId(userId, courseId);
    }

    @Override
    public CourseUser save(CourseUser courseUserModel) {
        return null;
    }

    @Override
    public CourseUser saveAndSendSubscriptionUserInCourse(UUID courseId, SubscriptionDTO subscriptionDTO) {

        CourseDTO courseDTO = courseService.findById(courseId);

        var existByCourseAndUser = existsByCourseAndUserId(courseId, subscriptionDTO.userId());

        if (existByCourseAndUser) throw new ConflictException("User already subscribed to this course");


        try {
            ResponseEntity<UserDTO> responseUser = authUserClient.getUserById(subscriptionDTO.userId());

            if (Objects.requireNonNull(responseUser.getBody()).userStatus().equals(UserStatus.BLOCKED)) throw new ConflictException("User is blocked.");

        } catch (HttpStatusCodeException e) {

            log.error("Error to get user by id: " + e.getResponseBodyAsString());

            if (e.getStatusCode().equals(HttpStatus.NOT_FOUND)) throw new ResourceNotFoundException("User not found.");

        }

        CourseUser courseUser = CourseUser
                .builder()
                .course(courseMapper.toEntity(courseDTO))
                .userId(subscriptionDTO.userId())
                .build();

        courseUserRepository.save(courseUser);

        authUserClient.postSubscriptionUserInCourse(courseUser.getCourse().getId(), subscriptionDTO.userId());

        return courseUser;
    }

    @Override
    @Transactional
    public void deleteCourseUserByUser(UUID userId) {
        var existis = courseUserRepository.existsByUser(userId);

        if (!existis) throw new ResourceNotFoundException(format("User not found %s", userId));

         courseUserRepository.deleteAllByUserId(userId);
    }
}
