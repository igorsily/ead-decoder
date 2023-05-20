package com.br.igorsily.ead.course.controller;

import com.br.igorsily.ead.course.dtos.LessonDTO;
import com.br.igorsily.ead.course.service.LessonService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.UUID;

@Log4j2
@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class LessonController {

    private final LessonService lessonService;

    @PostMapping("/modules/{moduleId}/lessons")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<LessonDTO> saveLesson(@PathVariable(value = "moduleId") UUID moduleId,
                                             @RequestBody @Valid LessonDTO lessonDTO) {

        log.debug("POST saveLesson lessonDto received {} ", lessonDTO.toString());

        LessonDTO lessonDTOSaved = lessonService.saveLesson(moduleId, lessonDTO);

        log.debug("POST saveLesson lessonDto saved {} ", lessonDTOSaved.toString());

        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri()
                .path("/{id}")
                .buildAndExpand(lessonDTOSaved.id())
                .toUri();

        return ResponseEntity.created(uri).body(lessonDTOSaved);
    }

    @DeleteMapping("/modules/{moduleId}/lessons/{lessonId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> deleteLesson(@PathVariable(value = "moduleId") UUID moduleId,
                                               @PathVariable(value = "lessonId") UUID lessonId) {
        log.debug("DELETE deleteLesson lessonId received {} ", lessonId);


        lessonService.deleteLesson(moduleId, lessonId);

        return ResponseEntity.noContent().build();
    }


}