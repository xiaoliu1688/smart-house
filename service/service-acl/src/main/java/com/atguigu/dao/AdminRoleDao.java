package com.atguigu.dao;

import com.atguigu.entity.AdminRole;

import java.util.List;

/**
 * @author 刘翰林
 * @create 2023-06-16 10:21
 */
public interface AdminRoleDao extends BaseDao<AdminRole>{

    /**
     * 通过用户id查找出已经分配的角色
     * @date
     * @author
     * @return
     * @throws
     */
    List<Long> findRoleByAdminId(Long adminId);

    /**
     * 根据用户id删除所有角色
     * @date
     * @author
     * @return
     * @throws
     */
    void deleteByAdminId(Long adminId);
}
