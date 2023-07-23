package com.atguigu.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.atguigu.dao.BaseDao;
import com.atguigu.dao.UserFollowDao;
import com.atguigu.entity.UserFollow;
import com.atguigu.entity.UserInfo;
import com.atguigu.service.DictService;
import com.atguigu.service.Impl.BaseServiceImpl;
import com.atguigu.service.UserFollowService;
import com.atguigu.vo.UserFollowVo;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author 刘翰林
 * @create 2023-06-13 19:47
 */

@Service(interfaceClass = UserFollowService.class)
@Transactional
public class UserFollowServiceImpl extends BaseServiceImpl<UserFollow> implements UserFollowService {

    @Resource
    UserFollowDao userFollowDao;

    @Reference
    DictService dictService;

    @Override
    protected BaseDao<UserFollow> getEntityDao() {
        return userFollowDao;
    }



    @Override
    public void follow(Long userId, Long houseId) {
        //创建一个关注房源对象
        UserFollow userFollow = new UserFollow();

        userFollow.setUserId(userId);
        userFollow.setHouseId(houseId);

        //在调用插入关注房源信息表
        userFollowDao.insert(userFollow);

    }

    @Override
    public boolean isFollow(Long houseId,Long userId) {

        Integer count = userFollowDao.getCountByHouseIdAndUserId(houseId,userId);

        if(count > 0){
            //则说明已经关注
            return true;
        }
        return false;
    }

    @Override
    public PageInfo<UserFollowVo> findListPage(Integer pageNum, Integer pageSize, Long userId) {

        //开启分页
        PageHelper.startPage(pageNum, pageSize);    //设置分页参数,设置定位在哪一页和每页的大小

        //调用分页并查询的方法
        Page<UserFollowVo> page = userFollowDao.findListPage(userId);//通过用户id查找所有关注条目
        List<UserFollowVo> list = page.getResult();
        //这里需要调用dict来查找房屋朝向等信息，因为dao层查出来的是编号
        for(UserFollowVo userFollowVo : list){

            userFollowVo.setDirectionName(dictService.getNameById(userFollowVo.getDirectionId()));
            userFollowVo.setFloorName(dictService.getNameById(userFollowVo.getFloorId()));
            userFollowVo.setHouseTypeName(dictService.getNameById(userFollowVo.getHouseTypeId()));

        }


        return new PageInfo<>(page,5);  //pageInfo里封装了页面数据以及分页信息
    }

    @Override
    public void cancelFollow(Long id) {
        userFollowDao.delete(id);
    }


}
