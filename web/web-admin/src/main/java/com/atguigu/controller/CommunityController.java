package com.atguigu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.entity.Community;
import com.atguigu.entity.Dict;
import com.atguigu.service.CommunityService;
import com.atguigu.service.DictService;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * @author 刘翰林
 * @create 2023-06-03 13:43
 */

@Controller
@RequestMapping("/community")
public class CommunityController extends BaseController {

    @Reference
    CommunityService communityService;

    @Reference
    DictService dictService;


    /**
     * 去首页
     * @date
     * @author
     * @return
     * @throws
     */
    @RequestMapping
    public String toIndex(Map map, HttpServletRequest request){

        //通过调用getFilters得到分页信息和请求参数封装到filters中
        Map<String, Object> filters = getFilters(request);

        //将filters放入map中返回
        map.put("filters",filters);

        //进行分页,得到分页信息
        PageInfo<Community> page = communityService.findPage(filters);

        //将分页信息放入map中返回
        map.put("page",page);

        List<Dict> areaList = dictService.findListByDictCode("beijing");    //根据编码获取子节点数据列表

        map.put("areaList",areaList);

        return "community/index";
    }


    /**
     * 去新增页面
     * @date
     * @author
     * @return
     * @throws
     */
    @RequestMapping("/create")
    public String toCreate(Map map){

        List<Dict> areaList = dictService.findListByDictCode("beijing");    //根据编码获取子节点数据列表

        map.put("areaList",areaList);
        return "community/create";
    }

    /**
     * 新增小区
     * @date
     * @author
     * @return
     * @throws
     */
    @RequestMapping("/save")
    public String insert(Community community){    //参数会自动入参
        communityService.insert(community);
        return SUCCESS_PAGE;
    }

    /**
     * 去修改界面
     * @date
     * @author
     * @return
     * @throws
     */
    @RequestMapping("/edit/{id}")
    public String toEditPage(@PathVariable("id") Long id,Map map){

        Community community = communityService.getById(id);     //通过id获取需要修改的小区信息

        map.put("community",community);     //将查询到的小区信息响应给前端

        //查出所有区域
        List<Dict> areaList = dictService.findListByDictCode("beijing");

        //将这所有区域响应给前端
        map.put("areaList",areaList);


        return "community/edit";
    }

    /**
     * 修改小区信息
     * @date
     * @author
     * @return
     * @throws
     */
    @RequestMapping("/update")
    public String update(Community community){

        communityService.update(community);

        return SUCCESS_PAGE;
    }

    /**
     * 删除小区
     * @date
     * @author
     * @return
     * @throws
     */
    @RequestMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id){

        communityService.delete(id);

        return "redirect:/community";
    }

}
