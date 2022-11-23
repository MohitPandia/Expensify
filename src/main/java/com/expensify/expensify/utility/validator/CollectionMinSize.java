package com.expensify.expensify.utility.validator;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import com.expensify.expensify.utility.CollectionSizeMinValidator;

@Documented
@Constraint(validatedBy = CollectionSizeMinValidator.class)
@Target({ ElementType.FIELD, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
public @interface CollectionMinSize {

	String message() default "Collection Size should be larger than {min}";

	int min() default 0;

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}
