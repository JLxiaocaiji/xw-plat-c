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

    @Override
    public boolean selectCaptchaEnabled() {
        String captchaEnabled = selectConfigByKey("sys.account.captchaEnabled");

        if (StringUtil.isEmpty(captchaEnabled)) {
            return true;
        }
        return Convert.toBool(captchaEnabled);
    }

    /**
     * 根据键名查询参数配置信息
     *
     * @param configKey 参数key
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

        SysConfig config = new SysConfig();
        config.setConfigKey(configKey);

        SysConfig retConfig = configMapper.selectConfig(config);

        System.out.println(retConfig);

        if (StringUtil.isNotNull(retConfig)) {
            // cache key: 参数键值
            redisCache.setCacheObject(getCacheKey(configKey), retConfig.getConfigValue());
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
