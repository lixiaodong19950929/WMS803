package com.wms.base.storeson.controller;

import java.util.List;

import com.wms.base.storeson.domain.StoreSon;
import com.wms.base.storeson.service.IStoreSonService;
import com.wms.common.utils.JsonUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import com.wms.common.annotation.Log;
import com.wms.common.enums.BusinessType;
import com.wms.common.core.controller.BaseController;
import com.wms.common.core.domain.AjaxResult;
import com.wms.common.utils.poi.ExcelUtil;
import com.wms.common.core.page.TableDataInfo;

/**
 * 出库物料明细Controller
 * 
 * @author assassin
 * @date 2021-08-25
 */
@Controller
@RequestMapping("/base/storeson")
public class StoreSonController extends BaseController
{
    private String prefix = "base/storeson";

    @Autowired
    private IStoreSonService storeSonService;

    @RequiresPermissions("base:storeson:view")
    @GetMapping()
    public String storeson()
    {
        return prefix + "/storeson";
    }

        /**
     * 查询出库物料明细列表
     */
    @RequiresPermissions("base:storeson:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(StoreSon storeSon)
    {
        startPage();
        List<StoreSon> list = storeSonService.selectStoreSonList(storeSon);
        return getDataTable(list);
    }
    
    /**
     * 导出出库物料明细列表
     */
    @RequiresPermissions("base:storeson:export")
    @Log(title = "出库物料明细", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(StoreSon storeSon)
    {
        List<StoreSon> list = storeSonService.selectStoreSonList(storeSon);
        ExcelUtil<StoreSon> util = new ExcelUtil<StoreSon>(StoreSon.class);
        return util.exportExcel(list, "storeson");
    }

    /**
     * 新增出库物料明细
     */
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存出库物料明细
     */
    @RequiresPermissions("base:storeson:add")
    @Log(title = "出库物料明细", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(StoreSon storeSon)
    {
        return toAjax(storeSonService.insertStoreSon(storeSon));
    }

    /**
     * 修改出库物料明细
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap)
    {
        StoreSon storeSon = storeSonService.selectStoreSonById(id);
        mmap.put("storeSon", storeSon);
        return prefix + "/edit";
    }

    /**
     * 修改保存出库物料明细
     */
    @RequiresPermissions("base:storeson:edit")
    @Log(title = "出库物料明细", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(StoreSon storeSon)
    {
        return toAjax(storeSonService.updateStoreSon(storeSon));
    }

    /**
     * 删除出库物料明细
     */
    @RequiresPermissions("base:storeson:remove")
    @Log(title = "出库物料明细", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(storeSonService.deleteStoreSonByIds(ids));
    }

    //批量新增
    @Log(title = "出库物料明细", businessType = BusinessType.INSERT)
    @PostMapping("/adds")
    @ResponseBody
    public AjaxResult addSaves(@RequestParam("list") String list) throws Exception {
        List<StoreSon> storeSonList= JsonUtils.json2list(list,StoreSon.class);
        return toAjax(storeSonService.insertStoreSons(storeSonList));
    }

    /**
     * 出库-指定条码-根据任务号和产品号查询已有的任务明细数据
     */
    @PostMapping("/getOutTaskInfo")
    @ResponseBody
    public TableDataInfo getOutTaskInfo(StoreSon storeSon)
    {
        startPage();
        List<StoreSon> list = storeSonService.getOutTaskInfoByTaskProduct(storeSon.getTaskcode(),storeSon.getProductcode());
        return getDataTable(list);
    }

}
