package com.ruoyi.system.service;

import com.ruoyi.common.core.domain.entity.SysUser;

/**
 * 用户 业务层
 */
public interface ISysUserService {

    /**
     * 修改用户基本信息
     *
     * @param user 用户信息
     * @return 结果
     */
    public int updateUserProfile(SysUser user);

    /**
     * 通过用户名查询用户
     *
     * @param userName 用户名
     * @return 用户对象信息
     */
    public SysUser selectUserByUserName(String userName);
}
