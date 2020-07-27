package com.api.core;

/**
 * 响应状态码封装类
 *
 * @author coderyong
 */
public enum Code {

    SUCCESS(200, "成功"),

    ERROR(-1, "未知错误"),
    ERROR_PARA(400, "参数错误"),
    ERROR_PARA_FILE(401, "文件错误"),
    ERROR_API(404, "错误的访问路径"),
    FAIL_OPERATE(405, "操作失败，请稍后重试!"),

    ERROR_DATA_DUPLICATION(406, "数据重复"),
    ERROR_REPEAT_INSERTION(407, "信息已存在"),
    ERROR_USER_NO_LOGIN(460, "用户没有登录!"),

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

    public Code updateMessage(String message) {
        if (message != null && message.length() > 0) {
            this.message = message;
        }
        return this;
    }

    public static Code create(int code, String message) {
        CUSTOM.code = code;
        CUSTOM.message = message;
        return CUSTOM;
    }
}