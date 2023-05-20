package com.br.igorsily.ead.auth.validation;

import jakarta.validation.Constraint;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = UsernameConstraintImpl.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface UsernameConstraint {
    String message() default "Invalid username";
    Class[] groups() default {};
    Class[] payload() default {};
}
