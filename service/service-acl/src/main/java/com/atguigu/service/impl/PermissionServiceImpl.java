package com.atguigu.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.atguigu.dao.BaseDao;
import com.atguigu.dao.PermissionDao;
import com.atguigu.dao.RolePermissionDao;
import com.atguigu.entity.Permission;
import com.atguigu.entity.RolePermission;
import com.atguigu.helper.PermissionHelper;
import com.atguigu.service.Impl.BaseServiceImpl;
import com.atguigu.service.PermissionService;
import org.aspectj.weaver.ast.Var;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 刘翰林
 * @create 2023-06-16 13:05
 */

@Service(interfaceClass = PermissionService.class)
@Transactional
public class PermissionServiceImpl extends BaseServiceImpl<Permission> implements PermissionService {

    @Resource
    PermissionDao permissionDao;

    @Resource
    RolePermissionDao rolePermissionDao;

    @Override
    protected BaseDao<Permission> getEntityDao() {
        return permissionDao;
    }

    @Override
    public List<Map<String, Object>> findZNodeByRoleId(Long roleId) {

        //先查找所有权限
        List<Permission> AllPermissions = permissionDao.findAllPermission();

        //在通过roleId查找该角色所有的权限
        List<Long> rolePermissionsId = rolePermissionDao.findRolePerByRoleId(roleId);

        //要返回的zNode的数据类型
        // var zNodes =[
        //     { id:1, pId:0, name:"随意勾选 1", open:true},
        //     { id:11, pId:1, name:"随意勾选 1-1", open:true},
        //     { id:111, pId:11, name:"随意勾选 1-1-1"},

        //先创建一个需要返回的list
        ArrayList<Map<String, Object>> list = new ArrayList<>();


        for (Permission permission : AllPermissions) {
            HashMap<String, Object> map = new HashMap<>();
            map.put("id",permission.getId());
            map.put("pId",permission.getParentId());
            map.put("name",permission.getName());

            if(rolePermissionsId.contains(permission.getId())){ //如果该角色的权限id包含该权限
                //则将权限是否有置为是
                map.put("checked",true);
            }
            list.add(map);
        }

        return list;
    }

    @Override
    public void assignRolePermission(Long roleId, Long[] permissionIds) {

        //先删除用户权限再重新分配、

        //先删除权限
        rolePermissionDao.deletePermissionByRoleId(roleId);

        RolePermission rolePermission = new RolePermission();
        rolePermission.setRoleId(roleId);

        //再重新分配
        for (Long permissionId : permissionIds) {

            rolePermission.setPermissionId(permissionId);
            rolePermissionDao.insert(rolePermission);

        }
    }

    @Override
    public List<Permission> findMenuPermissionByAdminId(Long adminId) {

        List<Permission> permissionList = null;

        if(adminId.longValue() == 1){   //如果是1则是系统管理员，拥有所有权限
            permissionList = permissionDao.findAllPermission();
        }else {
            //查找所有权限菜单
            permissionList = permissionDao.findMenuPermissionByAdminId(adminId);
        }

        //把权限数据构建成树形结构数据，这是一个自定义的工具类
        List<Permission> list = PermissionHelper.bulid(permissionList);

        return list;
    }

    @Override
    public List<Permission> findAll() {
        //此处查出来的上级菜单是id，所以需要进行处理
        List<Permission> permissions = permissionDao.findAllPermission();

        if(CollectionUtils.isEmpty(permissions)) return null;
        //构建树形数据,总共三级
        //把权限数据构建成树形结构数据
        List<Permission> result = PermissionHelper.bulid(permissions);

        return result;
    }

    @Override
    public Permission findMenuById(Long id) {
        Permission permission = permissionDao.getById(id);
        return permission;
    }

    @Override
    public List<String> findPermissionCodeByAdminId(Long adminId) {

        List<String> list = new ArrayList<>();

        if(adminId == 1){
            list = permissionDao.findAllPermissionCode();
        }else {
            list = permissionDao.findPermissionCodeByAdminId(adminId);
        }

        return list;
    }
}
