package com.ruoyi.common.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ServiceException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    /** 错误提示 **/
    private String message;
}
