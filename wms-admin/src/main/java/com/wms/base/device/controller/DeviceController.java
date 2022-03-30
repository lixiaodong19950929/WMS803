package com.wms.base.device.controller;

import com.wms.base.device.domain.Device;
import com.wms.base.device.service.IDeviceService;
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
 * 设备Controller
 * 
 * @author lixiaodong
 * @date 2020-01-08
 */
//提交测试
@Controller
@RequestMapping("/base/device")
public class DeviceController extends BaseController
{
    private String prefix = "base/device";

    @Autowired
    private IDeviceService deviceService;

    @Autowired
    private IWarehouseService warehouseService;

    @RequiresPermissions("base:device:view")
    @GetMapping()
    public String Device()
    {
        return prefix + "/device";
    }

        /**
     * 查询设备列表
     */
    @RequiresPermissions("base:device:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(Device device)
    {
        startPage();
        List<Device> list = deviceService.selectDeviceList(device);
        return getDataTable(list);
    }
    
    /**
     * 导出设备列表
     */
    @RequiresPermissions("base:device:export")
    @Log(title = "设备", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(Device device)
    {
        List<Device> list = deviceService.selectDeviceList(device);
        ExcelUtil<Device> util = new ExcelUtil<Device>(Device.class);
        return util.exportExcel(list, "device");
    }

    /**
     * 新增设备
     */
    @GetMapping("/add")
    public String add(ModelMap mmap)
    {
        mmap.put("whList",warehouseService.selectWarehouseDict());
        return prefix + "/add";
    }

    /**
     * 新增保存设备
     */
    @RequiresPermissions("base:device:add")
    @Log(title = "设备", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(Device device)
    {
        device.setCreateuser(ShiroUtils.getSysUser().getUserName());
        device.setCreatedate(new Date());
        return toAjax(deviceService.insertDevice(device));
    }

    /**
     * 修改设备
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") String id, ModelMap mmap)
    {
        Device device = deviceService.selectDeviceById(id);
        mmap.put("device", device);
        mmap.put("whList",warehouseService.selectWarehouseDict());
        return prefix + "/edit";
    }

    /**
     * 修改保存设备
     */
    @RequiresPermissions("base:device:edit")
    @Log(title = "设备", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(Device device)
    {
        String isUnique = deviceService.checkDeviceCode(device);
        if (isUnique.equals("1")) {
            return error("设备编码已经存在");
        }
        return toAjax(deviceService.updateDevice(device));
    }

    /**
     * 删除设备
     */
    @RequiresPermissions("base:device:remove")
    @Log(title = "设备", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(deviceService.deleteDeviceByIds(ids));
    }

    /**
     * 校验设备编码
     */
    @PostMapping("/checkDeviceCode")
    @ResponseBody
    public String checkDeviceCode(Device device)
    {
        return deviceService.checkDeviceCode(device);
    }

}
