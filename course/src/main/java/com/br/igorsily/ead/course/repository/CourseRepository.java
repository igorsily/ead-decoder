package com.br.igorsily.ead.course.repository;

import com.br.igorsily.ead.course.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CourseRepository extends JpaRepository<Course, UUID>, JpaSpecificationExecutor<Course> {
}