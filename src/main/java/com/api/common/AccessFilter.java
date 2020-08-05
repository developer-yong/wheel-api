package com.api.common;

import com.api.core.Logger;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Web请求过滤器
 */
@WebFilter(urlPatterns = "/*", filterName = "AccessFilter")
public class AccessFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) {
        Logger.d("AccessFilter -> init" + filterConfig.toString());
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        httpResponse.setHeader("Access-Control-Allow-Origin", "*");
        httpResponse.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
        httpResponse.setHeader("Access-Control-Max-Age", "3600");
        httpResponse.setHeader("Access-Control-Allow-Headers", "x-requested-with,Content-Type");
        httpResponse.setHeader("Access-Control-Allow-Credentials", "true");
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        Logger.d("AccessFilter -> destroy");
    }
}
