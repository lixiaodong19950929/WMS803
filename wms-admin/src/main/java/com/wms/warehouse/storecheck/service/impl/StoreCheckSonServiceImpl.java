package com.wms.warehouse.storecheck.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.wms.warehouse.storecheck.mapper.StoreCheckSonMapper;
import com.wms.warehouse.storecheck.domain.StoreCheckSon;
import com.wms.warehouse.storecheck.service.IStoreCheckSonService;
import com.wms.common.core.text.Convert;

/**
 * 盘点子Service业务层处理
 * 
 * @author assassin
 * @date 2020-02-10
 */
@Service
public class StoreCheckSonServiceImpl implements IStoreCheckSonService 
{
    @Autowired
    private StoreCheckSonMapper storeCheckSonMapper;

    /**
     * 查询盘点子
     * 
     * @param rowid 盘点子ID
     * @return 盘点子
     */
    @Override
    public StoreCheckSon selectStoreCheckSonById(Long rowid)
    {
        return storeCheckSonMapper.selectStoreCheckSonById(rowid);
    }

    /**
     * 查询盘点子列表
     * 
     * @param storeCheckSon 盘点子
     * @return 盘点子
     */
    @Override
    public List<StoreCheckSon> selectStoreCheckSonList(StoreCheckSon storeCheckSon)
    {
        return storeCheckSonMapper.selectStoreCheckSonList(storeCheckSon);
    }

    /**
     * 新增盘点子
     * 
     * @param storeCheckSon 盘点子
     * @return 结果
     */
    @Override
    public int insertStoreCheckSon(StoreCheckSon storeCheckSon)
    {
        return storeCheckSonMapper.insertStoreCheckSon(storeCheckSon);
    }

    /**
     * 修改盘点子
     * 
     * @param storeCheckSon 盘点子
     * @return 结果
     */
    @Override
    public int updateStoreCheckSon(StoreCheckSon storeCheckSon)
    {
        return storeCheckSonMapper.updateStoreCheckSon(storeCheckSon);
    }

    /**
     * 删除盘点子对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteStoreCheckSonByIds(String ids)
    {
        return storeCheckSonMapper.deleteStoreCheckSonByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除盘点子信息
     * 
     * @param rowid 盘点子ID
     * @return 结果
     */
    public int deleteStoreCheckSonById(Long rowid)
    {
        return storeCheckSonMapper.deleteStoreCheckSonById(rowid);
    }

    @Override
    public List<StoreCheckSon> selectListStoreCheckSon(StoreCheckSon storeCheckSon) {
        return storeCheckSonMapper.selectListStoreCheckSon(storeCheckSon);
    }

    @Override
    public List<StoreCheckSon> selectListStoreCheckSons(StoreCheckSon storeCheckSon) {
        return storeCheckSonMapper.selectListStoreCheckSons(storeCheckSon);
    }
}
