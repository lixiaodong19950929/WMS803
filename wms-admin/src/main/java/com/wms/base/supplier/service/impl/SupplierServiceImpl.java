package com.wms.base.supplier.service.impl;

import com.wms.base.supplier.domain.Supplier;
import com.wms.base.supplier.mapper.SupplierMapper;
import com.wms.base.supplier.service.ISupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.wms.common.core.text.Convert;

import java.util.List;

/**
 * 供应商Service业务层处理
 * 
 * @author assassin
 * @date 2021-09-06
 */
@Service
public class SupplierServiceImpl implements ISupplierService
{
    @Autowired
    private SupplierMapper supplierMapper;

    /**
     * 查询供应商
     * 
     * @param id 供应商ID
     * @return 供应商
     */
    @Override
    public Supplier selectSupplierById(Long id)
    {
        return supplierMapper.selectSupplierById(id);
    }

    /**
     * 查询供应商列表
     * 
     * @param supplier 供应商
     * @return 供应商
     */
    @Override
    public List<Supplier> selectSupplierList(Supplier supplier)
    {
        return supplierMapper.selectSupplierList(supplier);
    }

    /**
     * 新增供应商
     * 
     * @param supplier 供应商
     * @return 结果
     */
    @Override
    public int insertSupplier(Supplier supplier)
    {
        return supplierMapper.insertSupplier(supplier);
    }

    /**
     * 修改供应商
     * 
     * @param supplier 供应商
     * @return 结果
     */
    @Override
    public int updateSupplier(Supplier supplier)
    {
        return supplierMapper.updateSupplier(supplier);
    }

    /**
     * 删除供应商对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteSupplierByIds(String ids)
    {
        return supplierMapper.deleteSupplierByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除供应商信息
     * 
     * @param id 供应商ID
     * @return 结果
     */
    public int deleteSupplierById(Long id)
    {
        return supplierMapper.deleteSupplierById(id);
    }

    @Override
    public List<Supplier> selectSupplierDict(){
        return supplierMapper.selectSupplierDict();
    }
}
