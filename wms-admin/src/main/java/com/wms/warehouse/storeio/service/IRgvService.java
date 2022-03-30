package com.wms.warehouse.storeio.service;

import com.wms.warehouse.storeio.domain.Rgv;
import java.util.List;

/**
 * RGV任务Service接口
 * 
 * @author assassin
 * @date 2020-01-03
 */
public interface IRgvService 
{
    /**
     * 查询RGV任务
     * 
     * @param id RGV任务ID
     * @return RGV任务
     */
    public Rgv selectRgvById(Long id);

    /**
     * 查询RGV任务列表
     * 
     * @param rgv RGV任务
     * @return RGV任务集合
     */
    public List<Rgv> selectRgvList(Rgv rgv);

    /**
     * 新增RGV任务
     * 
     * @param rgv RGV任务
     * @return 结果
     */
    public int insertRgv(Rgv rgv);

    /**
     * 修改RGV任务
     * 
     * @param rgv RGV任务
     * @return 结果
     */
    public int updateRgv(Rgv rgv);

    /**
     * 批量删除RGV任务
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteRgvByIds(String ids);

    /**
     * 删除RGV任务信息
     * 
     * @param id RGV任务ID
     * @return 结果
     */
    public int deleteRgvById(Long id);
}
