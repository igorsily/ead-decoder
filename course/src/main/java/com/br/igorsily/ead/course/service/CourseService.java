package com.br.igorsily.ead.course.service;

import com.br.igorsily.ead.course.dtos.CourseDTO;
import com.br.igorsily.ead.course.entity.Course;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.UUID;

public interface CourseService {

    void deleteCourse(UUID id);

    CourseDTO save(CourseDTO courseDTO);

    Page<CourseDTO> findAll(Specification<Course> specCourse, Pageable pageable);

   CourseDTO findById(UUID courseId);

    void delete(UUID courseId);

    CourseDTO update(UUID courseId, CourseDTO courseDTO);
}
