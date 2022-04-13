package cn.changeforyou.web.cloud.devUtilApi.common.validator;

import cn.changeforyou.utils.string.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/***
 * @author zhyu
 * @date 2020/12/08
 */
public class InStringsValidator implements ConstraintValidator<InStrings, String> {

    private String[] mustIn;

    @Override
    public void initialize(InStrings constraintAnnotation) {
        mustIn = constraintAnnotation.in();
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        if(StringUtils.isEmpty(s)) {
            return false;
        }
       return StringUtils.in(s, mustIn);
    }
}
