package com.br.igorsily.ead.course.service;

import com.br.igorsily.ead.course.dtos.LessonDTO;

import java.util.UUID;

public interface LessonService {

    LessonDTO saveLesson(UUID moduleId, LessonDTO lessonDTO);

    void deleteLesson(UUID moduleId, UUID lessonId);
}
