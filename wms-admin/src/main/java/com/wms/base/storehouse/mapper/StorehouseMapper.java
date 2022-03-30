package com.wms.base.storehouse.mapper;

import com.wms.base.storehouse.domain.Storehouse;
import com.wms.base.storehouse.domain.StorehouseEx;
import com.wms.warehouse.storemove.domain.StoreMove;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 库位Mapper接口
 * 
 * @author dkwms
 * @date 2020-01-03
 */
public interface StorehouseMapper
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

    //库存导入时先删再插入
    public int deleteAndInsertsList(List<StorehouseEx> storehouseExList);

    //库存导入时根据库位号逐条更新
    public int updateListByStorehouseCode(StorehouseEx storehouseExList);

    public List<StorehouseEx> deleteAllStorehouse();

    /**
     * 批量新增库位
     *
     * @param storehouseExList 库位
     * @return 结果
     */
    public int insertstorehouseLists(List<StorehouseEx> storehouseExList);

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
     * 删除库位
     * 
     * @param whcode 库位ID
     * @return 结果
     */
    public int deleteStorehouseById(String whcode);

    /**
     * 批量删除库位
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteStorehouseByIds(String[] ids);

    List<Storehouse> getStorehouseByWhcode(String whcode);

    List<Storehouse> selectStorehouseCode(String regioncode);

    Storehouse getStorehouseCode(String storehousecode);

    List<Storehouse> selectByStoreHouse(Storehouse storehouse);

    List<String> selectStorehouseModelList();

    Storehouse selectByWhAndStore(@Param("whcode") String whcode, @Param("storehousecode")String storehousecode);

    List<Storehouse> selectStorehouseTrayList(Storehouse storehouse);

    List<Storehouse> selectStorehouseTrayOutList(Storehouse storehouse);

    List<Storehouse> selectSums();

    //查新库位表中有产品的库位
    List<Storehouse> selectProductList(Storehouse storehouse);
}
