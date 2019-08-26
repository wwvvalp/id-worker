package com.gwt.idworker.common;

/**
 * @author: gewentao
 * @date: 2019/8/21 14:57
 */

public enum ResultEnum {
    /**
     * 成功
     */
    SUCCESS(200,"success"),

    /**
     * 异常
     */
    EXCEPTION(400, "exception"),

    /**
     * 错误
     */
    ERROR(500, "error");

    private int code;
    private String desc;

    ResultEnum(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
