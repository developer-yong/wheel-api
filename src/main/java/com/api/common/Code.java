package com.api.common;

import com.api.utils.Check;

/**
 * @author coderyong
 */
public enum Code {

    SUCCESS(200, "成功"),

    ERROR(-1, "未知错误"),
    ERROR_PARA(400, "参数错误"),
    ERROR_PARA_FILE(401, "文件错误"),
    ERROR_API(404, "接口错误"),
    FAIL_OPERATE(405, "操作失败，请稍后重试!"),

    ERROR_SERVER(500, "服务器内部错误"),

    CUSTOM(0, "请求失败");

    // 成员变量
    private int code;
    private String message;

    Code(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public static Code create(int code, String message) {
        CUSTOM.code = code;
        CUSTOM.message = message;
        return CUSTOM;
    }

    public static Code create(Code code, String message) {
        if (!Check.isEmpty(message)) {
            code.message = message;
        }
        return code;
    }
}