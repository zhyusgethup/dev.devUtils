package cn.changeforyou.web.cloud.devUtilApi.common.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @author zhyu
 * @date 2020/12/08
 */
@Documented
@Constraint(validatedBy = {InStringsValidator.class})
@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE })
@Retention(RUNTIME)
@Repeatable(InStrings.List.class)
public @interface InStrings {

    String message() default "字符串不在设定范围内";

    String[] in();

    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

    /**
     * Defines several {@code @NotEmpty} constraints on the same element.
     *
     * @see InStrings
     */
    @Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE })
    @Retention(RUNTIME)
    @Documented
    @interface List {
        InStrings[] value();
    }
}
