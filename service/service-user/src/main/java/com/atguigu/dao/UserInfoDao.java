package com.atguigu.dao;

import com.atguigu.entity.UserInfo;

/**
 * @author 刘翰林
 * @create 2023-06-13 13:38
 */
public interface UserInfoDao extends BaseDao<UserInfo>{
    /**
     * 通过手机号获取用户信息
     * @date
     * @author
     * @return
     * @throws
     */
    UserInfo findUserInfoByPhone(String phone);
}
