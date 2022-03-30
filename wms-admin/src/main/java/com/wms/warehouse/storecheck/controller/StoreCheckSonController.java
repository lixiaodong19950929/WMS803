package com.wms.warehouse.storecheck.controller;

import java.util.List;
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
import com.wms.warehouse.storecheck.domain.StoreCheckSon;
import com.wms.warehouse.storecheck.service.IStoreCheckSonService;
import com.wms.common.core.controller.BaseController;
import com.wms.common.core.domain.AjaxResult;
import com.wms.common.utils.poi.ExcelUtil;
import com.wms.common.core.page.TableDataInfo;

/**
 * 盘点子Controller
 * 
 * @author assassin
 * @date 2020-02-10
 */
@Controller
@RequestMapping("/warehouse/storecheckson")
public class StoreCheckSonController extends BaseController
{
    private String prefix = "warehouse/storecheckson";

    @Autowired
    private IStoreCheckSonService storeCheckSonService;

    @RequiresPermissions("warehouse:storecheckson:view")
    @GetMapping()
    public String storecheckson()
    {
        return prefix + "/storecheckson";
    }

        /**
     * 查询盘点子列表
     */
    @RequiresPermissions("warehouse:storecheckson:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(StoreCheckSon storeCheckSon)
    {
        startPage();
        List<StoreCheckSon> list = storeCheckSonService.selectStoreCheckSonList(storeCheckSon);
        return getDataTable(list);
    }
    
    /**
     * 导出盘点子列表
     */
    @RequiresPermissions("warehouse:storecheckson:export")
    @Log(title = "盘点子", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(StoreCheckSon storeCheckSon)
    {
        List<StoreCheckSon> list = storeCheckSonService.selectStoreCheckSonList(storeCheckSon);
        ExcelUtil<StoreCheckSon> util = new ExcelUtil<StoreCheckSon>(StoreCheckSon.class);
        return util.exportExcel(list, "storecheckson");
    }

    /**
     * 新增盘点子
     */
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存盘点子
     */
    @RequiresPermissions("warehouse:storecheckson:add")
    @Log(title = "盘点子", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(StoreCheckSon storeCheckSon)
    {
        return toAjax(storeCheckSonService.insertStoreCheckSon(storeCheckSon));
    }

    /**
     * 修改盘点子
     */
    @GetMapping("/edit/{rowid}")
    public String edit(@PathVariable("rowid") Long rowid, ModelMap mmap)
    {
        StoreCheckSon storeCheckSon = storeCheckSonService.selectStoreCheckSonById(rowid);
        mmap.put("storeCheckSon", storeCheckSon);
        return prefix + "/edit";
    }

    /**
     * 修改保存盘点子
     */
    @RequiresPermissions("warehouse:storecheckson:edit")
    @Log(title = "盘点子", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(StoreCheckSon storeCheckSon)
    {
        return toAjax(storeCheckSonService.updateStoreCheckSon(storeCheckSon));
    }

    /**
     * 删除盘点子
     */
    @RequiresPermissions("warehouse:storecheckson:remove")
    @Log(title = "盘点子", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(storeCheckSonService.deleteStoreCheckSonByIds(ids));
    }


    /**
     * 查询盘点子列表
     */
    @PostMapping("/listCheck")
    @ResponseBody
    public TableDataInfo listCheck(StoreCheckSon storeCheckSon)
    {
        startPage();
        List<StoreCheckSon> list = storeCheckSonService.selectListStoreCheckSon(storeCheckSon);
        return getDataTable(list);
    }
}
