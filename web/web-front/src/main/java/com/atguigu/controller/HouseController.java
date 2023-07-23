package com.atguigu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.entity.*;
import com.atguigu.result.Result;
import com.atguigu.service.*;
import com.atguigu.vo.HouseQueryVo;
import com.atguigu.vo.HouseVo;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 刘翰林
 * @create 2023-06-12 17:11
 */
@RestController
@RequestMapping("/house")
public class HouseController {

    @Reference
    HouseService houseService;

    @Reference
    CommunityService communityService;

    @Reference
    HouseImageService houseImageService;

    @Reference
    HouseBrokerService houseBrokerService;

    @Reference
    UserFollowService userFollowService;

    /**
     * 分页及待条件查询方法
     * @date
     * @author
     * @return
     * @throws
     */
    @RequestMapping("/list/{pageNum}/{pageSize}")
    public Result findPageList(@PathVariable("pageSize") Integer pageSize,@PathVariable("pageNum") Integer pageNum,
                               @RequestBody HouseQueryVo houseQueryVo){

        //调用houseService中前端分页及带条件查询方法
        PageInfo<HouseVo> pageInfo = houseService.findPageList(pageNum,pageSize,houseQueryVo);

        return Result.ok(pageInfo);

    }

    /**
     * 查询房源具体信息
     * @date
     * @author
     * @return
     * @throws
     */
    @RequestMapping("/info/{id}")
    public Result getHouseInfo(@PathVariable("id") Long id, HttpSession session){

        //获取房源详细信息
        House house = houseService.findHouseById(id);

        //获取小区信息
        Community community = communityService.getById(house.getCommunityId());

        //获取经纪人
        List<HouseBroker> houseBrokerList = houseBrokerService.findListByHouseId(house.getId());

        //获取房源图片
        List<HouseImage> houseImage1List = houseImageService.findList(id, 1);

        HashMap<Object, Object> map = new HashMap<>();

        map.put("house",house);
        map.put("community",community);
        map.put("houseBrokerList",houseBrokerList);
        map.put("houseImage1List",houseImage1List);

        //设置默认没有关注
//        map.put("isFollow",false);

        boolean isFollow = false;

        //从会话域获得登录的信息
        UserInfo user = (UserInfo) session.getAttribute("user");

        if(user != null){
            //如果user不等于空，则说明已经登录
            Long userId = user.getId();

            //通过houseId和userId判断是否已经关注
            isFollow = userFollowService.isFollow(id,userId);

        }
        map.put("isFollow",isFollow);

        return Result.ok(map);

    }


}
