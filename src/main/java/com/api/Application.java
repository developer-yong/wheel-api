package com.api;

import com.api.core.config.ConfigProperties;
import com.api.core.utils.Logger;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

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


