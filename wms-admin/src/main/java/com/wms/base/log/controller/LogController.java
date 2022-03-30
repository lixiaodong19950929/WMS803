package com.wms.base.log.controller;

import java.util.List;

import com.wms.base.log.domain.TaskLog;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.wms.base.log.service.ILogService;
import com.wms.common.core.controller.BaseController;
import com.wms.common.core.domain.AjaxResult;
import com.wms.common.utils.poi.ExcelUtil;
import com.wms.common.core.page.TableDataInfo;

/**
 * 任务日志Controller
 * 
 * @author assassin
 * @date 2021-06-07
 */
@Controller
@RequestMapping("/base/taskLog")
public class LogController extends BaseController
{
    private String prefix = "base/log";

    @Autowired
    private ILogService logService;

    @RequiresPermissions("base:taskLog:view")
    @GetMapping()
    public String log()
    {
        return prefix + "/log";
    }

        /**
     * 查询任务日志列表
     */
    @RequiresPermissions("base:taskLog:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(TaskLog taskLog)
    {
        startPage();
        List<TaskLog> list = logService.selectLogList(taskLog);
        return getDataTable(list);
    }
    
    /**
     * 导出任务日志列表
     */
    @RequiresPermissions("base:taskLog:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(TaskLog taskLog)
    {
        List<TaskLog> list = logService.selectLogList(taskLog);
        ExcelUtil<TaskLog> util = new ExcelUtil<TaskLog>(TaskLog.class);
        return util.exportExcel(list, "taskLog");
    }

    /**
     * 新增任务日志
     */
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存任务日志
     */
    @RequiresPermissions("base:taskLog:add")
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(TaskLog taskLog)
    {
        return toAjax(logService.insertLog(taskLog));
    }

    /**
     * 修改任务日志
     */
    @GetMapping("/edit/{rowid}")
    public String edit(@PathVariable("rowid") Long rowid, ModelMap mmap)
    {
        TaskLog taskLog = logService.selectLogById(rowid);
        mmap.put("taskLog", taskLog);
        return prefix + "/edit";
    }

    /**
     * 修改保存任务日志
     */
    @RequiresPermissions("base:taskLog:edit")
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(TaskLog taskLog)
    {
        return toAjax(logService.updateLog(taskLog));
    }

    /**
     * 删除任务日志
     */
    @RequiresPermissions("base:taskLog:remove")
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(logService.deleteLogByIds(ids));
    }
}
