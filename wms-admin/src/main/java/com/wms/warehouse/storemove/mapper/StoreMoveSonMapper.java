package com.wms.warehouse.storemove.mapper;

import com.wms.warehouse.storemove.domain.StoreMoveSon;

import java.util.List;

/**
 * 移库子Mapper接口
 * 
 * @author assassin
 * @date 2020-01-16
 */
public interface StoreMoveSonMapper 
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
     * 删除移库子
     * 
     * @param rowid 移库子ID
     * @return 结果
     */
    public int deleteStoreMoveSonById(Long rowid);

    /**
     * 批量删除移库子
     * 
     * @param rowids 需要删除的数据ID
     * @return 结果
     */
    public int deleteStoreMoveSonByIds(String[] rowids);

    int insertStoreMoveSonList(List<StoreMoveSon> storeMoveSonList);

    List<StoreMoveSon> selectStoreMoveList(StoreMoveSon storeMoveSon);

    int updateStoreMoveSonList(List<StoreMoveSon> updateSons);

    int deleteStoreMoveSonByIdList(List<StoreMoveSon> storeMoveSonList);

    int cancelStoreMoveSonByTaskCodes(String[] toStrArray);

    int restartStoreMoveSonByTaskCodes(String[] toStrArray);

    int deleteStoreMoveSonByTaskCodes(String[] toStrArray);

    //移库任务下发
    int executeStoreMoveSonByTaskCodes(String[] toStrArray);

}
