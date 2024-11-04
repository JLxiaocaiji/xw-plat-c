package com.ruoyi.system.mapper;

import com.ruoyi.common.core.domain.entity.SysMenu;

import java.util.List;

public interface SysMenuMapper {
    /**
     * 查询系统菜单列表
     *
     * @param menu 菜单信息
     * @return 菜单列表
     */
    public List<SysMenu> selectMenuList(SysMenu menu);


}
