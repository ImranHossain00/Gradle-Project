package com.imran.eshoppers.util;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Map;
import java.util.stream.Collectors;

public class ValidationUtil {

    private final Validator validator;
    private static final ValidationUtil INSTANCE
            = new ValidationUtil();

    private ValidationUtil() {
        var validatorFactory
                = Validation.buildDefaultValidatorFactory();
        this.validator
                = validatorFactory.getValidator();
    }

    public static ValidationUtil getInstance() {
        return INSTANCE;
    }

    public <T>Map<String , String > validate(T object) {
        var violations = validator.validate(object);

        return violations.stream().collect(Collectors.toMap(
                violation -> violation.
                        getPropertyPath().
                        toString(),
                ConstraintViolation::getMessage,
                (errMsg1, errMsg2) -> errMsg1 + " <br/> " + errMsg2
        ));
    }
}
