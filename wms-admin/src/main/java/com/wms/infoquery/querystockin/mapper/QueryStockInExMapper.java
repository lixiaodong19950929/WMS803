package com.wms.infoquery.querystockin.mapper;
import com.wms.infoquery.querystockin.domain.StoreIoEx;

import com.wms.infoquery.querystockin.domain.StoreIoSonEx;
import com.wms.infoquery.querystockstatus.domain.Stock;
import com.wms.task.wincc.pojo.SernEntity;
import com.wms.task.wincc.pojo.StoreIoEntity;
import com.wms.warehouse.sern.domain.Sern;
import com.wms.warehouse.storeio.domain.StoreIo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QueryStockInExMapper {

    List<StoreIoEx> selectStoreIoPage(StoreIoEx storeIoEx);

    int selectStoreIoTotalCount(@Param(value = "taskType") String taskType);

    int insertStoreIo(StoreIoEx storeIoEx);

    int updateStoreIo(StoreIoEx storeIoEx);

    int insertStoreIoSon(StoreIoSonEx storeIoSonEx);


    int insertStoreIoSonList(List<StoreIoSonEx> storeIoSonExList);

    int insertSernList(List<Sern> sernlist);

    List<StoreIoEntity> selectStoreList(String taskCode);

    StoreIoEntity selectStoreIoByOther(String outerOrderId);

    List<Stock> selectStockNullList();

    int updateIo(StoreIoEntity storeIoEx);

    int updateIoOuterOrderId(StoreIoEntity storeIoEx);

    int updateSern(SernEntity sern);

    int updateStock(Stock stock);

    int updateIoSon(StoreIoSonEx storeIoSon);

    List<Stock> selectByStockOutList(String taskCode);

    int updateStockNull(Stock stock);

    int updateStockEnd(Stock stock);

}
