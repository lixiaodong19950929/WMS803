package com.wms.warehouse.inventroyManager.service;

import com.wms.warehouse.inventroyManager.domain.InventroyManager;
import com.wms.warehouse.inventroyManager.mapper.InventroyManagerMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @program: wms
 * @description: service
 * @author: 刺客
 * @create: 2020-01-17 11:15
 */
@Service
public class InventroyManagerServiceImp {

    @Autowired
    private InventroyManagerMapper inventroyManagerMapper;

    public Map getinventroyManagerByStock(InventroyManager inventroyManager) {
        Map<String,Object > map=new HashMap<>();

        //查询库位信息
        List<InventroyManager> inventroyManagerlist=inventroyManagerMapper.selectInventroyManagerByStock(inventroyManager);
        map.put("list",inventroyManagerlist);

        //查询库存量
        //库位数
        map.put("storeHouseCount",inventroyManagerMapper.selectInventroyManagerByStoreHouseCount(inventroyManager));
        //已使用库位数
        map.put("useStoreHouseCount",inventroyManagerMapper.selectInventroyManagerByUseStoreHouseCount(inventroyManager));

        //入库数
        map.put("storeInCount",inventroyManagerMapper.selectInventroyManagerByStoreInCount(inventroyManager));

        //出库数
        map.put("storeOutCount",inventroyManagerMapper.selectInventroyManagerByStoreOutCount(inventroyManager));

        //移库数
        map.put("storeMoveCount",inventroyManagerMapper.selectInventroyManagerByStoreMoveCount(inventroyManager));

        return map;
    }
}
