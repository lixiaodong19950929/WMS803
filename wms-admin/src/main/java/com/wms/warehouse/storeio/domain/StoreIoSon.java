package com.wms.warehouse.storeio.domain;

import com.wms.common.annotation.Excel;
import com.wms.common.core.domain.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import java.util.Date;

/**
 * 出入库子（1.子可以由主拆分而来，还可以来自上游）对象 T_Base_StoreIoSon
 * 
 * @author assassin
 * @date 2019-12-30
 */
@Getter
@Setter
public class StoreIoSon extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 任务单子表-唯一标识 */
    @Excel(name = "任务单子表-唯一标识")
    private Long id;

    /** 任务单的行号 */
    private Long rowid;

    /** 任务号 */
    private String taskcode;

    /** 产品编码 */
    @Excel(name = "产品编码")
    private String productcode;

    /** 产品名称 */
    @Excel(name = "产品名称")
    private String productname;

    /** 产品型号 */
    @Excel(name = "产品型号")
    private String productmodel;

    /** 产品批次 */
    @Excel(name = "产品批次")
    private String productbatch;

    /** 数量 */
    @Excel(name = "数量")
    private Long quantity;

    /** 任务状态 */
    @Excel(name = "任务状态")
    private String taskstate;

    /** 备注 */
    @Excel(name = "备注")
    private String memo;

    /** 创建人 */
    @Excel(name = "创建人")
    private String createuser;

    /** 创建时间 */
    @Excel(name = "创建时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date createdate;

    /** 唯一标识 */
    @Excel(name = "唯一标识")
    private String guid;

    /** 备用 */
    @Excel(name = "备用")
    private String f1;

    /** 备用 */
    @Excel(name = "备用")
    private String f2;

    /** 备用 */
    @Excel(name = "备用")
    private String f3;

    /** 备用 */
    @Excel(name = "备用")
    private Long f4;

    /** 库存数量 */
    @Excel(name = "库存数量")
    private Long quantityInfo;

    /** 1:启用，0:停用 */
    @Excel(name = "1:启用，0:停用")
    private Long isenable;

    /** 1:删除，0:正常 */
    @Excel(name = "1:删除，0:正常")
    private Long isdelete;

    /***起始库位*/
    private String startcode;

    private String StorehouseName;
    private String otherProductCode;

    public StoreIoSon() {
        super();
    }

    public StoreIoSon(Long rowid, String taskcode, String productcode, String productname, String productmodel, Long quantity, String memo, String createuser, Date createdate) {
        this.rowid = rowid;
        this.taskcode = taskcode;
        this.productcode = productcode;
        this.productname = productname;
        this.productmodel = productmodel;
        this.quantity = quantity;
        this.memo = memo;
        this.createuser = createuser;
        this.createdate = createdate;
    }

    public StoreIoSon(Long rowid, String taskcode, String productcode, String productname, String productmodel, Long quantity, String memo, String createuser, Date createdate, String productbatch) {
        this.rowid = rowid;
        this.taskcode = taskcode;
        this.productcode = productcode;
        this.productname = productname;
        this.productmodel = productmodel;
        this.quantity = quantity;
        this.memo = memo;
        this.createuser = createuser;
        this.createdate = createdate;
        this.productbatch = productbatch;
    }

    //针对新的入库模板的构造函数
    public StoreIoSon(Long rowid, String taskcode, Long quantity, String createuser, Date createdate) {
        this.rowid = rowid;
        this.taskcode = taskcode;
        this.quantity = quantity;
        this.createuser = createuser;
        this.createdate = createdate;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
        
        .append("id", getId())
        
        .append("rowid", getRowid())
        
        .append("taskcode", getTaskcode())
        
        .append("productcode", getProductcode())
        
        .append("productname", getProductname())
        
        .append("productmodel", getProductmodel())

        .append("productbatch", getProductbatch())
        
        .append("quantity", getQuantity())
        
        .append("taskstate", getTaskstate())
        
        .append("memo", getMemo())
        
        .append("createuser", getCreateuser())
        
        .append("createdate", getCreatedate())
        
        .append("guid", getGuid())
        
        .append("f1", getF1())
        
        .append("f2", getF2())
        
        .append("f3", getF3())
        
        .append("f4", getF4())
            .toString();
    }
}
