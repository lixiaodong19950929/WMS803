package com.wms.base.running.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.wms.common.annotation.Excel;
import com.wms.common.core.domain.BaseEntity;

/**
 * 任务执行对象 T_TaskRunning
 * 
 * @author assassin
 * @date 2021-09-16
 */
public class Running extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 单据子表的Id */
    @Excel(name = "单据子表的Id")
    private Long id;

    /** 唯一标识 */
    @Excel(name = "唯一标识")
    private String guid;

    /** 单据子表的行号 */
    @Excel(name = "单据子表的行号")
    private Long rowid;

    /** 任务类型 */
    @Excel(name = "任务类型")
    private String tasktype;

    /** 任务代号 */
    @Excel(name = "任务代号")
    private String taskcode;

    /** 产品代号 */
    @Excel(name = "产品代号")
    private String productcode;

    /** 起始库位 */
    @Excel(name = "起始库位")
    private String storehousecode;

    /** 起始库位状态 */
    @Excel(name = "起始库位状态")
    private String storehousestate;

    /** 托盘号 */
    @Excel(name = "托盘号")
    private String traycode;

    /** 堆垛机设备号 */
    @Excel(name = "堆垛机设备号")
    private String devicecode;

    /** RGV小车号 */
    @Excel(name = "RGV小车号")
    private String rgvdevicecode;

    /** 是否转轨 */
    @Excel(name = "是否转轨")
    private Long iszg;

    /** 总步序 */
    @Excel(name = "总步序")
    private Long totalstep;

    /** 当前步序 */
    @Excel(name = "当前步序")
    private Long step;

    /** 堆垛机设备名 */
    @Excel(name = "堆垛机设备名")
    private String devicename;

    /** 条码 */
    @Excel(name = "条码")
    private String sern;

    /** 任务状态 */
    @Excel(name = "任务状态")
    private String taskstate;

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }

    @Excel(name = "闯将时间")
    private String createtime;

    /** 目的位 */
    @Excel(name = "目的位")
    private String endcode;

    /** 目的位库位状态 */
    @Excel(name = "目的位库位状态")
    private String endstorehousestate;

    /** $column.columnComment */
    @Excel(name = "目的位库位状态")
    private Long isdelete;

        public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
        public void setGuid(String guid)
    {
        this.guid = guid;
    }

    public String getGuid() 
    {
        return guid;
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
        public void setProductcode(String productcode)
    {
        this.productcode = productcode;
    }

    public String getProductcode() 
    {
        return productcode;
    }
        public void setStorehousecode(String storehousecode)
    {
        this.storehousecode = storehousecode;
    }

    public String getStorehousecode() 
    {
        return storehousecode;
    }
        public void setStorehousestate(String storehousestate)
    {
        this.storehousestate = storehousestate;
    }

    public String getStorehousestate() 
    {
        return storehousestate;
    }
        public void setTraycode(String traycode)
    {
        this.traycode = traycode;
    }

    public String getTraycode() 
    {
        return traycode;
    }
        public void setDevicecode(String devicecode)
    {
        this.devicecode = devicecode;
    }

    public String getDevicecode() 
    {
        return devicecode;
    }
        public void setRgvdevicecode(String rgvdevicecode)
    {
        this.rgvdevicecode = rgvdevicecode;
    }

    public String getRgvdevicecode() 
    {
        return rgvdevicecode;
    }
        public void setIszg(Long iszg)
    {
        this.iszg = iszg;
    }

    public Long getIszg() 
    {
        return iszg;
    }
        public void setTotalstep(Long totalstep)
    {
        this.totalstep = totalstep;
    }

    public Long getTotalstep() 
    {
        return totalstep;
    }
        public void setStep(Long step)
    {
        this.step = step;
    }

    public Long getStep() 
    {
        return step;
    }
        public void setDevicename(String devicename)
    {
        this.devicename = devicename;
    }

    public String getDevicename() 
    {
        return devicename;
    }
        public void setSern(String sern)
    {
        this.sern = sern;
    }

    public String getSern() 
    {
        return sern;
    }
        public void setTaskstate(String taskstate)
    {
        this.taskstate = taskstate;
    }

    public String getTaskstate() 
    {
        return taskstate;
    }
        public void setEndcode(String endcode)
    {
        this.endcode = endcode;
    }

    public String getEndcode() 
    {
        return endcode;
    }
        public void setEndstorehousestate(String endstorehousestate)
    {
        this.endstorehousestate = endstorehousestate;
    }

    public String getEndstorehousestate() 
    {
        return endstorehousestate;
    }
        public void setIsdelete(Long isdelete)
    {
        this.isdelete = isdelete;
    }

    public Long getIsdelete() 
    {
        return isdelete;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
        
        .append("id", getId())
        
        .append("guid", getGuid())
        
        .append("rowid", getRowid())
        
        .append("tasktype", getTasktype())
        
        .append("taskcode", getTaskcode())
        
        .append("productcode", getProductcode())
        
        .append("storehousecode", getStorehousecode())
        
        .append("storehousestate", getStorehousestate())
        
        .append("traycode", getTraycode())
        
        .append("devicecode", getDevicecode())
        
        .append("rgvdevicecode", getRgvdevicecode())
        
        .append("iszg", getIszg())
        
        .append("totalstep", getTotalstep())
        
        .append("step", getStep())
        
        .append("devicename", getDevicename())
        
        .append("sern", getSern())
        
        .append("taskstate", getTaskstate())
        
        .append("createtime", getCreatetime())
        
        .append("endcode", getEndcode())
        
        .append("endstorehousestate", getEndstorehousestate())
        
        .append("isdelete", getIsdelete())
            .toString();
    }
}
