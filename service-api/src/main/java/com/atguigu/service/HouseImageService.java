package com.atguigu.service;

import com.atguigu.entity.HouseImage;
import com.atguigu.service.Impl.BaseServiceImpl;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author 刘翰林
 * @create 2023-06-05 22:40
 */
public interface HouseImageService extends BaseService<HouseImage> {

    /**
     * 通过房源id和类型查找房源图片
     * @date
     * @author
     * @return
     * @throws
     */
    List<HouseImage> findList(Long houseId, Integer type);
}
