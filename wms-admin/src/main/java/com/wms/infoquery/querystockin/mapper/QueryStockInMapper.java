package com.wms.infoquery.querystockin.mapper;


import com.wms.infoquery.querystockin.domain.QueryStoreIo;
import com.wms.infoquery.querystockin.domain.StoreIoEx;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 入库表
 *
 * @author assassin
 * @date 2019-12-30
 */
@Mapper
public interface QueryStockInMapper {
    /**
     * 查询入库列表
     *
     * @param queryStockIn
     * @return
     */
    public List<QueryStoreIo> selectQueryStockInList(QueryStoreIo queryStockIn);

    /**
     * 查询入库明细列表
     *
     * @param queryStockIn
     * @return
     */
    public List<QueryStoreIo> selectQueryStockInDetailList(QueryStoreIo queryStockIn);
}
