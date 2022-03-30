package com.wms.infoquery.querystockbycode.controller;

import com.wms.common.core.controller.BaseController;
import com.wms.common.core.page.TableDataInfo;
import com.wms.infoquery.querystockin.domain.QueryStoreIo;
import com.wms.infoquery.querystockin.service.IQueryStockInService;
import com.wms.infoquery.querystockstatus.domain.Stock;
import com.wms.infoquery.querystockstatus.service.IStockService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/infoQuery/queryStockByCode")
public class QueryStockByCodeController extends BaseController {

    @Autowired
    private IStockService stockService;

    @Autowired
    private IQueryStockInService queryStockInService;

    private String prefix = "infoquery/querystockbycode";

    @RequiresPermissions("infoquery:queryStockByCode:view")
    @GetMapping()
    public String queryStockByCode() {
        return prefix + "/querystockbycode";
    }

    /**
     * 查询库存列表
     */
    @RequiresPermissions("infoquery:queryStockByCode:list")
    @PostMapping("/selectStockList")
    @ResponseBody
    public TableDataInfo selectQueryStockList(Stock stock)
    {
        startPage();
        List<Stock> list = stockService.selectQueryStockList(stock);
        return getDataTable(list);
    }

    /**
     * 查询出入库主列表
     */
    @RequiresPermissions("infoquery:queryStockByCode:list")
    @PostMapping("/storeIoList")
    @ResponseBody
    public TableDataInfo list(QueryStoreIo queryStoreIo)
    {
        startPage();
        List<QueryStoreIo> list = queryStockInService.selectQueryStockInList(queryStoreIo);
        return getDataTable(list);
    }

    /**
     * 查询出入库主明细列表
     */
    @RequiresPermissions("infoquery:queryStockByCode:list")
    @PostMapping("/detailList")
    @ResponseBody
    public TableDataInfo detailList(QueryStoreIo queryStoreIo)
    {
        startPage();
        List<QueryStoreIo> list = queryStockInService.selectQueryStockInDetailList(queryStoreIo);
        return getDataTable(list);
    }
}
