package com.br.igorsily.ead.auth.service;

import com.br.igorsily.ead.auth.dtos.UserCourseDTO;
import com.br.igorsily.ead.auth.entity.UserCourse;

import java.util.UUID;

public interface UserCourseService {

    boolean existsByUserAndCourseId(UUID userId, UUID courseId);

    UserCourse save(UserCourse userCourseModel);

    UserCourse saveUserInCourse(UUID userId, UserCourseDTO userCourseDTO);

    void deleteUserCourseByCourse(UUID courseId);
}
