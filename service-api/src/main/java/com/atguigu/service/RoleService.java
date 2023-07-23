package com.atguigu.service;

import com.atguigu.entity.Role;
import com.atguigu.service.BaseService;

import java.util.List;
import java.util.Map;

/**
 * @author 刘翰林
 * @create 2023-04-17 0:16
 */
public interface RoleService extends BaseService<Role> {

    /**
     * 查找所有角色
     * @date
     * @author
     * @return
     * @throws
     */
    List<Role> findAllRole();

    /**
     * 通过用户id查找角色
     * @date
     * @author
     * @return
     * @throws
     */
    Map<String, Object> findRoleByAdminId(Long adminId);

    /**
     * 给用户分配角色
     * @date
     * @author
     * @return
     * @throws
     */
    void assignRole(Long adminId, Long[] roleIds);
}
