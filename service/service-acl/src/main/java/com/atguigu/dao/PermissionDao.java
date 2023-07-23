package com.atguigu.dao;

import com.atguigu.entity.Permission;

import java.util.List;

/**
 * @author 刘翰林
 * @create 2023-06-16 13:06
 */
public interface PermissionDao extends BaseDao<Permission> {
    /**
     * 查找出所有权限
     * @date
     * @author
     * @return
     * @throws
     */
    List<Permission> findAllPermission();

    /**
     * 通过用户id查找所有权限菜单
     * @date
     * @author
     * @return
     * @throws
     */
    List<Permission> findMenuPermissionByAdminId(Long adminId);

    /**
     * 查找所有权限码
     * @date
     * @author
     * @return
     * @throws
     */
    List<String> findAllPermissionCode();

    /**
     * 通过用户id查找权限码
     * @date
     * @author
     * @return
     * @throws
     */
    List<String> findPermissionCodeByAdminId(Long adminId);
}
