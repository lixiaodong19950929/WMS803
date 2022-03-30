package com.wms.warehouse.storemove.service;

import com.wms.base.storehouse.domain.Storehouse;
import com.wms.common.core.domain.AjaxResult;
import com.wms.warehouse.storemove.domain.StoreMove;
import com.wms.warehouse.storemove.domain.StoreMoveExcel;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * 移库主Service接口
 * 
 * @author assassin
 * @date 2020-01-16
 */
public interface IStoreMoveService 
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
    public int updateStoreMove(StoreMove storeMove,String userName);

    /**
     * 批量删除移库主
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteStoreMoveByIds(String ids);

    /**
     * 删除移库主信息
     * 
     * @param tasktype 移库主ID
     * @return 结果
     */
    public int deleteStoreMoveById(String tasktype);

    List<Storehouse> selectByStoreHouse(Storehouse storehouse);

    int cancel(String taskcodes);

    int restart(String taskcodes);

    AjaxResult exportStoreModel(String storeMoveModel);

    AjaxResult importExcel(MultipartFile file, String s) throws IOException;

    List<StoreMoveExcel> selectStoreMoveExcelList(StoreMove storeMove);

    //移库下发
    int execute(String taskcodes);
}
