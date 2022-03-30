package com.wms.base.supplier.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.wms.common.annotation.Excel;
import com.wms.common.core.domain.BaseEntity;
import java.util.Date;

/**
 * 供应商对象 T_Base_Supplier
 * 
 * @author assassin
 * @date 2021-09-06
 */
public class Supplier extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 自增列 */
    @Excel(name = "自增列")
    private Long id;

    /** 供应商编码 */
    @Excel(name = "供应商编码")
    private String suppliercode;

    /** 供应商名称 */
    @Excel(name = "供应商名称")
    private String suppliername;

    /** 供应商简称 */
    @Excel(name = "供应商简称")
    private String supabbreviation;

    /** 供应商地址 */
    @Excel(name = "供应商地址")
    private String supaddress;

    /** 供应商座机电话 */
    @Excel(name = "供应商座机电话")
    private String suplandline;

    /** 联系人 */
    @Excel(name = "联系人")
    private String supcontact;

    /** 性别 */
    @Excel(name = "性别")
    private String supsex;

    /** 身份证号 */
    @Excel(name = "身份证号")
    private String supidcard;

    /** 联系人电话 */
    @Excel(name = "联系人电话")
    private String contacttel;

    /** 备注 */
    @Excel(name = "备注")
    private String memo;

    /** 创建人 */
    @Excel(name = "创建人")
    private String createuser;

    /** 创建时间 */
    @Excel(name = "创建时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date createdate;

    /** 备用字段1 */
    @Excel(name = "备用字段1")
    private String f1;

    /** 备用字段2 */
    @Excel(name = "备用字段2")
    private String f2;

        public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
        public void setSuppliercode(String suppliercode)
    {
        this.suppliercode = suppliercode;
    }

    public String getSuppliercode() 
    {
        return suppliercode;
    }
        public void setSuppliername(String suppliername)
    {
        this.suppliername = suppliername;
    }

    public String getSuppliername() 
    {
        return suppliername;
    }
        public void setSupabbreviation(String supabbreviation)
    {
        this.supabbreviation = supabbreviation;
    }

    public String getSupabbreviation() 
    {
        return supabbreviation;
    }
        public void setSupaddress(String supaddress)
    {
        this.supaddress = supaddress;
    }

    public String getSupaddress() 
    {
        return supaddress;
    }
        public void setSuplandline(String suplandline)
    {
        this.suplandline = suplandline;
    }

    public String getSuplandline() 
    {
        return suplandline;
    }
        public void setSupcontact(String supcontact)
    {
        this.supcontact = supcontact;
    }

    public String getSupcontact() 
    {
        return supcontact;
    }
        public void setSupsex(String supsex)
    {
        this.supsex = supsex;
    }

    public String getSupsex() 
    {
        return supsex;
    }
        public void setSupidcard(String supidcard)
    {
        this.supidcard = supidcard;
    }

    public String getSupidcard() 
    {
        return supidcard;
    }
        public void setContacttel(String contacttel)
    {
        this.contacttel = contacttel;
    }

    public String getContacttel() 
    {
        return contacttel;
    }
        public void setMemo(String memo)
    {
        this.memo = memo;
    }

    public String getMemo() 
    {
        return memo;
    }
        public void setCreateuser(String createuser)
    {
        this.createuser = createuser;
    }

    public String getCreateuser() 
    {
        return createuser;
    }
        public void setCreatedate(Date createdate)
    {
        this.createdate = createdate;
    }

    public Date getCreatedate() 
    {
        return createdate;
    }
        public void setF1(String f1)
    {
        this.f1 = f1;
    }

    public String getF1() 
    {
        return f1;
    }
        public void setF2(String f2)
    {
        this.f2 = f2;
    }

    public String getF2() 
    {
        return f2;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
        
        .append("id", getId())
        
        .append("suppliercode", getSuppliercode())
        
        .append("suppliername", getSuppliername())
        
        .append("supabbreviation", getSupabbreviation())
        
        .append("supaddress", getSupaddress())
        
        .append("suplandline", getSuplandline())
        
        .append("supcontact", getSupcontact())
        
        .append("supsex", getSupsex())
        
        .append("supidcard", getSupidcard())
        
        .append("contacttel", getContacttel())
        
        .append("memo", getMemo())
        
        .append("createuser", getCreateuser())
        
        .append("createdate", getCreatedate())
        
        .append("f1", getF1())
        
        .append("f2", getF2())
            .toString();
    }
}
