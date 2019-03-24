package com.api.common;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author coderyong
 */
public class Response {

    /**
     * 响应成功结果信息
     *
     * @return 响应结果对象
     */
    public static Map<String, Object> success() {
        return success(null);
    }

    /**
     * 响应成功结果信息
     *
     * @param data 响应数据
     * @return 响应结果对象
     */
    public static Map<String, Object> success(Object data) {
        return create(Code.SUCCESS, data, 0);
    }

    /**
     * 响应成功结果信息
     *
     * @param data 响应数据
     * @return 响应结果对象
     */
    public static Map<String, Object> success(Object data, int total) {
        return create(Code.SUCCESS, data, total);
    }

    /**
     * 响应失败结果信息
     *
     * @return 响应结果对象
     */
    public static Map<String, Object> fail() {
        return create(Code.FAIL_OPERATE, null, 0);
    }

    /**
     * 响应错误结果信息
     *
     * @param code 响应码
     * @return 响应结果对象
     */
    public static Map<String, Object> error(Code code) {
        return create(code, null, 0);
    }

    /**
     * 响应封装
     *
     * @param code 响应码
     * @param data 响应数据
     * @return 响应结果对象
     */
    private static Map<String, Object> create(Code code, Object data, int total) {
        Map<String, Object> result = new HashMap<>();
        result.put("code", code.getCode());
        result.put("message", code.getMessage());
        if (total == 0) {
            if (data instanceof List) {
                result.put("total", ((List) data).size());
            }
        } else {
            result.put("total", total);
        }
        result.put("data", data);
        return result;
    }
}

