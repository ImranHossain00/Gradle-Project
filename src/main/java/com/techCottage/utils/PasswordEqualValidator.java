package com.techCottage.utils;

import com.techCottage.annotations.PasswordEqual;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.lang.reflect.Field;
import java.util.Objects;

public class PasswordEqualValidator
        implements ConstraintValidator<PasswordEqual, Object> {
    private String firstField;
    private String secondField;
    private String message;
    @Override
    public void initialize(PasswordEqual constraint) {
        firstField = constraint.first();
        secondField = constraint.second();
        message = constraint.message();
    }

    @Override
    public boolean isValid(Object value,
                           ConstraintValidatorContext context) {
        boolean valid = true;
        try {
            final Object firstObj
                    = getValue(value, firstField);
            final Object secondObj
                    = getValue(value, secondField);
            valid = Objects.equals(firstObj, secondObj);
        } catch (final Exception ignore) {
            // ignore
        }

        if (!valid) {
            context.buildConstraintViolationWithTemplate(message)
                    .addPropertyNode(firstField)
                    .addConstraintViolation()
                    .disableDefaultConstraintViolation();
        }
        return valid;
    }

    private Object getValue(Object object,
                            String firstName)
            throws NoSuchFieldException, IllegalAccessException{
        Field field
                = object.getClass().getDeclaredField(firstName);
        field.setAccessible(true);

        return field.get(object);
    }
}
