package com.br.igorsily.ead.course.specification;


import com.br.igorsily.ead.course.entity.Course;
import com.br.igorsily.ead.course.entity.CourseUser;
import com.br.igorsily.ead.course.entity.Lesson;
import com.br.igorsily.ead.course.entity.Module;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Root;
import net.kaczmarzyk.spring.data.jpa.domain.Equal;
import net.kaczmarzyk.spring.data.jpa.domain.LikeIgnoreCase;
import net.kaczmarzyk.spring.data.jpa.web.annotation.And;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;
import org.springframework.data.jpa.domain.Specification;

import java.util.Collection;
import java.util.UUID;

public class SpecificationTemplate {
    @And({
            @Spec(path = "level", spec = Equal.class),
            @Spec(path = "status", spec = Equal.class),
            @Spec(path = "name", spec = LikeIgnoreCase.class),
    })
    public interface CourseSpec extends Specification<Course> {
    }

    @Spec(path = "title", spec = LikeIgnoreCase.class)
    public interface ModuleSpec extends Specification<Module> {
    }

    @Spec(path = "title", spec = LikeIgnoreCase.class)
    public interface LessonSpec extends Specification<Lesson> {
    }


    public static Specification<Module> moduleCourseId(final UUID courseId) {
        return (root, query, cb) -> {
            query.distinct(true);
            Root<Course> course = query.from(Course.class);
            Expression<Collection<Module>> coursesModules = course.get("modules");
            return cb.and(cb.equal(course.get("courseId"), courseId), cb.isMember(root, coursesModules));
        };
    }

      public static Specification<Lesson> lessonModuleId(final UUID moduleId) {
        return (root, query, cb) -> {
            query.distinct(true);
            Root<Module> module = query.from(Module.class);
            Expression<Collection<Lesson>> moduleLessons = module.get("lessons");
            return cb.and(cb.equal(module.get("moduleId"), moduleId), cb.isMember(root, moduleLessons));
        };
    }

    public static Specification<Course> courseUserId(final UUID userId) {
        return (root, query, cb) -> {
            query.distinct(true);
            Join<Course, CourseUser> courseProd = root.join("coursesUsers");
            return cb.equal(courseProd.get("userId"), userId);
        };
    }
}
