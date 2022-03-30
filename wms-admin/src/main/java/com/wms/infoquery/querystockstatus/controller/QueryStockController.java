package com.wms.infoquery.querystockstatus.controller;

import com.wms.common.core.controller.BaseController;
import com.wms.common.core.page.TableDataInfo;
import com.wms.common.utils.StringUtils;
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
@RequestMapping("/infoquery/queryStock")
public class QueryStockController extends BaseController {

    @Autowired
    private IStockService stockService;

    private String prefix = "infoquery/stock";

    @RequiresPermissions("infoquery:queryStock:view")
    @GetMapping()
    public String queryStock()
    {
        return prefix + "/queryStock";
    }


    /**
     * 查询库存列表
     */
    @RequiresPermissions("infoquery:stock:queryList")
    @PostMapping("/selectQueryStockList")
    @ResponseBody
    public TableDataInfo selectQueryStockList(Stock stock)
    {
        startPage();
        /*if (StringUtils.isNotEmpty(stock.getStorehouseline())&& StringUtils.isNotEmpty(stock.getStorehousecolum())
                && StringUtils.isNotEmpty(stock.getStorehouselayer())) {
            stock.setStorehousecode(stock.getStorehouseline().trim() + stock.getStorehousecolum().trim() + stock.getStorehouselayer().trim());
        }*/
        List<Stock> list = stockService.selectQueryStockList(stock);
        return getDataTable(list);
    }

}
