package com.atguigu.service;

import com.atguigu.entity.UserInfo;

/**
 * @author 刘翰林
 * @create 2023-06-13 13:38
 */
public interface UserInfoService extends BaseService<UserInfo>{
    /**
     * 通过手机号查找用户信息
     * @date
     * @author
     * @return
     * @throws
     */
    UserInfo findUserInfoByPhone(String phone);
}
