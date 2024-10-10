package com.ruoyi.common.xss;

import com.ruoyi.common.util.StringUtil;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class XssValidator implements ConstraintValidator<Xss, String> {

    private static final String HTML_PATTERN = "<(\\S*?)[^>]*>.*?|<.*? />";

    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        // value 是空的或者只包含空白字符（通过 StringUtil.isBlank 方法检查），则认为它是有效的，并返回 true
        if (StringUtil.isBlank(value)) {
            return true;
        }
        return !containsHtml(value);
    }

    /**
     * 是否包含HTML标签
     * 正则表达式来检查字符串是否匹配HTML模式
     * @param value
     * @return
     */
    public static boolean containsHtml(String value) {
        // 编译一个正则表达式模式，该模式应该定义在类的某个地方，并且用于匹配HTML标签
        Pattern pattern = Pattern.compile(HTML_PATTERN);
        // 创建一个 Matcher 对象，用于对给定的字符串 value 进行模式匹配
        Matcher matcher = pattern.matcher(value);
        // 检查整个字符串是否与正则表达式模式匹配
        return matcher.matches();
    }
}
