package com.wms.warehouse.sern.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.wms.common.annotation.Excel;
import com.wms.common.core.domain.BaseEntity;
import java.util.Date;
public class TrayCodeDetail extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /** 主键ID */
    @Excel(name = "主键ID")
    private Long id;

    /** 任务号 */
    @Excel(name = "任务号")
    private String taskcode;

    /** 库位名称 */
    @Excel(name = "库位名称")
    private String storehousename;

    /** 库位编码 */
    @Excel(name = "库位编码")
    private String storehousecode;

    /** 托盘 */
    @Excel(name = "托盘")
    private String traycode;

    /** 产品代号 */
    @Excel(name = "产品代号")
    private String productcode;

    /** 产品名称 */
    @Excel(name = "产品名称")
    private String productname;

    /** 产品批次 */
    @Excel(name = "产品批次")
    private String productbatch;

    /** 条码 */
    @Excel(name = "条码")
    private String sern;

    /** 创建时间 */
    @Excel(name = "创建时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date createdate;

    /** 箱号 */
    @Excel(name = "箱号")
    private String Xhao;

    /** 备用字段 */
    @Excel(name = "扫描批次")
    private String Batch;

    /** 备用字段 */
    @Excel(name = "备用字段")
    private String FullSern;

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

    /** 备用字段 */
//    @Excel(name = "备用字段")
//    private Long f4;

    private Long sums;


    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId()
    {
        return id;
    }
    public void setTaskcode(String taskcode)
    {
        this.taskcode = taskcode;
    }

    public String getTaskcode()
    {
        return taskcode;
    }
    public void setStorehousename(String storehousename)
    {
        this.storehousename = storehousename;
    }

    public String getStorehousename()
    {
        return storehousename;
    }
    public void setStorehousecode(String storehousecode)
    {
        this.storehousecode = storehousecode;
    }

    public String getStorehousecode()
    {
        return storehousecode;
    }
    public void setTraycode(String traycode)
    {
        this.traycode = traycode;
    }

    public String getTraycode()
    {
        return traycode;
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
    public void setProductbatch(String productbatch)
    {
        this.productbatch = productbatch;
    }

    public String getProductbatch()
    {
        return productbatch;
    }
    public void setSern(String sern)
    {
        this.sern = sern;
    }

    public String getSern()
    {
        return sern;
    }
    public void setCreatedate(Date createdate)
    {
        this.createdate = createdate;
    }

    public Date getCreatedate()
    {
        return createdate;
    }
    public void setXhao(String Xhao)
    {
        this.Xhao = Xhao;
    }

    public String getXhao()
    {
        return Xhao;
    }
    public void setBatch(String Batch)
    {
        this.Batch = Batch;
    }

    public String getBatch()
    {
        return Batch;
    }
    public void setFullSern(String FullSern)
    {
        this.FullSern = FullSern;
    }

    public String getFullSern()
    {
        return FullSern;
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
        this.f3 = f2;
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
        this.f3 = f4;
    }

    public String getF4()
    {
        return f4;
    }
    public void setSums(Long sums)
    {
        this.sums = sums;
    }

    public Long getSums()
    {
        return sums;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)

                .append("id", getId())

                .append("taskcode", getTaskcode())

                .append("storehousename", getStorehousename())

                .append("storehousecode", getStorehousecode())

                .append("traycode", getTraycode())

                .append("productcode", getProductcode())

                .append("productname", getProductname())

                .append("productbatch", getProductbatch())

                .append("sern", getSern())

                .append("createdate", getCreatedate())

                .append("Xhao", getXhao())

                .append("Batch", getBatch())

                .append("FullSern", getFullSern())

                .append("sums", getSums())
                .toString();
    }
}
