package com.wms.base.tray.domain;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.wms.common.annotation.Excel;
import com.wms.common.core.domain.BaseEntity;
import java.util.Date;

/**
 * 托盘对象 T_Base_Tray
 * 
 * @author dkwms
 * @date 2020-01-07
 */
@Getter
@Setter
public class Tray extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 自增列 */
    @Excel(name = "自增列")
    private Long id;

    /** 托盘编码 */
    @Excel(name = "托盘编码")
    private String traycode;

    /** 对应RFID编码 */
    @Excel(name = "对应RFID编码}")
    private String rfidcode;

    /** 库位表的ID列 */
    @Excel(name = "库位表的ID列")
    private Long storeid;

    /** 托盘状态（0：空闲；1：占用：2：故障） */
    @Excel(name = "$托盘状态")
    private Long traystate;

    /** 托盘规格尺寸 */
    @Excel(name = "托盘规格尺寸")
    private String traysize;

    /** 备注 */
    @Excel(name = "备注")
    private String memo;

    /** 创建人 */
    @Excel(name = "创建人")
    private String createuser;

    /** 创建时间 */
    @Excel(name = "创建时间")
    private Date createdate;

    /** 是否启用 */
    @Excel(name = "是否启用")
    private Long isenable;

    /** 是否删除 */
    @Excel(name = "是否删除")
    private Long isdelete;

    /** 备用字段 */
    @Excel(name = "备用字段")
    private String f1;

    /** 备用字段 */
    @Excel(name = "备用字段")
    private String f2;

    /** 备用字段 */
    @Excel(name = "备用字段")
    private String f3;

    /** 备用字段 */
    @Excel(name = "备用字段")
    private Long f4;

    /** 仓库名称 */
    private String whname;

    /** 库位编码 */
    private String storehousecode;

    /** 库位名称 */
    private String storehousename;

    /** 仓库编码 */
    private String whcode;

    /** 仓库负责人 */
    private String whperson;

    /** 仓库电话 */
    private String whphone;

    /** 标记 初始化为 false */
    private boolean flag = false;

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
        
        .append("id", getId())
        
        .append("traycode", getTraycode())
        
        .append("rfidcode", getRfidcode())
        
        .append("storeid", getStoreid())
        
        .append("traystate", getTraystate())
        
        .append("traysize", getTraysize())
        
        .append("memo", getMemo())
        
        .append("createuser", getCreateuser())
        
        .append("createdate", getCreatedate())
        
        .append("isenable", getIsenable())
        
        .append("isdelete", getIsdelete())
        
        .append("f1", getF1())
        
        .append("f2", getF2())
        
        .append("f3", getF3())
        
        .append("f4", getF4())
            .toString();
    }
}
