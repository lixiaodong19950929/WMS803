package com.wms.warehouse.storeio.mapper;

import com.wms.warehouse.storeio.domain.StoreIo;
import com.wms.warehouse.storeio.domain.StoreIoExcel;

import java.util.List;

/**
 * 出入库主Mapper接口
 * 
 * @author assassin
 * @date 2019-12-30
 */
public interface StoreIoMapper 
{
    /**
     * 查询出入库主
     * 
     * @param tasktype 出入库主ID
     * @return 出入库主
     */
    public StoreIo selectStoreIoById(String tasktype);

    /**
     * 查询出入库主列表
     * 
     * @param storeIo 出入库主
     * @return 出入库主集合
     */
    public List<StoreIo> selectStoreIoList(StoreIo storeIo);

    /**
     * 新增出入库主
     * 
     * @param storeIo 出入库主
     * @return 结果
     */
    public int insertStoreIo(StoreIo storeIo);

    /**
     * 修改出入库主
     * 
     * @param storeIo 出入库主
     * @return 结果
     */
    public int updateStoreIo(StoreIo storeIo);

    /**
     * 删除出入库主
     * 
     * @param tasktype 出入库主ID
     * @return 结果
     */
    public int deleteStoreIoById(String tasktype);

    /**
     * 批量删除出入库主
     * 
     * @param tasktypes 需要删除的数据ID
     * @return 结果
     */
    public int deleteStoreIoByIds(String[] tasktypes);

    StoreIo selectStoreIoByIdAndList(StoreIo storeIo);

    int cancelStoreIoByTaskCodes(String[] toStrArray);

    int executeStoreIoByTaskCodes(String[] toStrArray);

    int restartStoreIoByTaskCodes(String[] toStrArray);

    int insertStoreIoList(List<StoreIo> storeIoList);

    List<StoreIoExcel> selectStoreIoExcelList(StoreIo storeIo);

}
