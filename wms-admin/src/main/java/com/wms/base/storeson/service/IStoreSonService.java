package com.wms.base.storeson.service;

import com.wms.base.storeson.domain.StoreSon;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 出库物料明细Service接口
 * 
 * @author assassin
 * @date 2021-08-25
 */
public interface IStoreSonService 
{
    /**
     * 查询出库物料明细
     * 
     * @param id 出库物料明细ID
     * @return 出库物料明细
     */
    public StoreSon selectStoreSonById(Long id);

    /**
     * 查询出库物料明细列表
     * 
     * @param storeSon 出库物料明细
     * @return 出库物料明细集合
     */
    public List<StoreSon> selectStoreSonList(StoreSon storeSon);

    /**
     * 新增出库物料明细
     * 
     * @param storeSon 出库物料明细
     * @return 结果
     */
    public int insertStoreSon(StoreSon storeSon);

    /**
     * 修改出库物料明细
     * 
     * @param storeSon 出库物料明细
     * @return 结果
     */
    public int updateStoreSon(StoreSon storeSon);

    /**
     * 批量删除出库物料明细
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteStoreSonByIds(String ids);

    /**
     * 删除出库物料明细信息
     * 
     * @param id 出库物料明细ID
     * @return 结果
     */
    public int deleteStoreSonById(Long id);
    //批量新增
    public int insertStoreSons(List<StoreSon> storeSons);


    //根据行号和条码查询条码明细表中已经存入的条码
    public List<StoreSon> getOutTaskInfoByTaskProduct(@Param("taskcode") String taskcode, @Param("productcode")String productcode);

    //根据任务号查询已经指定库位的任务明细
    public List<StoreSon> getInTaskInfoByTaskCode(@Param("taskcode") String taskcode);

    public List<StoreSon> deleteStoreSonByTaskAndPro(@Param("taskcode") String taskcode, @Param("productcode")String productcode);

    int executeStoreSonByTaskCodes(String taskcodes);
}
