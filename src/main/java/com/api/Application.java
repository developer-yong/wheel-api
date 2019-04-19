package com.api;

import com.api.common.ConfigProperties;
import com.api.core.Logger;
import com.api.core.RequestInterceptor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author coderyong
 */
@SpringBootApplication
@WebFilter(urlPatterns = "/api/*", filterName = "Application")
public class Application extends WebMvcConfigurationSupport implements FilterChain {

    public static void main(String[] args) {
        Logger.ENABLE = true;
        Logger.T_ENABLE = true;
        Logger.D_ENABLE = true;
        Logger.I_ENABLE = true;
        Logger.W_ENABLE = true;
        Logger.E_ENABLE = true;
        SpringApplication.run(Application.class, args);
    }

    @Bean
    @ConfigurationProperties(prefix = "config")
    public ConfigProperties configProperties() {
        return new ConfigProperties();
    }

    /**
     * 添加拦截器
     *
     * @param registry 拦截器注册处
     */
    @Override
    protected void addInterceptors(InterceptorRegistry registry) {
        super.addInterceptors(registry);
        RequestInterceptor.printHeaders = false;
        registry.addInterceptor(new RequestInterceptor());
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse) throws IOException, ServletException {
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers", "x-requested-with,Content-Type");
        response.setHeader("Access-Control-Allow-Credentials", "true");
    }
}


