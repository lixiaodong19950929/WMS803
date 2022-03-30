package com.wms.warehouse.storemove.mapper;

import com.wms.infoquery.querystockstatus.domain.Stock;
import com.wms.task.wincc.pojo.StoreCheckEntity;
import com.wms.warehouse.storemove.domain.StoreMoveEx;
import com.wms.warehouse.storemove.domain.StoreMoveSonEx;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Administrator on 2020/2/27.
 */
@Repository
public interface StoreMoveExMapper {
    List<StoreMoveEx> selectStoreMovePage(StoreMoveEx StoreMoveEx);

    Integer selectStoreMoveTotalCount();

    Integer insertStoreMove(StoreMoveEx storeMoveEx);

    Integer insertStoreMoveSon(StoreMoveSonEx  storeMoveSonEx);

    int insertStoreMoveSonList(List<StoreMoveSonEx> storeIoSonExList);

    List<StoreMoveEx> selectStoreMoveList(String taskCode);

    StoreMoveEx selectStoreMoveByOtherList(String OuterOrderId);

    List<StoreMoveSonEx> selectStoreMoveSonList(String taskCode);

    Stock selectStock(String StorehouseCode);

    int updateMoveSon(StoreMoveSonEx storeMoveSonEx);

    int updateMove(StoreMoveEx storeMoveEx);

    int updateMoveOuterOrderId(StoreMoveEx storeMoveEx);

}
