package com.chendie.teststation.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author: chendie
 * @description:
 */
@Getter
@AllArgsConstructor
public enum ResultCode {
    // 正确返回
    OK(200, "ok"),
    // 异常返回
    ERROR(500, "error"),
    ;
    private final Integer code;
    private final String msg;
}
