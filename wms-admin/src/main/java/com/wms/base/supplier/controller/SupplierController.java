package com.wms.base.supplier.controller;

import com.wms.base.supplier.domain.Supplier;
import com.wms.base.supplier.service.ISupplierService;
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
import com.wms.common.core.controller.BaseController;
import com.wms.common.core.domain.AjaxResult;
import com.wms.common.utils.poi.ExcelUtil;
import com.wms.common.core.page.TableDataInfo;

import java.util.List;

/**
 * 供应商Controller
 * 
 * @author assassin
 * @date 2021-09-06
 */
@Controller
@RequestMapping("/base/supplier")
public class SupplierController extends BaseController
{
    private String prefix = "base/supplier";

    @Autowired
    private ISupplierService supplierService;

    @RequiresPermissions("base:supplier:view")
    @GetMapping()
    public String supplier()
    {
        return prefix + "/supplier";
    }

        /**
     * 查询供应商列表
     */
    @RequiresPermissions("base:supplier:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(Supplier supplier)
    {
        startPage();
        List<Supplier> list = supplierService.selectSupplierList(supplier);
        return getDataTable(list);
    }
    
    /**
     * 导出供应商列表
     */
    @RequiresPermissions("base:supplier:export")
    @Log(title = "供应商", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(Supplier supplier)
    {
        List<Supplier> list = supplierService.selectSupplierList(supplier);
        ExcelUtil<Supplier> util = new ExcelUtil<Supplier>(Supplier.class);
        return util.exportExcel(list, "supplier");
    }

    /**
     * 新增供应商
     */
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存供应商
     */
    @RequiresPermissions("base:supplier:add")
    @Log(title = "供应商", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(Supplier supplier)
    {
        return toAjax(supplierService.insertSupplier(supplier));
    }

    /**
     * 修改供应商
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap)
    {
        Supplier supplier = supplierService.selectSupplierById(id);
        mmap.put("supplier", supplier);
        return prefix + "/edit";
    }

    /**
     * 修改保存供应商
     */
    @RequiresPermissions("base:supplier:edit")
    @Log(title = "供应商", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(Supplier supplier)
    {
        return toAjax(supplierService.updateSupplier(supplier));
    }

    /**
     * 删除供应商
     */
    @RequiresPermissions("base:supplier:remove")
    @Log(title = "供应商", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(supplierService.deleteSupplierByIds(ids));
    }
}
