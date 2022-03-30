package com.wms.warehouse.storeio.controller;

import java.util.List;

import com.wms.common.annotation.Log;
import com.wms.common.core.controller.BaseController;
import com.wms.common.core.domain.AjaxResult;
import com.wms.common.core.page.TableDataInfo;
import com.wms.common.enums.BusinessType;
import com.wms.common.utils.poi.ExcelUtil;
import com.wms.warehouse.storeio.domain.StoreIoSon;
import com.wms.warehouse.storeio.service.IStoreIoSonService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;



/**
 * 出入库子（1.子可以由主拆分而来，还可以来自上游）Controller
 *
 * @author assassin
 * @date 2019-12-30
 */
@Controller
@RequestMapping("/warehouse/storeIoSon")
public class StoreIoSonController extends BaseController
{
    private String prefix = "/warehouse/storeIoSon";

    @Autowired
    private IStoreIoSonService storeIoSonService;

    @RequiresPermissions("warehouse:StoreIoSon:view")
    @GetMapping()
    public String StoreIoSon()
    {
        return prefix + "/StoreIoSon";
    }

        /**
     * 查询出入库子（1.子可以由主拆分而来，还可以来自上游）列表
     */
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(StoreIoSon storeIoSon)
    {
        startPage();
        List<StoreIoSon> list = storeIoSonService.selectStoreIoSonList(storeIoSon);
        return getDataTable(list);
    }
    /**
     * 查询出库子（1.子可以由主拆分而来，还可以来自上游）列表
     */
    @PostMapping("/listOut")
    @ResponseBody
    public TableDataInfo listOut(StoreIoSon storeIoSon)
    {
        startPage();
        List<StoreIoSon> list = storeIoSonService.selectStoreIoOutSonList(storeIoSon);
        return getDataTable(list);
    }

    /**
     * 导出出入库子（1.子可以由主拆分而来，还可以来自上游）列表
     */
    @RequiresPermissions("warehouse:StoreIoSon:export")
    @Log(title = "出入库子（1.子可以由主拆分而来，还可以来自上游）", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(StoreIoSon storeIoSon)
    {
        List<StoreIoSon> list = storeIoSonService.selectStoreIoSonList(storeIoSon);
        ExcelUtil<StoreIoSon> util = new ExcelUtil<StoreIoSon>(StoreIoSon.class);
        return util.exportExcel(list, "StoreIoSonEx");
    }

    /**
     * 新增出入库子（1.子可以由主拆分而来，还可以来自上游）
     */
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存出入库子（1.子可以由主拆分而来，还可以来自上游）
     */
    @RequiresPermissions("warehouse:StoreIoSon:add")
    @Log(title = "出入库子（1.子可以由主拆分而来，还可以来自上游）", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(StoreIoSon storeIoSon)
    {
        return toAjax(storeIoSonService.insertStoreIoSon(storeIoSon));
    }

    /**
     * 修改出入库子（1.子可以由主拆分而来，还可以来自上游）
     */
    @GetMapping("/edit/{rowid}")
    public String edit(@PathVariable("rowid") Long rowid, ModelMap mmap)
    {
        StoreIoSon storeIoSon = storeIoSonService.selectStoreIoSonById(rowid);
        mmap.put("storeIoSon", storeIoSon);
        return prefix + "/edit";
    }

    /**
     * 修改保存出入库子（1.子可以由主拆分而来，还可以来自上游）
     */
    @RequiresPermissions("warehouse:StoreIoSon:edit")
    @Log(title = "出入库子（1.子可以由主拆分而来，还可以来自上游）", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(StoreIoSon storeIoSon)
    {
        return toAjax(storeIoSonService.updateStoreIoSon(storeIoSon));
    }

    /**
     * 删除出入库子（1.子可以由主拆分而来，还可以来自上游）
     */
    @RequiresPermissions("warehouse:StoreIoSon:remove")
    @Log(title = "出入库子（1.子可以由主拆分而来，还可以来自上游）", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(storeIoSonService.deleteStoreIoSonByIds(ids));
    }

    @PostMapping("/listIo")
    @ResponseBody
    public TableDataInfo listIo(StoreIoSon storeIoSon)
    {
        startPage();
        List<StoreIoSon> list = storeIoSonService.selectStoreIoList(storeIoSon);
        return getDataTable(list);
    }
}
