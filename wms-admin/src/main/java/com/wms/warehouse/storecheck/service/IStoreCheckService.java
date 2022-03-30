package com.wms.warehouse.storecheck.service;

import com.wms.common.core.domain.AjaxResult;
import com.wms.warehouse.storecheck.domain.CheckResult;
import com.wms.warehouse.storecheck.domain.StoreCheck;
import com.wms.warehouse.storecheck.domain.StoreCheckExcel;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * 盘点主Service接口
 * 
 * @author assassin
 * @date 2020-02-10
 */
public interface IStoreCheckService 
{
    /**
     * 查询盘点主
     * 
     * @param tasktype 盘点主ID
     * @return 盘点主
     */
    public StoreCheck selectStoreCheckById(String tasktype);

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
     * @param userName
     * @return 结果
     */
    public int updateStoreCheck(StoreCheck storeCheck, String userName);

    /**
     * 批量删除盘点主
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteStoreCheckByIds(String ids);

    /**
     * 删除盘点主信息
     * 
     * @param tasktype 盘点主ID
     * @return 结果
     */
    public int deleteStoreCheckById(String tasktype);

    int cancel(String taskcodes);

    int restart(String taskcodes);

    AjaxResult importExcel(MultipartFile file,String taskType) throws IOException;

    AjaxResult exportStoreModel(String storeCheckModel);

    List<StoreCheckExcel> selectStoreCheckAndSonList(StoreCheck storeCheck);

    //盘库任务下发
    int execute(String taskcodes);

    /**
     * 查询盘点结果列表
     */
    public List<CheckResult> selectCheckResultList(String taskcode);
}
