package com.atguigu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.entity.Permission;
import com.atguigu.service.PermissionService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @author 刘翰林
 * @create 2023-06-18 9:38
 */

@RequestMapping("/permission")
@Controller
public class PermissionController extends BaseController{

    @Reference
    PermissionService permissionService;

    /**
     * 去菜单管理首页
     * @date
     * @author
     * @return
     * @throws
     */
    @RequestMapping
    public String toMenuIndex(Map map){

        //将所有菜单查出
        List<Permission> list = permissionService.findAll();

        map.put("list",list);

        return "permission/index";

    }

    /**
     * 去新增页面
     * @date
     * @author
     * @return
     * @throws
     */
    @RequestMapping("/create")
    public String toCreatePage(Permission permission ,Map map){


        map.put("permission",permission);


        return "permission/create";
    }

    /**
     * 新增菜单
     * @date
     * @author
     * @return
     * @throws
     */
    @RequestMapping("/save")
    public String saveMenu(Permission permission){
        permissionService.insert(permission);

        return SUCCESS_PAGE;
    }

    /**
     * 去修改界面
     * @date
     * @author
     * @return
     * @throws
     */

    @RequestMapping("/edit/{id}")
    public String toEditPage(@PathVariable("id") Long id,Map map){

        Permission permission = permissionService.findMenuById(id);

        map.put("permission",permission);
        return "permission/edit";

    }

    /**
     * 修改菜单信息
     * @date
     * @author
     * @return
     * @throws
     */
    @RequestMapping("/update")
    public String update(Permission permission){

        permissionService.update(permission);

        return SUCCESS_PAGE;

    }

    /**
     * 删除菜单信息
     * @date
     * @author
     * @return
     * @throws
     */

    @RequestMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id){
        permissionService.delete(id);

        return "redirect:/permission";
    }

}
