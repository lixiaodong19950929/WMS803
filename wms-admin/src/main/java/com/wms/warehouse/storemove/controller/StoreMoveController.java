package com.wms.warehouse.storemove.controller;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import com.wms.base.storehouse.domain.Storehouse;
import com.wms.base.warehouse.service.IWarehouseService;
import com.wms.common.annotation.Log;
import com.wms.common.core.controller.BaseController;
import com.wms.common.core.domain.AjaxResult;
import com.wms.common.core.page.TableDataInfo;
import com.wms.common.enums.BusinessType;
import com.wms.common.utils.JsonUtils;
import com.wms.common.utils.ToolsUtils;
import com.wms.common.utils.poi.ExcelUtil;
import com.wms.framework.util.ShiroUtils;
import com.wms.warehouse.storemove.domain.StoreMove;
import com.wms.warehouse.storemove.domain.StoreMoveExcel;
import com.wms.warehouse.storemove.domain.StoreMoveSon;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import com.wms.warehouse.storemove.service.IStoreMoveService;
import org.springframework.web.multipart.MultipartFile;

/**
 * 移库任务管理Controller
 * 
 * @author assassin
 * @date 2020-01-16
 */
@Controller
@RequestMapping("/warehouse/storemove")
public class StoreMoveController extends BaseController
{
    private String prefix = "warehouse/storemove";

    @Autowired
    private IStoreMoveService storeMoveService;

    @Autowired
    private IWarehouseService warehouseService;

    @RequiresPermissions("warehouse:storemove:view")
    @GetMapping()
    public String storemove()
    {
        return prefix + "/storemove";
    }

        /**
     * 查询移库任务管理列表
     */
    @RequiresPermissions("warehouse:storemove:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(StoreMove storeMove)
    {
        startPage();
        List<StoreMove> list = storeMoveService.selectStoreMoveList(storeMove);
        return getDataTable(list);
    }
    
    /**
     * 导出移库任务管理列表
     */
    @RequiresPermissions("warehouse:storemove:export")
    @Log(title = "移库任务管理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(StoreMove storeMove)
    {
        List<StoreMoveExcel> list = storeMoveService.selectStoreMoveExcelList(storeMove);
        ExcelUtil<StoreMoveExcel> util = new ExcelUtil<StoreMoveExcel>(StoreMoveExcel.class);
        return util.exportExcel(list, "storemove");
    }

    /**
     * 新增移库任务管理
     */
    @GetMapping("/add")
    public String add(ModelMap mmap)
    {
        mmap.put("whList",warehouseService.selectWarehouseDict());
        return prefix + "/add";
    }

    /**
     * 新增保存移库任务管理
     */
    @RequiresPermissions("warehouse:storemove:add")
    @Log(title = "移库任务管理", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(StoreMove storeMove,@RequestParam("list") String list) throws Exception {
        storeMove.setTaskcode(ToolsUtils.getOrdersId("SME"));
        storeMove.setStoreMoveSonList(JsonUtils.json2list(list, StoreMoveSon.class));
        storeMove.setCreateuser(ShiroUtils.getSysUser().getUserName());
        storeMove.setCreatedate(new Date());
        storeMove.setTaskstate("0");
        return toAjax(storeMoveService.insertStoreMove(storeMove));
    }

    /**
     * 修改移库任务管理
     */
    @GetMapping("/edit/{taskcode}")
    public String edit(@PathVariable("taskcode") String taskcode, ModelMap mmap)
    {
        StoreMove storeMove = storeMoveService.selectStoreMoveById(taskcode);
        mmap.put("storeMove", storeMove);
        mmap.put("whList",warehouseService.selectWarehouseDict());
        return prefix + "/edit";
    }

    /**
     * 修改保存移库任务管理
     */
    @RequiresPermissions("warehouse:storemove:edit")
    @Log(title = "移库任务管理", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(StoreMove storeMove,@RequestParam("list") String list) throws Exception {
        storeMove.setStoreMoveSonList(JsonUtils.json2list(list, StoreMoveSon.class));
        return toAjax(storeMoveService.updateStoreMove(storeMove,ShiroUtils.getSysUser().getUserName()));
    }

    /**
     * 删除移库任务管理
     */
    @RequiresPermissions("warehouse:storemove:remove")
    @Log(title = "移库任务管理", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(storeMoveService.deleteStoreMoveByIds(ids));
    }

    /***
     * 选择产品界面，移库
     */
    @Log(title = "移库任务管理",businessType = BusinessType.OTHER)
    @GetMapping("/product/{whcode}")
    public String product(@PathVariable("whcode") String whcode, ModelMap mmap){
        mmap.put("whcode",whcode);
        return prefix+"/product";
    }

    /**
     * 查询库位
     */
    @PostMapping("/getStoreHouse")
    @ResponseBody
    public AjaxResult getStoreHouse(Storehouse storehouse){
        return AjaxResult.success(storeMoveService.selectByStoreHouse(storehouse));
    }

    /**
     * 移库详情
     */
    @GetMapping("/detail/{taskcode}")
    public String detail(@PathVariable("taskcode") String taskcode, ModelMap mmap)
    {
        StoreMove storeMove = storeMoveService.selectStoreMoveById(taskcode);
        mmap.put("storeMove", storeMove);
        mmap.put("whList",warehouseService.selectWarehouseDict());
        return prefix + "/detail";
    }

    /**
     * 取消移库
     */
    @RequiresPermissions("warehouse:storemove:cancel")
    @Log(title = "移库任务管理", businessType = BusinessType.CANCEL)
    @PostMapping( "/cancel")
    @ResponseBody
    public AjaxResult cancel(String taskcodes)
    {
        return toAjax(storeMoveService.cancel(taskcodes));
    }

    /**
     * 重启移库
     */
    @RequiresPermissions("warehouse:storemove:restart")
    @Log(title = "移库任务管理", businessType = BusinessType.RESTART)
    @PostMapping( "/restart")
    @ResponseBody
    public AjaxResult restart(String taskcodes)
    {
        return toAjax(storeMoveService.restart(taskcodes));
    }

    /***
     * 导出模板
     */
    @Log(title = "模板导出", businessType = BusinessType.EXPORT)
    @GetMapping("/importTemplate")
    @ResponseBody
    public AjaxResult exportModel() throws IOException {

        return storeMoveService.exportStoreModel("移库模板");
    }

    /**
     * 移库导入
     */
    @RequiresPermissions("warehouse:storemove:import")
    @Log(title = "移库任务导入",businessType = BusinessType.IMPORT)
    @PostMapping("/import")
    @ResponseBody
    public AjaxResult importMove(MultipartFile file) throws IOException {
        return storeMoveService.importExcel(file,"3");
    }

    /**
     * 移库任务下发
     */
    @Log(title = "移库任务下发", businessType = BusinessType.EXECUTE)
    @PostMapping("/execute")
    @ResponseBody
    public AjaxResult execute(String taskcodes) {
            return toAjax(storeMoveService.execute(taskcodes));
        }
}
