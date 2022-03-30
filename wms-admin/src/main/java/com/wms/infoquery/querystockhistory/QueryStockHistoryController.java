package com.wms.infoquery.querystockhistory;

import com.wms.common.core.controller.BaseController;
import com.wms.common.core.page.TableDataInfo;
import com.wms.infoquery.queryStockRealtime.domain.Main;
import com.wms.infoquery.queryStockRealtime.service.IMainService;
import com.wms.infoquery.querystockin.domain.QueryStoreIo;
import com.wms.infoquery.querystockstatus.domain.Stock;
import com.wms.warehouse.storeio.domain.Rgv;
import com.wms.warehouse.storeio.domain.Stacker;
import com.wms.warehouse.storeio.service.IRgvService;
import com.wms.warehouse.storeio.service.IStackerService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/infoquery/querystockhistory")
public class QueryStockHistoryController extends BaseController {

    @Autowired
    private IMainService mainService;

    @Autowired
    private IRgvService rgvService;

    @Autowired
    private IStackerService stackerService;

    private String prefix = "infoquery/querystockhistory";


    @RequiresPermissions("infoquery:querystockhistory:view")
    @GetMapping()
    public String querystockhistory() {
        return prefix + "/querystockhistory";
    }

    /**
     * 查询库存列表
     */
    @RequiresPermissions("infoquery:querystockhistory:list")
    @PostMapping("/rgvList")
    @ResponseBody
    public TableDataInfo rgvList(Rgv rgv)
    {
        startPage();
        List<Rgv> list = rgvService.selectRgvList(rgv);
        return getDataTable(list);
    }

    /**
     * 查询出入库主列表
     */
    @RequiresPermissions("infoquery:querystockhistory:list")
    @PostMapping("/stackerList")
    @ResponseBody
    public TableDataInfo stackerList(Stacker stacker)
    {
        startPage();
        List<Stacker> list = stackerService.selectStackerList(stacker);
        return getDataTable(list);
    }

    /**
     * 查询出入库主明细列表
     */
    @RequiresPermissions("infoquery:querystockhistory:list")
    @PostMapping("/mainList")
    @ResponseBody
    public TableDataInfo mainList(Main main)
    {
        startPage();
        List<Main> list = mainService.selectMainList(main);
        return getDataTable(list);
    }

}
