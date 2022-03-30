package com.wms.infoquery.queryStockRealtime.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.wms.infoquery.queryStockRealtime.mapper.MainMapper;
import com.wms.infoquery.queryStockRealtime.domain.Main;
import com.wms.infoquery.queryStockRealtime.service.IMainService;
import com.wms.common.core.text.Convert;

/**
 * 扫描记录Service业务层处理
 * 
 * @author assassin
 * @date 2020-02-06
 */
@Service
public class MainServiceImpl implements IMainService 
{
    @Autowired
    private MainMapper mainMapper;

    /**
     * 查询扫描记录
     * 
     * @param id 扫描记录ID
     * @return 扫描记录
     */
    @Override
    public Main selectMainById(Long id)
    {
        return mainMapper.selectMainById(id);
    }

    /**
     * 查询扫描记录列表
     *
     * @param main 扫描记录
     * @return 扫描记录
     */
    @Override
    public List<Main> selectMainList(Main main)
    {
        return mainMapper.selectMainList(main);
    }

    /**
     * 首页看板 查询 出入库 数量
     *
     * @param main 扫描记录
     * @return 扫描记录
     */
    @Override
    public List<Main> selectIoScanCount(Main main)
    {
        return mainMapper.selectIoScanCount(main);
    }

    /**
     * 新增扫描记录
     * 
     * @param main 扫描记录
     * @return 结果
     */
    @Override
    public int insertMain(Main main)
    {
        return mainMapper.insertMain(main);
    }

    /**
     * 修改扫描记录
     * 
     * @param main 扫描记录
     * @return 结果
     */
    @Override
    public int updateMain(Main main)
    {
        return mainMapper.updateMain(main);
    }

    /**
     * 删除扫描记录对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteMainByIds(String ids)
    {
        return mainMapper.deleteMainByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除扫描记录信息
     * 
     * @param id 扫描记录ID
     * @return 结果
     */
    public int deleteMainById(Long id)
    {
        return mainMapper.deleteMainById(id);
    }
}
