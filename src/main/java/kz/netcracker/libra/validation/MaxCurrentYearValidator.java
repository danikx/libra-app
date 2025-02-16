package kz.netcracker.libra.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Optional;

@Component
public class MaxCurrentYearValidator implements ConstraintValidator<MaxCurrentYear, Integer> {
    @Override
    public boolean isValid(Integer year, ConstraintValidatorContext context) {
        int currentYear = LocalDate.now().getYear();

        boolean isValid = Optional.ofNullable(year)
                .map(y -> y <= currentYear)
                .orElse(false);

        if (!isValid) {
            context.disableDefaultConstraintViolation();
            context
                    .buildConstraintViolationWithTemplate("Year must not be greater than " + currentYear)
                    .addConstraintViolation();
        }

        return isValid;
    }
}