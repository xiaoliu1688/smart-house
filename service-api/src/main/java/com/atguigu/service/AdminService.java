package com.atguigu.service;

import com.atguigu.entity.Admin;

import java.util.List;

/**
 * @author 刘翰林
 * @create 2023-04-30 15:37
 */
public interface AdminService extends BaseService<Admin>{
    List<Admin> findAll();

    /**
     * 通过用户名字查找用户
     * @date
     * @author
     * @return
     * @throws
     */
    Admin findAdminByName(String userName);
}
