package com.api.core;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class ExceptionController {

    /**
     * 处理404异常
     *
     * @param e NoHandlerFoundException
     * @return 接口访问错误
     */
    @ExceptionHandler(NoHandlerFoundException.class)
    public Object handleNotFoundError(NoHandlerFoundException e) {
        return Response.error(Code.ERROR_API.updateMessage("错误的访问路径: " + e.getRequestURL()));
    }

    /**
     * 处理未知异常
     *
     * @param e       Exception
     * @param request HttpServletRequest
     * @return 系统错误数据
     */
    @ExceptionHandler(Exception.class)
    public Object handleException(Exception e, HttpServletRequest request) {
        Logger.e(request.getRequestURI(), e);
        return Response.error(Code.ERROR_SERVER);
    }
}