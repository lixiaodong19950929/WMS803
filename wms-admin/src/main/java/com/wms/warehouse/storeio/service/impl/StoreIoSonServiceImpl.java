package com.wms.warehouse.storeio.service.impl;

import java.util.List;

import com.wms.common.core.text.Convert;
import com.wms.warehouse.storeio.domain.StoreIo;
import com.wms.warehouse.storeio.mapper.StoreIoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.wms.warehouse.storeio.mapper.StoreIoSonMapper;
import com.wms.warehouse.storeio.domain.StoreIoSon;
import com.wms.warehouse.storeio.service.IStoreIoSonService;

/**
 * 出入库子（1.子可以由主拆分而来，还可以来自上游）Service业务层处理
 * 
 * @author assassin
 * @date 2019-12-30
 */
@Service
public class StoreIoSonServiceImpl implements IStoreIoSonService 
{
    @Autowired
    private StoreIoSonMapper storeIoSonMapper;

    @Autowired
    private StoreIoMapper storeIoMapper;

    /**
     * 查询出入库子（1.子可以由主拆分而来，还可以来自上游）
     * 
     * @param rowid 出入库子（1.子可以由主拆分而来，还可以来自上游）ID
     * @return 出入库子（1.子可以由主拆分而来，还可以来自上游）
     */
    @Override
    public StoreIoSon selectStoreIoSonById(Long rowid)
    {
        return storeIoSonMapper.selectStoreIoSonById(rowid);
    }

    /**
     * 查询出入库子（1.子可以由主拆分而来，还可以来自上游）列表
     * 
     * @param storeIoSon 出入库子（1.子可以由主拆分而来，还可以来自上游）
     * @return 出入库子（1.子可以由主拆分而来，还可以来自上游）
     */
    @Override
    public List<StoreIoSon> selectStoreIoSonList(StoreIoSon storeIoSon)
    {
        StoreIo storeIo=storeIoMapper.selectStoreIoById(storeIoSon.getTaskcode());
        if (storeIo.getTasktype().equals("1")){
            return storeIoSonMapper.selectStoreIoSonList(storeIoSon);
        }else if (storeIo.getTasktype().equals("2")){
            return storeIoSonMapper.selectStoreIoOutSonList(storeIoSon);
        }
        return null;
    }

    /**
     * 新增出入库子（1.子可以由主拆分而来，还可以来自上游）
     * 
     * @param storeIoSon 出入库子（1.子可以由主拆分而来，还可以来自上游）
     * @return 结果
     */
    @Override
    public int insertStoreIoSon(StoreIoSon storeIoSon)
    {
        return storeIoSonMapper.insertStoreIoSon(storeIoSon);
    }

    /**
     * 修改出入库子（1.子可以由主拆分而来，还可以来自上游）
     * 
     * @param storeIoSon 出入库子（1.子可以由主拆分而来，还可以来自上游）
     * @return 结果
     */
    @Override
    public int updateStoreIoSon(StoreIoSon storeIoSon)
    {
        return storeIoSonMapper.updateStoreIoSon(storeIoSon);
    }

    /**
     * 删除出入库子（1.子可以由主拆分而来，还可以来自上游）对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteStoreIoSonByIds(String ids)
    {
        return storeIoSonMapper.deleteStoreIoSonByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除出入库子（1.子可以由主拆分而来，还可以来自上游）信息
     * 
     * @param rowid 出入库子（1.子可以由主拆分而来，还可以来自上游）ID
     * @return 结果
     */
    public int deleteStoreIoSonById(Long rowid)
    {
        return storeIoSonMapper.deleteStoreIoSonById(rowid);
    }

    @Override
    public List<StoreIoSon> selectStoreIoList(StoreIoSon storeIoSon) {
        return storeIoSonMapper.selectStoreIoList(storeIoSon);
    }

    @Override
    public List<StoreIoSon> selectStoreIoOutSonList(StoreIoSon storeIoSon) {
        return storeIoSonMapper.selectStoreIoOutSonList(storeIoSon);
    }
}
