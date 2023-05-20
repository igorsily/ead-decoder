package com.br.igorsily.ead.course.service;

import com.br.igorsily.ead.course.dtos.SubscriptionDTO;
import com.br.igorsily.ead.course.entity.CourseUser;

import java.util.UUID;


public interface CourseUserService {
    boolean existsByCourseAndUserId(UUID courseId, UUID userId);

    CourseUser save(CourseUser courseUserModel);

    CourseUser saveAndSendSubscriptionUserInCourse(UUID courseId, SubscriptionDTO subscriptionDTO);

    void deleteCourseUserByUser(UUID userId);
}
