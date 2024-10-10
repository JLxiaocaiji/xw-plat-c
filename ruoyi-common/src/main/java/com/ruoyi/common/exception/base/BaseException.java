package com.ruoyi.common.exception.base;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BaseException extends RuntimeException {
    /**
     * serialVersionUID: 是一个序列化版本标识符，它是用于序列化过程中验证版本一致性的
     * 当一个对象被序列化时，这个唯一标识符会与对象一起被保存。当对象被反序列化时，这个标识符会被用来确认序列化的对象与当前类的定义是否兼容
     * 如果在序列化一个对象之后，你修改了该类的定义（例如，添加、删除或更改了字段），而没有更新 serialVersionUID，则在反序列化时可能会抛出 InvalidClassException，因为序列化时的类定义与现在的类定义不匹配
     */
    private static final long serialVersionUID = 1L;

    /** 所属模块 **/
    private String module;

    /** 错误码 **/
    private String code;

    /** 错误码对应的参数 **/
    private Object[] args;

    /** 错误消息 **/
    private String defaultMessage;

}
