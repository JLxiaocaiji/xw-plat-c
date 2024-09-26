package com.ruoyi;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

// 配置和初始化一个Spring Boot应用程序
public class RuoYiServletInitializer extends SpringBootServletInitializer {
    // SpringApplicationBuilder是Spring Boot提供的用于构建和配置Spring应用程序的构建器
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        // 表明 SpringApplicationBuilder应该使用哪个类作为Spring应用程序的源
        return application.sources(RuoYiApplication.class);
    }
}
