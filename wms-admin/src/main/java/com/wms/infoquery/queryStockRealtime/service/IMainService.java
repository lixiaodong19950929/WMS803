package com.wms.infoquery.queryStockRealtime.service;

import com.wms.infoquery.queryStockRealtime.domain.Main;
import java.util.List;

/**
 * 扫描记录Service接口
 * 
 * @author assassin
 * @date 2020-02-06
 */
public interface IMainService 
{
    /**
     * 查询扫描记录
     * 
     * @param id 扫描记录ID
     * @return 扫描记录
     */
    public Main selectMainById(Long id);

    /**
     * 查询扫描记录列表
     * 
     * @param main 扫描记录
     * @return 扫描记录集合
     */
    public List<Main> selectMainList(Main main);


    /**
     * 首页看板 查询 出入库 数量
     *
     * @param main 扫描记录
     * @return 扫描记录集合
     */
    public List<Main> selectIoScanCount(Main main);

    /**
     * 新增扫描记录
     * 
     * @param main 扫描记录
     * @return 结果
     */
    public int insertMain(Main main);

    /**
     * 修改扫描记录
     * 
     * @param main 扫描记录
     * @return 结果
     */
    public int updateMain(Main main);

    /**
     * 批量删除扫描记录
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteMainByIds(String ids);

    /**
     * 删除扫描记录信息
     * 
     * @param id 扫描记录ID
     * @return 结果
     */
    public int deleteMainById(Long id);
}
