package com.atguigu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.entity.House;
import com.atguigu.entity.HouseUser;
import com.atguigu.service.HouseUserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Map;


/**
 * @author 刘翰林
 * @create 2023-06-06 19:16
 */
@Controller
@RequestMapping("/houseUser")
public class HouseUserController extends BaseController{

    @Reference
    HouseUserService houseUserService;

    /**
     * 去新增房东页面
     * @date
     * @author
     * @return
     * @throws
     */
    @RequestMapping("/create")
    public ModelAndView toCreatePage(@RequestParam("houseId") Long houseId){

        ModelAndView mav = new ModelAndView();

        mav.setViewName("houseUser/create");

        mav.addObject("houseId",houseId);

        return mav;
    }

    /**
     * 添加房东
     * @date
     * @author
     * @return
     * @throws
     */
    @RequestMapping("/save")
    public String createHouseUser(HouseUser houseUser){

        houseUserService.insert(houseUser);

        return SUCCESS_PAGE;
    }

    /**
     * 删除房东
     * @date
     * @author
     * @return
     * @throws
     */

    @RequestMapping("/delete/{houseId}/{id}")
    public String delete(@PathVariable("houseId") Long houseId,@PathVariable("id") Long id){

        houseUserService.delete(id);

        return "redirect:/house/" + houseId;
    }

    /**
     * 去修改界面
     * @date
     * @author
     * @return
     * @throws
     */
    @RequestMapping("/edit/{id}")
    public String toEditPage(@PathVariable("id") Long id, Map map){  //此id是房东的id

        HouseUser houseUser = houseUserService.getById(id);

        map.put("houseUser",houseUser);

        return "houseUser/edit";

    }
    /**
     * 修改房东信息
     * @date
     * @author
     * @return
     * @throws
     */
    @RequestMapping("/update")
    public String update(HouseUser houseUser){

        houseUserService.update(houseUser);

        return SUCCESS_PAGE;
    }

}
