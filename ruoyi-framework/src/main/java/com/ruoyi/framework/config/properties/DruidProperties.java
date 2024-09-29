package com.ruoyi.framework.config.properties;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * druid 配置属性
 */
@Configuration  // 记类作为配置类的注解。配置类的作用是定义Spring应用上下文中的bean
public class DruidProperties {
    // 从 application-druid 中获取对应属性键的值
    // spring.datasource.druid.xxx: 这些占位符对应于配置文件中的属性键
    @Value("${spring.datasource.druid.initialSize}")
    private int initialSize;

    // spring.datasource.druid.xxx: 这些占位符对应于配置文件中的属性键
    @Value("${spring.datasource.druid.minIdle}")
    private int minIdle;

    // spring.datasource.druid.xxx: 这些占位符对应于配置文件中的属性键
    @Value("${spring.datasource.druid.maxActive}")
    private int maxActive;

    // spring.datasource.druid.xxx: 这些占位符对应于配置文件中的属性键
    @Value("${spring.datasource.druid.maxWait}")
    private int maxWait;

    // spring.datasource.druid.xxx: 这些占位符对应于配置文件中的属性键
    @Value("${spring.datasource.druid.timeBetweenEvictionRunsMillis}")
    private int timeBetweenEvictionRunsMillis;

    // spring.datasource.druid.xxx: 这些占位符对应于配置文件中的属性键
    @Value("${spring.datasource.druid.minEvictableIdleTimeMillis}")
    private int minEvictableIdleTimeMillis;

    // spring.datasource.druid.xxx: 这些占位符对应于配置文件中的属性键
    @Value("${spring.datasource.druid.maxEvictableIdleTimeMillis}")
    private int maxEvictableIdleTimeMillis;

    // spring.datasource.druid.xxx: 这些占位符对应于配置文件中的属性键
    @Value("${spring.datasource.druid.validationQuery}")
    private String validationQuery;

    // spring.datasource.druid.xxx: 这些占位符对应于配置文件中的属性键
    @Value("${spring.datasource.druid.testWhileIdle}")
    private boolean testWhileIdle;

    // spring.datasource.druid.xxx: 这些占位符对应于配置文件中的属性键
    @Value("${spring.datasource.druid.testOnBorrow}")
    private boolean testOnBorrow;

    // spring.datasource.druid.xxx: 这些占位符对应于配置文件中的属性键
    @Value("${spring.datasource.druid.testOnReturn}")
    private boolean testOnReturn;

    /**
     * 配置DruidDataSource
     * @param dataSource
     * @return
     */
    // DruidDataSource是阿里巴巴开源的数据库连接池组件Druid中的一个核心类。这个方法用于设置数据源的多个参数
    public DruidDataSource dataSource(DruidDataSource dataSource) {
        /** 配置初始化大小、最小、最大 */
        // 初始化时建立物理连接的个数
        dataSource.setInitialSize(initialSize);
        // 最小空闲连接数
        dataSource.setMinIdle(minIdle);
        // 最大连接池数量
        dataSource.setMaxActive(maxActive);

        /** 配置获取连接等待超时的时间 */
        dataSource.setMaxWait(maxWait);

        /** 配置间隔多久才进行一次检测，检测需要关闭的空闲连接，单位是毫秒 */
        dataSource.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRunsMillis);

        /** 配置一个连接在池中最小、最大生存的时间，单位是毫秒 */
        dataSource.setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis);
        dataSource.setMaxEvictableIdleTimeMillis(maxEvictableIdleTimeMillis);

        /**
         * 用来检测连接是否有效的sql，要求是一个查询语句，常用select 'x'。如果validationQuery为null，testOnBorrow、testOnReturn、testWhileIdle都不会起作用。
         */
        dataSource.setValidationQuery(validationQuery);
        /** 建议配置为true，不影响性能，并且保证安全性。申请连接的时候检测，如果空闲时间大于timeBetweenEvictionRunsMillis，执行validationQuery检测连接是否有效。 */
        dataSource.setTestWhileIdle(testWhileIdle);
        /** 申请连接时执行validationQuery检测连接是否有效，做了这个配置会降低性能。 */
        dataSource.setTestOnBorrow(testOnBorrow);
        /** 归还连接时执行validationQuery检测连接是否有效，做了这个配置会降低性能。 */
        dataSource.setTestOnReturn(testOnReturn);
        return dataSource;
    }
}
