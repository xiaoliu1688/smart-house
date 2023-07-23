package com.atguigu.service;

import com.atguigu.entity.Permission;

import java.util.List;
import java.util.Map;

/**
 * @author 刘翰林
 * @create 2023-06-16 13:05
 */
public interface PermissionService extends BaseService<Permission>{

    //通过RoleId查找所有权限节点
    List<Map<String, Object>> findZNodeByRoleId(Long roleId);

    /**
     * 给角色分配权限
     * @date
     * @author
     * @return
     * @throws
     */
    void assignRolePermission(Long roleId, Long[] permissionIds);

    /**
     * 通过用户id查找所有权限菜单
     * @date
     * @author
     * @return
     * @throws
     */
    List<Permission> findMenuPermissionByAdminId(Long adminId);

    /**
     * 查找所有权限菜单
     * @date
     * @author
     * @return
     * @throws
     */
    List<Permission> findAll();


    /**
     * 通过id查找菜单信息
     * @date
     * @author
     * @return
     * @throws
     */
    Permission findMenuById(Long id);

    /**
     * 通过用户id查找权限码
     * @date
     * @author
     * @return
     * @throws
     */
    List<String> findPermissionCodeByAdminId(Long adminId);
}
