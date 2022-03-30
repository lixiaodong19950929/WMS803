package com.wms.warehouse.storecheck.service;

import com.wms.warehouse.storecheck.domain.StoreCheckSon;
import java.util.List;

/**
 * 盘点子Service接口
 * 
 * @author assassin
 * @date 2020-02-10
 */
public interface IStoreCheckSonService 
{
    /**
     * 查询盘点子
     * 
     * @param rowid 盘点子ID
     * @return 盘点子
     */
    public StoreCheckSon selectStoreCheckSonById(Long rowid);

    /**
     * 查询盘点子列表
     * 
     * @param storeCheckSon 盘点子
     * @return 盘点子集合
     */
    public List<StoreCheckSon> selectStoreCheckSonList(StoreCheckSon storeCheckSon);

    /**
     * 新增盘点子
     * 
     * @param storeCheckSon 盘点子
     * @return 结果
     */
    public int insertStoreCheckSon(StoreCheckSon storeCheckSon);

    /**
     * 修改盘点子
     * 
     * @param storeCheckSon 盘点子
     * @return 结果
     */
    public int updateStoreCheckSon(StoreCheckSon storeCheckSon);

    /**
     * 批量删除盘点子
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteStoreCheckSonByIds(String ids);

    /**
     * 删除盘点子信息
     * 
     * @param rowid 盘点子ID
     * @return 结果
     */
    public int deleteStoreCheckSonById(Long rowid);

    List<StoreCheckSon> selectListStoreCheckSon(StoreCheckSon storeCheckSon);

    //查询未执行完成的盘库列表
    List<StoreCheckSon> selectListStoreCheckSons(StoreCheckSon storeCheckSon);

}
