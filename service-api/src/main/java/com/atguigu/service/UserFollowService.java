package com.atguigu.service;

import com.atguigu.vo.UserFollowVo;
import com.github.pagehelper.PageInfo;

/**
 * @author 刘翰林
 * @create 2023-06-13 19:47
 */
public interface UserFollowService {
    /**
     * 关注房源
     * @date
     * @author
     * @return
     * @throws
     */
    void follow(Long userId, Long houseId);

    /**
     * 判断房源是否已经关注
     * @date
     * @author
     * @return  true表示已经关注，false表示未关注
     * @throws
     */
    boolean isFollow(Long houseId,Long userId);


    /**
     * 分页并查询
     * @date
     * @author
     * @return
     * @throws
     */
    PageInfo<UserFollowVo> findListPage(Integer pageNum, Integer pageSize, Long userId);

    /**
     * 取消关注
     * @date
     * @author
     * @return
     * @throws
     */
    void cancelFollow(Long id);
}
