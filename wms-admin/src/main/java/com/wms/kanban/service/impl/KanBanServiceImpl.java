package com.wms.kanban.service.impl;

import com.wms.base.device.domain.Device;
import com.wms.common.pagebean.PageBean;
import com.wms.infoquery.querystockstatus.domain.Stock;
import com.wms.kanban.domain.*;
import com.wms.kanban.mapper.KanBanMapper;
import com.wms.kanban.service.IKanBanService;
import com.wms.warehouse.storeio.domain.StoreIo;
import com.wms.warehouse.storemove.domain.StoreMove;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sun.management.Sensor;
import sun.nio.cs.ext.MacThai;

import java.util.ArrayList;
import java.util.List;

@Service
public class KanBanServiceImpl implements IKanBanService {

    @Autowired
    private KanBanMapper kanBanMapper;

    /**
     * 查询使用情况
     *
     * @return
     */
    @Override
    public UseCase selectUseCase() {
        UseCase useCase = new UseCase();
        // 查询库存数量
        Integer stockQuantity = kanBanMapper.selectStockNumber();
        // 查询出库的数量,2 表示已经使用，即已完成
        Integer outQuantity = 288-stockQuantity;
        // 查询任务作业未开始的数量
        Integer InQuantity = kanBanMapper.selectInTaskCountList(2);
        // 查询任务作业正在执行的数量
        Integer OutQuantity = kanBanMapper.selectOutTaskCountList(2);
        // 查询任务作业已经完成的数量
        Integer MoveQuantity = kanBanMapper.selectMoveTaskCountList(2);
        String usageRate = "0";
        if (null != stockQuantity && null != outQuantity) {
            if (outQuantity > 0 && stockQuantity > 0) {
                usageRate = String.valueOf(outQuantity / (stockQuantity + outQuantity));
            }
        }
        useCase.setStockQuantity(stockQuantity);
        useCase.setOutQuantity(outQuantity);
        useCase.setUsageRate(usageRate);
        useCase.setFinishedQuantity(MoveQuantity);
        useCase.setNotStartQuantity(InQuantity);
        useCase.setRunningQuantity(OutQuantity);
        return useCase;
    }

    /**
     * 查询入、出、移库的数量
     *
     * @return
     */
    @Override
    public UseCase selectInOutMoveCount() {
        UseCase useCase = new UseCase();
        // 移库数量
        Integer moveNumber = kanBanMapper.selectMoveTaskCountList(0);
        useCase.setMoveQuantity(moveNumber);
        // 入库数量
        Integer intoNumber = kanBanMapper.selectInTaskCountList(0);
        // 出库数量
        Integer outNumber = kanBanMapper.selectOutTaskCountList(0);
        useCase.setIntoQuantity(intoNumber);
        useCase.setOutQuantity(outNumber);
        return useCase;
    }

    /**
     * 查询设备的状态
     *
     * @return
     */
    @Override
    public List<StoreIo> selectDeviceStatus() {
//        return kanBanMapper.selectDeviceStatusList();
        return kanBanMapper.selectOutTaskList(null);
    }
    /* @Override
    public PageBean<KBDevice> selectDeviceStatus(Integer currentPage) {
        PageBean<KBDevice> pageBean = new PageBean<>();
        if (null == currentPage) {
            currentPage = 1;
        }
        // 每条显示条数
        int pageSizes = 6;

        // 总条数
        int totalCount = kanBanMapper.selectDeviceCount();
        double tc = totalCount;
        //计算总页码
        double pages = Math.ceil(tc / pageSizes);
        int totalPage = (new Double(pages)).intValue();
        int pageNum = (currentPage - 1) * pageSizes;
        // 查询设备列表
        List<KBDevice> deviceList = kanBanMapper.selectDeviceStatusList(pageNum, pageSizes);
        pageBean.setPageSizes(pageSizes);
        pageBean.setTotalCount(totalCount);
        pageBean.setTotalPage(totalPage);
        pageBean.setCurrentPage(currentPage);
        pageBean.setLists(deviceList);
        return pageBean;
    }*/

    /**
     * 查询库位的任务作业列表
     *
     * @return
     */
    @Override
    public List<Stock> selectKBStoreIoList() {
//        // 查询入库的库位任务列表
//        List<WhTaskCase> whTaskCaseList = kanBanMapper.selectKBStoreIoList("1");
//        // 查询出库的任务列表
//        List<WhTaskCase> taskCaseList = kanBanMapper.selectKBStoreIoList("2");
//        List<WhTaskCase> list = new ArrayList<>(whTaskCaseList.size() + taskCaseList.size());
//        list.addAll(whTaskCaseList);
//        list.addAll(taskCaseList);
        List<Stock> stockList=kanBanMapper.selectStockList();
        return stockList;
    }


    /* public PageBean<WhTaskCase> selectKBStoreIoList(Integer currentPage ) {
        PageBean<WhTaskCase> pageBean = new PageBean<>();
        if (null == currentPage) {
            currentPage = 1;
        }
        // 每条显示条数
        int pageSizes = 6;

        // 总条数
        int totalCount = kanBanMapper.selectKBStoreIoCount();
        double tc = totalCount;
        //计算总页码
        double pages = Math.ceil(tc / pageSizes);
        int totalPage = (new Double(pages)).intValue();
        int pageNum = (currentPage - 1) * pageSizes;
        // 查询设备列表
        List<WhTaskCase> whTaskCaseList = kanBanMapper.selectKBStoreIoList(pageNum, pageSizes);
        pageBean.setCurrentPage(currentPage);
        pageBean.setPageSizes(pageSizes);
        pageBean.setTotalCount(totalCount);
        pageBean.setTotalPage(totalPage);
        pageBean.setLists(whTaskCaseList);
        return pageBean;
    }*/


    /**
     * 查询堆垛机任务列表
     * @return
     */
    @Override
    public List<StoreIo> selectKBTaskStackerList() {
//        return kanBanMapper.selectKBTaskStackerList();
        return kanBanMapper.selectInTaskList(null);
    }



    /**
     *  查询传感器的列表
     * @return
     */
    @Override
    public List<StoreMove> selectSensorList() {
//        return kanBanMapper.selectSensorList();
        return kanBanMapper.selectMoveTaskList(null);
    }


   /* public PageBean<TaskStacker> selectKBTaskStackerList(Integer currentPage) {
        PageBean<TaskStacker> pageBean = new PageBean<>();
        if (null == currentPage) {
            currentPage = 1;
        }
        // 每条显示条数
        int pageSizes = 6;

        // 总条数
        int totalCount = kanBanMapper.selectKBTaskStackerCount();
        double tc = totalCount;
        //计算总页码
        double pages = Math.ceil(tc / pageSizes);
        int totalPage = (new Double(pages)).intValue();
        int pageNum = (currentPage - 1) * pageSizes;
        // 查询设备列表
        List<TaskStacker> whTaskCaseList = kanBanMapper.selectKBTaskStackerList(pageNum, pageSizes);
        pageBean.setPageSizes(pageSizes);
        pageBean.setCurrentPage(currentPage);
        pageBean.setTotalCount(totalCount);
        pageBean.setTotalPage(totalPage);
        pageBean.setLists(whTaskCaseList);
        return pageBean;
    }*/
}
