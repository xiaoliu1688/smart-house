package com.atguigu.dao;

import com.atguigu.entity.Dict;

import java.util.List;
import java.util.Map;

/**
 * @author 刘翰林
 * @create 2023-06-02 19:49
 */
public interface DictDao {

    /**
     * 根据父id获取下级列表（也就是通过传入节点获取该节点的所有子节点）
     * @date
     * @author
     * @return
     * @throws
     */
    List<Dict> findListByParentId(Long parentId);


    /**
     * 通过父id获取子节点的数量判断是否是父节点
     * @date
     * @author
     * @return
     * @throws
     */
    Integer countIsParent(Long id);

    /**
     * 根据编码获取节点数据
     * @date
     * @author
     * @return
     * @throws
     */
    Dict getByDictCode(String dictCode);

    /**
     * 通过区域id获取名字
     * @date
     * @author
     * @return
     * @throws
     */
    String getNameById(Long id);


}
