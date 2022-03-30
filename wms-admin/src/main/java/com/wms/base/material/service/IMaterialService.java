package com.wms.base.material.service;

import com.wms.base.material.domain.Material;

import java.util.List;

/**
 * 物料基础档案Service接口
 * 
 * @author dkwms
 * @date 2020-01-09
 */
public interface IMaterialService
{
    /**
     * 查询物料基础档案
     * 
     * @param id 物料基础档案ID
     * @return 物料基础档案
     */
    public Material selectMaterialById(String id);

    /**
     * 查询物料基础档案列表
     * 
     * @param material 物料基础档案
     * @return 物料基础档案集合
     */
    public List<Material> selectMaterialList(Material material);

    /**
     * 新增物料基础档案
     * 
     * @param material 物料基础档案
     * @return 结果
     */
    public int insertMaterial(Material material);

    /**
     * 修改物料基础档案
     * 
     * @param material 物料基础档案
     * @return 结果
     */
    public int updateMaterial(Material material);

    /**
     * 批量删除物料基础档案
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteMaterialByIds(String ids);

    /**
     * 删除物料基础档案信息
     * 
     * @param materialcode 物料基础档案ID
     * @return 结果
     */
    public int deleteMaterialById(String materialcode);

    /**
     * 查询物料基础档案
     *
     * @param material 物料基础数据
     * @return 物料基础档案
     */
    public String getMaterialCode(Material material);
}
