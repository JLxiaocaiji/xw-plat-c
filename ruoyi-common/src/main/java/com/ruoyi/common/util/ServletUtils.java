package com.ruoyi.common.util;

import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

public class ServletUtils {
    /**
     * 获取String参数
     */
    public static String getParameter(String name) {
        return getRequest().getParameter(name);
    }

    /**
     * 获取request
     */
    public static HttpServletRequest getRequest() {
        return getRequestAttributes().getRequest();
    }

    /**
     * 获取当前线程绑定的请求属性
     * @return
     */
    public static ServletRequestAttributes getRequestAttributes() {
        // RequestContextHolder 是Spring MVC中的一个类，用于存储和访问与当前请求相关的上下文信息，RequestContextHolder 类中获取当前线程的请求属性
        RequestAttributes attributes = RequestContextHolder.getRequestAttributes();
        // (ServletRequestAttributes):类型转换,确保了返回的对象是 ServletRequestAttributes 类型
        return (ServletRequestAttributes) attributes;
    }
}
