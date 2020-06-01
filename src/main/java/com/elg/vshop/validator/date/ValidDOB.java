package com.elg.vshop.validator.date;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({FIELD, ANNOTATION_TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy = DOBValidator.class)
@Documented
public @interface ValidDOB {
    String message() default "Vous n'avez pas 18 ans";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
