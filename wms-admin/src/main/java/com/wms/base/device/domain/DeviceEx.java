package com.wms.base.device.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.wms.common.annotation.Excel;
import com.wms.common.core.domain.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeviceEx extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 自增列
     */
    @Excel(name = "自增列")
    private Long id;

    /**
     * 设备编码
     */
    private String deviceCode;

    /**
     * 设备名称
     */
    @Excel(name = "设备名称")
    private String deviceName;

    /**
     * 设备类型
     */
    @Excel(name = "设备类型")
    private String deviceType;

    /**
     * 设备状态(报警、待机、运行中、停机)
     */
    @Excel(name = "设备状态(报警、待机、运行中、停机)")
    private String deviceState;

    /**
     * 设备型号
     */
    @Excel(name = "设备型号")
    private String deviceModel;

    /**
     * 设备参数
     */
    @Excel(name = "设备参数")
    private String deviceParameters;

    /**
     * 设备功率
     */
    @Excel(name = "设备功率")
    private String devicePower;

    /**
     * 生产厂家
     */
    @Excel(name = "生产厂家")
    private String deviceFactory;

    /**
     * 备注
     */
    @Excel(name = "备注")
    private String deviceMemo;

    /**
     * 创建人
     */
    @Excel(name = "创建人")
    private String createUser;

    /**
     * 创建时间
     */
    @Excel(name = "创建时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date createDate;

    /**
     * 设备用途
     */
    @Excel(name = "设备用途")
    private String devicePurpose;

    /**
     * 使用部门
     */
    @Excel(name = "使用部门")
    private String useDepartment;

    /**
     * 使用人员
     */
    @Excel(name = "使用人员")
    private String useEmployee;

    /**
     * 使用工位ID
     */
    @Excel(name = "使用工位ID")
    private String useLocationId;

    /**
     * 使用位置
     */
    @Excel(name = "使用位置")
    private String useLocation;

    /**
     * 上次检定日期
     */
    @Excel(name = "上次检定日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date inspectDate;

    /**
     * 检定周期
     */
    @Excel(name = "检定周期")
    private Long inspectCycle;

    /**
     * 检定周期单位（天，月，年）
     */
    @Excel(name = "检定周期单位", readConverterExp = "天=，月，年")
    private String inspectCycleUnit;

    /**
     * 是否是测量设备（1：是；0：不是）
     */
    @Excel(name = "是否是测量设备", readConverterExp = "1=：是；0：不是")
    private Long isMeterDevice;

    /**
     * 上次维保日期
     */
    @Excel(name = "上次维保日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date repairDate;

    /**
     * 维保周期
     */
    @Excel(name = "维保周期")
    private Long repairCycle;

    /**
     * 维保周期单位
     */
    @Excel(name = "维保周期单位")
    private String repairCycleUnit;

    /**
     * 0:非重要设备；1:重要设备
     */
    @Excel(name = "0:非重要设备；1:重要设备")
    private Long isImportant;

    /**
     * 1:启用，0:停用
     */
    @Excel(name = "1:启用，0:停用")
    private Long isEnable;

    /**
     * 仓库编码
     */
    @Excel(name = "仓库编码")
    private String whCode;

    /**
     * 备用字段
     */
    @Excel(name = "备用字段")
    private String f2;

    /**
     * 备用字段
     */
    @Excel(name = "备用字段")
    private String f3;

    /**
     * 备用字段
     */
    @Excel(name = "备用字段")
    private Long f4;

    @JsonIgnore
    private Integer pageIndex;

    @JsonIgnore
    private Integer pageSize;
}
