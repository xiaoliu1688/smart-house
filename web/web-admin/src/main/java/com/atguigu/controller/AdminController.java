package com.atguigu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.entity.Admin;
import com.atguigu.service.AdminService;
import com.atguigu.service.RoleService;
import com.atguigu.util.QiniuUtils;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Map;
import java.util.UUID;

/**
 * @author 刘翰林
 * @create 2023-04-30 15:35
 */
@Controller
@RequestMapping("/admin")
public class AdminController extends BaseController{

    @Reference
    AdminService adminService;

    @Reference
    RoleService roleService;

    @Resource
    PasswordEncoder passwordEncoder;

    @RequestMapping
    public ModelAndView toIndex( HttpServletRequest request){
        //获取页面提交的分页参数及请求参数
        Map<String, Object> filters = getFilters(request);

        ModelAndView modelAndView = new ModelAndView();

        //将filters放入请求域中，因为在index页面中需要用到filters
        modelAndView.addObject("filters",filters);

        //开启分页
        PageInfo<Admin> page = adminService.findPage(filters);

        //把页面信息封装
        modelAndView.addObject("page",page);

        //设置跳转的视图名称
        modelAndView.setViewName("admin/index");

        return modelAndView;
    }

    /**
     * 去添加页面
     * @date
     * @author
     * @return
     * @throws
     */
    @RequestMapping("/create")
    public String toAddPage(){

        return "admin/create";
    }

    /**
     * 添加用户
     * @date
     * @author
     * @return
     * @throws
     */
    @RequestMapping("/save")
    public String saveAdmin(Admin admin){
        //调用service中的保存的方法

        admin.setPassword(passwordEncoder.encode(admin.getPassword()));

        adminService.insert(admin);
        return SUCCESS_PAGE;
    }

    /**
     * 删除用户
     * @date
     * @author
     * @return
     * @throws
     */
    @PreAuthorize("hasAnyAuthority('admin.delete')")   //这个注解是控制权限的，只有有该权限的用户才能执行这个方法
    @RequestMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id){
        //调用service的删除方法
        adminService.delete(id);
        return "redirect:/admin";
    }

    /**
     * 跳转到修改界面
     * @date
     * @author
     * @return
     * @throws
     */
    @RequestMapping("/edit/{id}")
    public ModelAndView toEditPage(@PathVariable("id") Long id){

        //先根据id查找出用户的信息
        Admin admin = adminService.getById(id);

        ModelAndView mav = new ModelAndView();
        mav.addObject("admin",admin);
        mav.setViewName("admin/edit");

        return mav;

    }

    /**
     * 修改用户信息
     * @date
     * @author
     * @return
     * @throws
     */
    @RequestMapping("/update")
    public String update(Admin admin){
        //调用update方法
        adminService.update(admin);

        //跳转到首页
        return SUCCESS_PAGE;
    }

    /**
     * 去上传用户头像界面
     * @date
     * @author
     * @return
     * @throws
     */
    @RequestMapping("/uploadShow/{id}")
    public String toUploadPage(@PathVariable("id") Long id,Map map){

        map.put("id",id);       //此处的id就是用户的id

        return "admin/upload";
    }

    /**
     * 上传用户头像
     * @date
     * @author
     * @return
     * @throws
     */
    @RequestMapping("/upload/{id}")
    public String upload(@PathVariable("id") Long id, MultipartFile file){

        try {
            //获取字节流，此处具体解析可看房源图片上传
            byte[] bytes = file.getBytes();

            //通过用户id获取用户对象
            Admin admin = adminService.getById(id);

            //通过uuid随机获取一个文件名字
            String fileName = UUID.randomUUID().toString();

            //将图片上传到七牛云
            QiniuUtils.upload2Qiniu(bytes,fileName);

            //将用户信息更新
            admin.setHeadUrl("http://rvvm97mge.hn-bkt.clouddn.com/" + fileName);//此处是头像的url地址

            adminService.update(admin);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return SUCCESS_PAGE;
    }

    /**
     * 去分配角色界面
     * @date
     * @author
     * @return
     * @throws
     */
    @RequestMapping("/assignShow/{adminId}")
    public String toAssignShowPage(@PathVariable("adminId") Long adminId, ModelMap map){

        map.addAttribute("adminId",adminId);

        //通过用户id查找出已分配的角色和未分配的角色
        Map<String,Object> roleMap = roleService.findRoleByAdminId(adminId);

        map.addAllAttributes(roleMap);

        return "admin/assignShow";
    }

    /**
     * 分配角色
     * @date
     * @author
     * @return
     * @throws
     */

    @RequestMapping("/assignRole")
    public String assignRole(Long adminId,Long[] roleIds){

        //调用分配角色的方法
        roleService.assignRole(adminId,roleIds);

        return SUCCESS_PAGE;
    }






}
