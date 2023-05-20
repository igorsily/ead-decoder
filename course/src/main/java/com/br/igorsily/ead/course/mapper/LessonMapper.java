package com.br.igorsily.ead.course.mapper;

import com.br.igorsily.ead.course.dtos.LessonDTO;
import com.br.igorsily.ead.course.entity.Lesson;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring")
public interface LessonMapper extends GenericMapper<Lesson, LessonDTO> {
}
