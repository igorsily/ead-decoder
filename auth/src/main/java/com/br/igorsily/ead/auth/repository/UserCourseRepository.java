package com.br.igorsily.ead.auth.repository;

import com.br.igorsily.ead.auth.entity.UserCourse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface UserCourseRepository extends JpaRepository<UserCourse, UUID> {

    @Query(value = "SELECT CASE WHEN COUNT(uc) > 0 THEN TRUE ELSE FALSE END FROM UserCourse uc WHERE uc.user.id = :userId AND uc.courseId = :courseId")
    boolean existsByUserAndCourseId(UUID userId, UUID courseId);

    @Query(value = "SELECT CASE WHEN COUNT(uc) > 0 THEN TRUE ELSE FALSE END FROM UserCourse uc WHERE uc.courseId = :courseId")
    boolean existsByUserAndCourseId(UUID courseId);

    @Query(value = "select uc from UserCourse uc where uc.user.id = :userId")
    List<UserCourse> findAllUserCourseIntoUser(@Param("userId") UUID userId);


    @Modifying
    @Query(value = "DELETE FROM UserCourse uc WHERE  uc.courseId = :courseId")
    void deleteAllByCourseId(UUID courseId);
}