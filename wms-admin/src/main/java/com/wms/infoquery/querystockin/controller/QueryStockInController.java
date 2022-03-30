package com.wms.infoquery.querystockin.controller;

import com.wms.common.core.controller.BaseController;
import com.wms.common.core.page.TableDataInfo;
import com.wms.infoquery.querystockin.domain.QueryStoreIo;
import com.wms.infoquery.querystockin.service.IQueryStockInService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 出入库主Controller
 * 
 * @author assassin
 * @date 2019-12-30
 */
@Controller
@RequestMapping("/infoQuery/queryStockIn")
public class QueryStockInController extends BaseController
{
    private String prefix = "infoquery/queryStockIn";

    @Autowired
    private IQueryStockInService queryStockInService;


    @RequiresPermissions("infoquery:queryStockIn:view")
    @GetMapping()
    public String queryStockIn()
    {
        return prefix + "/queryStockIn";
    }

    /**
     * 查询出入库主列表
     */
    @RequiresPermissions("infoquery:queryStockIn:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(QueryStoreIo queryStoreIo)
    {
        startPage();
        queryStoreIo.setTasktype("1");
        List<QueryStoreIo> list = queryStockInService.selectQueryStockInList(queryStoreIo);
        return getDataTable(list);
    }

    /**
     * 查询出入库主明细列表
     */
    @RequiresPermissions("infoquery:queryStockIn:list")
    @PostMapping("/detailList")
    @ResponseBody
    public TableDataInfo detailList(QueryStoreIo queryStoreIo)
    {
        startPage();
        queryStoreIo.setTasktype("1");
        List<QueryStoreIo> list = queryStockInService.selectQueryStockInDetailList(queryStoreIo);
        return getDataTable(list);
    }

}
