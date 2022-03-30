package com.wms.base.employee.service;

import com.wms.base.employee.domain.Employee;

import java.util.List;

/**
 * 员工Service接口
 * 
 * @author wms
 * @date 2019-12-25
 */
public interface IEmployeeService
{
    /**
     * 查询员工
     * 
     * @param id 员工ID
     * @return 员工
     */
    public Employee selectEmployeeById(String id);

    /**
     * 查询员工列表
     * 
     * @param employee 员工
     * @return 员工集合
     */
    public List<Employee> selectEmployeeList(Employee employee);

    /**
     * 新增员工
     * 
     * @param employee 员工
     * @return 结果
     */
    public int insertEmployee(Employee employee);

    /**
     * 修改员工
     * 
     * @param employee 员工
     * @return 结果
     */
    public int updateEmployee(Employee employee);

    /**
     * 批量删除员工
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteEmployeeByIds(String ids);

    /**
     * 删除员工信息
     * 
     * @param employeecode 员工ID
     * @return 结果
     */
    public int deleteEmployeeById(String employeecode);

    String checkEmployeeCode(Employee employee);

    String checkPhoneUnique(Employee employee);
}
