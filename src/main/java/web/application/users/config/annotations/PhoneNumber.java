package web.application.users.config.annotations;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import jakarta.validation.constraints.Pattern;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = {})
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Pattern(regexp = "(\\+7|8)?"
        + "([ (]?\\d{3}[) ]?)?"
        + "[(-]?\\d{3}[-) ]?"
        + "\\d{2}[- ]?\\d{2}",
        message = "Invalid phone number! Use format: +7(9**)-***-**-**")
public @interface PhoneNumber {
    String message() default "";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
