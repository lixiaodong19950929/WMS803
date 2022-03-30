package com.wms.base.warehouse.service;

import com.wms.base.warehouse.domain.Warehouse;

import java.util.List;

/**
 * 仓库Service接口
 * 
 * @author åºå®¢
 * @date 2019-12-27
 */
public interface IWarehouseService 
{
    /**
     * 查询仓库
     * 
     * @param whcode 仓库ID
     * @return 仓库
     */
    public Warehouse selectWarehouseById(String whcode);

    /**
     * 查询仓库列表
     * 
     * @param warehouse 仓库
     * @return 仓库集合
     */
    public List<Warehouse> selectWarehouseList(Warehouse warehouse);

    /**
     * 新增仓库
     * 
     * @param warehouse 仓库
     * @return 结果
     */
    public int insertWarehouse(Warehouse warehouse);

    /**
     * 修改仓库
     * 
     * @param warehouse 仓库
     * @return 结果
     */
    public int updateWarehouse(Warehouse warehouse);

    /**
     * 批量删除仓库
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteWarehouseByIds(String ids);

    /**
     * 删除仓库信息
     * 
     * @param whcode 仓库ID
     * @return 结果
     */
    public int deleteWarehouseById(String whcode);

    String checkCbarCode(Warehouse warehouse);

    List<Warehouse> selectWarehouseDict();
}
