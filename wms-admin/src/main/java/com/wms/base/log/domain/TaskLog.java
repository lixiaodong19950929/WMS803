package com.wms.base.log.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.wms.common.annotation.Excel;
import com.wms.common.core.domain.BaseEntity;
import java.util.Date;

/**
 * 任务日志对象 T_Task_Log
 * 
 * @author assassin
 * @date 2021-06-07
 */
public class TaskLog extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private Long id;

    /** 任务中的行号 */
    private Long rowid;

    /** 任务类型 */
    @Excel(name = "任务类型")
    private String tasktype;

    /** 任务号 */
    private String taskcode;

    /** 条码 */
    @Excel(name = "条码")
    private String sern;

    /** 托盘号 */
    @Excel(name = "托盘号")
    private String traycode;

    /** 堆垛机号 */
    @Excel(name = "堆垛机号")
    private String deviceno;

    /** RGV小车号 */
    @Excel(name = "RGV小车号")
    private String rgvno;

    /** 起始库位 */
    @Excel(name = "起始库位")
    private String startpoint;

    /** 目标库位 */
    @Excel(name = "目标库位")
    private String endpoint;

    /** 任务状态 */
    @Excel(name = "任务状态")
    private String taskstate;

    /** 备注 */
    @Excel(name = "备注")
    private String memo;

    /** 创建人 */
    @Excel(name = "创建人")
    private String createuser;

    /** 创建日期 */
    @Excel(name = "创建日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date createdate;

    /** 开始时间 */
    @Excel(name = "开始时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date startdate;

    /** 结束时间 */
    @Excel(name = "结束时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date finishdate;

    /** 开始起始时间 */
    @Excel(name = "开始时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date startdateStart;

    /** 结束起始时间 */
    @Excel(name = "结束时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date finishdateStart;

    /** 开始结束时间 */
    @Excel(name = "开始时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date startdateEnd;

    /** 结束结束时间 */
    @Excel(name = "结束时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date finishdateEnd;

    /** 备用1 */
    @Excel(name = "备用1")
    private String f1;

    /** 备用2 */
    @Excel(name = "备用2")
    private String f2;

    /** 备用3 */
    @Excel(name = "备用3")
    private Long f3;

    /** 是否删除(默认0，1为已删除) */
    @Excel(name = "是否删除(默认0，1为已删除)")
    private Long isdelete;

    /** 堆垛机执行的任务号(自增) */
    @Excel(name = "堆垛机执行的任务号(自增)")
    private Long taskid;

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
        public void setTasktype(String tasktype)
    {
        this.tasktype = tasktype;
    }

    public String getTasktype() 
    {
        return tasktype;
    }
        public void setTaskcode(String taskcode)
    {
        this.taskcode = taskcode;
    }

    public String getTaskcode() 
    {
        return taskcode;
    }
        public void setSern(String sern)
    {
        this.sern = sern;
    }

    public String getSern() 
    {
        return sern;
    }
        public void setTraycode(String traycode)
    {
        this.traycode = traycode;
    }

    public String getTraycode() 
    {
        return traycode;
    }
        public void setDeviceno(String deviceno)
    {
        this.deviceno = deviceno;
    }

    public String getDeviceno() 
    {
        return deviceno;
    }
        public void setRgvno(String rgvno)
    {
        this.rgvno = rgvno;
    }

    public String getRgvno() 
    {
        return rgvno;
    }
        public void setStartpoint(String startpoint)
    {
        this.startpoint = startpoint;
    }

    public String getStartpoint() 
    {
        return startpoint;
    }
        public void setEndpoint(String endpoint)
    {
        this.endpoint = endpoint;
    }

    public String getEndpoint() 
    {
        return endpoint;
    }
        public void setTaskstate(String taskstate)
    {
        this.taskstate = taskstate;
    }

    public String getTaskstate() 
    {
        return taskstate;
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
        public void setStartdate(Date startdate)
    {
        this.startdate = startdate;
    }

    public Date getStartdate() 
    {
        return startdate;
    }
        public void setFinishdate(Date finishdate)
    {
        this.finishdate = finishdate;
    }

    public Date getFinishdate() 
    {
        return finishdate;
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
        public void setF3(Long f3)
    {
        this.f3 = f3;
    }

    public Long getF3() 
    {
        return f3;
    }
        public void setIsdelete(Long isdelete)
    {
        this.isdelete = isdelete;
    }

    public Long getIsdelete() 
    {
        return isdelete;
    }
        public void setTaskid(Long taskid)
    {
        this.taskid = taskid;
    }

    public Long getTaskid() 
    {
        return taskid;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
        
        .append("id", getId())
        
        .append("rowid", getRowid())
        
        .append("tasktype", getTasktype())
        
        .append("taskcode", getTaskcode())
        
        .append("sern", getSern())
        
        .append("traycode", getTraycode())
        
        .append("deviceno", getDeviceno())
        
        .append("rgvno", getRgvno())
        
        .append("startpoint", getStartpoint())
        
        .append("endpoint", getEndpoint())
        
        .append("taskstate", getTaskstate())
        
        .append("memo", getMemo())
        
        .append("createuser", getCreateuser())
        
        .append("createdate", getCreatedate())
        
        .append("startdate", getStartdate())
        
        .append("finishdate", getFinishdate())
        
        .append("f1", getF1())
        
        .append("f2", getF2())
        
        .append("f3", getF3())
        
        .append("isdelete", getIsdelete())
        
        .append("taskid", getTaskid())
            .toString();
    }
}
