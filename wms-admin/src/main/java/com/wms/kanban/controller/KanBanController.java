package com.wms.kanban.controller;

import com.wms.base.device.domain.Device;
import com.wms.common.pagebean.PageBean;
import com.wms.infoquery.querystockstatus.domain.Stock;
import com.wms.kanban.domain.KBDevice;
import com.wms.kanban.domain.SensorInfo;
import com.wms.kanban.domain.TaskStacker;
import com.wms.kanban.domain.WhTaskCase;
import com.wms.kanban.service.IKanBanService;
import com.wms.warehouse.storeio.domain.StoreIo;
import com.wms.warehouse.storemove.domain.StoreMove;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import sun.management.Sensor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/wms/kanban")
public class KanBanController {

    @Autowired
    private IKanBanService kanBanService;

    /**
     * 查询使用情况和入出移库的数量
     * @return
     */
    @ResponseBody
    @PostMapping("/useInOutMovePie")
    public Map<String,Object> charts() {
        Map<String,Object> map = new HashMap<>();
        map.put("useCasePie",kanBanService.selectUseCase());
        map.put("inOutMovePie",kanBanService.selectInOutMoveCount());
        return map;
    }

    /**
     * 查询设备列表
     * @param
     * @return
     */
    @ResponseBody
    @PostMapping("/deviceList")
    public List<StoreIo> kbDeviceList() {
        return kanBanService.selectDeviceStatus();
    }

    /**
     * 查询库位任务列表
     * @param
     * @return
     */
    @ResponseBody
    @PostMapping("/kbStoreIoList")
    public List<Stock> kbStoreIoList() {
        return kanBanService.selectKBStoreIoList();
    }

    /**
     * 查询堆垛机的任务列表
     * @param
     * @return
     */
    @ResponseBody
    @PostMapping("/kbTaskStackerList")
    public List<StoreIo> kbTaskStackerList() {
        return kanBanService.selectKBTaskStackerList();
    }

    @ResponseBody
    @PostMapping("/getSensorList")
    public List<StoreMove> selectSensorInfoList(){
        List<StoreMove> list = kanBanService.selectSensorList();
        return list;
    }


    @GetMapping()
    public String KanBan()
    {
        return "/kanban";
    }




}
