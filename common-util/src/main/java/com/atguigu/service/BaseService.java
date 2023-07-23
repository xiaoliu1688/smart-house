package com.atguigu.service;

import com.github.pagehelper.PageInfo;

import java.io.Serializable;
import java.util.Map;

/**
 * @author 刘翰林
 * @create 2023-04-22 23:34
 */
public interface BaseService<T> {

    Integer insert(T t);

    void delete(Long id);

    Integer update(T t);

    T getById(Serializable id);

    PageInfo<T> findPage(Map<String, Object> filters);
}
