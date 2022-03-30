package com.wms.infoquery.querystockstatus.service;

import com.wms.infoquery.querystockstatus.domain.Stock;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 库存Service接口
 * 
 * @author assassin
 * @date 2020-01-16
 */
public interface IStockService 
{
    /**
     * 查询库存
     * 
     * @param id 库存ID
     * @return 库存
     */
    public Stock selectStockById(Long id);

    /**
     * 查询库存列表
     * 
     * @param stock 库存
     * @return 库存集合
     */
    public List<Stock> selectStockList(Stock stock);

    /**
     * 新增库存
     * 
     * @param stock 库存
     * @return 结果
     */
    public int insertStock(Stock stock);

    /**
     * 修改库存
     * 
     * @param stock 库存
     * @return 结果
     */
    public int updateStock(Stock stock);

    /**
     * 批量删除库存
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteStockByIds(String ids);

    /**
     * 删除库存信息
     * 
     * @param id 库存ID
     * @return 结果
     */
    public int deleteStockById(Long id);

    List<Stock> selectByProductOut(Stock stock);

    /**
     * 查询库存明细
     * @param stock
     * @return
     */
    List<Stock> selectQueryStockList(Stock stock);

    /**
     * 查询每种产品占用库位总数
     * @param stock
     * @return
     */
    List<Stock> selectProStorehouseCount(Stock stock);

    /**
     * 批量修改库存上限和下限
     * @param stock
     * @return
     */
    int updateStockBulk(Stock stock);

    List<Stock> selectByproductStoreOut(Stock stock);

    /**
     * 查询库存预警列表
     * @param stock
     * @return
     */
    List<Stock> selectStockWarningList(Stock stock);
}
