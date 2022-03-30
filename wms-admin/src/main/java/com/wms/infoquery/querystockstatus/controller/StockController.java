package com.wms.infoquery.querystockstatus.controller;

import java.util.List;

import com.wms.base.storehouse.domain.Storehouse;
import com.wms.base.storehouse.service.IStorehouseService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.wms.common.annotation.Log;
import com.wms.common.enums.BusinessType;
import com.wms.infoquery.querystockstatus.domain.Stock;
import com.wms.infoquery.querystockstatus.service.IStockService;
import com.wms.common.core.controller.BaseController;
import com.wms.common.core.domain.AjaxResult;
import com.wms.common.utils.poi.ExcelUtil;
import com.wms.common.core.page.TableDataInfo;

/**
 * 库存Controller
 * 
 * @author assassin
 * @date 2020-01-16
 */
@Controller
@RequestMapping("/infoquery/stock")
public class StockController extends BaseController
{
    private String prefix = "infoquery/stock";

    @Autowired
    private IStockService stockService;

    @Autowired
    private IStorehouseService iStorehouseService;

    @RequiresPermissions("infoquery:stock:view")
    @GetMapping()
    public String stock()
    {
        return prefix + "/stock";
    }

    /**
     * 查询库存列表
     */
    @RequiresPermissions("infoquery:stock:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(Stock stock)
    {
        startPage();
        List<Stock> list = stockService.selectStockList(stock);
        return getDataTable(list);
    }

    /**
     * 查询每种产品占用库位总数
     */
    @RequiresPermissions("infoquery:stock:list")
    @PostMapping("/selectProStorehouseCount")
    @ResponseBody
    public TableDataInfo proStorehouseCountlist(Stock stock)
    {
        startPage();
        List<Stock> list = stockService.selectProStorehouseCount(stock);
        return getDataTable(list);
    }
    
    /**
     * 导出库存列表
     */
    @RequiresPermissions("infoquery:stock:export")
    @Log(title = "库存", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(Stock stock)
    {
        List<Stock> list = stockService.selectStockList(stock);
        ExcelUtil<Stock> util = new ExcelUtil<Stock>(Stock.class);
        return util.exportExcel(list, "stock");
    }

    /**
     * 新增库存
     */
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存库存
     */
    @RequiresPermissions("infoquery:stock:add")
    @Log(title = "库存", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(Stock stock)
    {
        return toAjax(stockService.insertStock(stock));
    }

    /**
     * 修改库存
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap)
    {
        Stock stock = stockService.selectStockById(id);
        mmap.put("stock", stock);
        return prefix + "/edit";
    }

    /**
     * 修改保存库存
     */
    @RequiresPermissions("infoquery:stock:edit")
    @Log(title = "库存", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(Stock stock)
    {
        return toAjax(stockService.updateStock(stock));
    }

    /**
     * 删除库存
     */
    @RequiresPermissions("infoquery:stock:remove")
    @Log(title = "库存", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(stockService.deleteStockByIds(ids));
    }


    /**
     * 出库时,不区分库位查询
     */
    @PostMapping( "/productOutList")
    @ResponseBody
    public TableDataInfo productOutList(Stock stock){
        startPage();
        List<Stock> stockList=stockService.selectByProductOut(stock);
        return getDataTable(stockList);
    }

    /**
     * 移库、盘库时区分库位查询库存信息
     */
    @PostMapping( "/productStoreOutList")
    @ResponseBody
    public TableDataInfo productStoreOutList(Stock stock){
        startPage();
        List<Stock> stockList=stockService.selectByproductStoreOut(stock);
        return getDataTable(stockList);
    }

    @PostMapping("/selectProductList")
    @ResponseBody
    public TableDataInfo productlist(Storehouse storehouse)
    {
        startPage();
        List<Storehouse> list = iStorehouseService.selectProductList(storehouse);
        return getDataTable(list);
    }

}
