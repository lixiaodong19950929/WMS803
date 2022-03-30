package com.wms.infoquery.queryStockRealtime.domain;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.wms.common.annotation.Excel;
import com.wms.common.core.domain.BaseEntity;
import java.util.Date;

/**
 * 扫描记录对象 T_Scan_Main
 * 
 * @author assassin
 * @date 2020-02-06
 */
@Getter
@Setter
public class Main extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 自增Id */
    @Excel(name = "自增Id")
    private Long id;

    /** 任务号 */
    @Excel(name = "任务号")
    private String taskcode;

    /** 任务号 */
    @Excel(name = "任务类型")
    private String tasktype;

    /** 任务号 */
    @Excel(name = "任务名称")
    private String taskname;

    /** 任务号 */
    @Excel(name = "任务状态")
    private String taskstate;

    /** 产品编码 */
    @Excel(name = "产品编码")
    private String productcode;

    /** 产品条码 */
    @Excel(name = "产品条码")
    private String sern;

    /** 托盘号 */
    @Excel(name = "托盘号")
    private String traycode;

    /** 小车号 */
    @Excel(name = "小车号")
    private String deviceno;

    /** 库位编码 */
    @Excel(name = "库位编码")
    private String storehousecode;

    /** 备注 */
    @Excel(name = "备注")
    private String memo;

    /** 扫描人 */
    @Excel(name = "扫描人")
    private String createuser;

    /** 扫描时间 */
    @Excel(name = "扫描时间", width = 30, dateFormat = "yyyy-MM-dd")
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

    private String keywords;

    private String startTime;

    private String endTime;

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
        
        .append("id", getId())
        
        .append("taskcode", getTaskcode())
        
        .append("productcode", getProductcode())
        
        .append("sern", getSern())
        
        .append("traycode", getTraycode())
        
        .append("deviceno", getDeviceno())
        
        .append("storehousecode", getStorehousecode())
        
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
