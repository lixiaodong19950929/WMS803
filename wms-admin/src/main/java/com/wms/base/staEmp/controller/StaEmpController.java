package com.wms.base.staEmp.controller;

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
import com.wms.base.staEmp.domain.StaEmp;
import com.wms.base.staEmp.service.IStaEmpService;
import com.wms.common.core.controller.BaseController;
import com.wms.common.core.domain.AjaxResult;
import com.wms.common.utils.poi.ExcelUtil;
import com.wms.common.core.page.TableDataInfo;

/**
 * 操作人员Controller
 * 
 * @author assassin
 * @date 2021-03-23
 */
@Controller
@RequestMapping("/base/staEmp")
public class StaEmpController extends BaseController
{
    private String prefix = "base/staEmp";

    @Autowired
    private IStaEmpService staEmpService;

    @RequiresPermissions("base:staEmp:view")
    @GetMapping()
    public String emp()
    {
        return prefix + "/emp";
    }

        /**
     * 查询操作人员列表
     */
    @RequiresPermissions("base:staEmp:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(StaEmp staEmp)
    {
        startPage();
        List<StaEmp> list = staEmpService.selectStaEmpList(staEmp);
        return getDataTable(list);
    }
    
    /**
     * 导出操作人员列表
     */
    @RequiresPermissions("base:staEmp:export")
    @Log(title = "操作人员", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(StaEmp staEmp)
    {
        List<StaEmp> list = staEmpService.selectStaEmpList(staEmp);
        ExcelUtil<StaEmp> util = new ExcelUtil<StaEmp>(StaEmp.class);
        return util.exportExcel(list, "emp");
    }

    /**
     * 新增操作人员
     */
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存操作人员
     */
    @RequiresPermissions("base:staEmp:add")
    @Log(title = "操作人员", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(StaEmp staEmp)
    {
        return toAjax(staEmpService.insertStaEmp(staEmp));
    }

    /**
     * 修改操作人员
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap)
    {
        StaEmp staEmp = staEmpService.selectStaEmpById(id);
        mmap.put("staEmp", staEmp);
        return prefix + "/edit";
    }

    /**
     * 修改保存操作人员
     */
    @RequiresPermissions("base:staEmp:edit")
    @Log(title = "操作人员", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(StaEmp staEmp)
    {
        return toAjax(staEmpService.updateStaEmp(staEmp));
    }

    /**
     * 删除操作人员
     */
    @RequiresPermissions("base:staEmp:remove")
    @Log(title = "操作人员", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(staEmpService.deleteStaEmpByIds(ids));
    }
}
