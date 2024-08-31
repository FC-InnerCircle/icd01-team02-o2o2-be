package org.example.o2o.config.exception.validator;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class EnumValidator implements ConstraintValidator<EnumValue, Object> {

	private Class<? extends Enum<?>> enumClass;
	private String acceptedValues;

	@Override
	public void initialize(EnumValue constraintAnnotation) {
		this.enumClass = constraintAnnotation.enumClass();
		this.acceptedValues = Arrays.stream(enumClass.getEnumConstants())
			.map(Enum::name)
			.collect(Collectors.joining(", "));
	}

	@Override
	public boolean isValid(Object value, ConstraintValidatorContext context) {
		if (value == null) {
			return false;
		}

		if (value instanceof String) {
			return isValidEnumValue((String)value, context);
		} else if (value instanceof Enum<?>) {
			return isValidEnumValue(((Enum<?>)value).name(), context);
		} else if (value instanceof List<?>) {
			return isValidEnumList((List<?>)value, context);
		} else {
			return false;
		}
	}

	private boolean isValidEnumValue(String value, ConstraintValidatorContext context) {
		boolean isValid = Arrays.stream(enumClass.getEnumConstants())
			.anyMatch(enumValue -> enumValue.name().equals(value));

		if (!isValid) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(
				context.getDefaultConstraintMessageTemplate().replace("{enumValues}", acceptedValues)
			).addConstraintViolation();
		}

		return isValid;
	}

	private boolean isValidEnumList(List<?> valueList, ConstraintValidatorContext context) {
		for (Object value : valueList) {
			if (value instanceof String) {
				if (!isValidEnumValue((String)value, context)) {
					return false;
				}
			} else if (value instanceof Enum<?>) {
				if (!isValidEnumValue(((Enum<?>)value).name(), context)) {
					return false;
				}
			} else {
				return false;
			}
		}
		return true;
	}

}
