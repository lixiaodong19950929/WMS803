package com.wms.base.storehouse.service;

import com.wms.base.storehouse.domain.Storehouse;
import com.wms.base.storehouse.domain.StorehouseEx;

import java.util.List;

/**
 * 库位Service接口
 * 
 * @author dkwms
 * @date 2020-01-03
 */
public interface IStorehouseService
{
    /**
     * 查询库位
     * 
     * @param id 库位id
     * @return 库位
     */
    public Storehouse selectStorehouseById(String id);

    /**
     * 查询库位列表
     * 
     * @param storehouse 库位
     * @return 库位集合
     */
    public List<Storehouse> selectStorehouseList(Storehouse storehouse);

    List<StorehouseEx> selectStorehouseExcelList(Storehouse storehouse);

    /**
     * 新增库位
     * 
     * @param storehouse 库位
     * @return 结果
     */
    public int insertStorehouse(Storehouse storehouse);

    /**
     * 修改库位
     * 
     * @param storehouse 库位
     * @return 结果
     */
    public int updateStorehouse(Storehouse storehouse);

    /**
     * 批量删除库位
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteStorehouseByIds(String ids);

    /**
     * 删除库位信息
     * 
     * @param id 库位ID
     * @return 结果
     */
    public int deleteStorehouseById(String id);



    /**
     * 根据whcode获取库位
     * @param whcode
     * @return
     */
    public List<Storehouse> getStorehouseByWhcode(String whcode);

    List<Storehouse> selectStorehouseCode(String regioncode);

    String getStorehouseCode(Storehouse storehouse);

    List<Storehouse> selectStorehouseTrayList(Storehouse storehouse);

    List<Storehouse> selectStorehouseTrayOutList(Storehouse storehouse);

    List<Storehouse> selectSums();

    List<Storehouse> selectProductList(Storehouse storehouse);

    //库存导入时先删再插入
    public int deleteAndInsertsList(List<StorehouseEx> storehouseExList);

    //库存导入时根据库位号逐条更新
    public int updateListByStorehouseCode(List<StorehouseEx> storehouseExList);

    public List<StorehouseEx> deleteAllStorehouse();
}
