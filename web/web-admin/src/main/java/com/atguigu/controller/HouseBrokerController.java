package com.atguigu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.entity.Admin;
import com.atguigu.entity.HouseBroker;
import com.atguigu.service.AdminService;
import com.atguigu.service.HouseBrokerService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Map;

/**
 * @author 刘翰林
 * @create 2023-06-05 23:51
 */
@Controller
@RequestMapping("/houseBroker")
public class HouseBrokerController extends BaseController{

    @Reference
    AdminService adminService;

    @Reference
    HouseBrokerService houseBrokerService;

    /**
     * 去新增经纪人界面
     * @date
     * @author
     * @return
     * @throws
     */
    @RequestMapping("/create")
    public String toCreatePage(@RequestParam("houseId") Long houseId , Map map){


        map.put("houseId",houseId);

        //查找出所有可选经纪人，也就是所有管理员
        List<Admin> adminList = adminService.findAll();

        //将可选经纪人放入request域中
        map.put("adminList",adminList);

        return "houseBroker/create";
    }



    /**
     * 去修改经纪人界面
     * @date
     * @author
     * @return
     * @throws
     */
    @RequestMapping("/edit/{id}")
    public ModelAndView toEditPage(@PathVariable("id") Long id){    //该id就是主键的id

        ModelAndView mav = new ModelAndView();

        List<Admin> adminList = adminService.findAll();

        mav.addObject("adminList",adminList);

        HouseBroker houseBroker = houseBrokerService.getById(id);

        mav.addObject("houseBroker",houseBroker);

        mav.setViewName("houseBroker/edit");

        return mav;
    }

    /**
     * 修改经纪人
     * @date
     * @author
     * @return
     * @throws
     */
    @RequestMapping("/update")
    public String update(HouseBroker houseBroker){

        //通过id获取完整信息
        Admin admin = adminService.getById(houseBroker.getBrokerId());
        houseBroker.setBrokerName(admin.getName());
        houseBroker.setBrokerHeadUrl(admin.getHeadUrl());

        //更新house_broker表，该表其实是记录了经纪人和房源的关系
        houseBrokerService.update(houseBroker);

        return SUCCESS_PAGE;
    }

    /**
     * 添加经纪人
     * @date
     * @author
     * @return
     * @throws
     */
    @RequestMapping("/save")
    public String createBroker(HouseBroker houseBroker){    //注意此时只入参了两个参数

        //利用adminService查找经纪人完整信息
        Admin admin = adminService.getById(houseBroker.getBrokerId());
        houseBroker.setBrokerName(admin.getName());
        houseBroker.setBrokerHeadUrl(admin.getHeadUrl());

        //将经纪人添加到hse_house_broker表中中

        houseBrokerService.insert(houseBroker);

        return SUCCESS_PAGE;
    }

    /**
     * 删除经纪人
     * @date
     * @author
     * @return
     * @throws
     */

    @RequestMapping("/delete/{houseId}/{id}")
    public String delete(@PathVariable("houseId") Long houseId,@PathVariable("id") Long id){

        houseBrokerService.delete(id);

        return "redirect:/house/" + houseId;
    }


}
