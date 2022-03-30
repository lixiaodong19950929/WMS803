package com.wms.warehouse.storemove.service;

import com.wms.warehouse.storemove.domain.StoreMoveSon;

import java.util.List;

/**
 * 移库子Service接口
 * 
 * @author assassin
 * @date 2020-01-16
 */
public interface IStoreMoveSonService 
{
    /**
     * 查询移库子
     * 
     * @param rowid 移库子ID
     * @return 移库子
     */
    public StoreMoveSon selectStoreMoveSonById(Long rowid);

    /**
     * 查询移库子列表
     * 
     * @param storeMoveSon 移库子
     * @return 移库子集合
     */
    public List<StoreMoveSon> selectStoreMoveSonList(StoreMoveSon storeMoveSon);

    /**
     * 新增移库子
     * 
     * @param storeMoveSon 移库子
     * @return 结果
     */
    public int insertStoreMoveSon(StoreMoveSon storeMoveSon);

    /**
     * 修改移库子
     * 
     * @param storeMoveSon 移库子
     * @return 结果
     */
    public int updateStoreMoveSon(StoreMoveSon storeMoveSon);

    /**
     * 批量删除移库子
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteStoreMoveSonByIds(String ids);

    /**
     * 删除移库子信息
     * 
     * @param rowid 移库子ID
     * @return 结果
     */
    public int deleteStoreMoveSonById(Long rowid);

    List<StoreMoveSon> selectStoreMoveList(StoreMoveSon storeMoveSon);
}
