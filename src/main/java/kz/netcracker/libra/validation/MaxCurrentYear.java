package kz.netcracker.libra.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = MaxCurrentYearValidator.class)
@Documented
public @interface MaxCurrentYear {
    String message() default "Year must not be greater than current year";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}