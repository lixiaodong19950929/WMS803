package com.wms.base.running.controller;

import com.wms.base.running.domain.Running;
import com.wms.base.running.service.IRunningService;
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
 * 任务执行Controller
 * 
 * @author assassin
 * @date 2021-09-16
 */
@Controller
@RequestMapping("/base/taskrunning")
public class RunningController extends BaseController
{
    private String prefix = "base/taskrunning";

    @Autowired
    private IRunningService runningService;

    @RequiresPermissions("base:taskrunning:view")
    @GetMapping()
    public String taskrunning()
    {
        return prefix + "/taskrunning";
    }

        /**
     * 查询任务执行列表
     */
    @RequiresPermissions("base:taskrunning:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(Running running)
    {
        startPage();
        List<Running> list = runningService.selectRunningList(running);
        return getDataTable(list);
    }
    
    /**
     * 导出任务执行列表
     */
    @RequiresPermissions("base:taskrunning:export")
    @Log(title = "任务执行", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(Running running)
    {
        List<Running> list = runningService.selectRunningList(running);
        ExcelUtil<Running> util = new ExcelUtil<Running>(Running.class);
        return util.exportExcel(list, "taskrunning");
    }

    /**
     * 新增任务执行
     */
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存任务执行
     */
    @RequiresPermissions("base:taskrunning:add")
    @Log(title = "任务执行", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(Running running)
    {
        return toAjax(runningService.insertRunning(running));
    }

    /**
     * 修改任务执行
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap)
    {
        Running running = runningService.selectRunningById(id);
        mmap.put("running", running);
        return prefix + "/edit";
    }

    /**
     * 修改保存任务执行
     */
    @RequiresPermissions("base:taskrunning:edit")
    @Log(title = "任务执行", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(Running running)
    {
        return toAjax(runningService.updateRunning(running));
    }

    /**
     * 删除任务执行
     */
    @RequiresPermissions("base:taskrunning:remove")
    @Log(title = "任务执行", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(runningService.deleteRunningByIds(ids));
    }
}
