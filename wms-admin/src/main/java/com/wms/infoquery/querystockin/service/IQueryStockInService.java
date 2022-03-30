package com.wms.infoquery.querystockin.service;


import com.wms.infoquery.querystockin.domain.QueryStoreIo;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 出入库主Service接口
 * 
 * @author assassin
 * @date 2019-12-30
 */
@Service
public interface IQueryStockInService
{
    /**
     * 查询入库列表
     * @param queryStockIn
     * @return
     */
    public List<QueryStoreIo> selectQueryStockInList(QueryStoreIo queryStockIn);

    /**
     * 查询入库明细列表
     * @param queryStockIn
     * @return
     */
    public List<QueryStoreIo> selectQueryStockInDetailList(QueryStoreIo queryStockIn);
}
