package com.wms.base.employee.controller;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.wms.base.employee.domain.Employee;
import com.wms.base.employee.service.IEmployeeService;
import com.wms.common.annotation.Log;
import com.wms.common.core.controller.BaseController;
import com.wms.common.core.domain.AjaxResult;
import com.wms.common.core.page.TableDataInfo;
import com.wms.common.enums.BusinessType;
import com.wms.common.utils.poi.ExcelUtil;

import com.wms.framework.util.ShiroUtils;
import com.wms.system.service.ISysPostService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.util.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;



/**
 * 员工Controller
 * 
 * @author wms
 * @date 2019-12-25
 */
@Controller
@RequestMapping("/base/employee")
public class EmployeeController extends BaseController
{

    private String prefix = "base/employee";

    @Autowired
    private IEmployeeService employeeService;

    @Autowired
    private ISysPostService postService;

    @RequiresPermissions("base:Employee:view")
    @GetMapping()
    public String Employee()
    {
        return prefix + "/employee";
    }

        /**
     * 查询员工列表
     */
    @RequiresPermissions("base:Employee:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(Employee employee)
    {
        startPage();
        List<Employee> list = employeeService.selectEmployeeList(employee);
        SimpleDateFormat sdf = new SimpleDateFormat("YYYY");
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        if (!CollectionUtils.isEmpty(list)) {
            list.stream().forEach(emp-> {
                if (null != emp.getBirthday()) {
                    String birYear = sdf.format(emp.getBirthday());
                    int age = year - Integer.parseInt(birYear);
                    if (age <= 0) {
                        age = 1;
                    }
                    emp.setAge(age);
                }
            });
        }
        return getDataTable(list);
    }
    
    /**
     * 导出员工列表
     */
    @RequiresPermissions("base:Employee:export")
    @Log(title = "员工", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(Employee employee)
    {
        List<Employee> list = employeeService.selectEmployeeList(employee);
        ExcelUtil<Employee> util = new ExcelUtil<Employee>(Employee.class);
        return util.exportExcel(list, "Employee");
    }

    /**
     * 新增员工
     */
    @GetMapping("/add")
    public String add(ModelMap mmap)
    {
        mmap.put("posts", postService.selectPostAll());
        return prefix + "/add";
    }

    /**
     * 新增保存员工
     */
    @RequiresPermissions("base:Employee:add")
    @Log(title = "员工", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(Employee employee)
    {
        String isUnique = employeeService.checkEmployeeCode(employee);
        if (isUnique.equals("1")) {
            return error("员工编码已存在");
        }
        employee.setCreateuser(ShiroUtils.getSysUser().getUserName());
        employee.setCreatedate(new Date());
        return toAjax(employeeService.insertEmployee(employee));
    }

    /**
     * 修改员工
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") String id, ModelMap mmap)
    {
        Employee employee = employeeService.selectEmployeeById(id);
        mmap.put("employee", employee);
        mmap.put("posts", postService.selectPostAll());
        return prefix + "/edit";
    }

    /**
     * 修改保存员工
     */
    @RequiresPermissions("base:Employee:edit")
    @Log(title = "员工", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(Employee employee)
    {
        String isUnique = employeeService.checkEmployeeCode(employee);
        if (isUnique.equals("1")) {
            return error("员工编码已存在");
        }
        employee.setModifyuser(ShiroUtils.getSysUser().getUserName());
        employee.setModifydate(new Date());
        return toAjax(employeeService.updateEmployee(employee));
    }

    /**
     * 删除员工
     */
    @RequiresPermissions("base:Employee:remove")
    @Log(title = "员工", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(employeeService.deleteEmployeeByIds(ids));
    }

    /**
     * 校验员工编码
     */
    @PostMapping("/checkEmployeeCode")
    @ResponseBody
    public String checkEmployeeCode(Employee employee)
    {
        return employeeService.checkEmployeeCode(employee);
    }

    /**
     * 校验员工电话
     */
    @PostMapping("/checkPhoneUnique")
    @ResponseBody
    public String checkPhoneUnique(Employee employee)
    {
        return employeeService.checkPhoneUnique(employee);
    }
}
