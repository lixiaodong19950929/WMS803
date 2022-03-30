package com.wms.base.material.service.impl;

import com.wms.base.material.domain.Material;
import com.wms.base.material.mapper.MaterialMapper;
import com.wms.base.product.domain.Product;
import com.wms.common.constant.BaseConstants;
import com.wms.common.core.text.Convert;
import com.wms.base.material.service.IMaterialService;
import com.wms.common.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 物料基础档案Service业务层处理
 * 
 * @author dkwms
 * @date 2020-01-09
 */
@Service
public class MaterialServiceImpl implements IMaterialService
{
    @Autowired
    private MaterialMapper materialMapper;

    /**
     * 查询物料基础档案
     * 
     * @param id 物料基础档案ID
     * @return 物料基础档案
     */
    @Override
    public Material selectMaterialById(String id)
    {
        return materialMapper.selectMaterialById(id);
    }

    /**
     * 查询物料基础档案列表
     * 
     * @param material 物料基础档案
     * @return 物料基础档案
     */
    @Override
    public List<Material> selectMaterialList(Material material)
    {
        return materialMapper.selectMaterialList(material);
    }

    /**
     * 新增物料基础档案
     * 
     * @param material 物料基础档案
     * @return 结果
     */
    @Override
    public int insertMaterial(Material material)
    {
        return materialMapper.insertMaterial(material);
    }

    /**
     * 修改物料基础档案
     * 
     * @param material 物料基础档案
     * @return 结果
     */
    @Override
    public int updateMaterial(Material material)
    {
        return materialMapper.updateMaterial(material);
    }

    /**
     * 删除物料基础档案对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteMaterialByIds(String ids)
    {
        return materialMapper.deleteMaterialByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除物料基础档案信息
     * 
     * @param materialcode 物料基础档案ID
     * @return 结果
     */
    public int deleteMaterialById(String materialcode)
    {
        return materialMapper.deleteMaterialById(materialcode);
    }

    @Override
    public String getMaterialCode(Material material)
    {
        Long userId = StringUtils.isNull(material.getId()) ? -1L : material.getId();
        Material info= materialMapper.getMaterialCode(material.getMaterialcode());
        if (StringUtils.isNotNull(info) && info.getId().longValue() != userId.longValue()){
            return BaseConstants.PRODUCT_PRODUCTCODE_NOT_UNIQUE;
        }
        return BaseConstants.PRODUCT_PRODUCTCODE_UNIQUE;
    }
}
