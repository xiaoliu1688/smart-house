package com.atguigu.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.atguigu.dao.DictDao;
import com.atguigu.entity.Dict;
import com.atguigu.service.DictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.rmi.MarshalledObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 刘翰林
 * @create 2023-06-02 19:48
 */

@Service(interfaceClass = DictService.class)        //此处是指将DictServiceImpl类使用cglib创建代理类对象进行发布服务
@Transactional
public class DictServiceImpl implements DictService {

    @Autowired      //自动装配属性
    DictDao dictDao;

    @Override
    public List<Map<String, Object>> findZnodes(Long id) {

//      zTree需要返回这样的数据进行显示[{ id:'0331',name:'n3.3.n1',	isParent:true}]


        //首先通过传入节点的id获得该节点的所有子节点
        List<Dict> allSubNode = dictDao.findListByParentId(id);


        //构建ztree数据
        List<Map<String, Object>> zTree = new ArrayList<>();



        for(Dict dict : allSubNode){     //每一个子节点构建成一个{ id:'0331',name:'n3.3.n1',	isParent:true}数据对象

            HashMap<String, Object> map = new HashMap<>();

            map.put("id",dict.getId());
            map.put("name",dict.getName());


            Integer count = dictDao.countIsParent(dict.getId());  //count为该节点具有子节点的数量

            map.put("isParent",count > 0 ? true : false );  //通过该节点具有子节点的数量判断是否为父节点

            //另一种写法
//            if (count > 0){     //通过该节点具有子节点的数量判断是否为父节点
//                map.put("isParent",true);
//            }else {
//                map.put("isParent",false);
//            }
            zTree.add(map);

        }


        return zTree;
    }

    @Override
    public List<Dict> findListByDictCode(String dictCode) { //根据编码获取子节点数据列表

        Dict dict = dictDao.getByDictCode(dictCode);      //首先通过编码获取节点的数据

        //再根据该节点id查找出所有子节点
        List<Dict> listByParentId = dictDao.findListByParentId(dict.getId());

        return listByParentId;
    }

    @Override
    public List<Dict> findListByParentId(Long parentId) {   //根据节点id查找出所有子节点
        List<Dict> listByParentId = dictDao.findListByParentId(parentId);
        return listByParentId;
    }

    @Override
    public String getNameById(Long id) {
        return dictDao.getNameById(id);
    }
}
