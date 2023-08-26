package com.chendie.teststation.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: chendie
 * @description:
 */
@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResultView<T> {
    private Integer code;
    private String msg;
    private T data;

    public ResultView(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public ResultView(Integer code) {
        this.code = code;
    }

    public ResultView(Integer code, T data) {
        this.code = code;
        this.data = data;
    }

    public ResultView(Integer code, String msg, T data) {
        this.code = code;
        this.data = data;
        this.msg = msg;
    }

    public static <T> ResultView<T> success(String message, T data) {
        return new ResultView<>(ResultCode.OK.getCode(), message, data);
    }

    public static <T> ResultView<T> success(T data) {
        return new ResultView<>(ResultCode.OK.getCode(), data);
    }

    public static <T> ResultView<T> success() {
        return new ResultView<>(ResultCode.OK.getCode());
    }

    public static <T> ResultView<T> fail(String message) {
        return new ResultView<>(ResultCode.ERROR.getCode(), message);
    }

    public static <T> ResultView<T> fail(Integer code, String message) {
        return new ResultView<>(code, message);
    }
}
