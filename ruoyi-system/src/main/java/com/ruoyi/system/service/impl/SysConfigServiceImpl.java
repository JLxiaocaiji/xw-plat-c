package com.ruoyi.system.service.impl;

import com.ruoyi.common.core.redis.RedisCache;
import com.ruoyi.common.core.text.Convert;
import com.ruoyi.common.util.StringUtil;
import com.ruoyi.system.service.ISysConfigService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.common.constant.CacheConstants;
import com.ruoyi.system.domain.SysConfig;
import com.ruoyi.system.mapper.SysConfigMapper;

@Service
public class SysConfigServiceImpl implements ISysConfigService {

    @Autowired
    private RedisCache redisCache;

    @Autowired
    private SysConfigMapper configMapper;

    /**
     * 获取验证码开关
     *
     * @return true开启，false关闭
     */
    @Override
    public boolean selectCaptchaEnabled() {
        String captchaEnabled = selectConfigByKey("sys.account.captchaEnabled");

        // 如果配置值为空，则默认启用验证码
        if (StringUtil.isEmpty(captchaEnabled)) {
            return true;
        }
        // 否则，将配置值转换为布尔值并返回
        return Convert.toBool(captchaEnabled);
    }

    /**
     * 根据键名查询参数配置信息
     *
     * @param configKey 参数key sys.account.captchaEnabled
     * @return 参数键值
     */
    public String selectConfigByKey(String configKey) {
        System.out.println("aaaaaaaa");
        System.out.println(getCacheKey(configKey));
        // 从Redis缓存中获取配置值，并将其转换为字符串
        String configValue = Convert.toStr(redisCache.getCacheObject(getCacheKey(configKey)));
        // 如果缓存中的配置值不为空，则直接返回该值
        if(StringUtils.isNotEmpty(configValue)) {
            return configValue;
        }

        // 如果缓存中没有配置值，则创建一个SysConfig对象并设置配置键
        SysConfig config = new SysConfig();
        config.setConfigKey(configKey);

        // 使用configMapper从数据库中检索配置
        SysConfig retConfig = configMapper.selectConfig(config);

        System.out.println(retConfig);

        if (StringUtil.isNotNull(retConfig)) {
            // 将配置值存入Redis缓存，缓存键为配置键
            // cache key: 参数键值
            redisCache.setCacheObject(getCacheKey(configKey), retConfig.getConfigValue());
            // 返回配置值
            return retConfig.getConfigValue();
        }
        // 返回值为： ""
        return StringUtil.EMPTY;
    }

    /**
     * 设置cache key
     *
     * @param configKey 参数键
     * @return 缓存键key
     */
    private String getCacheKey(String configKey) {
        // "sys_config:" + configKey
        return CacheConstants.SYS_CONFIG_KEY + configKey;
    }
}
