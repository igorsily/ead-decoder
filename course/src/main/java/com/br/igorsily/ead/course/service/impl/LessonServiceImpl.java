package com.br.igorsily.ead.course.service.impl;

import com.br.igorsily.ead.course.dtos.LessonDTO;
import com.br.igorsily.ead.course.dtos.ModuleDTO;
import com.br.igorsily.ead.course.entity.Lesson;
import com.br.igorsily.ead.course.exception.ResourceNotFoundException;
import com.br.igorsily.ead.course.mapper.LessonMapper;
import com.br.igorsily.ead.course.mapper.ModuleMapper;
import com.br.igorsily.ead.course.repository.LessonRepository;
import com.br.igorsily.ead.course.service.LessonService;
import com.br.igorsily.ead.course.service.ModuleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class LessonServiceImpl implements LessonService {

    private final LessonRepository lessonRepository;

    private final ModuleService moduleService;

    private final LessonMapper lessonMapper;

    private final ModuleMapper moduleMapper;

    @Override
    public LessonDTO saveLesson(UUID moduleId, LessonDTO lessonDTO) {

        ModuleDTO moduleDTO = this.moduleService.findModuleById(moduleId);

        Lesson lesson = lessonMapper.toEntity(lessonDTO);

        lesson.setModule(moduleMapper.toEntity(moduleDTO));

        lessonRepository.save(lesson);

        log.debug("POST saveLesson lessonId saved {} ", lesson.getId());
        log.info("Lesson saved successfully lessonId {} ", lesson.getId());

        return lessonMapper.toDTO(lesson);
    }

    @Override
    public void deleteLesson(UUID moduleId, UUID lessonId) {

        this.lessonRepository.findLessonIntoModule(moduleId, lessonId)
                .orElseThrow(() -> new ResourceNotFoundException("Lesson in modulen ot found"));

        this.lessonRepository.deleteById(lessonId);

        log.debug("DELETE deleteLesson lessonId deleted {} ", lessonId);
        log.info("Lesson deleted successfully lessonId {} ", lessonId);
    }


    public LessonDTO findLessonById(UUID id) {

        Optional<Lesson> lessonOptional = lessonRepository.findById(id);

        if (lessonOptional.isEmpty()) {
            throw new ResourceNotFoundException("Lesson not found");
        }

        return lessonMapper.toDTO(lessonOptional.get());

    }
}
