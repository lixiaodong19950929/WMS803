package com.wms.base.warehouseiorules.controller;

import com.wms.common.core.controller.BaseController;
import com.wms.common.core.domain.AjaxResult;
import com.wms.system.domain.SysDictData;
import com.wms.system.service.ISysDictDataService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * 出入库规则Controller
 * 
 * @author assassin
 * @date 2019-12-27
 */
@Controller
@RequestMapping("/base/warehouseiorules")
public class WarehouseIoRulesController extends BaseController
{
    private String prefix = "base/warehouseIoRules";

    @Autowired
    private ISysDictDataService dictDataService;

    @RequiresPermissions("base:WarehouseIoRules:view")
    @GetMapping()
    public String WarehouseIoRules()
    {
        return prefix + "/warehouseIoRules";
    }

    @RequestMapping("/setDefaultRules")
    @ResponseBody
    public AjaxResult getDefaultRules(String WarehouseRules){
        //将原来的默认值清空
        SysDictData sysDictData = new SysDictData();
        sysDictData.setDictType("base_warehouse_rules");
        sysDictData.setIsDefault("N");
        dictDataService.updateDictDataByDictType(sysDictData);
        //设置新的默认值
        SysDictData sysDictData1 = new SysDictData();
        sysDictData1.setDictCode(Long.valueOf(WarehouseRules));
        sysDictData1.setIsDefault("Y");
        dictDataService.updateDictData(sysDictData1);
        return success();
    }
}
