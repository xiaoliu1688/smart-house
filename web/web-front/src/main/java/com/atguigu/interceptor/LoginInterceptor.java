package com.atguigu.interceptor;


import com.atguigu.entity.UserInfo;
import com.atguigu.result.Result;
import com.atguigu.result.ResultCodeEnum;
import com.atguigu.util.WebUtil;
import org.springframework.lang.Nullable;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author 刘翰林
 * @create 2023-06-13 23:17
 */
public class LoginInterceptor implements HandlerInterceptor {

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        UserInfo user = (UserInfo) request.getSession().getAttribute("user");

        if(user == null){
            //说明没有登录
            //设置返回result
            Result<String> result = Result.build("还没有登陆", ResultCodeEnum.LOGIN_AUTH);

            //将result响应给前端界面
            WebUtil.writeJSON(response,result);

            return false;   //不放行
        }


        return true;    //放行
    }

    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable ModelAndView modelAndView) throws Exception {
    }

    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable Exception ex) throws Exception {
    }

}
