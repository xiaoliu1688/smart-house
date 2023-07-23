package com.atguigu.dao;

import com.atguigu.entity.Community;

import java.util.List;

/**
 * @author 刘翰林
 * @create 2023-06-03 14:38
 */
public interface CommunityDao extends BaseDao<Community> {
    /**
     * 查找所有小区
     * @date
     * @author
     * @return
     * @throws
     */
    List<Community> findAllCommunity();
}
