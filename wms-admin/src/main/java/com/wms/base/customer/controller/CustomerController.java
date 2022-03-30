package com.wms.base.customer.controller;

import java.util.List;

import com.wms.base.customer.domain.Customer;
import com.wms.base.customer.service.ICustomerService;
import com.wms.common.utils.poi.ExcelUtil;
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
import com.wms.common.core.page.TableDataInfo;

/**
 * 客户Controller
 * 
 * @author assassin
 * @date 2021-09-06
 */
@Controller
@RequestMapping("/base/customer")
public class CustomerController extends BaseController
{
    private String prefix = "base/customer";


    @Autowired
    private ICustomerService customerService;

    @RequiresPermissions("base:customer:view")
    @GetMapping()
    public String customer()
    {
        return prefix + "/customer";
    }

        /**
     * 查询客户列表
     */
    @RequiresPermissions("base:customer:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(Customer customer)
    {
        startPage();
        List<Customer> list = customerService.selectCustomerList(customer);
        return getDataTable(list);
    }
    
    /**
     * 导出客户列表
     */
    @RequiresPermissions("base:customer:export")
    @Log(title = "客户", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(Customer customer)
    {
        List<Customer> list = customerService.selectCustomerList(customer);
        ExcelUtil<Customer> util = new ExcelUtil<Customer>(Customer.class);
        return util.exportExcel(list, "customer");
    }

    /**
     * 新增客户
     */
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存客户
     */
    @RequiresPermissions("base:customer:add")
    @Log(title = "客户", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(Customer customer)
    {
        return toAjax(customerService.insertCustomer(customer));
    }

    /**
     * 修改客户
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap)
    {
        Customer customer = customerService.selectCustomerById(id);
        mmap.put("customer", customer);
        return prefix + "/edit";
    }

    /**
     * 修改保存客户
     */
    @RequiresPermissions("base:customer:edit")
    @Log(title = "客户", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(Customer customer)
    {
        return toAjax(customerService.updateCustomer(customer));
    }

    /**
     * 删除客户
     */
    @RequiresPermissions("base:customer:remove")
    @Log(title = "客户", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        System.out.println("客户删除删除删除");
        return toAjax(customerService.deleteCustomerByIds(ids));
    }
}
