package com.atguigu.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.atguigu.dao.BaseDao;
import com.atguigu.dao.CommunityDao;
import com.atguigu.dao.DictDao;
import com.atguigu.entity.Community;
import com.atguigu.entity.Dict;
import com.atguigu.service.CommunityService;
import com.atguigu.service.DictService;
import com.atguigu.service.Impl.BaseServiceImpl;
import com.atguigu.util.CastUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @author 刘翰林
 * @create 2023-06-03 14:57
 */

@Transactional      //开始事务
@Service(interfaceClass = CommunityService.class)    //发布为服务
public class CommunityServiceImpl extends BaseServiceImpl<Community> implements CommunityService {

    @Autowired
    private CommunityDao communityDao;

    @Autowired
    private DictDao dictDao;

    @Override
    public PageInfo<Community> findPage(Map<String, Object> filters) {

        //获取每页的条目数，这里两处的defaultValue的就是当filters的get的为空时默认赋值，这是工具类，具体可以看尚好房day06第九集
        int pageSize = CastUtil.castInt(filters.get("pageSize"),3);
        //获取当前页码
        int pageNum = CastUtil.castInt(filters.get("pageNum"),1);

        //通过分页插件开启分页
//            pageNum：当前页码
//            pages：总页数【计算：总页数=总数据数量/每页显示数据数量】
//            total：总记录（数据）数，会通过SQL查询，所以执行一次分页操作，相当于执行了两次SQL语句
//            pageSize：每页显示记录（数据）数量
//            List<T> result(封装前) / list(封装后)：当前页显示的记录（数据）集合
        PageHelper.startPage(pageNum,pageSize);

        //调用dao层的分页查询方法
        //page继承ArrayList，其实就是一个list
        Page<Community> page = getEntityDao().findPage(filters);

        for(Community community : page){

            String areaName = dictDao.getNameById(community.getAreaId()); //通过区域id获取区域名
            String plateName = dictDao.getNameById(community.getPlateId());   //通过板块id获取板块名

            community.setAreaName(areaName);
            community.setPlateName(plateName);
        }


        //navigatePages的意思就是导航栏显示多少页
        PageInfo<Community> pageInfo = new PageInfo<Community>(page,5);
        return pageInfo;
    }

    @Override
    protected BaseDao<Community> getEntityDao() {
        return communityDao;
    }


    @Override
    public List<Community> findAllCommunity() {
        return communityDao.findAllCommunity();
    }

    @Override
    public Community getById(Serializable id) {

        //先通过id查找出小区信息

        Community community = communityDao.getById(id);

        //再利用字典通过区域编码和板块编码的查出区域和板块的名字

        //查找出区域名字
        String areaName = dictDao.getNameById(community.getAreaId());

        //查找出板块名字
        String plateName = dictDao.getNameById(community.getPlateId());

        //将小区的区域和板块名赋值
        community.setAreaName(areaName);
        community.setPlateName(plateName);


        return community;
    }
}
