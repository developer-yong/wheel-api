package com.api.core.config;

import com.alibaba.fastjson.JSON;
import com.api.core.Code;
import com.api.core.RequestInterceptor;
import com.api.core.Response;
import com.api.core.utils.Logger;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import java.io.IOException;
import java.util.List;

@Configuration
public class WebMvcConfig extends WebMvcConfigurationSupport {
    @Override
    protected void addInterceptors(InterceptorRegistry registry) {
        super.addInterceptors(registry);
        RequestInterceptor.printHeaders = false;
        registry.addInterceptor(new RequestInterceptor());
    }

    @Override
    public void extendHandlerExceptionResolvers(List<HandlerExceptionResolver> resolvers) {
        //添加错误解析
        resolvers.add((request, response, handler, e) -> {
            response.setCharacterEncoding("UTF-8");
            response.setHeader("Content-type", "application/json;charset=UTF-8");
            try {
                if (e instanceof NoHandlerFoundException) {
                    response.getWriter().write(JSON.toJSONString(
                            Response.error(Code.create(Code.ERROR_API, "接口 [" + request.getRequestURI() + "] 不存在"))));
                } else {
                    response.getWriter().write(JSON.toJSONString(Response.error(Code.ERROR_SERVER)));
                    Logger.e(request.getRequestURI(), e);
                }
            } catch (IOException ex) {
                Logger.e("异常处理错误", ex);
            }
            return new ModelAndView();
        });
    }
}