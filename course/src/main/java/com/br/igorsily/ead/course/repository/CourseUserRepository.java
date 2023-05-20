package com.br.igorsily.ead.course.repository;

import com.br.igorsily.ead.course.entity.CourseUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CourseUserRepository extends JpaRepository<CourseUser, UUID> {

    @Query(value = "SELECT CASE WHEN COUNT(cu) > 0 THEN TRUE ELSE FALSE END FROM CourseUser cu WHERE cu.userId = :userId AND cu.course.id = :courseId")
    boolean existsByUserAndCourseId(UUID userId, UUID courseId);

    @Query(value = "SELECT c FROM Course c where c.id = :courseId")
    List<CourseUser> findAllCourseUserIntoCourse(@Param("courseId") UUID courseId);

    @Query(value = "SELECT CASE WHEN COUNT(cu) > 0 THEN TRUE ELSE FALSE END FROM CourseUser cu WHERE cu.userId = :userId")
    boolean existsByUser(UUID userId);

    @Modifying
    @Query(value = "DELETE FROM CourseUser cu WHERE cu.userId = :userId")
    void deleteAllByUserId(UUID userId);
}