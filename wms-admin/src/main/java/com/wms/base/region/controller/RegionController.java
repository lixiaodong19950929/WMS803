package com.wms.base.region.controller;

import java.util.Date;
import java.util.List;

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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wms.base.region.domain.Region;
import com.wms.base.region.service.IRegionService;


/**
 * 库区Controller
 * 
 * @author assassin
 * @date 2019-12-27
 */
@Controller
@RequestMapping("/base/region")
public class RegionController extends BaseController
{
    private String prefix = "base/region";

    @Autowired
    private IRegionService regionService;

    @Autowired
    private IWarehouseService warehouseService;

    @RequiresPermissions("base:Region:view")
    @GetMapping()
    public String Region()
    {
        return prefix + "/region";
    }

        /**
     * 查询库区列表
     */
    @RequiresPermissions("base:Region:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(Region region)
    {
        startPage();
        List<Region> list = regionService.selectRegionList(region);
        return getDataTable(list);
    }
    
    /**
     * 导出库区列表
     */
    @RequiresPermissions("base:Region:export")
    @Log(title = "库区", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(Region region)
    {
        List<Region> list = regionService.selectRegionList(region);
        ExcelUtil<Region> util = new ExcelUtil<Region>(Region.class);
        return util.exportExcel(list, "Region");
    }

    /**
     * 新增库区
     */
    @GetMapping("/add")
    public String add(ModelMap mmap)
    {
        mmap.put("whList",warehouseService.selectWarehouseDict());
        return prefix + "/add";
    }

    /**
     * 新增保存库区
     */
    @RequiresPermissions("base:Region:add")
    @Log(title = "库区", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(Region region)
    {
        region.setRegioncode(ToolsUtils.getOrdersId("RGC"));
        region.setCreateuser(ShiroUtils.getSysUser().getUserName());
        region.setCreatedate(new Date());
        return toAjax(regionService.insertRegion(region));
    }

    /**
     * 修改库区
     */
    @GetMapping("/edit/{regioncode}")
    public String edit(@PathVariable("regioncode") String regioncode, ModelMap mmap)
    {
        Region region = regionService.selectRegionById(regioncode);
        mmap.put("whList",warehouseService.selectWarehouseDict());
        mmap.put("region", region);
        return prefix + "/edit";
    }

    /**
     * 修改保存库区
     */
    @RequiresPermissions("base:Region:edit")
    @Log(title = "库区", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(Region region)
    {
        region.setCreateuser(ShiroUtils.getSysUser().getUserName());
        region.setCreatedate(new Date());
        return toAjax(regionService.updateRegion(region));
    }

    /**
     * 删除库区
     */
    @RequiresPermissions("base:Region:remove")
    @Log(title = "库区", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(regionService.deleteRegionByIds(ids));
    }
}
