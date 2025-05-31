package org.attractor.flightbooking.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.attractor.flightbooking.model.City;

public class ValidCityValidator implements ConstraintValidator<ValidCity, String> {
    @Override
    public boolean isValid(String cityName, ConstraintValidatorContext context) {
        if (cityName == null) {
            return true;
        }
        try {
            City.valueOf(cityName);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
}