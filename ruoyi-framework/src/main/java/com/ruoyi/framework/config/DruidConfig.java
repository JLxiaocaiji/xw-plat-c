package com.ruoyi.framework.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import com.ruoyi.common.enums.DataSourceType;
import com.ruoyi.common.util.spring.SpringUtils;
import com.ruoyi.framework.config.properties.DruidProperties;
import com.ruoyi.framework.datasource.DynamicDataSource;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration  // 标记类作为配置类的注解;定义Spring应用上下文中的bean;通常包含一个或多个被@Bean注解的方法，这些方法用于生成bean，并可以被Spring容器管理
public class DruidConfig {

    /**
     * 绑定配置文件中的属性 master 到 bean 上
     * @param druidProperties
     * @return
     */
    @Bean
    @ConfigurationProperties("spring.datasource.druid.master")
    public DataSource masterDataSource(DruidProperties druidProperties){
        // Spring Boot提供的一个便捷方法，用于创建一个DruidDataSource实例，并且它会自动绑定配置文件中对应前缀的属性
        DruidDataSource dataSource = DruidDataSourceBuilder.create().build();
        return druidProperties.dataSource(dataSource);
    }

    /**
     * 将配置文件中以spring.datasource.druid.slave为前缀的属性绑定到DataSource Bean上
     * @param druidProperties
     * @return
     */
    @Bean
    @ConfigurationProperties("spring.datasource.druid.slave")
    // 这个注解确保了只有在配置文件中spring.datasource.druid.slave.enabled属性值为true时，slaveDataSource Bean才会被创建
    @ConditionalOnProperty(prefix = "spring.datasource.druid.slave", name = "enabled", havingValue = "true")
    public DataSource slaveDataSource(DruidProperties druidProperties) {
        DruidDataSource dataSource = DruidDataSourceBuilder.create().build();
        return druidProperties.dataSource(dataSource);
    }

    /**
     * 创建并返回一个新的DynamicDataSource实例，它使用主数据源作为默认数据源，并使用targetDataSources映射来存储其他数据源
     * @param masterDataSource
     * @return
     */
    @Bean(name = "dynamicDataSource")   // 定义了一个Bean，并给它指定了一个名字dynamicDataSource
    @Primary    // 标记这个Bean为主要的，当有多个同类型的Bean时，Spring容器将优先注入这个Bean
    public DynamicDataSource dataSource(DataSource masterDataSource) {
        Map<Object, Object> targetDataSources = new HashMap();
        targetDataSources.put(DataSourceType.MASTER.name(), masterDataSource);
        setDataSource(targetDataSources, DataSourceType.SLAVE.name(), "slaveData");
        return new DynamicDataSource(masterDataSource, targetDataSources);
    }


    public void setDataSource(Map<Object, Object> targetDataSources, String sourceName, String beanName) {
        try {
            DataSource dataSource = SpringUtils.getBean(beanName);
            targetDataSources.put(sourceName, dataSource);
        } catch (Exception e) {

        }
    }
}
