package com.atguigu.service.Impl;

import com.atguigu.dao.BaseDao;
import com.atguigu.service.BaseService;
import com.atguigu.util.CastUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.Map;

/**
 * @author 刘翰林
 * @create 2023-04-22 23:41
 */
@Transactional
@Service
public abstract class BaseServiceImpl<T> implements BaseService<T> {

    protected abstract BaseDao<T> getEntityDao();

    public Integer insert(T t) {
        return getEntityDao().insert(t);
    }

    public void delete(Long id) {
        getEntityDao().delete(id);
    }

    public Integer update(T t) {
        return getEntityDao().update(t);
    }

    public T getById(Serializable id) {
        return getEntityDao().getById(id);
    }

    @Override
    public PageInfo<T> findPage(Map<String, Object> filters) {

        //获取每页的条目数，这里两处的defaultValue的就是当filters的get的为空时默认赋值，这是工具类，具体可以看尚好房day06第九集
        int pageSize = CastUtil.castInt(filters.get("pageSize"),3);
        //获取当前页码
        int pageNum = CastUtil.castInt(filters.get("pageNum"),1);

        //通过分页插件开启分页
//            pageNum：当前页码
//            pages：总页数【计算：总页数=总数据数量/每页显示数据数量】
//            total：总记录（数据）数，会通过SQL查询，所以执行一次分页操作，相当于执行了两次SQL语句
//            pageSize：每页显示记录（数据）数量
//            List<T> result(封装前) / list(封装后)：当前页显示的记录（数据）集合
        PageHelper.startPage(pageNum,pageSize);

        //调用dao层的分页查询方法
        //page继承ArrayList，其实就是一个list
        Page<T> page = getEntityDao().findPage(filters);

        //navigatePages的意思就是导航栏显示多少页
        PageInfo<T> pageInfo = new PageInfo<T>(page,5);
        return pageInfo;
    }
}