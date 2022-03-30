package com.wms.warehouse.storemove.mapper;

import com.wms.warehouse.storeio.domain.StoreIo;
import com.wms.warehouse.storemove.domain.StoreMove;
import com.wms.warehouse.storemove.domain.StoreMoveExcel;

import java.util.List;

/**
 * 移库主Mapper接口
 * 
 * @author assassin
 * @date 2020-01-16
 */
public interface StoreMoveMapper 
{
    /**
     * 查询移库主
     * 
     * @param tasktype 移库主ID
     * @return 移库主
     */
    public StoreMove selectStoreMoveById(String taskcode);

    /**
     * 查询移库主列表
     * 
     * @param storeMove 移库主
     * @return 移库主集合
     */
    public List<StoreMove> selectStoreMoveList(StoreMove storeMove);

    /**
     * 新增移库主
     * 
     * @param storeMove 移库主
     * @return 结果
     */
    public int insertStoreMove(StoreMove storeMove);

    /**
     * 修改移库主
     * 
     * @param storeMove 移库主
     * @return 结果
     */
    public int updateStoreMove(StoreMove storeMove);

    /**
     * 删除移库主
     * 
     * @param tasktype 移库主ID
     * @return 结果
     */
    public int deleteStoreMoveById(String tasktype);

    /**
     * 批量删除移库主
     * 
     * @param tasktypes 需要删除的数据ID
     * @return 结果
     */
    public int deleteStoreMoveByIds(String[] taskcodes);

    StoreMove selectStoreMoveByIdAndList(StoreMove storeMove);

    int cancelStoreMoveByTaskCodes(String[] toStrArray);

    int restartStoreMoveByTaskCodes(String[] toStrArray);

    int insertStoreMoveList(List<StoreMove> storeMoveList);

    List<StoreMoveExcel> selectStoreMoveExcelList(StoreMove storeMove);

    //移库下发
    int executeStoreMoveByTaskCodes(String[] toStrArray);


}
