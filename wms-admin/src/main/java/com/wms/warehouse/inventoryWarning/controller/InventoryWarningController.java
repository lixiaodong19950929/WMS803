package com.wms.warehouse.inventoryWarning.controller;

import com.wms.common.annotation.Log;
import com.wms.common.core.controller.BaseController;
import com.wms.common.core.domain.AjaxResult;
import com.wms.common.core.page.TableDataInfo;
import com.wms.common.enums.BusinessType;
import com.wms.infoquery.querystockstatus.domain.Stock;
import com.wms.infoquery.querystockstatus.service.IStockService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/warehouse/inventoryWarning")
public class InventoryWarningController extends BaseController {

    @Autowired
    private IStockService stockService;

    private String prefix = "warehouse/inventoryWarning";

    @RequiresPermissions("warehouse:inventoryWarning:view")
    @GetMapping()
    public String inventoryWarning()
    {
        return prefix + "/inventoryWarning";
    }




    /**
     * 查询库存列表
     */
    @RequiresPermissions("warehouse:inventoryWarning:list")
    @PostMapping("/selectInventoryWarningList")
    @ResponseBody
    public TableDataInfo selectInventoryWarningList(Stock stock)
    {
        startPage();
        List<Stock> list = stockService.selectStockWarningList(stock);
        int topLimit = 0;
        int lowerLimit = 0;
        int quantity = 0;
        for (Stock stock2 : list) {
            topLimit = stock2.getToplimit();
            lowerLimit = stock2.getLowerlimit();
            quantity = stock2.getQuantity();
            if (topLimit > 0 && lowerLimit > 0) {
                if (stock2.getQuantity() > topLimit) {
                    stock2.setDifferencequantity(quantity - topLimit);
                } else if (quantity < lowerLimit) {
                    stock2.setDifferencequantity(lowerLimit - quantity);
                }
            }
        }
        return getDataTable(list);
    }

    @PostMapping("/selectStockById")
    @ResponseBody
    public Stock selectStockById(Long id) {
        return stockService.selectStockById(id);
    }

    /**
     * 批量更新库存上限和下限
     * @param stock
     * @return
     */
    @PostMapping("/updateStockBulk")
    @Log(title = "库存预警", businessType = BusinessType.UPDATE)
    @ResponseBody
    public AjaxResult updateStockBulk(@RequestBody Stock stock) {
        return toAjax(stockService.updateStockBulk(stock));
    }
}
