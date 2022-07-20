package com.moreno.validators;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

public abstract class ProgramValidator {
    private static ValidatorFactory FACTORY = Validation.buildDefaultValidatorFactory();
    protected static Validator PROGRAMA_VALIDATOR = FACTORY.getValidator();
}
