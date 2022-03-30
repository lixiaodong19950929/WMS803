package com.wms.warehouse.storeio.mapper;

import com.wms.warehouse.storeio.domain.StoreIo;
import com.wms.warehouse.storeio.domain.StoreIoSon;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 出入库子（1.子可以由主拆分而来，还可以来自上游）Mapper接口
 * 
 * @author assassin
 * @date 2019-12-30
 */
public interface StoreIoSonMapper 
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
     * 删除出入库子（1.子可以由主拆分而来，还可以来自上游）
     * 
     * @param rowid 出入库子（1.子可以由主拆分而来，还可以来自上游）ID
     * @return 结果
     */
    public int deleteStoreIoSonById(Long rowid);

    /**
     * 批量删除出入库子（1.子可以由主拆分而来，还可以来自上游）
     * 
     * @param rowids 需要删除的数据ID
     * @return 结果
     */
    public int deleteStoreIoSonByIds(String[] rowids);

    int insertStoreIoSonList(List<StoreIoSon> storeIoSonList);

    int updateStoreIoSonList(List<StoreIoSon> updateSons);

    int deleteStoreIoSonByIdList(List<StoreIoSon> storeIoSonList);

    int deleteStoreIoSonByTaskCodes(String[] toStrArray);

    int cancelStoreIoSonByTaskCodes(String[] toStrArray);

    int executeStoreIoSonByTaskCodes(String[] toStrArray);

    int restartStoreIoSonByTaskCodes(String[] toStrArray);

    List<StoreIoSon> selectStoreIoList(StoreIoSon storeIoSon);

    int insertStoreIoTraySonList(List<StoreIoSon> storeIoSonList);

    int deleteStoreIoTraySonByIdList(List<StoreIoSon> storeIoSonList);

    List<StoreIoSon> selectStoreIoOutSonList(StoreIoSon storeIoSon);

    int deleteStoreIoTraySonByNotList(@Param("list") List<StoreIoSon> storeIoSonList, @Param("taskCode")String taskCode);
}
