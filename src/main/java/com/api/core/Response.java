package com.api.core;

import com.alibaba.fastjson.JSON;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author coderyong
 */
public class Response<T> {

    public int code;
    public String message;
    public int total;
    public T data;

    public Response(int code, String message, int total, T data) {
        this.code = code;
        this.message = message;
        this.total = total;
        this.data = data;
    }

    /**
     * 响应成功结果信息
     *
     * @return 响应结果对象
     */
    public static <T> Response<T> success() {
        return success(null);
    }

    /**
     * 响应成功结果信息
     *
     * @param data 响应数据
     * @return 响应结果对象
     */
    public static <T> Response<T> success(T data) {
        return create(Code.SUCCESS, data, 0);
    }

    /**
     * 响应成功结果信息
     *
     * @param data 响应数据
     * @return 响应结果对象
     */
    public static <T> Response<T> success(T data, int total) {
        return create(Code.SUCCESS, data, total);
    }

    /**
     * 响应失败结果信息
     *
     * @return 响应结果对象
     */
    public static <T> Response<T> fail() {
        return create(Code.FAIL_OPERATE, null, 0);
    }

    /**
     * 响应失败结果信息
     *
     * @param message 响应失败信息
     * @return 响应结果对象
     */
    public static <T> Response<T> fail(String message) {
        return create(Code.FAIL_OPERATE.updateMessage(message), null, 0);
    }

    /**
     * 响应错误结果信息
     *
     * @param code 响应码
     * @return 响应结果对象
     */
    public static <T> Response<T> error(Code code) {
        return create(code, null, 0);
    }

    /**
     * 响应参数错误结果信息
     *
     * @return 响应结果对象
     */
    public static <T> Response<T> errorParameter() {
        return create(Code.ERROR_PARA, null, 0);
    }

    /**
     * 响应参数错误结果信息
     *
     * @param message 响应参数错误信息
     * @return 响应结果对象
     */
    public static <T> Response<T> errorParameter(String message) {
        return create(Code.ERROR_PARA.updateMessage(message), null, 0);
    }

    /**
     * 响应封装
     *
     * @param code 响应码
     * @param data 响应数据
     * @return 响应结果对象
     */
    private static <T> Response<T> create(Code code, T data, int total) {
        return new Response<>(code.getCode(), code.getMessage(), total, data);
    }

    public static void write(HttpServletResponse response, Code code) {
        try {
            response.setCharacterEncoding("UTF-8");
            response.setHeader("Content-type", "application/json;charset=UTF-8");
            response.getWriter().write(JSON.toJSONString(error(code)));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void write(HttpServletResponse response, Object data) {
        try {
            response.setCharacterEncoding("UTF-8");
            response.setHeader("Content-type", "application/json;charset=UTF-8");
            response.getWriter().write(JSON.toJSONString(data));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

