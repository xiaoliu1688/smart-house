package com.atguigu.service;

import com.atguigu.entity.Dict;

import java.util.List;
import java.util.Map;

/**
 * @author 刘翰林
 * @create 2023-06-02 19:45
 */
public interface DictService {

    /**
     * 数据字典查找节点
     * @date
     * @author
     * @return
     * @throws
     */
    List<Map<String,Object>> findZnodes(Long id);

    /**
     * 根据编码获取子节点数据列表
     * @date
     * @author
     * @return
     * @throws
     */
    public List<Dict> findListByDictCode(String dictCode);

    /**
     * 根据父id获取下级列表（也就是通过传入节点获取该节点的所有子节点）
     * @date
     * @author
     * @return
     * @throws
     */
    List<Dict> findListByParentId(Long parentId);

    /**
     * 根据id查找名字
     * @date
     * @author
     * @return
     * @throws
     */
    String getNameById(Long id);


}
