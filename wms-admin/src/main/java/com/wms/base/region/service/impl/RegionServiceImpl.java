package com.wms.base.region.service.impl;

import com.wms.base.region.domain.Region;
import com.wms.base.region.mapper.RegionMapper;
import com.wms.common.core.text.Convert;
import com.wms.base.region.service.IRegionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 库区Service业务层处理
 * 
 * @author assassin
 * @date 2019-12-27
 */
@Service
public class RegionServiceImpl implements IRegionService
{
    @Autowired
    private RegionMapper regionMapper;

    /**
     * 查询库区
     * 
     * @param regioncode 库区ID
     * @return 库区
     */
    @Override
    public Region selectRegionById(String regioncode)
    {
        return regionMapper.selectRegionById(regioncode);
    }

    /**
     * 查询库区列表
     * 
     * @param region 库区
     * @return 库区
     */
    @Override
    public List<Region> selectRegionList(Region region)
    {
        return regionMapper.selectRegionList(region);
    }

    /**
     * 新增库区
     * 
     * @param region 库区
     * @return 结果
     */
    @Override
    public int insertRegion(Region region)
    {
        return regionMapper.insertRegion(region);
    }

    /**
     * 修改库区
     * 
     * @param region 库区
     * @return 结果
     */
    @Override
    public int updateRegion(Region region)
    {
        return regionMapper.updateRegion(region);
    }

    /**
     * 删除库区对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteRegionByIds(String ids)
    {
        return regionMapper.deleteRegionByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除库区信息
     * 
     * @param whcode 库区ID
     * @return 结果
     */
    public int deleteRegionById(String whcode)
    {
        return regionMapper.deleteRegionById(whcode);
    }

    @Override
    public List<Region> getRegionByWhcode(String whcode) {
        return regionMapper.getRegionByWhcode(whcode);
    }
}
