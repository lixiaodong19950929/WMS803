package com.wms.base.region.mapper;

import com.wms.base.region.domain.Region;
import java.util.List;

/**
 * 库区Mapper接口
 * 
 * @author assassin
 * @date 2019-12-27
 */
public interface RegionMapper 
{
    /**
     * 查询库区
     * 
     * @param regioncode 库区ID
     * @return 库区
     */
    public Region selectRegionById(String regioncode);

    /**
     * 查询库区列表
     * 
     * @param region 库区
     * @return 库区集合
     */
    public List<Region> selectRegionList(Region region);

    /**
     * 新增库区
     * 
     * @param region 库区
     * @return 结果
     */
    public int insertRegion(Region region);

    /**
     * 修改库区
     * 
     * @param region 库区
     * @return 结果
     */
    public int updateRegion(Region region);

    /**
     * 删除库区
     * 
     * @param regioncode 库区ID
     * @return 结果
     */
    public int deleteRegionById(String regioncode);

    /**
     * 批量删除库区
     * 
     * @param regioncodes 需要删除的数据ID
     * @return 结果
     */
    public int deleteRegionByIds(String[] regioncodes);

    /**
     *  根据whcode获取库区
     * @param whcode 仓库ID
     * @return
     */
    List<Region> getRegionByWhcode(String whcode);
}
