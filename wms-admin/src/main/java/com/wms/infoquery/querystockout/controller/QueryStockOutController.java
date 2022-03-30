package com.wms.infoquery.querystockout.controller;

import com.wms.common.core.controller.BaseController;
import com.wms.common.core.page.TableDataInfo;
import com.wms.infoquery.querystockin.domain.QueryStoreIo;
import com.wms.infoquery.querystockin.service.IQueryStockInService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/infoQuery/queryStockOut")
public class QueryStockOutController extends BaseController {
    private String prefix = "infoquery/queryStockOut";

    @Autowired
    private IQueryStockInService queryStockInService;


    @RequiresPermissions("infoquery:queryStockOut:view")
    @GetMapping()
    public String queryStockOut()
    {
        return prefix + "/queryStockOut";
    }

    /**
     * 查询出入库主列表
     */
    @RequiresPermissions("infoquery:queryStockOut:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(QueryStoreIo queryStoreIo)
    {
        startPage();
        queryStoreIo.setTasktype("2");
        List<QueryStoreIo> list = queryStockInService.selectQueryStockInList(queryStoreIo);
        return getDataTable(list);
    }

    /**
     * 查询出入库明细主列表
     */
    @RequiresPermissions("infoquery:queryStockOut:list")
    @PostMapping("/detailList")
    @ResponseBody
    public TableDataInfo detailList(QueryStoreIo queryStoreIo)
    {
        startPage();
        queryStoreIo.setTasktype("2");
        List<QueryStoreIo> list = queryStockInService.selectQueryStockInDetailList(queryStoreIo);
        return getDataTable(list);
    }

}
