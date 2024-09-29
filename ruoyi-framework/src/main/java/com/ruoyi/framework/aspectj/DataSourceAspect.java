package com.ruoyi.framework.aspectj;

import com.ruoyi.common.util.StringUtil;
import com.ruoyi.framework.datasource.DynamicDataSourceContextHolder;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.ruoyi.common.annotation.DataSource;
import org.springframework.core.annotation.AnnotationUtils;

import java.util.Objects;

@Aspect // 定义一个切面

public class DataSourceAspect {
    protected Logger logger = LoggerFactory.getLogger(getClass());

    // 定义切点： 切点是程序中可以应用通知的一个或多个连接点（如方法执行）
    // 前者：如果一个方法上有@DataSource注解，那么这个方法就会被这个切点匹配
    // 后者：匹配所有在带有@DataSource注解的类中定义的方法；如果一个类被@DataSource注解标记，那么这个类中的所有方法都会被这个切点匹配
    @Pointcut("@annotation(com.ruoyi.common.annotation.DataSource) || @within(com.ruoyi.common.annotation.DataSource)")
    public void dsPointCut() {}

    // @Around: 方法执行之前和之后执行
    // AOP中的连接点，它代表被拦截的方法
    @Around("dsPointCut()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        DataSource dataSource = getDataSource(point);

        if (StringUtil.isNotNull(dataSource)) {
            // 这个方法（假设它已经被定义）用于设置当前线程的数据源类型。dataSource.value()返回注解DataSource的value属性，通常是枚举或字符串常量，表示数据源的名称
            DynamicDataSourceContextHolder.setDataSourceType(dataSource.value().name());
        }

        try {
            return point.proceed();
        } finally {
            DynamicDataSourceContextHolder.clearDataSourceType();
        }
    }


    // ProceedingJoinPoint point: 正在执行的方法;
    // ProceedingJoinPoint用于控制目标方法的执行
    public DataSource getDataSource(ProceedingJoinPoint point) {

        // (MethodSignature) point.getSignature(): 获取连接点的签名
        // MethodSignature提供了对连接点方法（即被注解的方法）的访问
        MethodSignature signature = (MethodSignature) point.getSignature();

        // 使用AnnotationUtils工具类来查找方法上直接应用的DataSource注解。如果该方法上有DataSource注解，则将其赋值给dataSource变量
        // find annotation
        DataSource dataSource = AnnotationUtils.findAnnotation(signature.getMethod(), DataSource.class);

        if (Objects.nonNull(dataSource)) {
            return dataSource;
        }

        // 如果方法上没有找到DataSource注解，那么该方法将尝试在声明该方法的类上查找DataSource注解。如果类上有DataSource注解，则返回它
        return AnnotationUtils.findAnnotation(signature.getDeclaringType(), DataSource.class);
    }
}
