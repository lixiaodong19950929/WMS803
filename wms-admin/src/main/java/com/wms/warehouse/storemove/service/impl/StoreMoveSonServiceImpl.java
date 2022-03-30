package com.wms.warehouse.storemove.service.impl;

import java.util.List;

import com.wms.warehouse.storemove.domain.StoreMoveSon;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.wms.warehouse.storemove.mapper.StoreMoveSonMapper;
import com.wms.warehouse.storemove.service.IStoreMoveSonService;
import com.wms.common.core.text.Convert;

/**
 * 移库子Service业务层处理
 * 
 * @author assassin
 * @date 2020-01-16
 */
@Service
public class StoreMoveSonServiceImpl implements IStoreMoveSonService 
{
    @Autowired
    private StoreMoveSonMapper storeMoveSonMapper;

    /**
     * 查询移库子
     * 
     * @param rowid 移库子ID
     * @return 移库子
     */
    @Override
    public StoreMoveSon selectStoreMoveSonById(Long rowid)
    {
        return storeMoveSonMapper.selectStoreMoveSonById(rowid);
    }

    /**
     * 查询移库子列表
     * 
     * @param storeMoveSon 移库子
     * @return 移库子
     */
    @Override
    public List<StoreMoveSon> selectStoreMoveSonList(StoreMoveSon storeMoveSon)
    {
        return storeMoveSonMapper.selectStoreMoveSonList(storeMoveSon);
    }

    /**
     * 新增移库子
     * 
     * @param storeMoveSon 移库子
     * @return 结果
     */
    @Override
    public int insertStoreMoveSon(StoreMoveSon storeMoveSon)
    {
        return storeMoveSonMapper.insertStoreMoveSon(storeMoveSon);
    }

    /**
     * 修改移库子
     * 
     * @param storeMoveSon 移库子
     * @return 结果
     */
    @Override
    public int updateStoreMoveSon(StoreMoveSon storeMoveSon)
    {
        return storeMoveSonMapper.updateStoreMoveSon(storeMoveSon);
    }

    /**
     * 删除移库子对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteStoreMoveSonByIds(String ids)
    {
        return storeMoveSonMapper.deleteStoreMoveSonByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除移库子信息
     * 
     * @param rowid 移库子ID
     * @return 结果
     */
    public int deleteStoreMoveSonById(Long rowid)
    {
        return storeMoveSonMapper.deleteStoreMoveSonById(rowid);
    }

    @Override
    public List<StoreMoveSon> selectStoreMoveList(StoreMoveSon storeMoveSon) {
        return storeMoveSonMapper.selectStoreMoveList(storeMoveSon);
    }
}
