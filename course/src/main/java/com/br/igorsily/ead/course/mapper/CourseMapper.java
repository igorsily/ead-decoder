package com.br.igorsily.ead.course.mapper;

import com.br.igorsily.ead.course.dtos.CourseDTO;
import com.br.igorsily.ead.course.entity.Course;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring")
public interface CourseMapper extends GenericMapper<Course, CourseDTO> {
}
