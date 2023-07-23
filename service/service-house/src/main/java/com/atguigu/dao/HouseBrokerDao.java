package com.atguigu.dao;

import com.atguigu.entity.HouseBroker;

import java.util.List;

/**
 * @author 刘翰林
 * @create 2023-06-05 22:03
 */
public interface HouseBrokerDao extends BaseDao<HouseBroker>{

    /**
     * 通过houseId查找经纪人
     * @date
     * @author
     * @return
     * @throws
     */
    List<HouseBroker> findListByHouseId(Long houseId);

}
