package com.wms.warehouse.storeio.domain;

import com.wms.common.annotation.Excel;
import com.wms.common.core.domain.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.xml.ws.Endpoint;
import java.util.Date;

/**
 * 堆垛机任务对象 T_Task_Stacker
 * 
 * @author assassin
 * @date 2020-01-03
 */
@Getter
@Setter
public class Stacker extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 自增列 */
    @Excel(name = "自增列")
    private Long id;

    /** 条码表的Guid */
    @Excel(name = "条码表的Guid")
    private String guid;

    /** 任务单的行号 */
    @Excel(name = "任务单的行号")
    private Long rowid;

    /** 任务类型(入库、出库) */
    @Excel(name = "任务类型(入库、出库)")
    private String tasktype;

    /** 任务号 */
    @Excel(name = "任务号")
    private String taskcode;

    /** 任务名称 */
    @Excel(name = "任务名称")
    private String taskname;

    /** 产品条码 */
    @Excel(name = "产品条码")
    private String sern;

    /** 托盘号 */
    @Excel(name = "托盘号")
    private String traycode;

    /** 堆垛机小车号 */
    @Excel(name = "堆垛机小车号")
    private String deviceno;

    /** 起始点 */
    @Excel(name = "起始点")
    private String startpoint;

    /** 终点 */
    @Excel(name = "终点")
    private String endpoint;

    /** 任务状态（未执行、执行中、已完成） */
    @Excel(name = "任务状态", readConverterExp = "未=执行、执行中、已完成")
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

    /** 修改人 */
    @Excel(name = "修改人")
    private String modifyuser;

    /** 修改时间 */
    @Excel(name = "修改时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date modifydate;

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

    private String keywords;

    private String startTime;

    private String endTime;


    /** 数量 */
    private  long count;

    public Stacker() {
        super();
    }

    public Stacker(String taskstate) {
        this.taskstate = taskstate;
    }

    public Stacker( Long rowid, String tasktype, String taskcode,String taskstate, String createuser, Date createdate) {
        this.rowid = rowid;
        this.tasktype = tasktype;
        this.taskcode = taskcode;
        this.taskstate = taskstate;
        this.createuser = createuser;
        this.createdate = createdate;
    }

    public Stacker(Long rowid, String tasktype, String taskcode, String Endpoint,String taskstate, String createuser, Date createdate) {
        this.rowid = rowid;
        this.tasktype = tasktype;
        this.taskcode = taskcode;
        this.taskstate = taskstate;
        this.endpoint=Endpoint;
        this.createuser = createuser;
        this.createdate = createdate;
    }

    public Stacker(Long rowid, String taskcode, String modifyuser, Date modifydate, long count) {
        this.rowid = rowid;
        this.taskcode = taskcode;
        this.modifyuser = modifyuser;
        this.modifydate = modifydate;
        this.count=count;
    }



    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
        
        .append("id", getId())
        
        .append("guid", getGuid())
        
        .append("rowid", getRowid())
        
        .append("tasktype", getTasktype())
        
        .append("taskcode", getTaskcode())
        
        .append("sern", getSern())
        
        .append("traycode", getTraycode())
        
        .append("deviceno", getDeviceno())
        
        .append("startpoint", getStartpoint())
        
        .append("endpoint", getEndpoint())
        
        .append("taskstate", getTaskstate())
        
        .append("memo", getMemo())
        
        .append("createuser", getCreateuser())
        
        .append("createdate", getCreatedate())
        
        .append("modifyuser", getModifyuser())
        
        .append("modifydate", getModifydate())
        
        .append("f1", getF1())
        
        .append("f2", getF2())
        
        .append("f3", getF3())
        
        .append("f4", getF4())
            .toString();
    }
}
