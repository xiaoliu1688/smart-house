package com.atguigu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.atguigu.entity.UserInfo;
import com.atguigu.result.Result;
import com.atguigu.service.UserFollowService;
import com.atguigu.vo.UserFollowVo;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.aopalliance.intercept.Interceptor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

/**
 * @author 刘翰林
 * @create 2023-06-13 19:32
 */

@RestController
@RequestMapping("/userFollow")
public class UserFollowController {


    @Reference
    UserFollowService userFollowService;

    /**
     * 关注房源方法
     * @date
     * @author
     * @return
     * @throws
     */

    @RequestMapping("/auth/follow/{id}")
    public Result follow(@PathVariable("id") Long houseId, HttpSession session){

        UserInfo userInfo = (UserInfo) session.getAttribute("user");

        Long userId = userInfo.getId();

        //调用service的关注方法
        userFollowService.follow(userId,houseId);

        return Result.ok();

    }

    /**
     * 分页查询我的关注
     * @date
     * @author
     * @return
     * @throws
     */

    @RequestMapping("/auth/list/{pageNum}/{pageSize}")
    public Result findListPage(@PathVariable("pageNum") Integer pageNum,@PathVariable("pageSize") Integer pageSize,HttpSession session){

        //获取已经登录的用户
        UserInfo user = (UserInfo) session.getAttribute("user");

        //获取该用户的id
        Long userId = user.getId();

        //分页查询该用户的关注
        PageInfo<UserFollowVo> pageInfo = userFollowService.findListPage(pageNum,pageSize,userId);
        return Result.ok(pageInfo);

    }

    /**
     * 取消关注
     * @date
     * @author
     * @return
     * @throws
     */

    @RequestMapping("/auth/cancelFollow/{id}")
    public Result cancelFollow(@PathVariable("id") Long id){

        userFollowService.cancelFollow(id);
        return Result.ok();
    }
}
