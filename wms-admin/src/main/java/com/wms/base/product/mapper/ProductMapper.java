package com.wms.base.product.mapper;

import com.wms.base.product.domain.Product;
import java.util.List;

/**
 * 产品档案Mapper接口
 * 
 * @author assassin
 * @date 2020-01-02
 */
public interface ProductMapper
{
    /**
     * 查询产品档案
     * 
     * @param ProductCode 产品档案ID
     * @return 产品档案
     */
    public Product selectProductById(String ProductCode);

    public Product selectProductByProId(String ProductCode);

    /**
     * 查询产品档案列表（关联了库位表--求库存数量）
     * 
     * @param product 产品档案
     * @return 产品档案集合
     */
    public List<Product> selectProductList(Product product);

    /**
     * 查询产品档案列表（纯查产品表）
     *
     * @param product 产品档案
     * @return 产品档案集合
     */
    public List<Product> selectProductListForBaseProduct(Product product);

    /**
     * 新增产品档案
     * 
     * @param product 产品档案
     * @return 结果
     */
    public int insertProduct(Product product);

    /**
     * 修改产品档案
     * 
     * @param product 产品档案
     * @return 结果
     */
    public int updateProduct(Product product);

    /**
     * 删除产品档案
     * 
     * @param ProductCode 产品档案ID
     * @return 结果
     */
    public int deleteProductById(String ProductCode);

    /**
     * 批量删除产品档案
     * 
     * @param ProductCodes 需要删除的数据ID
     * @return 结果
     */
    public int deleteProductByIds(String[] ProductCodes);

    Product getProductCode(String productCode);

    List<String> getCodeAndNameAndModelAndBatch();

    Product selectProductByInfo(Product product);
}
