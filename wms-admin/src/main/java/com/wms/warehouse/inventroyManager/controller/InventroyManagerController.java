package com.wms.warehouse.inventroyManager.controller;

import com.wms.base.product.domain.Product;
import com.wms.base.product.service.IProductService;
import com.wms.base.storehouse.domain.Storehouse;
import com.wms.base.storehouse.service.IStorehouseService;
import com.wms.base.warehouse.service.IWarehouseService;
import com.wms.warehouse.sern.service.TrayCodeDetailService;
import com.wms.common.annotation.Log;
import com.wms.common.core.controller.BaseController;
import com.wms.common.core.domain.AjaxResult;
import com.wms.common.core.page.TableDataInfo;
import com.wms.common.enums.BusinessType;
import com.wms.common.utils.JsonUtils;
import com.wms.common.utils.ToolsUtils;
import com.wms.common.utils.poi.ExcelUtil;
import com.wms.framework.util.ShiroUtils;
import com.wms.infoquery.querystockstatus.domain.Stock;
import com.wms.warehouse.inventroyManager.domain.InventroyManager;
import com.wms.warehouse.inventroyManager.service.InventroyManagerServiceImp;
import com.wms.warehouse.storeio.domain.StoreIo;
import com.wms.warehouse.storeio.domain.StoreIoSon;
import com.wms.warehouse.storeio.service.IStoreIoService;
import com.wms.base.storehouse.domain.StorehouseEx;
import com.wms.warehouse.sern.domain.TrayCodeDetailEX;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

/**
 * 库存管理看板Controller
 * 
 * @author assassin
 * @date 2019-12-30
 */
@Controller
@RequestMapping("/warehouse/inventroyManager")
public class InventroyManagerController extends BaseController
{
    private String prefix = "warehouse/inventroyManager";

    @Autowired
    private IStoreIoService storeIoService;

    @Autowired
    private IWarehouseService warehouseService;

    @Autowired
    private IProductService productService;

    @Autowired
    private IStorehouseService storehouseService;

    @Autowired
    private TrayCodeDetailService TrayCodeDetailService;

    @Autowired
    private InventroyManagerServiceImp inventroyManagerService;


    /**
     * 库存管理
     */
    @RequiresPermissions("warehouse:storein:view")
    @GetMapping()
    public String InventroyManager(ModelMap mmap){
        mmap.put("whList",warehouseService.selectWarehouseDict());
        return prefix+"/inventroyManager";
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
    @PostMapping("/getinventroyManagerByStock")
    @ResponseBody
    public AjaxResult getinventroyManagerByStock(InventroyManager inventroyManager){
        return AjaxResult.success(inventroyManagerService.getinventroyManagerByStock(inventroyManager));
    }


    /**
     * 导出storehouse
     */
    @Log(title = "库存管理", businessType = BusinessType.EXPORT)
    @PostMapping("/exportIn")
    @ResponseBody
    public AjaxResult exportIn(Storehouse storehouse) {
        List<StorehouseEx> list = storehouseService.selectStorehouseExcelList(storehouse);
        ExcelUtil<StorehouseEx> util = new ExcelUtil<StorehouseEx>(StorehouseEx.class);
        return util.exportExcel(list, "库位信息");
    }

    /**
     * 导出traycodedetail
     */
    @Log(title = "产品库存", businessType = BusinessType.EXPORT)
    @PostMapping("/exportInTraycodeDetail")
    @ResponseBody
    public AjaxResult exportInTraycodeDetail() {
        List<TrayCodeDetailEX> list = TrayCodeDetailService.selectTrayCodeDetailExcelList();
        ExcelUtil<TrayCodeDetailEX> util = new ExcelUtil<TrayCodeDetailEX>(TrayCodeDetailEX.class);
        return util.exportExcel(list, "产品库存");
    }

   //全部删后全部插
    @Log(title = "更新库存", businessType = BusinessType.INSERT)
    @PostMapping("/deleteAndInsertsList")
    @ResponseBody
    public AjaxResult deleteAndInsertsList(@RequestParam("list") String list) throws Exception {
        List<StorehouseEx> storehouseExList=JsonUtils.json2list(list,StorehouseEx.class);
//        barcodelistService.deleteBarcodeList(barcodelist.get(0).getRowId(),barcodelist.get(0).getTaskCode());
        return toAjax(storehouseService.deleteAndInsertsList(storehouseExList));
    }

    //根据库位号逐条更新
    @Log(title = "更新库存", businessType = BusinessType.INSERT)
    @PostMapping("/updateListByStorehouseCode")
    @ResponseBody
    public AjaxResult updateListByStorehouseCode(@RequestParam("list") String list) throws Exception {
        List<StorehouseEx> storehouseExList=JsonUtils.json2list(list,StorehouseEx.class);
//        barcodelistService.deleteBarcodeList(barcodelist.get(0).getRowId(),barcodelist.get(0).getTaskCode());
        return toAjax(storehouseService.updateListByStorehouseCode(storehouseExList));
    }

    //根据库位号先删掉库位下的traycodedetail再插入从excel中获取的数据中的本库位下的traycodedetail
    @Log(title = "更新产品库存", businessType = BusinessType.INSERT)
    @PostMapping("/deleteAndInsertTraycodeDetailByStorehouseCode")
    @ResponseBody
    public AjaxResult deleteAndInsertTraycodeDetailByStorehouseCode(@RequestParam("list") String list) throws Exception {
        List<TrayCodeDetailEX> trayCodeDetailEXList=JsonUtils.json2list(list,TrayCodeDetailEX.class);
//        barcodelistService.deleteBarcodeList(barcodelist.get(0).getRowId(),barcodelist.get(0).getTaskCode());
        return toAjax(TrayCodeDetailService.deleteAndInsertTraycodeDetailByStorehouseCode(trayCodeDetailEXList));
    }

}
