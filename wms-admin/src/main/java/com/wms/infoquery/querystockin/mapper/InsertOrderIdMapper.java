package com.wms.infoquery.querystockin.mapper;

import com.wms.infoquery.querystockin.domain.QueryStoreIo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InsertOrderIdMapper {

    /**
     * 插入舜宇订单号
     *
     * @param outerOrderId
     * @return
     */
    public int insertOuterOrderId(@Param(value="outerOrderId")String outerOrderId);
}
