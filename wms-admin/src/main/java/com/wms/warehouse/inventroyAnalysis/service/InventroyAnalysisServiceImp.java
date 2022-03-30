package com.wms.warehouse.inventroyAnalysis.service;

import com.wms.warehouse.inventroyManager.domain.InventoryTime;
import com.wms.warehouse.inventroyManager.domain.InventroyManager;
import com.wms.warehouse.inventroyManager.domain.TypeAndCount;
import com.wms.warehouse.inventroyManager.mapper.InventroyManagerMapper;
import org.apache.shiro.util.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @program: wms
 * @description: service
 * @author: 刺客
 * @create: 2020-01-17 11:15
 */
@Service
public class InventroyAnalysisServiceImp {

    @Autowired
    private InventroyManagerMapper inventroyManagerMapper;

    public Map getInventroyAnalysis(InventroyManager inventroyManager) {
        Map<String,Object > map=new HashMap<>();

        //查询库存量
        //库位数
        map.put("storeHouseCount",inventroyManagerMapper.selectInventroyManagerByStoreHouseCount(inventroyManager));
        //已使用库位数
        map.put("useStoreHouseCount",inventroyManagerMapper.selectInventroyManagerByUseStoreHouseCount(inventroyManager));


        //库位使用时间
        map.put("useTimeStoreHouseCount",inventroyManagerMapper.selectByStoreHouseUse(inventroyManager));

        //产品入库时间
        map.put("productInTimeCount",inventroyManagerMapper.selectByProductInTime(inventroyManager));

        //近十次出入库
        List<InventroyManager> list = null;
        // 最近10次的日期,比如有3个日期 2020-02-24,2020-02-25,2020-02-26
        List<String> dateList = inventroyManagerMapper.selectCreateDateList();
        // 最近十次的记录
        List<InventroyManager> inventroyManagerList = inventroyManagerMapper.selectByStoreIoTenCount(inventroyManager);

        List<InventoryTime> inventoryTimeList = new ArrayList<>();
        InventoryTime inventoryTime = null;
        TypeAndCount typeAndCount = null;
        // 分别查询在这日期里的记录,判断是否为空
        if (!CollectionUtils.isEmpty(dateList)&&!CollectionUtils.isEmpty(inventroyManagerList)) {
           for (String date: dateList) {
               inventoryTime = new InventoryTime();
               typeAndCount = new TypeAndCount();
               list = new ArrayList<>();
               Iterator<InventroyManager> iterator = inventroyManagerList.iterator();
               while (iterator.hasNext()) {
                   // 如果日期相同，添加到集合中
                   InventroyManager invent = iterator.next();
                   if (invent.getCreateTime().equals(date)) {
                       list.add(invent);
                       iterator.remove();
                   }
               }
               // 在刚刚这个集合中再进行分割判断 是出库还是入库，即是 tasktype = 1 or 2
               if (!CollectionUtils.isEmpty(list)) {
                   int countIn = 0;
                   int countOut = 0;
                   for (InventroyManager im : list) {
                       // 如果是入库，那么将数量相加
                       if (im.getTasktype() == 1) {
                           countIn += im.getQuantity();
                       }

                       // 如果是出库将数量相加
                       if (im.getTasktype() == 2) {
                           countOut += im.getQuantity();
                       }
                   }
                   // 入库的数量
                   typeAndCount.setInCount(countIn);
                   // 出库的数量
                   typeAndCount.setOutCount(countOut);
               }
               inventoryTime.setCreateTime(date);
               inventoryTime.setTypeAndCount(typeAndCount);
               inventoryTimeList.add(inventoryTime);
           }
        }
        map.put("storeIoTenCount",inventoryTimeList);
        return map;
    }
}
