package com.flutter.alloffootball.common.annotation;

import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DateRange {

    int maxDays();
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
