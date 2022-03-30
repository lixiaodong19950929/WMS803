package com.wms.base.staEmp.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.wms.common.annotation.Excel;
import com.wms.common.core.domain.BaseEntity;
import java.util.Date;

/**
 * 操作人员对象 T_Base_Sta_Emp
 * 
 * @author assassin
 * @date 2021-03-23
 */
public class StaEmp extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 自增长主键 */
    private Long id;

    /** 工位编码 */
    @Excel(name = "工位编码")
    private String stationcode;

    /** 岗位名称 */
    @Excel(name = "岗位名称")
    private String stationname;

    /** 员工编码 */
    @Excel(name = "员工编码")
    private String employeecode;

    /** 员工姓名 */
    @Excel(name = "员工姓名")
    private String employeename;

    /** 是否启用 */
    @Excel(name = "是否启用")
    private Long isenable;

    /** 备注 */
    @Excel(name = "备注")
    private String note;

    /** 创建人 */
    @Excel(name = "创建人")
    private String createuser;

    /** 创建时间 */
    @Excel(name = "创建时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date createdate;

    /** $column.columnComment */
    @Excel(name = "创建时间")
    private String linecode;

        public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
        public void setStationcode(String stationcode)
    {
        this.stationcode = stationcode;
    }

    public String getStationcode() 
    {
        return stationcode;
    }
        public void setStationname(String stationname)
    {
        this.stationname = stationname;
    }

    public String getStationname() 
    {
        return stationname;
    }
        public void setEmployeecode(String employeecode)
    {
        this.employeecode = employeecode;
    }

    public String getEmployeecode() 
    {
        return employeecode;
    }
        public void setEmployeename(String employeename)
    {
        this.employeename = employeename;
    }

    public String getEmployeename() 
    {
        return employeename;
    }
        public void setIsenable(Long isenable)
    {
        this.isenable = isenable;
    }

    public Long getIsenable() 
    {
        return isenable;
    }
        public void setNote(String note)
    {
        this.note = note;
    }

    public String getNote() 
    {
        return note;
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
        public void setLinecode(String linecode)
    {
        this.linecode = linecode;
    }

    public String getLinecode() 
    {
        return linecode;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
        
        .append("id", getId())
        
        .append("stationcode", getStationcode())
        
        .append("stationname", getStationname())
        
        .append("employeecode", getEmployeecode())
        
        .append("employeename", getEmployeename())
        
        .append("isenable", getIsenable())
        
        .append("note", getNote())
        
        .append("createuser", getCreateuser())
        
        .append("createdate", getCreatedate())
        
        .append("linecode", getLinecode())
            .toString();
    }
}
