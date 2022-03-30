package com.wms.kanban.service;

import com.wms.base.device.domain.Device;
import com.wms.common.pagebean.PageBean;
import com.wms.infoquery.querystockstatus.domain.Stock;
import com.wms.kanban.domain.*;
import com.wms.warehouse.storeio.domain.StoreIo;
import com.wms.warehouse.storemove.domain.StoreMove;
import org.springframework.stereotype.Service;
import sun.management.Sensor;

import java.util.List;

@Service
public interface IKanBanService {

    /**
     * 查询使用情况
     * @return
     */
    public UseCase selectUseCase();

    /**
     * 查询设备的状态
     * @return
     */
    public List<StoreIo> selectDeviceStatus();
//    public PageBean<KBDevice> selectDeviceStatus(Integer currentPage);

    /**
     * 查询库位的任务作业 列表
     * @return
     */
    public List<Stock> selectKBStoreIoList();
//    PageBean<WhTaskCase> selectKBStoreIoList(Integer currentPage);

    /**
     * 查询入、出、移库的数量
     * @return
     */
    UseCase selectInOutMoveCount();

    /**
     * 查询堆垛机任务列表
     * @return
     */
    public List<StoreIo> selectKBTaskStackerList();
//    PageBean<TaskStacker> selectKBTaskStackerList(Integer currentPage);

    /**
     *  查询传感器的列表
     * @return
     */
    List<StoreMove> selectSensorList();
}
