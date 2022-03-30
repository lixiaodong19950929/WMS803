package com.wms.kanban.mapper;

import com.wms.base.device.domain.Device;
import com.wms.infoquery.querystockstatus.domain.Stock;
import com.wms.kanban.domain.KBDevice;
import com.wms.kanban.domain.SensorInfo;
import com.wms.kanban.domain.TaskStacker;
import com.wms.kanban.domain.WhTaskCase;
import com.wms.warehouse.storeio.domain.StoreIo;
import com.wms.warehouse.storemove.domain.StoreMove;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import sun.management.Sensor;

import java.util.List;

@Mapper
public interface KanBanMapper {

    /**
     * 查询库存数量
     * @return
     */
    Integer selectStockNumber();

    /**
     * 查询出入库的数量
     * @return
     */
    Integer selectOutInNumber(@Param("taskState") String taskState , @Param("taskType") String taskType);

    /**
     * 查询设备列表
     * @return
     */
//    List<KBDevice> selectDeviceStatusList(@Param("pageNum") Integer pageNum, @Param("pageSizes") Integer pageSizes);
    List<KBDevice> selectDeviceStatusList();

    /**
     * 查询设备的数量
     * @return
     */
    Integer selectDeviceCount();

    /**
     * 查询库位的任务作业 列表
     * @return
     */
//    List<WhTaskCase> selectKBStoreIoList(@Param("pageNum") Integer pageNum, @Param("pageSizes") Integer pageSizes);
    List<WhTaskCase> selectKBStoreIoList(@Param("taskType") String taskType);

    /**
     * 查询移库的数量
     * @param taskState 任务状态
     * @param taskType 任务类型
     * @return
     */
    Integer selectMoveCount(@Param("taskState") String taskState , @Param("taskType") String taskType);

    /**
     * 查询堆垛机任务列表
     * @return
     */
//    List<TaskStacker> selectKBTaskStackerList(@Param("pageNum") Integer pageNum, @Param("pageSizes") Integer pageSizes);
    List<TaskStacker> selectKBTaskStackerList();

    /**
     * 查询库位任务表的行数
     * @return
     */
    Integer selectKBStoreIoCount();

    /**
     * 查询堆垛机任务表的行数
     * @return
     */
    Integer selectKBTaskStackerCount(@Param("taskState") String taskState);

    /**
     *  查询传感器的列表
     * @return
     */
    List<SensorInfo> selectSensorList();

    Integer selectInTaskCountList(Integer taskState);

    Integer selectOutTaskCountList(Integer taskState);

    Integer selectMoveTaskCountList(Integer taskState);

    List<Stock> selectStockList();

    List<StoreIo> selectInTaskList(@Param("taskState")Integer taskState);

    List<StoreIo> selectOutTaskList(@Param("taskState") Integer taskState);

    List<StoreMove> selectMoveTaskList(@Param("taskState")Integer taskState);
}
