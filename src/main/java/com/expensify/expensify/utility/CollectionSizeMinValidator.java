package com.expensify.expensify.utility;

import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.expensify.expensify.utility.validator.CollectionMinSize;

public class CollectionSizeMinValidator implements ConstraintValidator<CollectionMinSize, List> {

	int minSize = 0;

	@Override
	public void initialize(CollectionMinSize constraintAnnotation) {
		// TODO Auto-generated method stub
		ConstraintValidator.super.initialize(constraintAnnotation);
		this.minSize = constraintAnnotation.min();
	}

	@Override
	public boolean isValid(List value, ConstraintValidatorContext context) {
		if (value == null) {
			return false;
		}
		System.out.print(value.size());
		if (value.size() < minSize) {
			return false;
		}
		return true;
	}

}
