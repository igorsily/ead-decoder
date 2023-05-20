package com.br.igorsily.ead.auth.specification;

import com.br.igorsily.ead.auth.entity.User;
import com.br.igorsily.ead.auth.entity.UserCourse;
import jakarta.persistence.criteria.Join;
import net.kaczmarzyk.spring.data.jpa.domain.Equal;
import net.kaczmarzyk.spring.data.jpa.domain.Like;
import net.kaczmarzyk.spring.data.jpa.domain.LikeIgnoreCase;
import net.kaczmarzyk.spring.data.jpa.web.annotation.And;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;
import org.springframework.data.jpa.domain.Specification;

import java.util.UUID;

public class SpecificationTemplate {
    @And({
            @Spec(path = "type", spec = Equal.class),
            @Spec(path = "status", spec = Equal.class),
            @Spec(path = "email", spec = Like.class),
            @Spec(path = "username", spec = Like.class),
            @Spec(path = "document", spec = Like.class),
            @Spec(path = "fullName", spec = LikeIgnoreCase.class)
    })
    public interface UserSpec extends Specification<User> {
    }

    public static Specification<User> userCourseId(final UUID courseId){
          return (root, query, cb) -> {
            query.distinct(true);
            Join<User, UserCourse> userProd = root.join("usersCourses");
            return cb.equal(userProd.get("courseId"), courseId);
        };
    }
}
