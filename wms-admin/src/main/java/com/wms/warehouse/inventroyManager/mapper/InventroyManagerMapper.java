package com.wms.warehouse.inventroyManager.mapper;

import com.wms.warehouse.inventroyManager.domain.InventroyManager;

import java.util.List;

public interface InventroyManagerMapper {


    List<InventroyManager> selectInventroyManagerByStock(InventroyManager inventroyManager);

    int selectInventroyManagerByStoreHouseCount(InventroyManager inventroyManager);

    int selectInventroyManagerByUseStoreHouseCount(InventroyManager inventroyManager);

    int selectInventroyManagerByStoreInCount(InventroyManager inventroyManager);

    int selectInventroyManagerByStoreOutCount(InventroyManager inventroyManager);

    int selectInventroyManagerByStoreMoveCount(InventroyManager inventroyManager);

    List<InventroyManager> selectByStoreHouseUse(InventroyManager inventroyManager);

    List<InventroyManager> selectByProductInTime(InventroyManager inventroyManager);

    List<InventroyManager> selectByStoreIoTenCount(InventroyManager inventroyManager);

    // 查询最近十次的日期
    List<String> selectCreateDateList();



}
