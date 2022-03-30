package com.wms.base.customer.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.wms.common.annotation.Excel;
import com.wms.common.core.domain.BaseEntity;
import java.util.Date;

/**
 * 客户对象 T_Base_Customer
 * 
 * @author assassin
 * @date 2021-09-06
 */
public class Customer extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 自增列 */
    @Excel(name = "自增列")
    private Long id;

    /** 客户编码 */
    @Excel(name = "客户编码")
    private String customercode;

    /** 客户名称 */
    @Excel(name = "客户名称")
    private String customername;

    /** 客户简称 */
    @Excel(name = "客户简称")
    private String cusabbreviation;

    /** 客户地址 */
    @Excel(name = "客户地址")
    private String cusaddress;

    /** 客户座机电话 */
    @Excel(name = "客户座机电话")
    private String cuslandline;

    /** 联系人 */
    @Excel(name = "联系人")
    private String customercontact;

    /** 性别 */
    @Excel(name = "性别")
    private String supsex;

    /** 身份证号 */
    @Excel(name = "身份证号")
    private String supidcard;

    /** 联系人电话 */
    @Excel(name = "联系人电话")
    private String custmertacttel;

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
        public void setCustomercode(String customercode)
    {
        this.customercode = customercode;
    }

    public String getCustomercode() 
    {
        return customercode;
    }
        public void setCustomername(String customername)
    {
        this.customername = customername;
    }

    public String getCustomername() 
    {
        return customername;
    }
        public void setCusabbreviation(String cusabbreviation)
    {
        this.cusabbreviation = cusabbreviation;
    }

    public String getCusabbreviation() 
    {
        return cusabbreviation;
    }
        public void setCusaddress(String cusaddress)
    {
        this.cusaddress = cusaddress;
    }

    public String getCusaddress() 
    {
        return cusaddress;
    }
        public void setCuslandline(String cuslandline)
    {
        this.cuslandline = cuslandline;
    }

    public String getCuslandline() 
    {
        return cuslandline;
    }
        public void setCustomercontact(String customercontact)
    {
        this.customercontact = customercontact;
    }

    public String getCustomercontact() 
    {
        return customercontact;
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
        public void setCustmertacttel(String custmertacttel)
    {
        this.custmertacttel = custmertacttel;
    }

    public String getCustmertacttel() 
    {
        return custmertacttel;
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
        
        .append("customercode", getCustomercode())
        
        .append("customername", getCustomername())
        
        .append("cusabbreviation", getCusabbreviation())
        
        .append("cusaddress", getCusaddress())
        
        .append("cuslandline", getCuslandline())
        
        .append("customercontact", getCustomercontact())
        
        .append("supsex", getSupsex())
        
        .append("supidcard", getSupidcard())
        
        .append("custmertacttel", getCustmertacttel())
        
        .append("memo", getMemo())
        
        .append("createuser", getCreateuser())
        
        .append("createdate", getCreatedate())
        
        .append("f1", getF1())
        
        .append("f2", getF2())
            .toString();
    }
}
