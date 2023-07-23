package com.atguigu.service;

import com.atguigu.entity.House;
import com.atguigu.vo.HouseQueryVo;
import com.atguigu.vo.HouseVo;
import com.github.pagehelper.PageInfo;

/**
 * @author 刘翰林
 * @create 2023-06-04 20:22
 */
public interface HouseService extends BaseService<House>{

    /**
     * 通过id查找房源信息
     * @date
     * @author
     * @return
     * @throws
     */
    public House findHouseById(Long id);

    /**
     * 发布或取消发布
     * @date
     * @author
     * @return
     * @throws
     */
    public void publish(Long id,Integer status);

    /**
     * 前端页面分页以及待条件查询方法
     * @date
     * @author
     * @return
     * @throws
     */
    PageInfo<HouseVo> findPageList(Integer pageNum, Integer pageSize, HouseQueryVo houseQueryVo);
}
