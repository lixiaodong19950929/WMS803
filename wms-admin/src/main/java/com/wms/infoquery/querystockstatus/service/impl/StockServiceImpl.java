package com.wms.infoquery.querystockstatus.service.impl;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.wms.infoquery.querystockstatus.mapper.StockMapper;
import com.wms.infoquery.querystockstatus.domain.Stock;
import com.wms.infoquery.querystockstatus.service.IStockService;
import com.wms.common.core.text.Convert;

/**
 * 库存Service业务层处理
 * 
 * @author assassin
 * @date 2020-01-16
 */
@Service
public class StockServiceImpl implements IStockService 
{
    @Autowired
    private StockMapper stockMapper;

    /**
     * 查询库存
     * 
     * @param id 库存ID
     * @return 库存
     */
    @Override
    public Stock selectStockById(Long id)
    {
        return stockMapper.selectStockById(id);
    }

    /**
     * 查询库存列表
     * 
     * @param stock 库存
     * @return 库存
     */
    @Override
    public List<Stock> selectStockList(Stock stock)
    {
        return stockMapper.selectStockList(stock);
    }

    /**
     * 查询每种产品占用库位总数
     *
     * @param stock 库存
     * @return 库存
     */
    @Override
    public List<Stock> selectProStorehouseCount(Stock stock)
    {
        System.out.println("222222222222222222");
        return stockMapper.selectProStorehouseCount(stock);
    }

    /**
     * 新增库存
     * 
     * @param stock 库存
     * @return 结果
     */
    @Override
    public int insertStock(Stock stock)
    {
        return stockMapper.insertStock(stock);
    }

    /**
     * 修改库存
     * 
     * @param stock 库存
     * @return 结果
     */
    @Override
    public int updateStock(Stock stock)
    {
        return stockMapper.updateStock(stock);
    }

    /**
     * 删除库存对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteStockByIds(String ids)
    {
        return stockMapper.deleteStockByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除库存信息
     * 
     * @param id 库存ID
     * @return 结果
     */
    public int deleteStockById(Long id)
    {
        return stockMapper.deleteStockById(id);
    }

    @Override
    public List<Stock> selectByProductOut(Stock stock) {
        return stockMapper.selectByProductOut(stock);
    }

    @Override
    public List<Stock> selectByproductStoreOut(Stock stock) {
        return stockMapper.selectByproductStoreOut(stock);
    }

    /**
     * 查询库存明细
     * @param stock
     * @return
     */
    @Override
    public List<Stock> selectQueryStockList(Stock stock) {
        return stockMapper.selectQueryStockList(stock);
    }

    /**
     * 批量修改库存上下限数量
     * @param stock
     * @return
     */
    @Override
    public int updateStockBulk(Stock stock) {
        return stockMapper.updateStockBulk(stock);
    }


    /**
     * 查询库存预警列表
     * @param stock
     * @return
     */
    @Override
    public List<Stock> selectStockWarningList(Stock stock) {
        return stockMapper.selectStockWarningList(stock);
    }
}
