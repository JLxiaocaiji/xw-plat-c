package com.ruoyi.common.util.spring;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * spring工具类 方便在非spring管理环境中获取bean
 */
@Component // 将 SpringBeanUtils 类注册为Spring容器中的一个Bean
// 在Spring容器启动过程中获取 ConfigurableListableBeanFactory 和 ApplicationContext 的引用，并允许静态地获取Spring容器中的Bean实例
public class SpringUtils implements BeanFactoryPostProcessor, ApplicationContextAware {
    /** Spring应用上下文环境 */
    private static ConfigurableListableBeanFactory beanFactory;

    private static ApplicationContext applicationContext;

    // postProcessBeanFactory 方法会在Spring容器启动过程中被调用，用于设置 ConfigurableListableBeanFactory 的静态引用
    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        SpringUtils.beanFactory = beanFactory;
    }

    // 在Spring容器启动过程中被调用，用于设置 ApplicationContext 的静态引用
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SpringUtils.applicationContext = applicationContext;
    }

    /**
     * 获取 bean 对象
     * @param name
     * @return
     * @param <T>
     * @throws BeansException
     */
    // 获取Spring容器中的Bean实例
    // 使用 ConfigurableListableBeanFactory 的静态引用来获取Bean实例
    public static <T> T getBean(String name) throws BeansException {
        return (T) beanFactory.getBean(name);
    }

    /**
     * 获取类型为requiredType的对象
     *
     * @param clz
     * @return
     * @throws org.springframework.beans.BeansException
     *
     */
    // Class<T> clz: Class对象，表示你想要获取的Bean的类型
    public static <T> T getBean(Class<T> clz) throws BeansException {
        // 从 beanFactory 中获取一个类型为 clz 的Bean实例
        T result = (T) beanFactory.getBean(clz);
        return result;
    }
}
