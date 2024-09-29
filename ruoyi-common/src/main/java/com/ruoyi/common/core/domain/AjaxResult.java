package com.ruoyi.common.core.domain;

import com.ruoyi.common.constant.HttpStatus;
import com.ruoyi.common.util.StringUtil;

import java.util.HashMap;

public class AjaxResult extends HashMap<String, Object> {
    // 用于序列化对象的版本标识符,serialVersionUID 的使用是为了确保在类的定义发生变化时，序列化和反序列化过程能够正确地进行
    private static final long serialVersionUID = 1L;

    /** 状态码 **/
    public static final String CODE_TAG = "code";

    /** 返回内容 **/
    public static final String MSG_TAG = "msg";

    /** 数据对象 **/
    public static final String DATA_TAG = "data";

    /** 初始化一个新创建的 AjaxResult 对象，使其表示一个空消息。 **/
    public AjaxResult() {}

    /**
     * 初始化一个新创建的 AjaxResult 对象
     * @param code
     * @param msg
     */
    public AjaxResult(int code, String msg) {
        super.put(CODE_TAG, code);
        super.put(MSG_TAG, msg);
    }

    /**
     * 初始化一个新创建的 AjaxResult 对象
     * @param code
     * @param msg
     * @param data
     */
    public AjaxResult(int code, String msg, Object data) {
        super.put(CODE_TAG, code);
        super.put(MSG_TAG, msg);

        if (StringUtil.isNotNull(data)) {
            super.put(DATA_TAG, data);
        }
    }

    /**
     * 返回成功消息
     * @param msg 返回内容
     * @param data 数据对象
     * @return 成功消息
     */
    public static AjaxResult success(String msg, Object data) {
        return new AjaxResult(HttpStatus.SUCCESS, msg, data);
    }

    /**
     * 返回成功消息
     * @return
     */
    public static AjaxResult success() {
        return AjaxResult.success("操作成功");
    }

    /**
     * 返回成功消息
     * @param msg
     * @return
     */
    public static AjaxResult success(String msg ) {
        return AjaxResult.success(msg, null);
    }

    /**
     * 返回成功消息
     * @param data
     * @return
     */
    public static AjaxResult success(Object data) {
        return AjaxResult.success("操作成功", data);
    }

    /**
     * 返回警告消息
     * @param msg
     * @param data
     * @return
     */
    public static AjaxResult warn(String msg, Object data) {
        return new AjaxResult(HttpStatus.WARN, msg, data);
    }

    /**
     * 返回警告消息
     * @param msg
     * @return
     */
    public static AjaxResult warn(String msg) {
        return AjaxResult.warn(msg, null);
    }

    /**
     * 返回错误消息
     * @param msg
     * @param data
     * @return
     */
    public static AjaxResult error(String msg, Object data) {
        return new AjaxResult(HttpStatus.ERROR, msg, data);
    }

    /**
     * 返回错误消息
     * @param msg
     * @return
     */
    public static AjaxResult error(String msg) {
        return AjaxResult.error(msg, null);
    }

    /**
     * 返回错误消息
     * @param code
     * @param msg
     * @return
     */
    public static AjaxResult error(int code, String msg) {
        return new AjaxResult(code, msg, null);
    }
}
