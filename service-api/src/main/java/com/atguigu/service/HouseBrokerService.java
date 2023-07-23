package com.atguigu.service;

import com.atguigu.entity.HouseBroker;

import java.util.List;

/**
 * @author 刘翰林
 * @create 2023-06-05 22:41
 */
public interface HouseBrokerService extends BaseService<HouseBroker>{
    /**
     * 通过houseId查找经纪人
     * @date
     * @author
     * @return
     * @throws
     */
    List<HouseBroker> findListByHouseId(Long houseId);
}
