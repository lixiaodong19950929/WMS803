package com.wms.warehouse.sern.controller;

import com.wms.common.annotation.Log;
import com.wms.common.core.controller.BaseController;
import com.wms.common.core.domain.AjaxResult;
import com.wms.common.core.page.TableDataInfo;
import com.wms.common.enums.BusinessType;
import com.wms.warehouse.sern.domain.Barcodelist;
import com.wms.warehouse.sern.service.BarcodelistService;
import com.wms.warehouse.storeio.domain.StoreIo;
import com.wms.warehouse.storeio.service.IStoreIoService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/warehouse/Barcodelist")
public class BarcodelistController extends BaseController {

    private String prefix = "warehouse/barcodeList";

    private String prefixSern = "warehouse/storeio";

    @Autowired
    private BarcodelistService barcodelistService;

    @Autowired
    private IStoreIoService storeIoService;

    @RequiresPermissions("warehouse:BarCodeList:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo BarCodeList()
    {
        startPage();
        List<Barcodelist> list=barcodelistService.BarcodeList();
        return getDataTable(list);
    }

    @GetMapping("/sernInfo/{taskcode}")
    public String detailSern(@PathVariable("taskcode") String taskcode, ModelMap mmap)
    {
        StoreIo storeIo = storeIoService.selectStoreIoById(taskcode);
        mmap.put("sernInfo", storeIo);
        return prefix + "/sernInfo";
    }

    @RequiresPermissions("warehouse:BarCodeList:add")
    @Log(title = "条码明细", businessType = BusinessType.INSERT)
    @PostMapping("/addBarCodeList")
    @ResponseBody
    public AjaxResult addBarCodeList(Barcodelist barcodelist){
        return toAjax(barcodelistService.insertBardCodeList(barcodelist));

    }




}
