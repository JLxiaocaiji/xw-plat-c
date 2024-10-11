package com.ruoyi.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Excel注解集
 */
@Target(ElementType.FIELD)  // 元注解指定了 Excels 注解可以应用的目标元素类型。在这里，它只能用来注解字段
@Retention(RetentionPolicy.RUNTIME) // 指定了 Excels 注解的保留策略,被保留在运行时，因此可以通过反射来读取
public @interface Excels {
    // Excels 注解接受一个 Excel 类型的数组作为其值
    public Excel[] value();
}
