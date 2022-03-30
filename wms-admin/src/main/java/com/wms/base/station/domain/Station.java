package com.wms.base.station.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.wms.common.annotation.Excel;
import com.wms.common.core.domain.BaseEntity;
import java.util.Date;

/**
 * 岗位对象 T_Base_Station
 * 
 * @author assassin
 * @date 2021-03-23
 */
public class Station extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 自增长主键 */
    @Excel(name = "自增长主键")
    private Long id;

    /** 工位编码 */
    private String stationcode;

    /** 工段编码 */
    @Excel(name = "工段编码")
    private String sectioncode;

    /** 工段名称 */
    @Excel(name = "工段名称")
    private String sectionname;

    /** 工位名称 */
    @Excel(name = "工位名称")
    private String stationname;

    /** 是否启用 */
    @Excel(name = "是否启用")
    private Long isenable;

    /** 页面名称 */
    @Excel(name = "页面名称")
    private String pagename;

    /** 排序序号 */
    @Excel(name = "排序序号")
    private Long sortno;

    /** 是否有拍照 */
    @Excel(name = "是否有拍照")
    private Long isphoto;

    /** 固定IP */
    @Excel(name = "固定IP")
    private String localip;

    /** 当前登录IP */
    @Excel(name = "当前登录IP")
    private String localipcur;

    /** 当前IP登录次数 */
    @Excel(name = "当前IP登录次数")
    private Long localcount;

    /** 最后登录时间 */
    @Excel(name = "最后登录时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date lastlogiondate;

    /** 线体编码 */
    private String linecode;

    /** 线体名称 */
    @Excel(name = "线体名称")
    private String linename;

    /** 工具集 */
    @Excel(name = "工具集")
    private String toollist;

    /** 备注 */
    @Excel(name = "备注")
    private String note;

    /** 创建人 */
    @Excel(name = "创建人")
    private String createuser;

    /** 创建时间 */
    @Excel(name = "创建时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date createdate;

    /** 超级用户(用户编码) */
    @Excel(name = "超级用户(用户编码)")
    private String superuser;

    /** 超级用户密码(用于通过密码登录工位) */
    @Excel(name = "超级用户密码(用于通过密码登录工位)")
    private String superpsd;

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
        public void setSectioncode(String sectioncode)
    {
        this.sectioncode = sectioncode;
    }

    public String getSectioncode() 
    {
        return sectioncode;
    }
        public void setSectionname(String sectionname)
    {
        this.sectionname = sectionname;
    }

    public String getSectionname() 
    {
        return sectionname;
    }
        public void setStationname(String stationname)
    {
        this.stationname = stationname;
    }

    public String getStationname() 
    {
        return stationname;
    }
        public void setIsenable(Long isenable)
    {
        this.isenable = isenable;
    }

    public Long getIsenable() 
    {
        return isenable;
    }
        public void setPagename(String pagename)
    {
        this.pagename = pagename;
    }

    public String getPagename() 
    {
        return pagename;
    }
        public void setSortno(Long sortno)
    {
        this.sortno = sortno;
    }

    public Long getSortno() 
    {
        return sortno;
    }
        public void setIsphoto(Long isphoto)
    {
        this.isphoto = isphoto;
    }

    public Long getIsphoto() 
    {
        return isphoto;
    }
        public void setLocalip(String localip)
    {
        this.localip = localip;
    }

    public String getLocalip() 
    {
        return localip;
    }
        public void setLocalipcur(String localipcur)
    {
        this.localipcur = localipcur;
    }

    public String getLocalipcur() 
    {
        return localipcur;
    }
        public void setLocalcount(Long localcount)
    {
        this.localcount = localcount;
    }

    public Long getLocalcount() 
    {
        return localcount;
    }
        public void setLastlogiondate(Date lastlogiondate)
    {
        this.lastlogiondate = lastlogiondate;
    }

    public Date getLastlogiondate() 
    {
        return lastlogiondate;
    }
        public void setLinecode(String linecode)
    {
        this.linecode = linecode;
    }

    public String getLinecode() 
    {
        return linecode;
    }
        public void setLinename(String linename)
    {
        this.linename = linename;
    }

    public String getLinename() 
    {
        return linename;
    }
        public void setToollist(String toollist)
    {
        this.toollist = toollist;
    }

    public String getToollist() 
    {
        return toollist;
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
        public void setSuperuser(String superuser)
    {
        this.superuser = superuser;
    }

    public String getSuperuser() 
    {
        return superuser;
    }
        public void setSuperpsd(String superpsd)
    {
        this.superpsd = superpsd;
    }

    public String getSuperpsd() 
    {
        return superpsd;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
        
        .append("id", getId())
        
        .append("stationcode", getStationcode())
        
        .append("sectioncode", getSectioncode())
        
        .append("sectionname", getSectionname())
        
        .append("stationname", getStationname())
        
        .append("isenable", getIsenable())
        
        .append("pagename", getPagename())
        
        .append("sortno", getSortno())
        
        .append("isphoto", getIsphoto())
        
        .append("localip", getLocalip())
        
        .append("localipcur", getLocalipcur())
        
        .append("localcount", getLocalcount())
        
        .append("lastlogiondate", getLastlogiondate())
        
        .append("linecode", getLinecode())
        
        .append("linename", getLinename())
        
        .append("toollist", getToollist())
        
        .append("note", getNote())
        
        .append("createuser", getCreateuser())
        
        .append("createdate", getCreatedate())
        
        .append("superuser", getSuperuser())
        
        .append("superpsd", getSuperpsd())
            .toString();
    }
}
