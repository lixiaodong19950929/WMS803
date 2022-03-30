package com.wms.infoquery.queryScanList;


import com.wms.common.core.controller.BaseController;
import com.wms.common.core.page.TableDataInfo;
import com.wms.infoquery.queryStockRealtime.service.IMainService;
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


/**
 * 扫描记录查询Controller
 *
 * @author lzk
 * @date 2020-02-06
 */
@Controller
@RequestMapping("/infoQuery/queryScanList")
public class QueryScanListController extends BaseController {
    private String prefix = "infoquery/queryScanList";

    @Autowired
    private IMainService mainService;

    @Autowired
    private IQueryStockInService queryStockInService;


    @RequiresPermissions("infoquery:queryScanList:view")
    @GetMapping()
    public String queryScanList()
    {
        return prefix + "/queryScanList";
    }

    /**
     * 查询出入库主列表
     */
    @RequiresPermissions("infoquery:queryScanList:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(QueryStoreIo queryStoreIo)
    {
        startPage();
//        queryStoreIo.setTasktype("1");
        List<QueryStoreIo> list = queryStockInService.selectQueryStockInList(queryStoreIo);
        return getDataTable(list);
    }




}
