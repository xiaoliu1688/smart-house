package com.atguigu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.entity.Role;
import com.atguigu.service.PermissionService;
import com.atguigu.service.RoleService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * @author 刘翰林
 * @create 2023-04-17 0:22
 */


@RequestMapping("/role")
@Controller
public class RoleController extends BaseController {

    @Reference
    PermissionService permissionService;

    @Reference
    RoleService roleService;


    /**
     * 查询所有并跳转到首页
     * @date
     * @author
     * @return
     * @throws
     */
//    @RequestMapping
//    public String findAllRole(Map map){
//        List<Role> roles = roleService.findAllRole();
//
//        //将roles入参
//        map.put("list",roles);
//
//        return "role/index";
//
//    }

    /**
     * 查询并进行分页
     * @date
     * @author
     * @return
     * @throws
     */
    @RequestMapping
    public String findAllRole(Map map, HttpServletRequest request){
        //获取页面提交的分页参数及请求参数
        Map<String, Object> filters = getFilters(request);

        //将filters放入请求域中，因为在index页面中需要用到filters
        map.put("filters",filters);

        //调用roleService中分页及带条件查询的方法
        PageInfo<Role> pageInfo = roleService.findPage(filters);

        //将pageInfo放入请求域中
        map.put("page",pageInfo);


        return "role/index";
    }

    /**
     * 跳转到增加角色页面
     * @date
     * @author
     * @return
     * @throws
     */
    @RequestMapping("/create")
    public String toAddPage(){
        return "role/create";
    }

    /**
     * 添加角色
     * @date
     * @author
     * @return
     * @throws
     */
    @RequestMapping("/save")
    public String addRole(Role role){

        roleService.insert(role);

        //重定向到首页，不推荐使用
//        return "redirect:/role";

        return SUCCESS_PAGE;
    }


    /**
     * 删除角色
     * @date
     * @author
     * @return
     * @throws
     */
    //这里入参的名称必须是{id}
    @RequestMapping("/delete/{roleId}")
    public String delete(@PathVariable("roleId") Long roleId){

        roleService.delete(roleId);

        //删除后重定向到首页
        return "redirect:/role";
    }

    /**
     * 跳转到修改页面并查出角色信息
     * @date
     * @author
     * @return
     * @throws
     */
    @RequestMapping("/edit/{roleId}")
    public ModelAndView toEditPage(@PathVariable("roleId") Long id){
        ModelAndView mav = new ModelAndView();
        //查出角色信息
        Role role = roleService.getById(id);

        mav.addObject("role",role);
        mav.setViewName("role/edit");
        return mav;
    }

    /**
     * 完成修改功能
     * @date
     * @author
     * @return
     * @throws
     */
    @RequestMapping("/update")
    public String update(Role role){

        //调用service修改
        roleService.update(role);
//      跳转到成功页面
        return SUCCESS_PAGE;
    }

    /**
     * 去给角色分配权限界面
     * @date
     * @author
     * @return
     * @throws
     */
    @RequestMapping("/assignShow/{id}")
    public String toAssignShowPage(@PathVariable("id") Long roleId,Map map){

        map.put("roleId",roleId);
        //zNode的数据类型
        // var zNodes =[
        //     { id:1, pId:0, name:"随意勾选 1", open:true},
        //     { id:11, pId:1, name:"随意勾选 1-1", open:true},
        //     { id:111, pId:11, name:"随意勾选 1-1-1"},
        //通过service查找zNode的数据类型
        List<Map<String,Object>> zNodes = permissionService.findZNodeByRoleId(roleId);

        map.put("zNodes",zNodes);
        return "role/assginShow";
    }

    /**
     * 给角色分配权限
     * @date
     * @author
     * @return
     * @throws
     */
    @RequestMapping("/assignPermission")
    public String assignPermission(Long roleId,Long[] permissionIds){

        //调用service分配权限
        permissionService.assignRolePermission(roleId,permissionIds);

        return SUCCESS_PAGE;
    }



}
