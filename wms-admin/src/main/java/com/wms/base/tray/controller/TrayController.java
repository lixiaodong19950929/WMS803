package com.wms.base.tray.controller;

import com.wms.base.storehouse.domain.Storehouse;
import com.wms.base.tray.domain.Tray;
import com.wms.base.storehouse.service.IStorehouseService;
import com.wms.base.tray.service.ITrayService;
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
 * 托盘Controller
 *
 * @author dkwms
 * @date 2020-01-07
 */
@Controller
@RequestMapping("/base/tray")
public class TrayController extends BaseController
{
    private String prefix = "base/tray";

    @Autowired
    private ITrayService trayService;

    @Autowired
    private IWarehouseService warehouseService;

    @Autowired
    private IStorehouseService storehouseService;

    @RequiresPermissions("base:tray:view")
    @GetMapping()
    public String Tray()
    {
        return prefix + "/tray";
    }

        /**
     * 查询托盘列表
     */
    @RequiresPermissions("base:tray:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(Tray tray)
    {
        startPage();
        List<Tray> list = trayService.selectTrayList(tray);
        return getDataTable(list);
    }

    /**
     * 导出托盘列表
     */
    @RequiresPermissions("base:tray:export")
    @Log(title = "托盘", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(Tray tray)
    {
        List<Tray> list = trayService.selectTrayList(tray);
        ExcelUtil<Tray> util = new ExcelUtil<Tray>(Tray.class);
        return util.exportExcel(list, "tray");
    }

    /**
     * 新增托盘
     */
    @GetMapping("/add")
    public String add(ModelMap mmap)
    {
        mmap.put("whList",warehouseService.selectWarehouseDict());
        return prefix + "/add";
    }

    /**
     * 新增保存托盘
     */
    @RequiresPermissions("base:tray:add")
    @Log(title = "托盘", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(Tray tray)
    {
        tray.setCreateuser(ShiroUtils.getSysUser().getUserName());
        tray.setCreatedate(new Date());
        return toAjax(trayService.insertTray(tray));
    }

    /**
     * 修改托盘
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap)
    {
        Tray tray = trayService.selectTrayById(id);
        mmap.put("whList",warehouseService.selectWarehouseDict());
        Storehouse storehouse = storehouseService.selectStorehouseById(tray.getStoreid().toString());
        List<Storehouse> storehouses = null;
        if (null != storehouse) {
            tray.setWhcode(storehouse.getWhcode());
            storehouses = storehouseService.getStorehouseByWhcode(storehouse.getWhcode());
        }
        mmap.put("shList", storehouses);
        mmap.put("tray", tray);
        return prefix + "/edit";
    }

    /**
     * 修改保存托盘
     */
    @RequiresPermissions("base:tray:edit")
    @Log(title = "托盘", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(Tray tray)
    {
        return toAjax(trayService.updateTray(tray));
    }

    /**
     * 删除托盘
     */
    @RequiresPermissions("base:tray:remove")
    @Log(title = "托盘", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(trayService.deleteTrayByIds(ids));
    }

    /**
     * 根据仓库code获取库位
     */
    @PostMapping("/getStorehouseByWhcode")
    @ResponseBody
    public List<Storehouse> getStorehouseByWhcode(String whcode)
    {
        List<Storehouse> storehouseList = storehouseService.getStorehouseByWhcode(whcode);
        return storehouseList;
    }
}
