package com.atguigu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.entity.Dict;
import com.atguigu.result.Result;
import com.atguigu.service.DictService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author 刘翰林
 * @create 2023-06-12 12:29
 */
@RestController     //这个注解就相当于response注解+controller注解
@RequestMapping("/dict")
public class DictController {

    @Reference
    DictService dictService;

    /**
     * 通过编码查找所有子节点
     * @date
     * @author
     * @return
     * @throws
     */
    @RequestMapping("/findListByDictCode/{code}")
    public Result findListByDictCode(@PathVariable("code") String code){

        List<Dict> listByDictCode = dictService.findListByDictCode(code);

        return Result.ok(listByDictCode);
    }

    /**
     * 根据父id查找所有子节点
     * @date
     * @author
     * @return
     * @throws
     */

    @RequestMapping("/findListByParentId/{id}")
    public Result findListByParentId(@PathVariable("id") Long id){

        List<Dict> listByParentId = dictService.findListByParentId(id);

        return Result.ok(listByParentId);

    }
}
