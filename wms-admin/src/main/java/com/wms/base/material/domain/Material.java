package com.wms.base.material.domain;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.wms.common.annotation.Excel;
import com.wms.common.core.domain.BaseEntity;
import java.util.Date;

/**
 * 物料基础档案对象 T_Base_Material
 * 
 * @author dkwms
 * @date 2020-01-09
 */
@Getter
@Setter
public class Material extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 自增列 */
    private Long id;

    /** 物料编码 */
    @Excel(name = "物料编码")
    private String materialcode;

    /** 物料名称 */
    @Excel(name = "物料名称")
    private String materialname;

    /** 物料类别(外购件、外协件、标准件) */
    private String materialtype;
    @Excel(name = "物料类别")
    private String materialtypeName;

    /** 物料批次 */
    @Excel(name = "物料批次")
    private String materialbatch;

    /** 包装袋编码 */
    @Excel(name = "包装袋编码")
    private String packagingcode;

    /** 物料型号 */
    @Excel(name = "物料型号")
    private String materialmodel;

    /** 零件图号 */
    @Excel(name = "零件图号")
    private String drawingnumber;

    /** 规格型号 */
    @Excel(name = "规格型号")
    private String specification;

    /** 标准(国标号及其他标准号) */
    private String standard;

    /** 炉批号-外购件用 */
    @Excel(name = "炉批号")
    private String furnacebatch;

    /** 出厂编码-外协件用 */
    @Excel(name = "出厂编码")
    private String outfacterycode;

    /** 单套数量 */
    @Excel(name = "单套数量")
    private Long quantity;

    /** 单位(个、根、捆、条、包等) */
    private String unit;
    @Excel(name = "单位")
    private String unitName;

    /** 是否需要扫描(0:不需要扫描；1：需要扫描) */
    private Long isscan;
    @Excel(name = "是否需要扫描")
    private String isscanName;

    /** 备注 */
    private String memo;

    /** 供应商名称 */
    private String suppliername;

    /** 供应商 */
    private String suppliercode;

    /** 创建人 */
    private String createuser;

    /** 创建时间 */
//    @Excel(name = "创建时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date createdate;

    /** 修改人 */
    private String modifyuser;

    /** 修改日期 */
//    @Excel(name = "修改日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date modifydate;

    /** 0:非重要物料件；1:重要物料件 */
    private Long isimportant;
    @Excel(name = "是否重要物料件")
    private String isimportantName;

    /** 1:启用，0:停用 */
    private Long isenable;
    @Excel(name = "状态")
    private String isenableName;

    /** 1:删除，0:正常 */
    private Long isdelete;

    /** 备用字段 */
    private String f1;

    /** 备用字段 */
    private String f2;

    /** 备用字段 */
    private String f3;

    /** 备用字段 */
    private Long f4;

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
        
        .append("id", getId())
        
        .append("materialtype", getMaterialtype())
        
        .append("materialcode", getMaterialcode())
        
        .append("materialname", getMaterialname())
        
        .append("materialbatch", getMaterialbatch())
        
        .append("packagingcode", getPackagingcode())
        
        .append("materialmodel", getMaterialmodel())
        
        .append("drawingnumber", getDrawingnumber())
        
        .append("specification", getSpecification())
        
        .append("standard", getStandard())
        
        .append("furnacebatch", getFurnacebatch())
        
        .append("outfacterycode", getOutfacterycode())
        
        .append("quantity", getQuantity())
        
        .append("unit", getUnit())
        
        .append("isscan", getIsscan())
        
        .append("memo", getMemo())
        
        .append("suppliername", getSuppliername())
        
        .append("createuser", getCreateuser())
        
        .append("createdate", getCreatedate())
        
        .append("modifyuser", getModifyuser())
        
        .append("modifydate", getModifydate())
        
        .append("isimportant", getIsimportant())
        
        .append("isenable", getIsenable())
        
        .append("isdelete", getIsdelete())
        
        .append("f1", getF1())
        
        .append("f2", getF2())
        
        .append("f3", getF3())
        
        .append("f4", getF4())

        .append("suppliercode",getSuppliercode())

            .toString();
    }
}
