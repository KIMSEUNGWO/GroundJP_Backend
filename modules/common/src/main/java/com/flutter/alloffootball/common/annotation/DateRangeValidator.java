package com.flutter.alloffootball.common.annotation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class DateRangeValidator implements ConstraintValidator<DateRange, LocalDateTime> {

    private int maxDays;

    @Override
    public void initialize(DateRange constraintAnnotation) {
        maxDays = constraintAnnotation.maxDays();
    }

    @Override
    public boolean isValid(LocalDateTime date, ConstraintValidatorContext constraintValidatorContext) {
        if (date == null) return true;

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime target = now.plusDays(maxDays);

        return !date.isBefore(target) && !date.isAfter(now);
    }
}
