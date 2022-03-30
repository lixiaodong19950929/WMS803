package com.wms.warehouse.storecheck.domain;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.wms.common.annotation.Excel;
import com.wms.common.core.domain.BaseEntity;
import java.util.Date;
import java.util.List;

/**
 * 盘点主对象 T_Base_StoreCheck
 * 
 * @author assassin
 * @date 2020-02-10
 */
@Getter
@Setter
public class StoreCheck extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 自增主键 */
    @Excel(name = "自增主键")
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

    /** 制单人 */
    @Excel(name = "制单人")
    private String maker;

    /** 仓库编码 */
    @Excel(name = "仓库编码")
    private String whcode;

    /** 总数量 */
    @Excel(name = "总数量")
    private Long quantity;

    /** 任务状态 */
    @Excel(name = "任务状态")
    private String taskstate;

    /** 备注 */
    @Excel(name = "备注")
    private String note;

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

    private Long maxRowId;

    private List<StoreCheckSon> storeCheckSonList;

    public StoreCheck() {
        super();
    }


    public StoreCheck(String tasktype, String taskcode, String taskname, String productdetail, String maker, String whcode, Long quantity, String note, String createuser, Date createdate) {
        this.tasktype = tasktype;
        this.taskcode = taskcode;
        this.taskname = taskname;
        this.productdetail = productdetail;
        this.maker = maker;
        this.whcode = whcode;
        this.quantity = quantity;
        this.note = note;
        this.createuser = createuser;
        this.createdate = createdate;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
        
        .append("id", getId())
        
        .append("tasktype", getTasktype())
        
        .append("taskcode", getTaskcode())
        
        .append("taskname", getTaskname())
        
        .append("productdetail", getProductdetail())
        
        .append("maker", getMaker())
        
        .append("whcode", getWhcode())
        
        .append("quantity", getQuantity())
        
        .append("taskstate", getTaskstate())
        
        .append("note", getNote())
        
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
