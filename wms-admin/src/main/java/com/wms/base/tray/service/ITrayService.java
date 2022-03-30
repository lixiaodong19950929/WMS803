package com.wms.base.tray.service;

import com.wms.base.tray.domain.Tray;

import java.util.List;

/**
 * 托盘Service接口
 * 
 * @author dkwms
 * @date 2020-01-07
 */
public interface ITrayService
{
    /**
     * 查询托盘
     * 
     * @param id 托盘ID
     * @return 托盘
     */
    public Tray selectTrayById(Long id);

    /**
     * 查询托盘列表
     * 
     * @param tray 托盘
     * @return 托盘集合
     */
    public List<Tray> selectTrayList(Tray tray);

    /**
     * 新增托盘
     * 
     * @param tray 托盘
     * @return 结果
     */
    public int insertTray(Tray tray);

    /**
     * 修改托盘
     * 
     * @param tray 托盘
     * @return 结果
     */
    public int updateTray(Tray tray);

    /**
     * 批量删除托盘
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteTrayByIds(String ids);

    /**
     * 删除托盘信息
     * 
     * @param id 托盘ID
     * @return 结果
     */
    public int deleteTrayById(Long id);


    public List<Tray> selectAllTray(String trayCode);

    public String selectTrayByStoreHouseCode(String storeHouseCode);
}
