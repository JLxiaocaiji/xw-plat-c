package com.ruoyi.framework.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

// 启用基于注解的安全配置，它可以在服务层的方法上应用安全约束。这个注解可以与@PreAuthorize、@PostAuthorize、@PreFilter、@PostFilter以及@Secured等注解一起使用，以声明方法级别的安全性
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception{
        return super.authenticationManagerBean();
    }
}
