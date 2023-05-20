package com.br.igorsily.ead.course.service.impl;

import com.br.igorsily.ead.course.dtos.ModuleDTO;
import com.br.igorsily.ead.course.entity.Lesson;
import com.br.igorsily.ead.course.entity.Module;
import com.br.igorsily.ead.course.exception.ResourceNotFoundException;
import com.br.igorsily.ead.course.mapper.ModuleMapper;
import com.br.igorsily.ead.course.repository.LessonRepository;
import com.br.igorsily.ead.course.repository.ModuleRepository;
import com.br.igorsily.ead.course.service.ModuleService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ModuleServiceImpl implements ModuleService {

    private final ModuleRepository moduleRepository;

    private final LessonRepository lessonRepository;

    private final ModuleMapper moduleMapper;

    @Override
    @Transactional
    public void deleteModule(UUID id) {

        ModuleDTO module = this.findModuleById(id);

        List<Lesson> lessons = lessonRepository.findAllLessonsIntoModule(module.id());

        if (!lessons.isEmpty()) {
            lessonRepository.deleteAll(lessons);
        }

        moduleRepository.deleteById(id);
    }

    @Override
    public ModuleDTO findModuleById(UUID id) {

        Optional<Module> moduleOptional = moduleRepository.findById(id);

        if (moduleOptional.isEmpty()) {
            throw new ResourceNotFoundException("Module not found");
        }

        return moduleMapper.toDTO(moduleOptional.get());

    }
}
