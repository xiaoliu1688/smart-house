package com.atguigu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.entity.Dict;
import com.atguigu.result.Result;
import com.atguigu.service.DictService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * @author 刘翰林
 * @create 2023-06-02 19:56
 */
@Controller
@RequestMapping("/dict")
public class DictController {

    @Reference      //通过dubbo远程获取到服务代理类
    private DictService dictService;

    /**
     * 去展示字典界面
     * @date
     * @author
     * @return
     * @throws
     */
    @RequestMapping()
    public String toIndex(){
        return "dict/index";
    }

    /**
     * 根据上级id获取子节点数据列表
     * @date
     * @author
     * @return
     * @throws
     */
    @ResponseBody
    @RequestMapping("/findZnodes")
    public Result findByParentId(@RequestParam(value = "id",defaultValue = "0") Long id){

        //得到所有的节点集
//        zTree需要返回这样的数据进行显示[{ id:'0331',name:'n3.3.n1',	isParent:true}]，所以此处使用list＋map
        //所以此处findZnodes所要做的就是要返回用这样的形式表示所有节点
        List<Map<String, Object>> znodes = dictService.findZnodes(id);
        //这里的Result里面封装了数据、状态码、以及消息（就是执行成功与否之类的）
        return Result.ok(znodes);
    }

    /**
     *
     * @date
     * @author
     * @return
     * @throws
     */
    @ResponseBody
    @RequestMapping("/findListByParentId/{areaId}")
    public Result findListByParentId(@PathVariable("areaId") Long parentId){

        List<Dict> listByParentId = dictService.findListByParentId(parentId);

        //此处之所以要用result返回是因为前端代码(前端需要的是json数据)写的是读取响应体的data如下：res.data
        return Result.ok(listByParentId);

    }


}
