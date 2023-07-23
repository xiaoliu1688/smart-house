package com.atguigu.dao;

import com.atguigu.entity.HouseImage;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author 刘翰林
 * @create 2023-06-05 22:14
 */

public interface HouseImageDao extends BaseDao<HouseImage>{

    /**
     * 通过房源id和类型查找房源图片
     * @date
     * @author
     * @return
     * @throws
     */
    List<HouseImage> findList(@Param("houseId") Long houseId, @Param("type") Integer type);
}
