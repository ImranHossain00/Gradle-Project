package com.techCottage.utils;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

import java.util.Map;
import java.util.stream.Collectors;

// This is a singleton design pattern
public class ValidationUtil {

    private static final ValidationUtil INSTANSE = new ValidationUtil();
    private final Validator validator;


    private ValidationUtil() {
        this.validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    public static ValidationUtil getInstance() {
        return INSTANSE;
    }

    public <T> Map<String, String> validate(T obj) {
        var violations = validator.validate(obj);

        return violations
                .stream()
                .collect(Collectors.toMap(
                        violation ->
                                violation.getPropertyPath().toString(),
                        ConstraintViolation::getMessage,
                        (eMsg1, eMsg2) -> eMsg1 + " <br/> " + eMsg2
                ));
    }
}
