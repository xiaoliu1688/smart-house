package com.atguigu.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.atguigu.dao.BaseDao;
import com.atguigu.dao.DictDao;
import com.atguigu.dao.HouseDao;
import com.atguigu.entity.House;
import com.atguigu.service.HouseService;
import com.atguigu.service.Impl.BaseServiceImpl;
import com.atguigu.vo.HouseQueryVo;
import com.atguigu.vo.HouseVo;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @author 刘翰林
 * @create 2023-06-04 20:22
 */

@Transactional
@Service(interfaceClass = HouseService.class)
public class HouseServiceImpl extends BaseServiceImpl<House> implements HouseService {

    @Resource
    HouseDao houseDao;

    @Resource
    DictDao dictDao;

    @Override
    protected BaseDao<House> getEntityDao() {
        return houseDao;
    }

    @Override
    public House findHouseById(Long id) {
        House house = houseDao.findHouseById(id);

        //户型：
        String houseTypeName = dictDao.getNameById(house.getHouseTypeId());
        //楼层
        String floorName = dictDao.getNameById(house.getFloorId());
        //建筑结构：
        String buildStructureName = dictDao.getNameById(house.getBuildStructureId());
        //朝向：
        String directionName = dictDao.getNameById(house.getDirectionId());
        //装修情况：
        String decorationName = dictDao.getNameById(house.getDecorationId());
        //房屋用途：
        String houseUseName = dictDao.getNameById(house.getHouseUseId());
        house.setHouseTypeName(houseTypeName);
        house.setFloorName(floorName);
        house.setBuildStructureName(buildStructureName);
        house.setDirectionName(directionName);
        house.setDecorationName(decorationName);
        house.setHouseUseName(houseUseName);

        return house;
    }

    @Override
    public void publish(Long id,Integer status) {

        //先调用查找,通过id得到house
        House house = houseDao.findHouseById(id);

        house.setStatus(status);

        //通过status修改状态
        houseDao.update(house);
    }

    @Override
    public PageInfo<HouseVo> findPageList(Integer pageNum, Integer pageSize, HouseQueryVo houseQueryVo) {

        //开启分页
        PageHelper.startPage(pageNum,pageSize);

        //调用dao层的分页查询方法
        //page继承ArrayList，其实就是一个list
        Page<HouseVo> page =  houseDao.findPageList(houseQueryVo);


        for(HouseVo houseVo : page){
            //户型：
            String houseTypeName = dictDao.getNameById(houseVo.getHouseTypeId());
            //楼层
            String floorName = dictDao.getNameById(houseVo.getFloorId());
            //朝向：
            String directionName = dictDao.getNameById(houseVo.getDirectionId());

            houseVo.setHouseTypeName(houseTypeName);
            houseVo.setFloorName(floorName);
            houseVo.setDirectionName(directionName);
        }

        //navigatePages是表示页码导航，显示导航栏显示多少页
        return new PageInfo<HouseVo>(page,5);
    }
}
