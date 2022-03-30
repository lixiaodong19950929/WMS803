package com.wms.base.product.controller;

import com.wms.base.product.domain.Product;
import com.wms.base.product.service.IProductService;
import com.wms.base.supplier.domain.Supplier;
import com.wms.base.supplier.service.ISupplierService;
import com.wms.common.annotation.Log;
import com.wms.common.core.controller.BaseController;
import com.wms.common.core.domain.AjaxResult;
import com.wms.common.core.page.TableDataInfo;
import com.wms.common.enums.BusinessType;
import com.wms.common.utils.DateUtils;
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
 * 产品档案Controller
 * 
 * @author assassin
 * @date 2020-01-02
 */
@Controller
@RequestMapping("/base/product")
public class ProductController extends BaseController
{
    private String prefix = "base/product";

    @Autowired
    private ISupplierService supplierService;

    @Autowired
    private IProductService productService;

    @Autowired
    private DictService dictService;

    @RequiresPermissions("base:product:view")
    @GetMapping()
    public String product()
    {
        return prefix + "/product";
    }

        /**
     * 查询产品档案列表
     */
    @RequiresPermissions("base:product:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(Product product)
    {
        startPage();
        List<Product> list = productService.selectProductListForBaseProduct(product);
        return getDataTable(list);
    }
    
    /**
     * 导出产品档案列表
     */
    @RequiresPermissions("base:product:export")
    @Log(title = "产品档案", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(Product product)
    {
        List<Product> list = productService.selectProductList(product);
        ExcelUtil<Product> util = new ExcelUtil<Product>(Product.class);
        List<SysDictData> base_product_type = dictService.getType("base_product_type");
        List<SysDictData> base_product_model = dictService.getType("base_product_model");
        List<SysDictData> base_product_unit = dictService.getType("base_product_unit");
        List<SysDictData> base_normal_status = dictService.getType("base_normal_status");
        //对键值进行转换
        for (Product product1 : list){
            //对产品类型进行转换
            for (SysDictData type : base_product_type){
                if (product1.getProducttype().equals(type.getDictValue())){
                    product1.setProducttypeName(type.getDictLabel());
                    break;
                }
            }
            //对产品型号进行转换
            for (SysDictData model : base_product_model){
                if (product1.getProductmodel().equals(model.getDictValue())){
                    product1.setProductmodelName(model.getDictLabel());
                    break;
                }
            }
            //对单位进行转换
            for (SysDictData unit : base_product_unit){
                if (product1.getUnit().equals(unit.getDictValue())){
                    product1.setUnitName(unit.getDictLabel());
                    break;
                }
            }
            //对状态进行转换
            for (SysDictData status : base_normal_status){
                if (product1.getIsenable().toString().equals(status.getDictValue())){
                    product1.setIsenableName(status.getDictLabel());
                    break;
                }
            }
        }
        return util.exportExcel(list, "product");
    }

    /**
     * 新增产品档案
     */
    @GetMapping("/add")
    public String add(ModelMap mmap)
    {
        mmap.put("supplierList",supplierService.selectSupplierDict());
        return prefix + "/add";
    }

    /**
     * 新增保存产品档案
     */
    @RequiresPermissions("base:product:add")
    @Log(title = "产品档案", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(Product product)
    {
        product.setCreateuser(ShiroUtils.getSysUser().getUserName());
        product.setCreatedate(new Date());
        return toAjax(productService.insertProduct(product));
    }

    /**
     * 修改产品档案
     */
    @GetMapping("/edit/{ProductCode}")
    public String edit(@PathVariable("ProductCode") String ProductCode, ModelMap mmap)
    {
        Product product = productService.selectProductById(ProductCode);
        mmap.put("product", product);
        mmap.put("supplierList",supplierService.selectSupplierDict());
        return prefix + "/edit";
    }

    /**
     * 修改保存产品档案
     */
    @RequiresPermissions("base:product:edit")
    @Log(title = "产品档案", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(Product product)
    {
        product.setModifyuser(ShiroUtils.getSysUser().getUserName());
        product.setModifydate(new Date());
        return toAjax(productService.updateProduct(product));
    }

    /**
     * 删除产品档案
     */
    @RequiresPermissions("base:product:remove")
    @Log(title = "产品档案", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(productService.deleteProductByIds(ids));
    }

    /**
     * 校验产品编码
     */
    @PostMapping("/getProductCode")
    @ResponseBody
    public String getProductCode(Product product)
    {
        return productService.getProductCode(product);
    }


    public static void main(String[] args) {
        String startDate="";
        String endDate="";
        //结束时间戳
        System.out.println();
        System.out.println(1618995600-1618992000);
    }
}
