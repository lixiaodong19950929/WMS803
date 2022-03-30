package com.wms.warehouse.storemove.controller;

import java.util.List;

import com.wms.warehouse.storemove.domain.StoreMoveSon;
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
import com.wms.warehouse.storemove.service.IStoreMoveSonService;
import com.wms.common.core.controller.BaseController;
import com.wms.common.core.domain.AjaxResult;
import com.wms.common.utils.poi.ExcelUtil;
import com.wms.common.core.page.TableDataInfo;

/**
 * 移库子Controller
 * 
 * @author assassin
 * @date 2020-01-16
 */
@Controller
@RequestMapping("/warehouse/storemoveson")
public class StoreMoveSonController extends BaseController
{
    private String prefix = "warehouse/storemoveson";

    @Autowired
    private IStoreMoveSonService storeMoveSonService;

    @RequiresPermissions("warehouse:storemoveson:view")
    @GetMapping()
    public String storemoveson()
    {
        return prefix + "/storemoveson";
    }

        /**
     * 查询移库子列表
     */
    @RequiresPermissions("warehouse:storemoveson:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(StoreMoveSon storeMoveSon)
    {
        startPage();
        storeMoveSon.setIsdelete(0L);
        storeMoveSon.setIsenable(1L);
        List<StoreMoveSon> list = storeMoveSonService.selectStoreMoveSonList(storeMoveSon);
        return getDataTable(list);
    }
    
    /**
     * 导出移库子列表
     */
    @RequiresPermissions("warehouse:storemoveson:export")
    @Log(title = "移库子", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(StoreMoveSon storeMoveSon)
    {
        List<StoreMoveSon> list = storeMoveSonService.selectStoreMoveSonList(storeMoveSon);
        ExcelUtil<StoreMoveSon> util = new ExcelUtil<StoreMoveSon>(StoreMoveSon.class);
        return util.exportExcel(list, "storemoveson");
    }

    /**
     * 新增移库子
     */
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存移库子
     */
    @RequiresPermissions("warehouse:storemoveson:add")
    @Log(title = "移库子", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(StoreMoveSon storeMoveSon)
    {
        return toAjax(storeMoveSonService.insertStoreMoveSon(storeMoveSon));
    }

    /**
     * 修改移库子
     */
    @GetMapping("/edit/{rowid}")
    public String edit(@PathVariable("rowid") Long rowid, ModelMap mmap)
    {
        StoreMoveSon storeMoveSon = storeMoveSonService.selectStoreMoveSonById(rowid);
        mmap.put("storeMoveSon", storeMoveSon);
        return prefix + "/edit";
    }

    /**
     * 修改保存移库子
     */
    @RequiresPermissions("warehouse:storemoveson:edit")
    @Log(title = "移库子", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(StoreMoveSon storeMoveSon)
    {
        return toAjax(storeMoveSonService.updateStoreMoveSon(storeMoveSon));
    }

    /**
     * 删除移库子
     */
    @RequiresPermissions("warehouse:storemoveson:remove")
    @Log(title = "移库子", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(storeMoveSonService.deleteStoreMoveSonByIds(ids));
    }

    @PostMapping("/listMove")
    @ResponseBody
    public TableDataInfo listMove(StoreMoveSon storeMoveSon)
    {
        startPage();
        List<StoreMoveSon> list = storeMoveSonService.selectStoreMoveList(storeMoveSon);
        return getDataTable(list);
    }
}
