package com.atguigu.dao;

import com.atguigu.entity.House;
import com.atguigu.vo.HouseQueryVo;
import com.atguigu.vo.HouseVo;
import com.github.pagehelper.Page;

/**
 * @author 刘翰林
 * @create 2023-06-04 20:24
 */
public interface HouseDao extends BaseDao<House>{
    House findHouseById(Long id);

    /**
     * 前端分页及带条件查询
     * @date
     * @author
     * @return
     * @throws
     */
    Page<HouseVo> findPageList(HouseQueryVo houseQueryVo);
}
