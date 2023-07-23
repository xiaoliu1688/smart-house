package com.atguigu.service;

import com.atguigu.entity.Community;

import java.util.List;

/**
 * @author 刘翰林
 * @create 2023-06-03 14:55
 */
public interface CommunityService extends BaseService<Community>{

    List<Community> findAllCommunity();
}
