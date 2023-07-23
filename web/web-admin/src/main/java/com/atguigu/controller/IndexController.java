package com.atguigu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.entity.Admin;
import com.atguigu.entity.Permission;
import com.atguigu.service.AdminService;
import com.atguigu.service.PermissionService;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;

/**
 * @author 刘翰林
 * @create 2023-04-17 23:46
 */

@Controller
public class IndexController {

    @Reference
    AdminService adminService;

    @Reference
    PermissionService permissionService;


    /**
     * 跳转到首页
     * @date
     * @author
     * @return
     * @throws
     */
//    @RequestMapping("/")
//    public String toIndex(){
//
//        return "frame/index";
//    }

    @RequestMapping("/")
    public String toIndex(Map map){

        //设置用户id
//        Long adminId = 1l;
        //通过id查找用户
//        Admin admin = adminService.getById(adminId);
        SecurityContext context = SecurityContextHolder.getContext();
        User user = (User) context.getAuthentication().getPrincipal();
        String username = user.getUsername();
        Admin admin = adminService.findAdminByName(username);

        map.put("admin",admin);

        //获取用户的所有权限菜单
        List<Permission> permissionList = permissionService.findMenuPermissionByAdminId(admin.getId());

        map.put("permissionList",permissionList);

        return "frame/index";
    }

    @RequestMapping("/main")
    public String toMain(){
        return "frame/main";
    }

    /**
     * 去登录界面
     * @date
     * @author
     * @return
     * @throws
     */

    @RequestMapping("/login")
    public String toLogin(){

        return "frame/login";
    }

    /**
     * 去没有权限的提示页面
     * @date
     * @author
     * @return
     * @throws
     */
    @RequestMapping("/auth")
    public String toAuth(){

        return "frame/auth";
    }


}
