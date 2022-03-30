package com.wms.base.product.domain;

import com.wms.common.annotation.Excel;
import com.wms.common.core.domain.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Date;

/**
 * 产品档案对象 T_Base_Product
 * 
 * @author assassin
 * @date 2020-01-02
 */
@Getter
@Setter
public class Product extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 自增列 */
    private Long id;

    /** 产品编码 */
    @Excel(name = "产品编码")
    private String productcode;

    /** 产品名称 */
    @Excel(name = "产品名称")
    private String productname;

    /** 产品类型 */
    private String producttype;
    @Excel(name = "产品类型")
    private String producttypeName;

    /** 产品批次 */
    @Excel(name = "产品批次")
    private String productbatch;

    /** 产品型号 */
    private String productmodel;
    @Excel(name = "产品型号")
    private String productmodelName;

    /** 零件图号 */
    @Excel(name = "零件图号")
    private String drawingnumber;

    /** 规格型号 */
    @Excel(name = "规格型号")
    private String specification;

    /** 单位(个、根、捆、条、包等) */
    private String unit;
    @Excel(name = "单位")
    private String unitName;

    /** 供应商 */
    @Excel(name = "供应商")
    private String suppliername;

    /** 供应商 */
    @Excel(name = "供应商code")
    private String suppliercode;

    /** 供应商 */
    @Excel(name = "有效期expiringDate")
    private String expiringDate;

    /** 备注 */
    @Excel(name = "备注")
    private String memo;

    /** 创建人 */
    private String createuser;

    /** 创建时间 */
//    @Excel(name = "创建时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date createdate;

    /** 修改人 */
//    @Excel(name = "修改人")
    private String modifyuser;

    /** 修改时间 */
//    @Excel(name = "修改时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date modifydate;

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

    private Long sums;

    private String projectNum;
    @Excel(name = "外部编码")
    private String productNum;

    public Product() {
        super();
    }

    public Product(String productcode, String productname,String productmodel, String productbatch) {
        this.productcode = productcode;
        this.productname = productname;
        this.productbatch = productbatch;
        this.productmodel = productmodel;
    }

    //针对新的入库模板的构造函数
//    public Product(String productcode, String productname, String productmodel) {
//        this.productcode = productcode;
//        this.productname = productname;
//        this.productmodel = productmodel;
//    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
        
        .append("id", getId())
        
        .append("producttype", getProducttype())
        
        .append("productcode", getProductcode())
        
        .append("productname", getProductname())
        
        .append("productbatch", getProductbatch())
        
        .append("productmodel", getProductmodel())
        
        .append("drawingnumber", getDrawingnumber())
        
        .append("specification", getSpecification())
        
        .append("unit", getUnit())
        
        .append("memo", getMemo())
        
        .append("suppliername", getSuppliername())
        
        .append("createuser", getCreateuser())
        
        .append("createdate", getCreatedate())
        
        .append("modifyuser", getModifyuser())
        
        .append("modifydate", getModifydate())
        
        .append("isenable", getIsenable())
        
        .append("isdelete", getIsdelete())
        
        .append("f1", getF1())
        
        .append("f2", getF2())
        
        .append("f3", getF3())
        
        .append("f4", getF4())

        .append("sums",getSums())

        .append("productNum",getProductNum())

        .append("suppliercode",getSuppliercode())

        .append("expiringDate",getExpiringDate())

            .toString();
    }
}
