package com.wms.base.customer.mapper;


import com.wms.base.customer.domain.Customer;

import java.util.List;

/**
 * 客户Mapper接口
 * 
 * @author assassin
 * @date 2021-09-06
 */
public interface CustomerMapper 
{
    /**
     * 查询客户
     * 
     * @param id 客户ID
     * @return 客户
     */
    public Customer selectCustomerById(Long id);

    /**
     * 查询客户列表
     * 
     * @param customer 客户
     * @return 客户集合
     */
    public List<Customer> selectCustomerList(Customer customer);

    /**
     * 新增客户
     * 
     * @param customer 客户
     * @return 结果
     */
    public int insertCustomer(Customer customer);

    /**
     * 修改客户
     * 
     * @param customer 客户
     * @return 结果
     */
    public int updateCustomer(Customer customer);

    /**
     * 删除客户
     * 
     * @param id 客户ID
     * @return 结果
     */
    public int deleteCustomerById(Long id);

    /**
     * 批量删除客户
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteCustomerByIds(String[] ids);

    List<Customer> selectCustomerDict();
}
