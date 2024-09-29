package com.ruoyi.framework.web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ruoyi.system.service.ISysConfigService;

@Component  // 将此类标记为Spring容器中的一个Bean
public class SysLoginService {

    @Autowired
    private ISysConfigService configService;

    public String login(String username, String password, String code, String uuid) {
        boolean captchaEnabled = configService.selectCaptchEnabled();
    }
}
