package com.ruoyi.framework.web.service;

import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.core.domain.entity.SysRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.ruoyi.system.service.ISysMenuService;

@Component
public class SysPermissionService {

    @Autowired
    private ISysMenuService menuService;

    /**
     * 获取菜单数据权限
     *
     * @param user 用户信息
     * @return 菜单权限信息
     */

    public Set<String> getMenuPermission(SysUser user) {
        Set<String> perms = new HashSet<String>();

        if (user.isAdmin()) {
            perms.add("*:*:*");
        } else {
            List<SysRole> roles = user.getRoles();

            if (!roles.isEmpty() && roles.size() > 1) {
                for(SysRole role: roles) {
                    Set<String> rolePerms = menuService.selectMenuPermsByRoleId(role.getRoleId());
                }
            }
        }
    }
}
