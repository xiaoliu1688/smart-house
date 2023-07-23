package com.atguigu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.entity.UserInfo;
import com.atguigu.result.Result;
import com.atguigu.result.ResultCodeEnum;
import com.atguigu.service.UserInfoService;
import com.atguigu.util.MD5;
import com.atguigu.vo.LoginVo;
import com.atguigu.vo.RegisterVo;
import com.github.pagehelper.StringUtil;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.spi.RegisterableService;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;

/**
 * @author 刘翰林
 * @create 2023-06-13 14:21
 */

@RestController
@RequestMapping("/userInfo")
public class UserInfoController {

    @Reference
    UserInfoService userInfoService;

    /**
     * 发送验证码方法
     * @date
     * @author
     * @return
     * @throws
     */
    @RequestMapping("/sendCode/{phoneNum}")
    public Result sendCode(@PathVariable("phoneNum") Long phoneNum, HttpServletRequest request){

        //模拟发送给手机的验证码是8888
        String code = "8888";

        //将验证码放入会话域中，用于等下验证注册
        request.getSession().setAttribute("code",code);

        return Result.ok(code);
    }

    /**
     * 用户注册
     * @date
     * @author
     * @return
     * @throws
     */
    @RequestMapping("/register")
    public Result register(@RequestBody RegisterVo registerVo, HttpServletRequest request){

       //获取会话域中的注册码
        String registerCode = (String) request.getSession().getAttribute("code");

        //获取注册信息
        String code = registerVo.getCode();
        String nickName = registerVo.getNickName();
        String password = registerVo.getPassword();
        String phone = registerVo.getPhone();


        //对注册信息进行验空
        if(StringUtils.isEmpty(code) || StringUtils.isEmpty(nickName) || StringUtils.isEmpty(password) || StringUtils.isEmpty(phone)){

            //如果是空就返回错误信息
            return Result.build(null, ResultCodeEnum.PARAM_ERROR);
        }

        //对注册码进行校验
        if(!code.equals(registerCode)){
            //如果注册码不一致就返回注册码错误信息
            return Result.build(null,ResultCodeEnum.CODE_ERROR);
        }

        //对用户是否已经注册进行校验
        UserInfo userInfo = userInfoService.findUserInfoByPhone(phone);
        if(userInfo != null){
            //如果用户不等于空，说明该用户已经注册过，返回错误信息
            return Result.build(null,ResultCodeEnum.PHONE_REGISTER_ERROR);
        }

        //如果以上验证都通过则返回注册成功信息，并且将用户信息插入数据库表中
        UserInfo userInfo1 = new UserInfo();
        userInfo1.setNickName(nickName);
        userInfo1.setPassword(MD5.encrypt(password));
        userInfo1.setPhone(phone);

        userInfoService.insert(userInfo1);

        return Result.ok();

    }

    /**
     * 实现用户登录
     * @date
     * @author
     * @return
     * @throws
     */
    @RequestMapping("/login")
    public Result login(@RequestBody LoginVo loginVo, HttpSession session){

        //获取登录信息
        String password = loginVo.getPassword();
        String phone = loginVo.getPhone();

        //判断参数是否为空
        if(StringUtils.isEmpty(password) || StringUtils.isEmpty(phone)){
            return Result.build(null,ResultCodeEnum.PARAM_ERROR);

        }

        //判断账号是否存在
        UserInfo userInfo = userInfoService.findUserInfoByPhone(phone);
        if(userInfo == null){
            //如果为空则返回用户不存在参数
            return Result.build(null,ResultCodeEnum.ACCOUNT_ERROR);
        }

        //判断密码是否正确
        if(!userInfo.getPassword().equals(MD5.encrypt(password))){
            //如果密码不正确则返回密码错误参数
            return Result.build(null,ResultCodeEnum.PASSWORD_ERROR);
        }

        //判断用户状态是否锁定
        if(userInfo.getStatus() == 0){
            //如果状态等于0则说明用户状态被锁定，返回被锁定参数
            return Result.build(null,ResultCodeEnum.ACCOUNT_LOCK_ERROR);
        }

        //否则登录成功，将用户信息放入会话域
        session.setAttribute("user",userInfo);

        //返回相应参数
        HashMap<Object, Object> map = new HashMap<>();
        map.put("phone", userInfo.getPhone());
        map.put("nickName", userInfo.getNickName());
        return Result.ok(map);

    }

    /**
     * 退出登录
     * @date
     * @author
     * @return
     * @throws
     */
    @RequestMapping("/logout")
    public Result loginOut(HttpSession session){

        session.removeAttribute("user");

        return Result.ok();
    }

}
