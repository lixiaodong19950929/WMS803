package com.wms.warehouse.storecheck.mapper;

import com.wms.warehouse.storecheck.domain.CheckResult;
import com.wms.warehouse.storecheck.domain.StoreCheck;
import com.wms.warehouse.storecheck.domain.StoreCheckExcel;
import com.wms.warehouse.storeio.domain.StoreIo;

import java.util.List;

/**
 * 盘点主Mapper接口
 * 
 * @author assassin
 * @date 2020-02-10
 */
public interface StoreCheckMapper 
{
    /**
     * 查询盘点主
     * 
     * @param tasktype 盘点主ID
     * @return 盘点主
     */
    public StoreCheck selectStoreCheckById(String taskcode);

    /**
     * 查询盘点主列表
     * 
     * @param storeCheck 盘点主
     * @return 盘点主集合
     */
    public List<StoreCheck> selectStoreCheckList(StoreCheck storeCheck);

    /**
     * 新增盘点主
     * 
     * @param storeCheck 盘点主
     * @return 结果
     */
    public int insertStoreCheck(StoreCheck storeCheck);

    /**
     * 修改盘点主
     * 
     * @param storeCheck 盘点主
     * @return 结果
     */
    public int updateStoreCheck(StoreCheck storeCheck);

    /**
     * 删除盘点主
     * 
     * @param tasktype 盘点主ID
     * @return 结果
     */
    public int deleteStoreCheckById(String tasktype);

    /**
     * 批量删除盘点主
     * 
     * @param tasktypes 需要删除的数据ID
     * @return 结果
     */
    public int deleteStoreCheckByIds(String[] tasktypes);

    StoreCheck selectStoreCheckByIdAndList(StoreCheck storeCheck);

    int cancelStoreCheckByTaskCodes(String[] toStrArray);

    int restartStoreCheckByTaskCodes(String[] toStrArray);

    int insertStoreCheckList(List<StoreCheck> storeCheckList);

    List<StoreCheckExcel> selectStoreCheckAndSonList(StoreCheck storeCheck);

    //盘库任务下发
    int executeStoreCheckByTaskCodes(String[] toStrArray);

    /**
     * 查询盘点结果列表
     */
    public List<CheckResult> selectCheckResultList(String taskcode);
}
