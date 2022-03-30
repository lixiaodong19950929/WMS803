package com.wms.base.station.service.impl;

import java.util.List;

import com.wms.system.domain.SysDictData;
import com.wms.system.mapper.SysUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.wms.base.station.mapper.StationMapper;
import com.wms.base.station.domain.Station;
import com.wms.base.station.service.IStationService;
import com.wms.common.core.text.Convert;

/**
 * 岗位Service业务层处理
 * 
 * @author assassin
 * @date 2021-03-23
 */
@Service("station")
public class StationServiceImpl implements IStationService 
{
    @Autowired
    private StationMapper stationMapper;

    @Autowired
    private SysUserMapper userMapper;

    /**
     * 查询岗位
     * 
     * @param stationcode 岗位ID
     * @return 岗位
     */
    @Override
    public Station selectStationById(String stationcode)
    {
        return stationMapper.selectStationById(stationcode);
    }

    /**
     * 查询岗位列表
     * 
     * @param station 岗位
     * @return 岗位
     */
    @Override
    public List<Station> selectStationList(Station station)
    {
        return stationMapper.selectStationList(station);
    }

    /**
     * 新增岗位
     * 
     * @param station 岗位
     * @return 结果
     */
    @Override
    public int insertStation(Station station)
    {
        return stationMapper.insertStation(station);
    }

    /**
     * 修改岗位
     * 
     * @param station 岗位
     * @return 结果
     */
    @Override
    public int updateStation(Station station)
    {
        return stationMapper.updateStation(station);
    }

    /**
     * 删除岗位对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteStationByIds(String ids)
    {
        return stationMapper.deleteStationByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除岗位信息
     * 
     * @param stationcode 岗位ID
     * @return 结果
     */
    public int deleteStationById(String stationcode)
    {
        return stationMapper.deleteStationById(stationcode);
    }

    @Override
    public List<SysDictData> getDictType() {
        return stationMapper.selectStataionDictList();
    }

    public List<SysDictData> getUserDictType(){
        return userMapper.selectUserEmpDictList();
    }
}
