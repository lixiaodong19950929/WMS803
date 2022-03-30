package com.wms.infoquery.querystockstatus.mapper;

import com.wms.infoquery.querystockstatus.domain.Stock;
import com.wms.infoquery.querystockstatus.domain.StockEx;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StockExMapper {
    List<StockEx> selectStockPage(StockEx stockEx);

    Integer selectStockTotalCount();

    Stock selectByStock(@Param("productCode") String productCode, @Param("startCode")String startCode);
}
