package com.br.igorsily.ead.auth.service.impl;

import com.br.igorsily.ead.auth.dtos.UserCourseDTO;
import com.br.igorsily.ead.auth.dtos.UserDTO;
import com.br.igorsily.ead.auth.entity.User;
import com.br.igorsily.ead.auth.entity.UserCourse;
import com.br.igorsily.ead.auth.exception.ConflictException;
import com.br.igorsily.ead.auth.exception.ResourceNotFoundException;
import com.br.igorsily.ead.auth.mapper.UserMapper;
import com.br.igorsily.ead.auth.repository.UserCourseRepository;
import com.br.igorsily.ead.auth.service.UserCourseService;
import com.br.igorsily.ead.auth.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static java.lang.String.format;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))

public class UserCourseServiceImpl implements UserCourseService {

    private final UserCourseRepository userCourseRepository;

    private final UserService userService;

    private UserMapper userMapper;

    @Override
    public boolean existsByUserAndCourseId(UUID userId, UUID courseId) {
        return userCourseRepository.existsByUserAndCourseId(userId, courseId);
    }

    @Override
    public UserCourse save(UserCourse userCourseModel) {
        return userCourseRepository.save(userCourseModel);
    }

    @Override
    public UserCourse saveUserInCourse(UUID userId, UserCourseDTO userCourseDTO) {
        UserDTO userDTO = userService.findUserById(userId);

        boolean exist = existsByUserAndCourseId(userId, userCourseDTO.courseId());

        if (exist) throw new ConflictException("Subscription already exists!");

        User user = userMapper.toEntity(userDTO);

        UserCourse userCourse = user.convertToUserCourse(userCourseDTO.courseId());

        return save(userCourse);

    }


    @Transactional
    @Override
    public void deleteUserCourseByCourse(UUID courseId) {

        var existis = userCourseRepository.existsByUserAndCourseId(courseId);

        if (!existis) throw new ResourceNotFoundException(format("Course not exists %s", courseId));

        userCourseRepository.deleteAllByCourseId(courseId);
    }
}
