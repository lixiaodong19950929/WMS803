package com.wms.base.region.service;

import com.wms.base.region.domain.Region;
import java.util.List;

/**
 * 库区Service接口
 * 
 * @author assassin
 * @date 2019-12-27
 */
public interface IRegionService 
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
     * 批量删除库区
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteRegionByIds(String ids);

    /**
     * 删除库区信息
     * 
     * @param whcode 库区ID
     * @return 结果
     */
    public int deleteRegionById(String whcode);

    /**
     *  根据whcode获取库区
     * @param whcode
     * @return
     */
    List<Region> getRegionByWhcode(String whcode);
}
