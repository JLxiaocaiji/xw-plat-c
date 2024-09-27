//package com.ruoyi.common.annotation;
//
//
//
//import java.lang.annotation.*;
//import com.ruoyi.common.enums.DataSourceType;
//
//@Target({ ElementType.METHOD, ElementType.TYPE})   // @Target 指定被修饰的注解可以应用于哪些 java 元素上
//@Retention(RetentionPolicy.RUNTIME) // 注解保留在编译后的字节码文件中，并在运行时可以通过反射机制读取,可以在运行时处理注解
//@Documented // 保留在生成的Java类文件中，并且会被Javadoc工具提取并在生成的文档中显示
//@Inherited
//public @interface DataSource {
//
//    // 定义一个名为value的方法，默认值为DataSourceType.MASTER
//    // 注解是有个 value()
//    public DataSourceType value() default DataSourceType.MASTER;
//}
