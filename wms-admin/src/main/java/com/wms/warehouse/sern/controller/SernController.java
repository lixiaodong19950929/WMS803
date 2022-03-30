package com.wms.warehouse.sern.controller;

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
import com.wms.warehouse.sern.domain.Sern;
import com.wms.warehouse.sern.service.ISernService;
import com.wms.common.core.controller.BaseController;
import com.wms.common.core.domain.AjaxResult;
import com.wms.common.utils.poi.ExcelUtil;
import com.wms.common.core.page.TableDataInfo;

/**
 * 产品条码Controller
 * 
 * @author assassin
 * @date 2021-03-02
 */
@Controller
@RequestMapping("/warehouse/sern")
public class SernController extends BaseController
{
    private String prefix = "warehouse/sern";

    @Autowired
    private ISernService sernService;

    @RequiresPermissions("warehouse:sern:view")
    @GetMapping()
    public String sern()
    {
        return prefix + "/sern";
    }

        /**
     * 查询产品条码列表
     */
    @RequiresPermissions("warehouse:sern:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(Sern sern)
    {
        startPage();
        List<Sern> list = sernService.selectSernList(sern);
        return getDataTable(list);
    }
    
    /**
     * 导出产品条码列表
     */
    @RequiresPermissions("warehouse:sern:export")
    @Log(title = "产品条码", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(Sern sern)
    {
        List<Sern> list = sernService.selectSernList(sern);
        ExcelUtil<Sern> util = new ExcelUtil<Sern>(Sern.class);
        return util.exportExcel(list, "sern");
    }

    /**
     * 新增产品条码
     */
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存产品条码
     */
    @RequiresPermissions("warehouse:sern:add")
    @Log(title = "产品条码", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(Sern sern)
    {
        return toAjax(sernService.insertSern(sern));
    }

    /**
     * 修改产品条码
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap)
    {
        Sern sern = sernService.selectSernById(id);
        mmap.put("sern", sern);
        return prefix + "/edit";
    }

    /**
     * 修改保存产品条码
     */
    @RequiresPermissions("warehouse:sern:edit")
    @Log(title = "产品条码", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(Sern sern)
    {
        return toAjax(sernService.updateSern(sern));
    }

    /**
     * 删除产品条码
     */
    @RequiresPermissions("warehouse:sern:remove")
    @Log(title = "产品条码", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(sernService.deleteSernByIds(ids));
    }
}
