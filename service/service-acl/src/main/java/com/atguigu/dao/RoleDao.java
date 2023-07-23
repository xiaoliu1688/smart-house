package com.atguigu.dao;

import com.atguigu.entity.Role;

import java.util.List;

/**
 * @author 刘翰林
 * @create 2023-04-17 0:13
 */
public interface RoleDao extends BaseDao<Role> {
    /**
     *
     * 查找所有角色
     * @date
     * @author
     * @return
     * @throws
     */
    List<Role> findAllRole();


}
