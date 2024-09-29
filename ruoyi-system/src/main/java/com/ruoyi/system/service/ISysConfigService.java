package com.ruoyi.system.service;

/** 参数配置 服务层 **/
public interface ISysConfigService {
    /**
     * 获取验证码开关
     *
     * @return true开启，false关闭
     */
    public boolean selectCaptchaEnabled();
}
