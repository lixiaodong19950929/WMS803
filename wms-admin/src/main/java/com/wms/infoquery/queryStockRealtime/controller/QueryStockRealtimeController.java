package com.wms.infoquery.queryStockRealtime.controller;

import com.wms.common.core.controller.BaseController;
import com.wms.common.core.page.TableDataInfo;
import com.wms.infoquery.queryStockRealtime.domain.Main;
import com.wms.infoquery.queryStockRealtime.service.IMainService;
import com.wms.warehouse.storeio.domain.Rgv;
import com.wms.warehouse.storeio.domain.Stacker;
import com.wms.warehouse.storeio.service.IRgvService;
import com.wms.warehouse.storeio.service.IStackerService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 当前任务查询Controller
 * 
 * @author lzk
 * @date 2020-02-06
 */
@Controller
@RequestMapping("/infoQuery/queryStockRealtime")
public class QueryStockRealtimeController extends BaseController
{
    private String prefix = "infoquery/queryStockRealtime";

    @Autowired
    private IMainService mainService;

    @Autowired
    private IRgvService rgvService;

    @Autowired
    private IStackerService stackerService;

    @RequiresPermissions("infoquery:queryStockRealtime:view")
    @GetMapping()
    public String queryStockRealtime()
    {
        return prefix + "/queryStockRealtime";
    }

    /**
     * 查询扫描记录列表
     */
    @RequiresPermissions("infoquery:queryStockRealtime:list")
    @PostMapping("/mainList")
    @ResponseBody
    public TableDataInfo mainList(Main main)
    {
        startPage();
        List<Main> list = mainService.selectMainList(main);
        return getDataTable(list);
    }

    /**
     * 首页看板 查询 出入库 数量
     */
    @RequiresPermissions("infoquery:queryStockRealtime:list")
    @PostMapping("/selectIoScanCount")
    @ResponseBody
    public TableDataInfo selectIoScanCount(Main main)
    {
        startPage();
        List<Main> list = mainService.selectIoScanCount(main);
        return getDataTable(list);
    }

    /**
     * 查询RGV任务列表
     */
    @RequiresPermissions("infoquery:queryStockRealtime:list")
    @PostMapping("/rgvList")
    @ResponseBody
    public TableDataInfo rgvList(Rgv rgv)
    {
        startPage();
        List<Rgv> list = rgvService.selectRgvList(rgv);
        return getDataTable(list);
    }

    /**
     * 查询堆垛机任务列表
     */
    @RequiresPermissions("infoquery:queryStockRealtime:list")
    @PostMapping("/stackerList")
    @ResponseBody
    public TableDataInfo stackerList(Stacker stacker)
    {
        startPage();
        List<Stacker> list = stackerService.selectStackerList(stacker);
        return getDataTable(list);
    }
}
