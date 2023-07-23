package com.atguigu.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.atguigu.dao.AdminRoleDao;
import com.atguigu.dao.BaseDao;
import com.atguigu.dao.RoleDao;
import com.atguigu.entity.AdminRole;
import com.atguigu.entity.Role;
import com.atguigu.service.Impl.BaseServiceImpl;
import com.atguigu.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 刘翰林
 * @create 2023-04-17 0:17
 */

@Service(interfaceClass = RoleService.class)        //此处的作用是指定发布的服务为RoleService接口
@Transactional
public class RoleServiceImpl extends BaseServiceImpl<Role> implements RoleService {


//    @Resource
    @Autowired    //会报红但是不影响使用
    RoleDao roleDao;

    @Resource
    AdminRoleDao adminRoleDao;


    /**
     * 查找所有角色
     * @date
     * @author
     * @return
     * @throws
     */

    public List<Role> findAllRole() {
        return roleDao.findAllRole();
    }

    @Override
    public Map<String, Object> findRoleByAdminId(Long adminId) {

        Map<String, Object> map = new HashMap<>();

        //查找出所有角色
        List<Role> allRole = roleDao.findAllRole();

        //通过用户id查找出用户已经分配的角色
        List<Long> list = adminRoleDao.findRoleByAdminId(adminId);

        ArrayList<Role> assginRoleList = new ArrayList<>();
        ArrayList<Role> noAssginRoleList = new ArrayList<>();

        for(Role role : allRole){

            if(list.contains(role.getId())){
                //如果包含则说明该角色已经分配给该用户，放入已分配集合中
                assginRoleList.add(role);
            }else {
                //否职责说明没有分配给该用户，放入未分配集合中
                noAssginRoleList.add(role);
            }
        }

        map.put("assginRoleList",assginRoleList);
        map.put("noAssginRoleList",noAssginRoleList);

        return map;
    }

    @Override
    public void assignRole(Long adminId, Long[] roleIds) {

        //将该用户的所有角色删除
        adminRoleDao.deleteByAdminId(adminId);

        AdminRole adminRole = new AdminRole();
        adminRole.setAdminId(adminId);

        //给用户添加角色
        for(Long roleId : roleIds){

            if(roleId != null){

                adminRole.setRoleId(roleId);

                adminRoleDao.insert(adminRole);
            }

        }


    }


    @Override
    protected BaseDao getEntityDao() {
        return this.roleDao;
    }

}
