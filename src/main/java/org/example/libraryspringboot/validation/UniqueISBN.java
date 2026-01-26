package org.example.libraryspringboot.validation;


import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UniqueISBNValidator.class)
public @interface UniqueISBN {

    String message() default "ISBN already exists in the system";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
