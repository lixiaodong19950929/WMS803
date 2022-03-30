package com.wms.warehouse.storeio.service;

import com.wms.warehouse.storeio.domain.StoreIoSon;
import java.util.List;

/**
 * 出入库子（1.子可以由主拆分而来，还可以来自上游）Service接口
 * 
 * @author assassin
 * @date 2019-12-30
 */
public interface IStoreIoSonService 
{
    /**
     * 查询出入库子（1.子可以由主拆分而来，还可以来自上游）
     * 
     * @param rowid 出入库子（1.子可以由主拆分而来，还可以来自上游）ID
     * @return 出入库子（1.子可以由主拆分而来，还可以来自上游）
     */
    public StoreIoSon selectStoreIoSonById(Long rowid);

    /**
     * 查询出入库子（1.子可以由主拆分而来，还可以来自上游）列表
     * 
     * @param storeIoSon 出入库子（1.子可以由主拆分而来，还可以来自上游）
     * @return 出入库子（1.子可以由主拆分而来，还可以来自上游）集合
     */
    public List<StoreIoSon> selectStoreIoSonList(StoreIoSon storeIoSon);

    /**
     * 新增出入库子（1.子可以由主拆分而来，还可以来自上游）
     * 
     * @param storeIoSon 出入库子（1.子可以由主拆分而来，还可以来自上游）
     * @return 结果
     */
    public int insertStoreIoSon(StoreIoSon storeIoSon);

    /**
     * 修改出入库子（1.子可以由主拆分而来，还可以来自上游）
     * 
     * @param storeIoSon 出入库子（1.子可以由主拆分而来，还可以来自上游）
     * @return 结果
     */
    public int updateStoreIoSon(StoreIoSon storeIoSon);

    /**
     * 批量删除出入库子（1.子可以由主拆分而来，还可以来自上游）
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteStoreIoSonByIds(String ids);

    /**
     * 删除出入库子（1.子可以由主拆分而来，还可以来自上游）信息
     * 
     * @param rowid 出入库子（1.子可以由主拆分而来，还可以来自上游）ID
     * @return 结果
     */
    public int deleteStoreIoSonById(Long rowid);

    List<StoreIoSon> selectStoreIoList(StoreIoSon storeIoSon);

    List<StoreIoSon> selectStoreIoOutSonList(StoreIoSon storeIoSon);
}
