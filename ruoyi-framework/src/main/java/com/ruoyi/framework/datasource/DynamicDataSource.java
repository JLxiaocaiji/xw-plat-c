package com.ruoyi.framework.datasource;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import javax.sql.DataSource;
import java.util.Map;

public class DynamicDataSource extends AbstractRoutingDataSource {

    public DynamicDataSource(DataSource defaultTargetDataSource, Map<Object, Object> targetDataSources) {
        // 设置默认的数据源
        super.setDefaultTargetDataSource(defaultTargetDataSource);
        // 设置目标数据源的映射
        super.setTargetDataSources(targetDataSources);
        // 在设置完属性后执行一些初始化操作，通常是在属性设置完成后调用
        super.afterPropertiesSet();
    }

    protected Object determineCurrentLookupKey() {
        return DynamicDataSourceContextHolder.getDataSourceType();
    }
}
