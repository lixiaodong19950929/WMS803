package com.wms.base.warehouse.controller;

import com.wms.base.warehouse.domain.Warehouse;
import com.wms.base.warehouse.service.IWarehouseService;
import com.wms.common.annotation.Log;
import com.wms.common.core.controller.BaseController;
import com.wms.common.core.domain.AjaxResult;
import com.wms.common.core.page.TableDataInfo;
import com.wms.common.enums.BusinessType;
import com.wms.common.utils.poi.ExcelUtil;
import com.wms.framework.util.ShiroUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;



/**
 * 仓库Controller
 * 
 * @author assassin
 * @date 2019-12-27
 */
@Controller
@RequestMapping("/base/warehouse")
public class WarehouseController extends BaseController
{
    private String prefix = "base/warehouse";

    @Autowired
    private IWarehouseService warehouseService;

    @RequiresPermissions("base:Warehouse:view")
    @GetMapping()
    public String Warehouse()
    {
        return prefix + "/warehouse";
    }

        /**
     * 查询仓库列表
     */
    @RequiresPermissions("base:Warehouse:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(Warehouse warehouse)
    {
        startPage();
        List<Warehouse> list = warehouseService.selectWarehouseList(warehouse);
        return getDataTable(list);
    }
    
    /**
     * 导出仓库列表
     */
    @RequiresPermissions("base:Warehouse:export")
    @Log(title = "仓库", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(Warehouse warehouse)
    {
        List<Warehouse> list = warehouseService.selectWarehouseList(warehouse);
        ExcelUtil<Warehouse> util = new ExcelUtil<Warehouse>(Warehouse.class);
        return util.exportExcel(list, "Warehouse");
    }

    /**
     * 新增仓库
     */
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存仓库
     */
    @RequiresPermissions("base:Warehouse:add")
    @Log(title = "仓库", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(Warehouse warehouse)
    {
        warehouse.setCreateuser(ShiroUtils.getSysUser().getUserName());
        warehouse.setCreatedate(new Date());
        return toAjax(warehouseService.insertWarehouse(warehouse));
    }

    /**
     * 修改仓库
     */
    @GetMapping("/edit/{whcode}")
    public String edit(@PathVariable("whcode") String whcode, ModelMap mmap)
    {
        Warehouse warehouse = warehouseService.selectWarehouseById(whcode);
        mmap.put("warehouse", warehouse);
        return prefix + "/edit";
    }

    /**
     * 修改保存仓库
     */
    @RequiresPermissions("base:Warehouse:edit")
    @Log(title = "仓库", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(Warehouse warehouse)
    {
        String isUnique = warehouseService.checkCbarCode(warehouse);
        if (isUnique.equals("1")) {
            return error("仓库条码已存在");
        }
        warehouse.setModifyuser(ShiroUtils.getSysUser().getUserName());
        warehouse.setModifydate(new Date());
        return toAjax(warehouseService.updateWarehouse(warehouse));
    }

    /**
     * 删除仓库
     */
    @RequiresPermissions("base:Warehouse:remove")
    @Log(title = "仓库", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(warehouseService.deleteWarehouseByIds(ids));
    }

    /**
     * 校验仓库条码的唯一性
     */
    @RequestMapping("/checkCbarCode")
    @ResponseBody
    public String checkCbarCode(Warehouse warehouse){
        return warehouseService.checkCbarCode(warehouse);
    }


    @RequestMapping("/selectWarehouseDict")
    @ResponseBody
    public List<Warehouse> selectWarehouseDict(){
        return warehouseService.selectWarehouseDict();
    }
}
