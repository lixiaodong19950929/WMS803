package com.wms.warehouse.storecheck.controller;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import com.wms.base.warehouse.service.IWarehouseService;
import com.wms.common.utils.JsonUtils;
import com.wms.common.utils.ToolsUtils;
import com.wms.framework.util.ShiroUtils;
import com.wms.warehouse.storecheck.domain.CheckResult;
import com.wms.warehouse.storecheck.domain.StoreCheckExcel;
import com.wms.warehouse.storecheck.domain.StoreCheckSon;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import com.wms.common.annotation.Log;
import com.wms.common.enums.BusinessType;
import com.wms.warehouse.storecheck.domain.StoreCheck;
import com.wms.warehouse.storecheck.service.IStoreCheckService;
import com.wms.common.core.controller.BaseController;
import com.wms.common.core.domain.AjaxResult;
import com.wms.common.utils.poi.ExcelUtil;
import com.wms.common.core.page.TableDataInfo;
import org.springframework.web.multipart.MultipartFile;

/**
 * 盘库任务Controller
 * 
 * @author assassin
 * @date 2020-02-10
 */
@Controller
@RequestMapping("/warehouse/storecheck")
public class StoreCheckController extends BaseController
{
    private String prefix = "warehouse/storecheck";

    @Autowired
    private IStoreCheckService storeCheckService;

    @Autowired
    private IWarehouseService warehouseService;

    @RequiresPermissions("warehouse:storecheck:view")
    @GetMapping()
    public String storecheck()
    {
        return prefix + "/storecheck";
    }

    @RequiresPermissions("warehouse:storecheck:view")
    @GetMapping("/checkResult/{taskcode}")
    public String checkResult(@PathVariable("taskcode") String taskcode, ModelMap mmap)
    {
//        StoreCheck storeCheck = storeCheckService.selectStoreCheckById(taskcode);
        mmap.put("taskcode", taskcode);
        return prefix + "/checkResult";
    }

    /**
     * 盘点结果列表获取
     */
    @PostMapping("/checkResultList")
    @ResponseBody
    public TableDataInfo checkResultList(CheckResult checkresult)
    {
        startPage();
        List<CheckResult> list = storeCheckService.selectCheckResultList(checkresult.getTaskCode());
        return getDataTable(list);
    }

        /**
     * 查询盘库任务列表
     */
    @RequiresPermissions("warehouse:storecheck:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(StoreCheck storeCheck)
    {
        startPage();
        List<StoreCheck> list = storeCheckService.selectStoreCheckList(storeCheck);
        return getDataTable(list);
    }
    
    /**
     * 导出盘库任务列表
     */
    @RequiresPermissions("warehouse:storecheck:export")
    @Log(title = "盘库任务", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(StoreCheck storeCheck)
    {
        List<StoreCheckExcel> list = storeCheckService.selectStoreCheckAndSonList(storeCheck);
        ExcelUtil<StoreCheckExcel> util = new ExcelUtil<StoreCheckExcel>(StoreCheckExcel.class);
        return util.exportExcel(list, "storecheck");
    }

    /**
     * 新增盘库任务
     */
    @GetMapping("/add")
    public String add( ModelMap mmap)
    {
        mmap.put("whList",warehouseService.selectWarehouseDict());
        return prefix + "/add";
    }

    /**
     * 新增保存盘库任务
     */
    @RequiresPermissions("warehouse:storecheck:add")
    @Log(title = "盘库任务", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(StoreCheck storeCheck, @RequestParam("list") String list) throws Exception {
        storeCheck.setTaskcode(ToolsUtils.getOrdersId("SCC"));
        storeCheck.setStoreCheckSonList(JsonUtils.json2list(list, StoreCheckSon.class));
        storeCheck.setCreateuser(ShiroUtils.getSysUser().getUserName());
        storeCheck.setCreatedate(new Date());
        return toAjax(storeCheckService.insertStoreCheck(storeCheck));
    }

    /**
     * 修改盘库任务
     */
    @GetMapping("/edit/{taskcode}")
    public String edit(@PathVariable("taskcode") String taskcode, ModelMap mmap)
    {
        StoreCheck storeCheck = storeCheckService.selectStoreCheckById(taskcode);
        mmap.put("whList",warehouseService.selectWarehouseDict());
        mmap.put("storeCheck", storeCheck);
        return prefix + "/edit";
    }

    /**
     * 修改保存盘库任务
     */
    @RequiresPermissions("warehouse:storecheck:edit")
    @Log(title = "盘库任务", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(StoreCheck storeCheck,@RequestParam("list") String list) throws Exception {
        storeCheck.setStoreCheckSonList(JsonUtils.json2list(list, StoreCheckSon.class));
        return toAjax(storeCheckService.updateStoreCheck(storeCheck,ShiroUtils.getSysUser().getUserName()));
    }

    /**
     * 删除盘库任务
     */
    @RequiresPermissions("warehouse:storecheck:remove")
    @Log(title = "盘库任务", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(storeCheckService.deleteStoreCheckByIds(ids));
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
     * 出入库详情
     */
    @GetMapping("/detail/{taskcode}")
    public String detail(@PathVariable("taskcode") String taskcode, ModelMap mmap)
    {
        StoreCheck storeCheck = storeCheckService.selectStoreCheckById(taskcode);
        mmap.put("storeCheck", storeCheck);
        mmap.put("whList",warehouseService.selectWarehouseDict());
        return prefix + "/detail";
    }

    /**
     * 取消入库
     */
    @RequiresPermissions("warehouse:storecheck:cancel")
    @Log(title = "盘库任务管理", businessType = BusinessType.CANCEL)
    @PostMapping( "/cancel")
    @ResponseBody
    public AjaxResult cancel(String taskcodes)
    {
        return toAjax(storeCheckService.cancel(taskcodes));
    }

    /**
     * 重启入库
     */
    @RequiresPermissions("warehouse:storecheck:restart")
    @Log(title = "盘库任务管理", businessType = BusinessType.RESTART)
    @PostMapping( "/restart")
    @ResponseBody
    public AjaxResult restart(String taskcodes)
    {
        return toAjax(storeCheckService.restart(taskcodes));
    }

    /**
     * 盘库导入
     */
    @RequiresPermissions("warehouse:storecheck:import")
    @Log(title = "盘库任务导入",businessType = BusinessType.IMPORT)
    @PostMapping("/import")
    @ResponseBody
    public AjaxResult importCheck(MultipartFile file) throws IOException {
        return storeCheckService.importExcel(file,"4");
    }



    /***
     * 导出模板
     */
    @Log(title = "模板导出", businessType = BusinessType.EXPORT)
    @GetMapping("/importTemplate")
    @ResponseBody
    public AjaxResult exportModel() throws IOException {

        return storeCheckService.exportStoreModel("盘库模板");
    }

    /***
     * 盘库任务下发
     */
    @Log(title = "盘库任务下发", businessType = BusinessType.EXECUTE)
    @PostMapping("/execute")
    @ResponseBody
    public AjaxResult execute(String taskcodes) {
           return toAjax(storeCheckService.execute(taskcodes));
       }
}
