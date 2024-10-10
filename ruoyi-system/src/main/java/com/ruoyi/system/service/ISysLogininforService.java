package com.ruoyi.system.service;

import com.ruoyi.system.domain.SysLogininfor;

/**
 * 系统访问日志情况信息 服务层
 */
public interface ISysLogininforService {
    /**
     * 新增系统登录日志
     * @param logininfor
     */
    public void insertLogininfor(SysLogininfor logininfor);
}
