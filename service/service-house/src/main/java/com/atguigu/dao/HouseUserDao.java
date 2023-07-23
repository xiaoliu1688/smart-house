package com.atguigu.dao;

import com.atguigu.entity.HouseUser;

import java.util.List;

/**
 * @author 刘翰林
 * @create 2023-06-05 22:17
 */
public interface HouseUserDao extends BaseDao<HouseUser>{

    /**
     * 根据房源id查找房东
     * @date
     * @author
     * @return
     * @throws
     */
    List<HouseUser> findListByHouseId(Long houseId);
}
