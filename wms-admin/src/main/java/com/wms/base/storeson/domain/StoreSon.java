package com.wms.base.storeson.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.wms.common.annotation.Excel;
import com.wms.common.core.domain.BaseEntity;
import java.util.Date;

/**
 * 出库物料明细对象 T_Base_StoreSon
 * 
 * @author assassin
 * @date 2021-08-25
 */
public class StoreSon extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    @Excel(name = "主键")
    private Long id;

    /** 行号 */
    @Excel(name = "行号")
    private Long rowid;

    /** 任务号 */
    @Excel(name = "任务号")
    private String taskcode;

    /** 库位 */
    @Excel(name = "库位")
    private String storehousecode;

    /** 物料编号 */
    @Excel(name = "物料编号")
    private String productcode;

    /** 物料名称 */
    @Excel(name = "物料名称")
    private String productname;

    /** 物料数量 */
    @Excel(name = "物料数量")
    private Long quantity;

    /** $column.columnComment */
    @Excel(name = "物料数量")
    private Long actualquantity;

    /** 任务状态 */
    @Excel(name = "任务状态")
    private String taskstate;

    /** 创建时间 */
    @Excel(name = "创建时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date createdate;

    /** RGV小车号 */
    @Excel(name = "RGV小车号")
    private String rgvdevicecode;

    /** 堆垛机任务号 */
    @Excel(name = "堆垛机任务号")
    private String devicecode;

    /** 托盘号 */
    @Excel(name = "托盘号")
    private String traycode;

    /** 备用字段 */
    @Excel(name = "备用字段")
    private String f1;

    /** 备用字段 */
    @Excel(name = "备用字段")
    private String f2;

    /** 备用字段 */
    @Excel(name = "备用字段")
    private String f3;

    /** 备用字段 */
    @Excel(name = "备用字段")
    private String f4;

        public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
        public void setRowid(Long rowid)
    {
        this.rowid = rowid;
    }

    public Long getRowid() 
    {
        return rowid;
    }
        public void setTaskcode(String taskcode)
    {
        this.taskcode = taskcode;
    }

    public String getTaskcode() 
    {
        return taskcode;
    }
        public void setStorehousecode(String storehousecode)
    {
        this.storehousecode = storehousecode;
    }

    public String getStorehousecode() 
    {
        return storehousecode;
    }
        public void setProductcode(String productcode)
    {
        this.productcode = productcode;
    }

    public String getProductcode() 
    {
        return productcode;
    }
        public void setProductname(String productname)
    {
        this.productname = productname;
    }

    public String getProductname() 
    {
        return productname;
    }
        public void setQuantity(Long quantity)
    {
        this.quantity = quantity;
    }

    public Long getQuantity() 
    {
        return quantity;
    }
        public void setActualquantity(Long actualquantity)
    {
        this.actualquantity = actualquantity;
    }

    public Long getActualquantity() 
    {
        return actualquantity;
    }
        public void setTaskstate(String taskstate)
    {
        this.taskstate = taskstate;
    }

    public String getTaskstate() 
    {
        return taskstate;
    }
        public void setCreatedate(Date createdate)
    {
        this.createdate = createdate;
    }

    public Date getCreatedate() 
    {
        return createdate;
    }
    public void setTraycode(String traycode)
    {
        this.traycode = traycode;
    }

    public String getTraycode()
    {
        return traycode;
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
        public void setF3(String f3)
    {
        this.f3 = f3;
    }

    public String getF3() 
    {
        return f3;
    }
        public void setF4(String f4)
    {
        this.f4 = f4;
    }

    public String getF4() 
    {
        return f4;
    }

    public String getRgvdevicecode() {
        return rgvdevicecode;
    }

    public void setRgvdevicecode(String rgvdevicecode) {
        this.rgvdevicecode = rgvdevicecode;
    }

    public String getDevicecode() {
        return devicecode;
    }

    public void setDevicecode(String devicecode) {
        this.devicecode = devicecode;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
        
        .append("id", getId())
        
        .append("rowid", getRowid())
        
        .append("taskcode", getTaskcode())
        
        .append("storehousecode", getStorehousecode())
        
        .append("productcode", getProductcode())
        
        .append("productname", getProductname())
        
        .append("quantity", getQuantity())
        
        .append("actualquantity", getActualquantity())
        
        .append("taskstate", getTaskstate())
        
        .append("createdate", getCreatedate())

        .append("devicecode",getDevicecode())

        .append("rgvdevicecode",getRgvdevicecode())

        .append("traycode", getTraycode())
        
        .append("f1", getF1())
        
        .append("f2", getF2())
        
        .append("f3", getF3())
        
        .append("f4", getF4())
            .toString();
    }
}
