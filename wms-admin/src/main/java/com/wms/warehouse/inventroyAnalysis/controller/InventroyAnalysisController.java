package com.wms.warehouse.inventroyAnalysis.controller;


import com.wms.base.warehouse.service.IWarehouseService;
import com.wms.common.core.controller.BaseController;
import com.wms.common.core.domain.AjaxResult;

import com.wms.warehouse.inventroyAnalysis.service.InventroyAnalysisServiceImp;
import com.wms.warehouse.inventroyManager.domain.InventroyManager;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 库存管理看板Controller
 * 
 * @author assassin
 * @date 2019-12-30
 */
@Controller
@RequestMapping("/warehouse/inventroyAnalysis")
public class InventroyAnalysisController extends BaseController
{
    private String prefix = "warehouse/inventoryAnalysis";

    @Autowired
    private IWarehouseService warehouseService;

    @Autowired
    private InventroyAnalysisServiceImp inventroyAnalysisService;


    /**
     * 库存管理
     */
    @RequiresPermissions("warehouse:storein:view")
    @GetMapping()
    public String InventroyManager(ModelMap mmap){
        mmap.put("whList",warehouseService.selectWarehouseDict());
        return prefix+"/inventoryAnalysis";
    }


    /**
     * 获取仓库列表
     */
    @PostMapping("/getWarehouse")
    @ResponseBody
    public AjaxResult getWarehouse(){
         return AjaxResult.success( warehouseService.selectWarehouseDict());
    }


    /**
     * 查询库位信息、库存量
     */
    @PostMapping("/getInventroyAnalysis")
    @ResponseBody
    public AjaxResult getInventroyAnalysis(InventroyManager inventroyManager){
        return AjaxResult.success(inventroyAnalysisService.getInventroyAnalysis(inventroyManager));
    }

}
