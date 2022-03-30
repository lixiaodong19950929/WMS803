package com.wms.base.station.controller;

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
import com.wms.base.station.domain.Station;
import com.wms.base.station.service.IStationService;
import com.wms.common.core.controller.BaseController;
import com.wms.common.core.domain.AjaxResult;
import com.wms.common.utils.poi.ExcelUtil;
import com.wms.common.core.page.TableDataInfo;

/**
 * 岗位Controller
 * 
 * @author assassin
 * @date 2021-03-23
 */
@Controller
@RequestMapping("/base/station")
public class StationController extends BaseController
{
    private String prefix = "base/station";

    @Autowired
    private IStationService stationService;

    @RequiresPermissions("warehouse:station:view")
    @GetMapping()
    public String station()
    {
        return prefix + "/station";
    }

        /**
     * 查询岗位列表
     */
    @RequiresPermissions("warehouse:station:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(Station station)
    {
        startPage();
        List<Station> list = stationService.selectStationList(station);
        return getDataTable(list);
    }
    
    /**
     * 导出岗位列表
     */
    @RequiresPermissions("warehouse:station:export")
    @Log(title = "岗位", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(Station station)
    {
        List<Station> list = stationService.selectStationList(station);
        ExcelUtil<Station> util = new ExcelUtil<Station>(Station.class);
        return util.exportExcel(list, "station");
    }

    /**
     * 新增岗位
     */
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存岗位
     */
    @RequiresPermissions("warehouse:station:add")
    @Log(title = "岗位", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(Station station)
    {
        return toAjax(stationService.insertStation(station));
    }

    /**
     * 修改岗位
     */
    @GetMapping("/edit/{stationcode}")
    public String edit(@PathVariable("stationcode") String stationcode, ModelMap mmap)
    {
        Station station = stationService.selectStationById(stationcode);
        mmap.put("station", station);
        return prefix + "/edit";
    }

    /**
     * 修改保存岗位
     */
    @RequiresPermissions("warehouse:station:edit")
    @Log(title = "岗位", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(Station station)
    {
        return toAjax(stationService.updateStation(station));
    }

    /**
     * 删除岗位
     */
    @RequiresPermissions("warehouse:station:remove")
    @Log(title = "岗位", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(stationService.deleteStationByIds(ids));
    }
}
