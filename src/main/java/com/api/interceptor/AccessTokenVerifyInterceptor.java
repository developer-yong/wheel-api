package com.api.interceptor;

import com.api.utils.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author coderyong
 */
@Component
public abstract class AccessTokenVerifyInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 获取token
        String accessToken = request.getParameter("token");
        boolean valid = verifyAccessToken(accessToken, request, response);
        Logger.i(valid + " token -> " + accessToken);

        if (!valid) {
            response.setStatus(HttpStatus.FORBIDDEN.value());
            response.getWriter().print("AccessToken ERROR");
        }
        return valid;
    }

    protected abstract boolean verifyAccessToken(String accessToken, HttpServletRequest request, HttpServletResponse response);
}
