package com.wms.base.employee.service.impl;

import java.util.List;

import com.wms.common.constant.BaseConstants;
import com.wms.common.core.text.Convert;
import com.wms.common.utils.StringUtils;
import com.wms.base.employee.domain.Employee;
import com.wms.base.employee.mapper.EmployeeMapper;
import com.wms.base.employee.service.IEmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 员工Service业务层处理
 * 
 * @author wms
 * @date 2019-12-25
 */
@Service
public class EmployeeServiceImpl implements IEmployeeService
{
    @Autowired
    private EmployeeMapper employeeMapper;

    /**
     * 查询员工
     * 
     * @param id 员工ID
     * @return 员工
     */
    @Override
    public Employee selectEmployeeById(String id)
    {
        return employeeMapper.selectEmployeeById(id);
    }

    /**
     * 查询员工列表
     * 
     * @param employee 员工
     * @return 员工
     */
    @Override
    public List<Employee> selectEmployeeList(Employee employee)
    {
        return employeeMapper.selectEmployeeList(employee);
    }

    /**
     * 新增员工
     * 
     * @param employee 员工
     * @return 结果
     */
    @Override
    public int insertEmployee(Employee employee)
    {
        return employeeMapper.insertEmployee(employee);
    }

    /**
     * 修改员工
     * 
     * @param employee 员工
     * @return 结果
     */
    @Override
    public int updateEmployee(Employee employee)
    {
        return employeeMapper.updateEmployee(employee);
    }

    /**
     * 删除员工对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteEmployeeByIds(String ids)
    {
        return employeeMapper.deleteEmployeeByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除员工信息
     * 
     * @param employeecode 员工ID
     * @return 结果
     */
    public int deleteEmployeeById(String employeecode)
    {
        return employeeMapper.deleteEmployeeById(employeecode);
    }

    @Override
    public String checkEmployeeCode(Employee employee) {
        Long userId = StringUtils.isNull(employee.getId()) ? -1L : employee.getId();
        Employee info= employeeMapper.checkEmployeeCode(employee.getEmployeecode());
        if (StringUtils.isNotNull(info) && info.getId().longValue() != userId.longValue()){
            return BaseConstants.EMPLOYEE_CODE_NOT_UNIQUE;
        }
        return BaseConstants.EMPLOYEE_CODE_UNIQUE;
    }

    @Override
    public String checkPhoneUnique(Employee employee) {
        Long userId = StringUtils.isNull(employee.getId()) ? -1L : employee.getId();
        Employee info = employeeMapper.checkPhoneUnique(employee.getTel());
        if (StringUtils.isNotNull(info) && info.getId().longValue() != userId.longValue())
        {
            return BaseConstants.EMPLOYEE_PHONE_NOT_UNIQUE;
        }
        return BaseConstants.EMPLOYEE_PHONE_UNIQUE;
    }

}
