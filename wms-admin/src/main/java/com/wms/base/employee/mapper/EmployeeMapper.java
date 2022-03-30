package com.wms.base.employee.mapper;

import com.wms.base.employee.domain.Employee;

import java.util.List;

/**
 * 员工Mapper接口
 * 
 * @author wms
 * @date 2019-12-25
 */
public interface EmployeeMapper
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
     * 删除员工
     * 
     * @param employeecode 员工ID
     * @return 结果
     */
    public int deleteEmployeeById(String employeecode);

    /**
     * 批量删除员工
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteEmployeeByIds(String[] ids);

    Employee checkEmployeeCode(String employeecode);

    Employee checkPhoneUnique(String tel);
}
