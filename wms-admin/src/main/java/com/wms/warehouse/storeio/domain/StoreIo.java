package com.wms.warehouse.storeio.domain;

import com.wms.common.annotation.Excel;
import com.wms.common.core.domain.BaseEntity;
import com.wms.warehouse.sern.domain.Barcodelist;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Date;
import java.util.List;

/**
 * 出入库主对象 T_Base_StoreIo
 * 
 * @author assassin
 * @date 2019-12-30
 */
@Getter
@Setter
public class StoreIo extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 自增 */
    @Excel(name = "自增")
    private Long id;

    /** 任务类型 */
    private String tasktype;

    /** 任务号 */
    private String taskcode;

    /** 任务名称 */
    @Excel(name = "任务名称")
    private String taskname;

    /** 任务描述 */
    @Excel(name = "任务描述")
    private String productdetail;

    /** 供应商信息 */
    @Excel(name = "供应商信息")
    private String veninfo;

    /** 制单人 */
    @Excel(name = "制单人")
    private String maker;

    /** 生产订单编号 */
    @Excel(name = "生产订单编号")
    private String mpocode;

    /** 采购订单编号 */
    @Excel(name = "采购订单编号")
    private String ordercode;

    /** 仓库编码 */
    @Excel(name = "仓库编码")
    private String whcode;

    /** 仓库名称 */
    @Excel(name = "仓库名称")
    private String whname;

    /** 总数量 */
    @Excel(name = "总数量")
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

    @Excel(name = "入库方式")
    private String intype;

    @Excel(name = "出库方式")
    private Long outtype;

    @Excel(name = "出库规则")
    private Long outrule;

    @Excel(name = "客户名称")
    private String customername;

    @Excel(name = "客户编码")
    private String customercode;

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

    /** 0:默认，1:货架一层，2:其他层 */
    @Excel(name = "1:货架一层，2:其他层")
    private Long layer;

    private Long maxRowId;

    private List<StoreIoSon> storeIoSonList;

    private List<Barcodelist> barcodelists;

    public StoreIo() {
        super();
    }

    public StoreIo(String tasktype, String taskcode, String taskname, String productdetail, String veninfo, String maker, String mpocode, String ordercode, String whcode,Long quantity, String memo, String createuser, Date createdate,Long layer,Long outrule,Long outtype,String customername,String customercode ) {
        this.tasktype = tasktype;
        this.taskcode = taskcode;
        this.taskname = taskname;
        this.productdetail = productdetail;
        this.veninfo = veninfo;
        this.maker = maker;
        this.mpocode = mpocode;
        this.ordercode = ordercode;
        this.whcode = whcode;
        this.quantity=quantity;
        this.memo = memo;
        this.createuser = createuser;
        this.createdate = createdate;
        this.layer=layer;
        this.outrule=outrule;
        this.outtype=outtype;
        this.customername = customername;
        this.customercode = customercode;
    }

    //针对新的出库模板的构造函数
    public StoreIo(String tasktype, String taskcode, String taskname, String productdetail, String maker, String whcode,Long quantity, String memo, String createuser, Date createdate,String customername,String customercode ) {
        this.tasktype = tasktype;
        this.taskcode = taskcode;
        this.taskname = taskname;
        this.productdetail = productdetail;
        this.maker = maker;
        this.whcode = whcode;
        this.quantity=quantity;
        this.memo = memo;
        this.createuser = createuser;
        this.createdate = createdate;
        this.customername = customername;
        this.customercode = customercode;
    }

    //针对新的入库模板的构造函数
    public StoreIo(String tasktype, String taskcode, String taskname, String productdetail, String maker, String whcode,Long quantity, String memo, String createuser, Date createdate,String intype) {
        this.tasktype = tasktype;
        this.taskcode = taskcode;
        this.taskname = taskname;
        this.productdetail = productdetail;
        this.maker = maker;
        this.whcode = whcode;
        this.quantity=quantity;
        this.memo = memo;
        this.createuser = createuser;
        this.createdate = createdate;
        this.intype = intype;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
        
        .append("id", getId())
        
        .append("tasktype", getTasktype())
        
        .append("taskcode", getTaskcode())
        
        .append("taskname", getTaskname())
        
        .append("productdetail", getProductdetail())
        
        .append("veninfo", getVeninfo())
        
        .append("maker", getMaker())
        
        .append("mpocode", getMpocode())
        
        .append("ordercode", getOrdercode())
        
        .append("whcode", getWhcode())
        
        .append("quantity", getQuantity())
        
        .append("taskstate", getTaskstate())
        
        .append("note", getMemo())
        
        .append("createuser", getCreateuser())
        
        .append("createdate", getCreatedate())
        
        .append("f1", getF1())
        
        .append("f2", getF2())
        
        .append("f3", getF3())
        
        .append("f4", getF4())

        .append("layer", getLayer())

        .append("outrule",getOutrule())

        .append("intype",getIntype())

        .append("outtype",getOuttype())

        .append("customername",getCustomername())

        .append("customercode",getCustomercode())        

        .toString();
    }
}
