package com.ruoyi.system.mapper;

import java.util.List;
import com.ruoyi.system.domain.SysConfig;

/** 参数配置 数据层 **/
public interface SysConfigMapper {

    /**
     * 查询参数配置信息
     * @param config
     * @return
     */
    public SysConfig selectConfig(SysConfig config);
}
