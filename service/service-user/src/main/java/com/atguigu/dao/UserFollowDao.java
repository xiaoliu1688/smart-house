package com.atguigu.dao;

import com.atguigu.entity.UserFollow;
import com.atguigu.vo.UserFollowVo;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Param;

/**
 * @author 刘翰林
 * @create 2023-06-13 19:49
 */
public interface UserFollowDao extends BaseDao<UserFollow>{
    /**
     * 获取关注的条目数
     * @date
     * @author
     * @return
     * @throws
     */
    Integer getCountByHouseIdAndUserId(@Param("houseId") Long houseId, @Param("userId") Long userId);

    /**
     * 调用分页并查询的方法
     * @date
     * @author
     * @return
     * @throws
     */
    Page<UserFollowVo> findListPage(Long userId);
}
