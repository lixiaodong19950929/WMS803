package com.wms.warehouse.sern.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.wms.warehouse.sern.mapper.SernMapper;
import com.wms.warehouse.sern.domain.Sern;
import com.wms.warehouse.sern.service.ISernService;
import com.wms.common.core.text.Convert;

/**
 * 产品条码Service业务层处理
 * 
 * @author assassin
 * @date 2021-03-02
 */
@Service
public class SernServiceImpl implements ISernService 
{
    @Autowired
    private SernMapper sernMapper;

    /**
     * 查询产品条码
     * 
     * @param id 产品条码ID
     * @return 产品条码
     */
    @Override
    public Sern selectSernById(Long id)
    {
        return sernMapper.selectSernById(id);
    }

    /**
     * 查询产品条码列表
     * 
     * @param sern 产品条码
     * @return 产品条码
     */
    @Override
    public List<Sern> selectSernList(Sern sern)
    {
        return sernMapper.selectSernList(sern);
    }

    /**
     * 新增产品条码
     * 
     * @param sern 产品条码
     * @return 结果
     */
    @Override
    public int insertSern(Sern sern)
    {
        return sernMapper.insertSern(sern);
    }

    /**
     * 修改产品条码
     * 
     * @param sern 产品条码
     * @return 结果
     */
    @Override
    public int updateSern(Sern sern)
    {
        return sernMapper.updateSern(sern);
    }

    /**
     * 删除产品条码对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteSernByIds(String ids)
    {
        return sernMapper.deleteSernByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除产品条码信息
     * 
     * @param id 产品条码ID
     * @return 结果
     */
    public int deleteSernById(Long id)
    {
        return sernMapper.deleteSernById(id);
    }
}
