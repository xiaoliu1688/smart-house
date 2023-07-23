package com.atguigu.config;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author 刘翰林
 * @create 2023-06-19 19:55
 */

/**
 * 未授权的统一处理方式
 * 这里是通过再MyWebSecurityConfig类中的方法设置的
 */
public class CustomAccessDeineHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AccessDeniedException e) throws IOException, ServletException {

        //授权不通过的统一重定向到该界面
        httpServletResponse.sendRedirect("/auth");


    }
}
