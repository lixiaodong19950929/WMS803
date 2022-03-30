package com.wms.base.supplier.mapper;


import com.wms.base.supplier.domain.Supplier;

import java.util.List;

/**
 * 供应商Mapper接口
 * 
 * @author assassin
 * @date 2021-09-06
 */
public interface SupplierMapper 
{
    /**
     * 查询供应商
     * 
     * @param id 供应商ID
     * @return 供应商
     */
    public Supplier selectSupplierById(Long id);

    /**
     * 查询供应商列表
     * 
     * @param supplier 供应商
     * @return 供应商集合
     */
    public List<Supplier> selectSupplierList(Supplier supplier);

    /**
     * 新增供应商
     * 
     * @param supplier 供应商
     * @return 结果
     */
    public int insertSupplier(Supplier supplier);

    /**
     * 修改供应商
     * 
     * @param supplier 供应商
     * @return 结果
     */
    public int updateSupplier(Supplier supplier);

    /**
     * 删除供应商
     * 
     * @param id 供应商ID
     * @return 结果
     */
    public int deleteSupplierById(Long id);

    /**
     * 批量删除供应商
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteSupplierByIds(String[] ids);

    List<Supplier> selectSupplierDict();
}
