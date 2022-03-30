package com.wms.base.material.controller;

import com.wms.base.material.domain.Material;
import com.wms.base.material.service.IMaterialService;
import com.wms.base.product.domain.Product;
import com.wms.base.supplier.service.ISupplierService;
import com.wms.common.annotation.Log;
import com.wms.common.core.controller.BaseController;
import com.wms.common.core.domain.AjaxResult;
import com.wms.common.core.page.TableDataInfo;
import com.wms.common.enums.BusinessType;
import com.wms.common.utils.poi.ExcelUtil;
import com.wms.framework.util.ShiroUtils;
import com.wms.framework.web.service.DictService;
import com.wms.system.domain.SysDictData;
import com.wms.system.service.ISysDictDataService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * 物料基础档案Controller
 * 
 * @author dkwms
 * @date 2020-01-09
 */
@Controller
@RequestMapping("/base/material")
public class MaterialController extends BaseController
{
    private String prefix = "base/material";

    @Autowired
    private ISupplierService supplierService;

    @Autowired
    private IMaterialService materialService;

    @Autowired
    private DictService dictService;

    @RequiresPermissions("base:material:view")
    @GetMapping()
    public String Material()
    {
        return prefix + "/material";
    }

        /**
     * 查询物料基础档案列表
     */
    @RequiresPermissions("base:material:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(Material material)
    {
        startPage();
        List<Material> list = materialService.selectMaterialList(material);
        return getDataTable(list);
    }
    
    /**
     * 导出物料基础档案列表
     */
    @RequiresPermissions("base:material:export")
    @Log(title = "物料基础档案", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(Material material)
    {
        List<Material> list = materialService.selectMaterialList(material);
        ExcelUtil<Material> util = new ExcelUtil<Material>(Material.class);
        List<SysDictData> base_material_type = dictService.getType("base_material_type");
        List<SysDictData> base_material_unit = dictService.getType("base_product_unit");
        List<SysDictData> base_material_scan = dictService.getType("base_material_scan");
        List<SysDictData> base_material_importance = dictService.getType("base_material_importance");
        List<SysDictData> base_normal_status = dictService.getType("base_normal_status");
        //对键值进行转换
        for (Material material1 : list){
            //对物料类别进行转换
            for (SysDictData type : base_material_type){
                if (material1.getMaterialtype().equals(type.getDictValue())){
                    material1.setMaterialtypeName(type.getDictLabel());
                    break;
                }
            }
            //对单位进行转换
            for (SysDictData unit : base_material_unit){
                if (null != material1.getUnit() && material1.getUnit().equals(unit.getDictValue())){
                    material1.setUnitName(unit.getDictLabel());
                    break;
                }
            }
            //对是否需要扫描进行转换
            for (SysDictData scan : base_material_scan){
                if (material1.getIsscan().toString().equals(scan.getDictValue())){
                    material1.setIsscanName(scan.getDictLabel());
                    break;
                }
            }
            //对是否重要物料进行转换
            for (SysDictData importance : base_material_importance){
                if (material1.getIsimportant().toString().equals(importance.getDictValue())){
                    material1.setIsimportantName(importance.getDictLabel());
                    break;
                }
            }
            //对状态进行转换
            for (SysDictData status : base_normal_status){
                if (material1.getIsenable().toString().equals(status.getDictValue())){
                    material1.setIsenableName(status.getDictLabel());
                    break;
                }
            }
        }
        return util.exportExcel(list, "material");
    }

    /**
     * 新增物料基础档案
     */
    @GetMapping("/add")
    public String add(ModelMap mmap)
    {
        mmap.put("supplierList",supplierService.selectSupplierDict());
        return prefix + "/add";
    }

    /**
     * 新增保存物料基础档案
     */
    @RequiresPermissions("base:material:add")
    @Log(title = "物料基础档案", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(Material material)
    {
        material.setCreateuser(ShiroUtils.getSysUser().getUserName());
        material.setCreatedate(new Date());
        return toAjax(materialService.insertMaterial(material));
    }

    /**
     * 修改物料基础档案
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") String id, ModelMap mmap)
    {
        Material material = materialService.selectMaterialById(id);
        mmap.put("material", material);
        mmap.put("supplierList",supplierService.selectSupplierDict());
        return prefix + "/edit";
    }

    /**
     * 修改保存物料基础档案
     */
    @RequiresPermissions("base:material:edit")
    @Log(title = "物料基础档案", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(Material material)
    {
        material.setModifyuser(ShiroUtils.getSysUser().getUserName());
        material.setModifydate(new Date());
        return toAjax(materialService.updateMaterial(material));
    }

    /**
     * 删除物料基础档案
     */
    @RequiresPermissions("base:material:remove")
    @Log(title = "物料基础档案", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(materialService.deleteMaterialByIds(ids));
    }

    /**
     * 校验物料编码
     */
    @PostMapping("/getMaterialCode")
    @ResponseBody
    public String getMaterialCode(Material material)
    {
        return materialService.getMaterialCode(material);
    }
}
