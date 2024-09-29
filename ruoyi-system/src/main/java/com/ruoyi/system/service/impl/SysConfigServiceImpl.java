package com.ruoyi.system.service.impl;

import com.ruoyi.common.core.redis.RedisCache;
import com.ruoyi.common.core.text.Convert;
import com.ruoyi.system.service.ISysConfigService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.common.constant.CacheConstants;
import com.ruoyi.system.domain.SysConfig;

@Service
public class SysConfigServiceImpl implements ISysConfigService {

    @Autowired
    private RedisCache redisCache;

    @Override
    public boolean selectCaptchaEnabled() {
        String captchaEnabled = selectConfigByKey("sys.account.captchaEnabled");
    }

    public String selectConfigByKey(String configKey) {
        String configValue = Convert.toStr(redisCache.getCacheObject(getCacheKey(configKey)));
        if(StringUtils.isNotEmpty(configValue)) {
            return configValue;
        }

        SysConfig = new SysConfig();

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
