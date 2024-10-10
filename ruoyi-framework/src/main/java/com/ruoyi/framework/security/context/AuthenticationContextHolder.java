package com.ruoyi.framework.security.context;

import org.springframework.security.core.Authentication;

/**
 * 身份验证信息
 */
public class AuthenticationContextHolder {
    // ThreadLocal 变量，它为每个线程提供了一个独立的 Authentication 对象副本。这意味着每个线程都可以有自己的认证上下文，而不会与其他线程共享
    private static final ThreadLocal<Authentication> contextHolder = new ThreadLocal<>();

    public static Authentication getContext() {
        return contextHolder.get();
    }

    public static void setContext(Authentication context) {
        contextHolder.set(context);
    }

    public static void clearContext() {
        contextHolder.remove();
    }
}
