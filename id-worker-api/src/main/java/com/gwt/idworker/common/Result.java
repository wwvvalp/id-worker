package com.gwt.idworker.common;

import java.io.Serializable;

/**
 * @author: gewentao
 * @date: 2019/8/21 14:53
 */

public class Result<T> implements Serializable {

    private static final long serialVersionUID = -6319307594578396290L;

    private int code;

    private String message;

    private T data;

    public Result(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public Result(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public static <T> Result<T> buildSuccess(T data) {
        return new Result<>(ResultEnum.SUCCESS.getCode(), ResultEnum.SUCCESS.getDesc(), data);
    }

    public static <T> Result<T> buildFailed(ResultEnum resultEnum) {
        return new Result<>(resultEnum.getCode(), resultEnum.getDesc());
    }

    public boolean isSuccess() {
        return this.code == ResultEnum.SUCCESS.getCode();
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
