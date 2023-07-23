package com.atguigu.dao;

import com.atguigu.entity.RolePermission;

import java.util.List;

/**
 * @author 刘翰林
 * @create 2023-06-16 13:10
 */
public interface RolePermissionDao extends BaseDao<RolePermission>{
    /**
     * 通过角色id查找该角色所拥有的权限
     * @date
     * @author
     * @return
     * @throws
     */
    List<Long> findRolePerByRoleId(Long roleId);

    /**
     * 删除所有权限
     * @date
     * @author
     * @return
     * @throws
     */
    void deletePermissionByRoleId(Long roleId);
}
