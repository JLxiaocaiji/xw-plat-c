package com.ruoyi.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME) // 注解保留在编译后的字节码文件中，并在运行时可以通过反射机制读取,可以在运行时处理注解
@Target(ElementType.FIELD)  // 字段声明（包括枚举常量）
public @interface Excel {

    /** 导出到Excel中的名字. **/
    public String name() default "";

    /** 导出类型（0数字 1字符串 2图片） **/
    // cellType() 方法定义了一个名为 cellType 的成员变量，其类型为 ColumnType。这个成员变量默认值为 ColumnType.STRING
    public ColumnType cellType() default ColumnType.STRING;

    /** 读取内容转表达式 (如: 0=男,1=女,2=未知) **/
    public String readConverterExp() default "";


    public enum ColumnType {
        // 分别定义了名为 NUMERIC，STRING，IMAGE 的枚举常量，其关联的整数值分别为 0，1，2
        NUMERIC(0), STRING(1), IMAGE(2);
        // 这是枚举类中的一个私有成员变量，用于存储每个枚举常量关联的整数值。final 关键字表示这个变量的值一旦被设置后就不能更改
        private final int value;
        // 这是枚举类的构造器，它接受一个整型参数 value，并将其赋值给枚举常量的私有成员变量
        ColumnType(int value) {
            this.value = value;
        }
        // 外部代码获取枚举常量关联的整数值
        public int value() {
            return this.value;
        }
    }
}
