package com.atguigu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.entity.*;
import com.atguigu.service.*;
import com.github.pagehelper.PageInfo;
import jdk.nashorn.internal.ir.LiteralNode;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;

/**
 * @author 刘翰林
 * @create 2023-06-04 20:13
 */

@Controller
@RequestMapping("/house")
public class HouseController extends BaseController{

    @Reference
    HouseImageService houseImageService;

    @Reference
    HouseUserService houseUserService;

    @Reference
    HouseBrokerService houseBrokerService;

    @Reference
    HouseService houseService;

    @Reference
    CommunityService communityService;

    @Reference
    DictService dictService;

    /**
     * 去房源管理首页
     * @date
     * @author
     * @return
     * @throws
     */

    @RequestMapping
    public String toIndex(Map map, HttpServletRequest request){

        Map<String, Object> filters = getFilters(request);

        map.put("filters",filters);

        PageInfo<House> pageInfo = houseService.findPage(filters);

        map.put("page",pageInfo);


        //查询所有小区
        List<Community> communityList = communityService.findAllCommunity();

        //查询所有户型
        List<Dict> houseTypeList = dictService.findListByDictCode("houseType");

        //查询所有楼层
        List<Dict> floorList = dictService.findListByDictCode("floor");

        //查询所有建筑结构
        List<Dict> buildStructureList = dictService.findListByDictCode("buildStructure");

        //查询所有朝向
        List<Dict> directionList = dictService.findListByDictCode("direction");

        //查询所有装修情况
        List<Dict> decorationList = dictService.findListByDictCode("decoration");

        //查询所有房屋用途
        List<Dict> houseUseList = dictService.findListByDictCode("houseUse");

        map.put("communityList", communityList);
        map.put("houseTypeList", houseTypeList);
        map.put("floorList", floorList);
        map.put("buildStructureList", buildStructureList);
        map.put("directionList", directionList);
        map.put("decorationList", decorationList);
        map.put("houseUseList", houseUseList);

        return "house/index";
    }

    /**
     * 去新增界面
     * @date
     * @author
     * @return
     * @throws
     */
    @RequestMapping("/create")
    public String toCreatePage(Map map){

        //查询所有小区
        List<Community> communityList = communityService.findAllCommunity();

        //查询所有户型
        List<Dict> houseTypeList = dictService.findListByDictCode("houseType");

        //查询所有楼层
        List<Dict> floorList = dictService.findListByDictCode("floor");

        //查询所有建筑结构
        List<Dict> buildStructureList = dictService.findListByDictCode("buildStructure");

        //查询所有朝向
        List<Dict> directionList = dictService.findListByDictCode("direction");

        //查询所有装修情况
        List<Dict> decorationList = dictService.findListByDictCode("decoration");

        //查询所有房屋用途
        List<Dict> houseUseList = dictService.findListByDictCode("houseUse");

        map.put("communityList", communityList);
        map.put("houseTypeList", houseTypeList);
        map.put("floorList", floorList);
        map.put("buildStructureList", buildStructureList);
        map.put("directionList", directionList);
        map.put("decorationList", decorationList);
        map.put("houseUseList", houseUseList);
        return "house/create";
    }

    /**
     * 新增房源
     * @date
     * @author
     * @return
     * @throws
     */
    @RequestMapping("/save")
    public ModelAndView saveHouse(House house){
        ModelAndView mav = new ModelAndView();

        houseService.insert(house);

        mav.setViewName(SUCCESS_PAGE);

        return mav;
    }

    /**
     * 去修改界面
     * @date
     * @author
     * @return
     * @throws
     */
    @RequestMapping("/edit/{id}")
    public ModelAndView toChangePage(@PathVariable("id") Long id){

        ModelAndView mav = new ModelAndView();

        //通过id查找出房源
        House house = houseService.findHouseById(id);
        mav.addObject("house",house);

        //查询所有小区
        List<Community> communityList = communityService.findAllCommunity();

        //查询所有户型
        List<Dict> houseTypeList = dictService.findListByDictCode("houseType");

        //查询所有楼层
        List<Dict> floorList = dictService.findListByDictCode("floor");

        //查询所有建筑结构
        List<Dict> buildStructureList = dictService.findListByDictCode("buildStructure");

        //查询所有朝向
        List<Dict> directionList = dictService.findListByDictCode("direction");

        //查询所有装修情况
        List<Dict> decorationList = dictService.findListByDictCode("decoration");

        //查询所有房屋用途
        List<Dict> houseUseList = dictService.findListByDictCode("houseUse");

        mav.addObject("communityList", communityList);
        mav.addObject("houseTypeList", houseTypeList);
        mav.addObject("floorList", floorList);
        mav.addObject("buildStructureList", buildStructureList);
        mav.addObject("directionList", directionList);
        mav.addObject("decorationList", decorationList);
        mav.addObject("houseUseList", houseUseList);


        mav.setViewName("house/edit");

        return mav;

    }

    /**
     * 修改房源信息
     * @date
     * @author
     * @return
     * @throws
     */
    @RequestMapping("/update")
    public String update(House house){

        houseService.update(house);

        return SUCCESS_PAGE;
    }

    /**
     * 删除房源信息
     * @date
     * @author
     * @return
     * @throws
     */
    @RequestMapping("/delete/{id}")
    public String deleted(@PathVariable("id") Long id){
        houseService.delete(id);
        return "redirect:/house";

    }

    /**
     * 发布或取消发布
     * @date
     * @author
     * @return
     * @throws
     */
    @RequestMapping("/publish/{id}/{status}")
    public String publish(@PathVariable("id") Long id,@PathVariable("status") Integer status){

        //执行发布或取消发布
        houseService.publish(id,status);

        return "redirect:/house";
    }

    /**
     * 跳转并且显示详情界面
     * @date
     * @author
     * @return
     * @throws
     */
    @RequestMapping("/{id}")
    public String toShowPage(@PathVariable("id") Long id,Map map){

        //查找出房屋信息
        House house = houseService.findHouseById(id);

        //将房屋信息放入request域中
        map.put("house",house);

        //查出小区信息
        Community community = communityService.getById(house.getCommunityId());

        //将小区信息放入request域中
        map.put("community",community);


        //查找出房源图片信息将房源图片信息放入请求域中
        List<HouseImage> houseImage1List = houseImageService.findList(house.getId(),1);
        map.put("houseImage1List",houseImage1List);
        List<HouseImage> houseImage2List = houseImageService.findList(house.getId(),2);
        map.put("houseImage2List",houseImage2List);


        //查找出所有房东信息并将房东信息放入请求域中
        List<HouseUser> houseUserList = houseUserService.findListByHouseId(house.getId());
        map.put("houseUserList",houseUserList);


        //查找出所有经纪人信息并将经纪人信息放入请求域中
        List<HouseBroker> houseBrokerList = houseBrokerService.findListByHouseId(house.getId());
        map.put("houseBrokerList",houseBrokerList);

        return "house/show";
    }


}
