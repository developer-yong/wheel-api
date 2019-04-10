package com.api;

import com.alibaba.fastjson.JSON;
import com.api.core.ConfigProperties;
import com.api.core.Response;
import com.api.core.Code;
import com.api.core.RequestInterceptor;
import com.api.core.utils.Logger;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @author coderyong
 */
@MapperScan("com.api.mapper")
@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        Logger.ENABLE = true;
        Logger.T_ENABLE = true;
        Logger.D_ENABLE = true;
        Logger.I_ENABLE = true;
        Logger.W_ENABLE = true;
        Logger.E_ENABLE = true;
        SpringApplication.run(Application.class, args);
    }

    @Configuration
    class WebMvcConfig extends WebMvcConfigurationSupport {
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

    @Component
    @WebFilter(urlPatterns = "/api/*", filterName = "CrossFilter")
    class CrossFilter implements Filter {

        @Override
        public void init(FilterConfig filterConfig) throws ServletException {
        }

        @Override
        public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
            HttpServletResponse response = (HttpServletResponse) servletResponse;
            response.setHeader("Access-Control-Allow-Origin", "*");
            response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
            response.setHeader("Access-Control-Max-Age", "3600");
            response.setHeader("Access-Control-Allow-Headers", "x-requested-with,Content-Type");
            response.setHeader("Access-Control-Allow-Credentials", "true");
            filterChain.doFilter(servletRequest, servletResponse);
        }

        @Override
        public void destroy() {
        }
    }

    @Bean
    @ConfigurationProperties(prefix = "config")
    public ConfigProperties mailProperties() {
        return new ConfigProperties();
    }
}


