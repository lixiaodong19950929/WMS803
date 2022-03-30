package com.wms.warehouse.sern.controller;

import com.wms.common.annotation.Log;
import com.wms.common.core.controller.BaseController;
import com.wms.common.core.domain.AjaxResult;
import com.wms.common.core.page.TableDataInfo;
import com.wms.common.enums.BusinessType;
import com.wms.common.utils.poi.ExcelUtil;
import com.wms.warehouse.sern.domain.TrayCodeDetail;
import com.wms.warehouse.sern.service.TrayCodeDetailService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/warehouse/TrayCodeDetail")
public class TrayCodeDetailController extends BaseController {

    private String prefix = "warehouse/TrayCodeDetail";

    @Autowired
    private TrayCodeDetailService trayCodeDetailService;

    @RequiresPermissions("warehouse:traycodedetail:view")
    @GetMapping()
    public String traycodedetail()
    {
        return prefix + "/traycodedetail";
    }

    /**
     * 查询条码信息明细列表
     */
    @RequiresPermissions("warehouse:traycodedetail:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(TrayCodeDetail trayCodeDetail)
    {
        startPage();
        List<TrayCodeDetail> list = trayCodeDetailService.selectTrayCodeDetailList(trayCodeDetail);
        return getDataTable(list);
    }

    /**
     * 导出条码信息明细列表
     */
    @RequiresPermissions("warehouse:traycodedetail:export")
    @Log(title = "条码信息明细", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(TrayCodeDetail trayCodeDetail)
    {
        List<TrayCodeDetail> list = trayCodeDetailService.selectTrayCodeDetailList(trayCodeDetail);
        ExcelUtil<TrayCodeDetail> util = new ExcelUtil<TrayCodeDetail>(TrayCodeDetail.class);
        return util.exportExcel(list, "traycodedetail");
    }

    /**
     * 新增条码信息明细
     */
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存条码信息明细
     */
    @RequiresPermissions("warehouse:traycodedetail:add")
    @Log(title = "条码信息明细", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(TrayCodeDetail trayCodeDetail)
    {
        return toAjax(trayCodeDetailService.insertTrayCodeDetail(trayCodeDetail));
    }

    /**
     * 修改条码信息明细
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap)
    {
        TrayCodeDetail trayCodeDetail = trayCodeDetailService.selectTrayCodeDetailById(id);
        mmap.put("trayCodeDetail", trayCodeDetail);
        return prefix + "/edit";
    }

    /**
     * 修改保存条码信息明细
     */
    @RequiresPermissions("warehouse:traycodedetail:edit")
    @Log(title = "条码信息明细", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(TrayCodeDetail trayCodeDetail)
    {
        return toAjax(trayCodeDetailService.updateTrayCodeDetail(trayCodeDetail));
    }

    /**
     * 删除条码信息明细
     */
    @RequiresPermissions("warehouse:traycodedetail:remove")
    @Log(title = "条码信息明细", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(trayCodeDetailService.deleteTrayCodeDetailByIds(ids));
    }


    /**
     * 出库添加产品后显示产品对应批次的库存数量
     */
    @RequiresPermissions("warehouse:traycodedetail:list")
    @PostMapping("/selectProductListForSumsByCodeBatch")
    @ResponseBody
    public TableDataInfo selectProductListForSumsByCodeBatch()
    {
        startPage();
        List<TrayCodeDetail> list = trayCodeDetailService.selectProductListForSumsByCodeBatch();
        return getDataTable(list);
    }


}
