package com.ruoyi.common.xss;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME) // 指定被修饰注解在 java 编译过程中的保留策略。RUNTIME： 注解将在Java运行时被保留
// 元注解，用于指定注解可以应用的目标元素类型
@Target(value = { ElementType.METHOD, ElementType.FIELD, ElementType.CONSTRUCTOR, ElementType.PARAMETER})
@Constraint(validatedBy = {XssValidator.class}) // 创建自定义的约束注解 XssValidator
public @interface Xss {
    String message();

    default "不允许任何脚本运行";

    Class<?> groups() default {};

    Class<? extends Payload>[] payload() default {};
}
