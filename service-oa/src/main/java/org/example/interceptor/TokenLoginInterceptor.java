package org.example.interceptor;

import conponent.ThreadLocalComponent;
import org.example.handler.LoginException;
import org.example.jwt.JwtHelper;
import org.example.result.ResultCodeEnum;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class TokenLoginInterceptor implements HandlerInterceptor {


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String requestURI = request.getRequestURI();
        if (requestURI.contains("login")) return true;

        String token = request.getHeader("token");
        if(StringUtils.isEmpty(token)) throw new LoginException(ResultCodeEnum.LOGIN_FAIL);
        Long userId = JwtHelper.getUserId(token);
        String username = JwtHelper.getUsername(token);
        ThreadLocalComponent.user_id.set(userId);
        ThreadLocalComponent.username.set(username);
        return HandlerInterceptor.super.preHandle(request, response, handler);
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        ThreadLocalComponent.remove();
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }
}
