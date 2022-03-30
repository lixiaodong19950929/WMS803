package com.wms.base.storehouse.controller;

import com.wms.base.region.domain.Region;
import com.wms.base.storehouse.domain.Storehouse;
import com.wms.base.region.service.IRegionService;
import com.wms.base.storehouse.service.IStorehouseService;
import com.wms.base.tray.domain.Tray;
import com.wms.base.tray.service.ITrayService;
import com.wms.base.warehouse.service.IWarehouseService;
import com.wms.common.annotation.Log;
import com.wms.common.core.controller.BaseController;
import com.wms.common.core.domain.AjaxResult;
import com.wms.common.core.page.TableDataInfo;
import com.wms.common.enums.BusinessType;
import com.wms.common.utils.ToolsUtils;
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
 * 库位Controller
 * 
 * @author dkwms
 * @date 2020-01-03
 */
@Controller
@RequestMapping("/base/storehouse")
public class StorehouseController extends BaseController
{
    private String prefix = "base/storehouse";

    @Autowired
    private IStorehouseService storehouseService;

    @Autowired
    private IWarehouseService warehouseService;

    @Autowired
    private IRegionService regionService;

    @Autowired
    private ITrayService trayService;

    @RequiresPermissions("base:storehouse:view")
    @GetMapping()
    public String Storehouse()
    {
        return prefix + "/storehouse";
    }

        /**
     * 查询库位列表
     */
    @RequiresPermissions("base:storehouse:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(Storehouse storehouse)
    {
        startPage();
        List<Storehouse> list = storehouseService.selectStorehouseList(storehouse);
        return getDataTable(list);
    }
    /**
     * 查询库位列表
     */
    @RequiresPermissions("base:storehouse:list")
    @PostMapping("/listTray")
    @ResponseBody
    public TableDataInfo listTray(Storehouse storehouse)
    {
        startPage();
        List<Storehouse> list = storehouseService.selectStorehouseTrayList(storehouse);
        return getDataTable(list);
    }

    /**
     * 查询库位列表
     */
    @RequiresPermissions("base:storehouse:list")
    @PostMapping("/listTrayOut")
    @ResponseBody
    public TableDataInfo listTrayOut(Storehouse storehouse)
    {
        startPage();
        List<Storehouse> list = storehouseService.selectStorehouseTrayOutList(storehouse);
        return getDataTable(list);
    }
    
    /**
     * 导出库位列表
     */
    @RequiresPermissions("base:storehouse:export")
    @Log(title = "库位", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(Storehouse storehouse)
    {
        List<Storehouse> list = storehouseService.selectStorehouseList(storehouse);
        ExcelUtil<Storehouse> util = new ExcelUtil<Storehouse>(Storehouse.class);
        return util.exportExcel(list, "storehouse");
    }

    /**
     * 新增库位
     */
    @GetMapping("/add")
    public String add(ModelMap mmap)
    {
        mmap.put("whList",warehouseService.selectWarehouseDict());
        mmap.put("trayList",trayService.selectTrayList(new Tray()));
        return prefix + "/add";
    }

    /**
     * 新增保存库位
     */
    @RequiresPermissions("base:storehouse:add")
    @Log(title = "库位", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(Storehouse storehouse)
    {
//        storehouse.setStorehousecode(ToolsUtils.getOrdersId("SHC"));
        storehouse.setCreateuser(ShiroUtils.getSysUser().getUserName());
        storehouse.setCreatedate(new Date());
        return toAjax(storehouseService.insertStorehouse(storehouse));
    }

    /**
     * 修改库位
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") String id, ModelMap mmap)
    {
        Storehouse storehouse = storehouseService.selectStorehouseById(id);
        mmap.put("whList",warehouseService.selectWarehouseDict());
        mmap.put("rgList",regionService.getRegionByWhcode(storehouse.getWhcode()));
        mmap.put("storehouse", storehouse);
        mmap.put("trayCodeString",trayService.selectTrayByStoreHouseCode(storehouse.getStorehousecode()));
        return prefix + "/edit";
    }

    /**
     * 修改保存库位
     */
    @RequiresPermissions("base:storehouse:edit")
    @Log(title = "库位", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(Storehouse storehouse)
    {
        String isUniuqe = storehouseService.getStorehouseCode(storehouse);
        if (isUniuqe.equals("1")) {
            return error("库位编码已存在");
        }
        return toAjax(storehouseService.updateStorehouse(storehouse));
    }

    /**
     * 删除库位
     */
    @RequiresPermissions("base:storehouse:remove")
    @Log(title = "库位", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(storehouseService.deleteStorehouseByIds(ids));
    }

    /**
     * 根据仓库code获取库区
     */
    @PostMapping("/getRegionByWhcode")
    @ResponseBody
    public List<Region> getRegionByWhcode(String whcode)
    {
        return regionService.getRegionByWhcode(whcode);
    }
//
//    /**
//     * 根据库区code获取库位
//     */
//    @PostMapping("/selectStorehouseCode")
//    @ResponseBody
//    public List<storehouse> selectStorehouseCode(String regioncode)
//    {
//        return storehouseService.selectStorehouseCode(regioncode);
//    }

    /**
     * 校验库位编码
     */
    @PostMapping("/getStorehouseCode")
    @ResponseBody
    public String getStorehouseCode(Storehouse storehouse)
    {
        return storehouseService.getStorehouseCode(storehouse);
    }


}
