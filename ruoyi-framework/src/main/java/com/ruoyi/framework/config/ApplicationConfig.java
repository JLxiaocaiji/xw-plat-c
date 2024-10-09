package com.ruoyi.framework.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import java.util.TimeZone;

@Configuration  // 这个类是一个配置类，它用于定义Bean和配置组件。在这个类中定义的任何带有@Bean注解的方法都会被Spring容器处理，以生成对应的Bean
// @EnableAspectJAutoProxy: 启用了Spring AOP的自动代理功能，允许你定义和使用切面（Aspect）
// exposeProxy = true参数表示将代理暴露到AopContext中，这样即使在同一个类中，也可以通过AopContext.currentProxy()获取到代理对象，从而允许在同一个类中的方法之间进行切面通知
@EnableAspectJAutoProxy(exposeProxy = true)
// 指定Mapper接口所在的包
@MapperScan("com.ruoyi.**.mapper")
public class ApplicationConfig {
    @Bean
    public Jackson2ObjectMapperBuilderCustomizer jackson2ObjectMapperBuilderCustomizer() {
        // 定义一个Bean来定制Jackson的ObjectMapper，设置默认时区
        return jacksonObjectMapperBuilder -> jacksonObjectMapperBuilder.timeZone(TimeZone.getDefault());
    }
}
