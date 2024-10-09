package com.ruoyi.common.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 读取项目相关配置
 */
@Component
@ConfigurationProperties(prefix = "ruoyi")
public class RuoYiConfig {

    /**
     * 获取地址开关
     */
    private static boolean addressEnabled;

    public static boolean isAddressEnabled() {
        return addressEnabled;
    }

//    public void setAddressEnabled(boolean addressEnabled) {
//        RuoYiConfig.addressEnabled = addressEnabled;
//    }
}
