package com.wms.warehouse.storecheck.domain;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.wms.common.annotation.Excel;
import com.wms.common.core.domain.BaseEntity;

import javax.validation.constraints.Size;
import java.util.Date;

/**
 * 盘点子对象 T_Base_StoreCheckSon
 * 
 * @author assassin
 * @date 2020-02-10
 */
@Setter
@Getter
public class StoreCheckSon extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private Long id;

    /** $column.columnComment */
    private Long rowid;

    /** $column.columnComment */
    private String taskcode;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String taskname;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String whcode;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String regioncode;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String storehousecode;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String productcode;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String productname;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String productmodel;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private Long quantity;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String pdresult;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String memo;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String createuser;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private Date createdate;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String f1;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String f2;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String f3;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private Long f4;

    /** 1:启用，0:停用 */
    @Excel(name = "1:启用，0:停用")
    private Long isenable;

    /** 1:删除，0:正常 */
    @Excel(name = "1:删除，0:正常")
    private Long isdelete;

    private Long quantityInfo;

    private String storehousename;
    private String otherProductCode;

    public StoreCheckSon() {
        super();
    }

    public StoreCheckSon(Long rowid, String taskcode, String taskname, String whcode, String regioncode, String storehousecode, String productcode, String productname, String productmodel, Long quantity, String memo, String createuser, Date createdate) {
        this.rowid = rowid;
        this.taskcode = taskcode;
        this.taskname = taskname;
        this.whcode = whcode;
        this.regioncode = regioncode;
        this.storehousecode = storehousecode;
        this.productcode = productcode;
        this.productname = productname;
        this.productmodel = productmodel;
        this.quantity = quantity;
        this.memo = memo;
        this.createuser = createuser;
        this.createdate = createdate;
    }

    //针对新的盘库导入模板
    public StoreCheckSon(Long rowid, String taskcode, String taskname, String whcode, String storehousecode, String productcode, String productname, Long quantity, String memo, String createuser, Date createdate) {
        this.rowid = rowid;
        this.taskcode = taskcode;
        this.taskname = taskname;
        this.whcode = whcode;
        this.regioncode = regioncode;
        this.storehousecode = storehousecode;
        this.productcode = productcode;
        this.productname = productname;
        this.productmodel = productmodel;
        this.quantity = quantity;
        this.memo = memo;
        this.createuser = createuser;
        this.createdate = createdate;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
        
        .append("id", getId())
        
        .append("rowid", getRowid())
        
        .append("taskcode", getTaskcode())
        
        .append("taskname", getTaskname())
        
        .append("whcode", getWhcode())
        
        .append("regioncode", getRegioncode())
        
        .append("storehousecode", getStorehousecode())
        
        .append("productcode", getProductcode())
        
        .append("productname", getProductname())
        
        .append("productmodel", getProductmodel())
        
        .append("quantity", getQuantity())
        
        .append("pdresult", getPdresult())
        
        .append("memo", getMemo())
        
        .append("createuser", getCreateuser())
        
        .append("createdate", getCreatedate())
        
        .append("f1", getF1())
        
        .append("f2", getF2())
        
        .append("f3", getF3())
        
        .append("f4", getF4())
        
        .append("isenable", getIsenable())
        
        .append("isdelete", getIsdelete())
            .toString();
    }
}
