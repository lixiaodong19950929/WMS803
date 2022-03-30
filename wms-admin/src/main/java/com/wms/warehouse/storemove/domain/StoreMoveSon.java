package com.wms.warehouse.storemove.domain;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.wms.common.annotation.Excel;
import com.wms.common.core.domain.BaseEntity;
import java.util.Date;

/**
 * 移库子对象 T_Base_StoreMoveSon
 * 
 * @author assassin
 * @date 2020-01-16
 */
@Getter
@Setter
public class StoreMoveSon extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private Long id;

    /** 任务单的行号 */
    private Long rowid;

    /** 任务号 */
    private String taskcode;

    /** 任务名称 */
    @Excel(name = "任务名称")
    private String taskname;

    /** 仓库编码 */
    @Excel(name = "仓库编码")
    private String whcode;

    /** 产品代号 */
    @Excel(name = "产品代号")
    private String productcode;

    /** 产品名称 */
    @Excel(name = "产品名称")
    private String productname;

    /** 产品型号 */
    @Excel(name = "产品型号")
    private String productmodel;

    /** 产品型号名称 */
    @Excel(name = "产品型号名称")
    private String productmodelname;

    /** 数量 */
    @Excel(name = "数量")
    private Long quantity;

    /** 起始库位 */
    @Excel(name = "起始库位")
    private String startcode;

    /** 目的库位 */
    @Excel(name = "目的库位")
    private String endcode;

    /** 完成状态 */
    @Excel(name = "完成状态")
    private String status;

    /** 备注 */
    @Excel(name = "备注")
    private String memo;

    /** 创建人 */
    @Excel(name = "创建人")
    private String createuser;

    /** 创建时间 */
    @Excel(name = "创建时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date createdate;

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

    /** 1:启用，0:停用 */
    @Excel(name = "1:启用，0:停用")
    private Long isenable;

    /** 1:删除，0:正常 */
    @Excel(name = "1:删除，0:正常")
    private Long isdelete;

    /** 库存数量 */
    @Excel(name = "库存数量")
    private Long quantityInfo;

    /** 目的库位 */
    @Excel(name = "目的库位")
    private String endname;

    /** 仓库编码 */
    @Excel(name = "仓库编码")
    private String whname;

    /** 区域编码 */
    private String regioncode;

    /** 区域名称 */
    private String regionname;

    /** 库位编码 */
    private String storehousecode;

    /** 库位名 */
    @Excel(name = "库位名")
    private String storehousename;

    private String otherProductCode;


    public StoreMoveSon() {
        super();
    }

    public StoreMoveSon(Long rowid, String taskcode, String taskname, String whcode,String startcode, String productcode, String productname, String productmodel, Long quantity, String endcode, String memo, String createuser, Date createdate, String endname, String whname) {
        this.rowid = rowid;
        this.taskcode = taskcode;
        this.taskname = taskname;
        this.whcode = whcode;
        this.startcode=startcode;
        this.productcode = productcode;
        this.productname = productname;
        this.productmodel = productmodel;
        this.quantity = quantity;
        this.endcode = endcode;
        this.memo = memo;
        this.createuser = createuser;
        this.createdate = createdate;
        this.endname = endname;
        this.whname = whname;
    }

    //针对新的移库导入模板
    public StoreMoveSon(Long rowid, String taskcode, String taskname, String whcode,String startcode, String productcode, String productname, String endcode, String createuser, Date createdate, String endname, String whname) {
        this.rowid = rowid;
        this.taskcode = taskcode;
        this.taskname = taskname;
        this.whcode = whcode;
        this.startcode=startcode;
        this.productcode = productcode;
        this.productname = productname;
        this.endcode = endcode;
        this.createuser = createuser;
        this.createdate = createdate;
        this.endname = endname;
        this.whname = whname;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
        
        .append("id", getId())
        
        .append("rowid", getRowid())
        
        .append("taskcode", getTaskcode())
        
        .append("taskname", getTaskname())
        
        .append("whcode", getWhcode())
        
        .append("productcode", getProductcode())
        
        .append("productname", getProductname())
        
        .append("productmodel", getProductmodel())
        
        .append("quantity", getQuantity())
        
        .append("startcode", getStartcode())
        
        .append("endcode", getEndcode())
        
        .append("status", getStatus())
        
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
