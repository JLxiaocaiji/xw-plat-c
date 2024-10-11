package com.ruoyi.common.core.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BaseEntity implements Serializable {
    /**
     * serialVersionUID: 是一个序列化版本标识符，它是用于序列化过程中验证版本一致性的
     * 当一个对象被序列化时，这个唯一标识符会与对象一起被保存。当对象被反序列化时，这个标识符会被用来确认序列化的对象与当前类的定义是否兼容
     * 如果在序列化一个对象之后，你修改了该类的定义（例如，添加、删除或更改了字段），而没有更新 serialVersionUID，则在反序列化时可能会抛出 InvalidClassException，因为序列化时的类定义与现在的类定义不匹配
     */
    private static final long serialVersionUID = 1L;

    /** 创建者 **/
    private String createBy;

    /** 创建时间 **/
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /** 更新者 **/
    private String updateBy;

    /** 更新时间 **/
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    // @TableField(exist = false): 这个字段在数据库表中不存在
    @TableField(exist = false)
    private String remark;

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }


}
