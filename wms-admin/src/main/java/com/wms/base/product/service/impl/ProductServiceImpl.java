package com.wms.base.product.service.impl;

import com.wms.base.employee.domain.Employee;
import com.wms.base.product.domain.Product;
import com.wms.base.product.mapper.ProductMapper;
import com.wms.common.constant.BaseConstants;
import com.wms.common.core.text.Convert;
import com.wms.base.product.service.IProductService;
import com.wms.common.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * 产品档案Service业务层处理
 * 
 * @author assassin
 * @date 2020-01-02
 */
@Service
public class ProductServiceImpl implements IProductService
{
    @Autowired
    private ProductMapper productMapper;

    /**
     * 查询产品档案
     * 
     * @param ProductCode 产品档案ID
     * @return 产品档案
     */
    @Override
    public Product selectProductById(String ProductCode)
    {
        return productMapper.selectProductById(ProductCode);
    }

    @Override
    public Product selectProductByProId(String ProductCode)
    {
        return productMapper.selectProductByProId(ProductCode);
    }

    /**
     * 查询产品档案列表（关联了库位表--求库存数量）
     * 
     * @param product 产品档案
     * @return 产品档案
     */
    @Override
    public List<Product> selectProductList(Product product)
    {
        return productMapper.selectProductList(product);
    }

    /**
     * 查询产品档案列表（纯查产品表）
     *
     * @param product 产品档案
     * @return 产品档案
     */
    @Override
    public List<Product> selectProductListForBaseProduct(Product product)
    {
        return productMapper.selectProductListForBaseProduct(product);
    }

    /**
     * 新增产品档案
     * 
     * @param product 产品档案
     * @return 结果
     */
    @Override
    public int insertProduct(Product product)
    {
        return productMapper.insertProduct(product);
    }

    /**
     * 修改产品档案
     * 
     * @param product 产品档案
     * @return 结果
     */
    @Override
    public int updateProduct(Product product)
    {
        return productMapper.updateProduct(product);
    }

    /**
     * 删除产品档案对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteProductByIds(String ids)
    {
        return productMapper.deleteProductByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除产品档案信息
     * 
     * @param id 产品档案ID
     * @return 结果
     */
    public int deleteProductById(String id)
    {
        return productMapper.deleteProductById(id);
    }

    public String getProductCode(Product product)
    {
        Long userId = StringUtils.isNull(product.getId()) ? -1L : product.getId();
        Product info= productMapper.getProductCode(product.getProductcode());
        if (StringUtils.isNotNull(info) && info.getId().longValue() != userId.longValue()){
            return BaseConstants.MATERIAL_MATERIALCODE_NOT_UNIQUE;
        }
        return BaseConstants.MATERIAL_MATERIALCODE_UNIQUE;
    }

    @Override
    public Product selectProductByInfo(Product product) {
        System.out.println("222222222222222222222222222222");
        System.out.println(product);
        return productMapper.selectProductByInfo(product);
    }

    ;
}
