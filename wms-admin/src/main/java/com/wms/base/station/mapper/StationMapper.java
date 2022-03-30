package com.wms.base.station.mapper;

import com.wms.base.station.domain.Station;
import com.wms.system.domain.SysDictData;

import java.util.List;

/**
 * 岗位Mapper接口
 * 
 * @author assassin
 * @date 2021-03-23
 */
public interface StationMapper 
{
    /**
     * 查询岗位
     * 
     * @param stationcode 岗位ID
     * @return 岗位
     */
    public Station selectStationById(String stationcode);

    /**
     * 查询岗位列表
     * 
     * @param station 岗位
     * @return 岗位集合
     */
    public List<Station> selectStationList(Station station);

    /**
     * 新增岗位
     * 
     * @param station 岗位
     * @return 结果
     */
    public int insertStation(Station station);

    /**
     * 修改岗位
     * 
     * @param station 岗位
     * @return 结果
     */
    public int updateStation(Station station);

    /**
     * 删除岗位
     * 
     * @param stationcode 岗位ID
     * @return 结果
     */
    public int deleteStationById(String stationcode);

    /**
     * 批量删除岗位
     * 
     * @param stationcodes 需要删除的数据ID
     * @return 结果
     */
    public int deleteStationByIds(String[] stationcodes);

    List<SysDictData> selectStataionDictList();
}
