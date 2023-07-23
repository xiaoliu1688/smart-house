package com.atguigu.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.atguigu.dao.AdminDao;
import com.atguigu.dao.BaseDao;
import com.atguigu.entity.Admin;
import com.atguigu.service.AdminService;
import com.atguigu.service.Impl.BaseServiceImpl;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;


/**
 * @author 刘翰林
 * @create 2023-04-30 15:38
 */
@Service(interfaceClass = AdminService.class)       //此处的作用是指明发布服务的接口为AdminService
@Transactional
public class AdminServiceImpl extends BaseServiceImpl<Admin> implements AdminService {

    @Resource
    private AdminDao adminDao;  //注意，此处的adminDao对象是由mybatis自动生成的代理类对象

    @Override
    protected BaseDao<Admin> getEntityDao() {
        return adminDao;
    }

    @Override
    public List<Admin> findAll() {
        return adminDao.findAll();
    }

    @Override
    public Admin findAdminByName(String userName) {
        return adminDao.findAdminByName(userName);
    }
}
