package com.br.igorsily.ead.course.service.impl;

import com.br.igorsily.ead.course.dtos.CourseDTO;
import com.br.igorsily.ead.course.entity.Course;
import com.br.igorsily.ead.course.exception.ResourceNotFoundException;
import com.br.igorsily.ead.course.mapper.CourseMapper;
import com.br.igorsily.ead.course.repository.CourseRepository;
import com.br.igorsily.ead.course.repository.LessonRepository;
import com.br.igorsily.ead.course.repository.ModuleRepository;
import com.br.igorsily.ead.course.service.CourseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;


@Log4j2
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;

    private final ModuleRepository moduleRepository;

    private final LessonRepository lessonRepository;

    private final CourseMapper courseMapper;

    @Override
    @Transactional
    public void deleteCourse(UUID id) {

        Course course = this.findCourseById(id);
//
//        List<Module> modules = moduleRepository.findAllByCourseId(course.getId());
//
//        if (!modules.isEmpty()) {
//            for (Module module : modules) {
//                List<Lesson> lessons = lessonRepository.findAllByModuleId(module.getId());
//                if (!lessons.isEmpty()) {
//                    lessonRepository.deleteAll(lessons);
//                }
//                moduleRepository.delete(module);
//            }
//        }

        courseRepository.delete(course);

    }

    @Override
    public CourseDTO save(CourseDTO courseDTO) {

        log.debug("POST saveCourse courseDTO received {} ", courseDTO.toString());

        Course course = courseMapper.toEntity(courseDTO);

        courseRepository.save(course);

        log.debug("POST saveCourse courseId saved {} ", course.getId());
        log.info("Course saved successfully courseId {} ", course.getId());

        return courseMapper.toDTO(course);

    }

    @Override
    public Page<CourseDTO> findAll(Specification<Course> specCourse, Pageable pageable) {
        Page<Course> users = this.courseRepository.findAll(specCourse, pageable);

        return users.map(courseMapper::toDTO);
    }

    @Override
    public CourseDTO findById(UUID courseId) {

        Optional<Course> courseOptional = courseRepository.findById(courseId);

        if (courseOptional.isEmpty()) throw new ResourceNotFoundException("Course not found");

        return courseMapper.toDTO(courseOptional.get());
    }

    @Override
    public void delete(UUID courseId) {
        log.debug("DELETE deleteCourse courseId received {} ", courseId);

        findById(courseId);

        courseRepository.deleteById(courseId);

        log.debug("DELETE deleteCourse courseId deleted {} ", courseId);
        log.info("Course deleted successfully courseId {} ", courseId);

    }

    @Override
    public CourseDTO update(UUID courseId, CourseDTO courseDTO) {

        log.debug("PUT updateCourse courseDto received {} ", courseDTO.toString());

        findById(courseId);

        Course course = courseMapper.toEntity(courseDTO);
        course.setId(courseId);

        courseRepository.save(course);

        log.debug("PUT updateCourse courseId saved {} ", courseId);
        log.info("Course updated successfully courseId {} ", courseId);

        return courseMapper.toDTO(course);
    }

    public Course findCourseById(UUID id) {

        Optional<Course> courseOptional = courseRepository.findById(id);

        return courseOptional.orElseThrow(() -> new RuntimeException("Course not found"));

    }
}
