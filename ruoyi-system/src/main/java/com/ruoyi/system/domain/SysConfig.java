package com.ruoyi.system.domain;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.annotation.Excel.ColumnType;
import com.ruoyi.common.core.domain.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SysConfig extends BaseEntity {
    /**
     * serialVersionUID: 是一个序列化版本标识符，它是用于序列化过程中验证版本一致性的
     * 当一个对象被序列化时，这个唯一标识符会与对象一起被保存。当对象被反序列化时，这个标识符会被用来确认序列化的对象与当前类的定义是否兼容
     * 如果在序列化一个对象之后，你修改了该类的定义（例如，添加、删除或更改了字段），而没有更新 serialVersionUID，则在反序列化时可能会抛出 InvalidClassException，因为序列化时的类定义与现在的类定义不匹配
     */
    private static final long serialVersionUID = 1L;

    /** 参数主键 */
    @Excel(name = "参数主键", cellType = ColumnType.NUMERIC)
    private Long configId;

    @Excel(name = "参数名称")
    private String configName;

    @Excel(name = "参数键名")
    private String configKey;

    @Excel(name = "参数键值")
    private String configValue;

    // 当从Excel文件中读取这个字段的值时，如果单元格中的值是 "Y"，它将被转换为 "是"；如果单元格中的值是 "N"，它将被转换为 "否"
    @Excel(name = "系统内置", readConverterExp = "Y=是,N=否")
    private String configType;
}
