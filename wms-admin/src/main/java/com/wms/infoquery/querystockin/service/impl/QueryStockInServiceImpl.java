package com.wms.infoquery.querystockin.service.impl;

import com.wms.infoquery.querystockin.domain.QueryStoreIo;
import com.wms.infoquery.querystockin.mapper.QueryStockInMapper;
import com.wms.infoquery.querystockin.service.IQueryStockInService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 出入库子（1.子可以由主拆分而来，还可以来自上游）Service业务层处理
 * 
 * @author assassin
 * @date 2019-12-30
 */
@Service
public class QueryStockInServiceImpl implements IQueryStockInService
{

    @Autowired
    private QueryStockInMapper queryStockInMapper;

    /**
     * 查询入库列表
     * @param queryStockIn
     * @return
     */
    @Override
    public List<QueryStoreIo> selectQueryStockInList(QueryStoreIo queryStockIn) {
        return queryStockInMapper.selectQueryStockInList(queryStockIn);
    }

    /**
     * 查询入库明细列表
     * @param queryStockIn
     * @return
     */
    @Override
    public List<QueryStoreIo> selectQueryStockInDetailList(QueryStoreIo queryStockIn) {
        return queryStockInMapper.selectQueryStockInDetailList(queryStockIn);
    }
}
