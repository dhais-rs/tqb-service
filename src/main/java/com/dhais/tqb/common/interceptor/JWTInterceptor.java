package com.dhais.tqb.common.interceptor;

import cn.hutool.core.util.StrUtil;
import com.dhais.tqb.common.exception.ServiceException;
import com.dhais.tqb.common.utils.JWTUtil;
import com.dhais.tqb.common.utils.PropertiesUtil;
import io.jsonwebtoken.Claims;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;


public class JWTInterceptor extends HandlerInterceptorAdapter {

    private static final String CONTEXT_PATH = "spring.servlet.context-path";
    private static final String IGNORE_URL_KEY = "ignore.url";

    @Override

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws ServiceException, IOException {
        String authHeader = request.getHeader("Authorization");
//        if (StrUtil.isBlank(authHeader)) {
//            throw new ServiceException("用户未登录");
//        }
        //OPTIONS请求直接放过
        if (HttpMethod.OPTIONS.toString().equals(request.getMethod())) {
            response.setStatus(HttpStatus.NO_CONTENT.value());
            return true;
        }
        String requestURI = request.getRequestURI();
        if (!"/".equals(PropertiesUtil.getString(CONTEXT_PATH, "/"))) {
            requestURI = requestURI.replace(PropertiesUtil.getString("spring.servlet.context-path"),"");
        }
        String ignoreStr = PropertiesUtil.getString(IGNORE_URL_KEY);
        if (!StrUtil.isEmpty(ignoreStr)) {
            List<String> ignoreUrlList = Arrays.asList(ignoreStr.split(","));
            if (ignoreUrlList.contains(requestURI)) {
                return true;
            }
        }
        if (authHeader == null || !authHeader.startsWith("Bearer:")) {
            throw new ServiceException("用户未登录");
        }


        //取得token
        String token = authHeader.substring(7);

        //验证token
        Claims claims = JWTUtil.checkToken(token);

        request.setAttribute("username", claims.getSubject());

        return true;

    }
}