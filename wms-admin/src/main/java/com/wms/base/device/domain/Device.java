package com.wms.base.device.domain;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.wms.common.annotation.Excel;
import com.wms.common.core.domain.BaseEntity;
import java.util.Date;

/**
 * 设备对象 T_Base_Device
 * 
 * @author dkwms
 * @date 2020-01-08
 */
@Getter
@Setter
public class Device extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 自增列 */
    @Excel(name = "自增列")
    private Long id;

    /** 设备编码 */
    private String devicecode;

    /** 设备名称 */
    @Excel(name = "设备名称")
    private String devicename;

    /** 设备类型 */
    @Excel(name = "设备类型")
    private String devicetype;

    /** 设备状态(报警、待机、运行中、停机) */
    @Excel(name = "设备状态(报警、待机、运行中、停机)")
    private String devicestate;

    /** 设备型号 */
    @Excel(name = "设备型号")
    private String devicemodel;

    /** 设备参数 */
    @Excel(name = "设备参数")
    private String deviceparameters;

    /** 设备功率 */
    @Excel(name = "设备功率")
    private String devicepower;

    /** 生产厂家 */
    @Excel(name = "生产厂家")
    private String devicefactory;

    /** 备注 */
    @Excel(name = "备注")
    private String memo;

    /** 创建人 */
    @Excel(name = "创建人")
    private String createuser;

    /** 创建时间 */
    @Excel(name = "创建时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date createdate;

    /** 设备用途 */
    @Excel(name = "设备用途")
    private String devicepurpose;

    /** 使用部门 */
    @Excel(name = "使用部门")
    private String usedepartment;

    /** 使用人员 */
    @Excel(name = "使用人员")
    private String useemployee;

    /** 使用工位ID */
    @Excel(name = "使用工位ID")
    private String uselocationid;

    /** 使用位置 */
    @Excel(name = "使用位置")
    private String uselocation;

    /** 上次检定日期 */
    @Excel(name = "上次检定日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date inspectdate;

    /** 检定周期 */
    @Excel(name = "检定周期")
    private Long inspectcycle;

    /** 检定周期单位（天，月，年） */
    @Excel(name = "检定周期单位", readConverterExp = "天=，月，年")
    private String inspectcycleunit;

    /** 是否是测量设备（1：是；0：不是） */
    @Excel(name = "是否是测量设备", readConverterExp = "1=：是；0：不是")
    private Long ismeterdevice;

    /** 上次维保日期 */
    @Excel(name = "上次维保日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date repairdate;

    /** 维保周期 */
    @Excel(name = "维保周期")
    private Long repaircycle;

    /** 维保周期单位 */
    @Excel(name = "维保周期单位")
    private String repaircycleunit;

    /** 0:非重要设备；1:重要设备 */
    @Excel(name = "0:非重要设备；1:重要设备")
    private Long isimportant;

    /** 1:启用，0:停用 */
    @Excel(name = "1:启用，0:停用")
    private Long isenable;

    /** 仓库编码 */
    @Excel(name = "仓库编码")
    private String whcode;

    /** 备用字段 */
    @Excel(name = "备用字段")
    private String f2;

    /** 备用字段 */
    @Excel(name = "备用字段")
    private String f3;

    /** 备用字段 */
    @Excel(name = "备用字段")
    private Long f4;

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
        
        .append("id", getId())
        
        .append("devicecode", getDevicecode())
        
        .append("devicename", getDevicename())
        
        .append("devicetype", getDevicetype())
        
        .append("devicestate", getDevicestate())
        
        .append("devicemodel", getDevicemodel())
        
        .append("deviceparameters", getDeviceparameters())
        
        .append("devicepower", getDevicepower())
        
        .append("devicefactory", getDevicefactory())
        
        .append("memo", getMemo())
        
        .append("createuser", getCreateuser())
        
        .append("createdate", getCreatedate())
        
        .append("devicepurpose", getDevicepurpose())
        
        .append("usedepartment", getUsedepartment())
        
        .append("useemployee", getUseemployee())
        
        .append("uselocationid", getUselocationid())
        
        .append("uselocation", getUselocation())
        
        .append("inspectdate", getInspectdate())
        
        .append("inspectcycle", getInspectcycle())
        
        .append("inspectcycleunit", getInspectcycleunit())
        
        .append("ismeterdevice", getIsmeterdevice())
        
        .append("repairdate", getRepairdate())
        
        .append("repaircycle", getRepaircycle())
        
        .append("repaircycleunit", getRepaircycleunit())
        
        .append("isimportant", getIsimportant())
        
        .append("isenable", getIsenable())
        
        .append("f2", getF2())
        
        .append("f3", getF3())
        
        .append("f4", getF4())
            .toString();
    }
}
