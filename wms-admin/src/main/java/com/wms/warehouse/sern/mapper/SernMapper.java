package com.wms.warehouse.sern.mapper;

import com.wms.warehouse.sern.domain.Sern;
import java.util.List;

/**
 * 产品条码Mapper接口
 * 
 * @author assassin
 * @date 2021-03-02
 */
public interface SernMapper 
{
    /**
     * 查询产品条码
     * 
     * @param id 产品条码ID
     * @return 产品条码
     */
    public Sern selectSernById(Long id);

    /**
     * 查询产品条码列表
     * 
     * @param sern 产品条码
     * @return 产品条码集合
     */
    public List<Sern> selectSernList(Sern sern);

    /**
     * 新增产品条码
     * 
     * @param sern 产品条码
     * @return 结果
     */
    public int insertSern(Sern sern);

    /**
     * 修改产品条码
     * 
     * @param sern 产品条码
     * @return 结果
     */
    public int updateSern(Sern sern);

    /**
     * 删除产品条码
     * 
     * @param id 产品条码ID
     * @return 结果
     */
    public int deleteSernById(Long id);

    /**
     * 批量删除产品条码
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteSernByIds(String[] ids);
}
