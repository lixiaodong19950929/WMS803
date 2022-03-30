package com.wms.warehouse.storecheck.mapper;

import com.wms.task.wincc.pojo.StoreCheckEntity;
import com.wms.warehouse.storecheck.domain.StoreCheckEx;
import com.wms.warehouse.storecheck.domain.StoreCheckSonEx;
import com.wms.warehouse.storemove.domain.StoreMoveSonEx;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StoreCheckExMapper {
    List<StoreCheckEx> selectStoreCheckPage(StoreCheckEx storeCheckEx);

    Integer selectStoreCheckTotalCount();

    Integer insertStoreCheck(StoreCheckEx storeCheckEx);

    Integer insertStoreCheckSon(StoreCheckSonEx storeCheckSonEx);

    int insertStoreCheckSonList(List<StoreCheckSonEx> storeIoSonExList);

    List<StoreCheckEntity> selectStoreCheckList(String taskCode);

    StoreCheckEntity selectStoreCheckByOuther(String outerOrderId);

    List<StoreCheckSonEx> selectStoreCheckSonList(String taskCode);

    int updateSon(StoreCheckSonEx storeCheckSonEx);

    int updateCheck(StoreCheckEntity storeCheckEntity);
}
