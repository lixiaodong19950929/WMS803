package com.wms.base.warehouse.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.wms.common.annotation.Excel;
import com.wms.common.core.domain.BaseEntity;
import com.wms.infoquery.querystockstatus.domain.Stock;
import lombok.*;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Date;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WarehouseEx extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 自增列 */
    @Excel(name = "自增列")
    private Long id;

    /** 仓库编码 */
    private String whCode;

    /** 仓库名称 */
    @Excel(name = "仓库名称")
    private String whName;

    /** 仓库地址 */
    @Excel(name = "仓库地址")
    private String whAddress;

    /** 仓库电话 */
    @Excel(name = "仓库电话")
    private String whPhone;

    /** 仓库负责人 */
    @Excel(name = "仓库负责人")
    private String whPerson;

    /** 仓库条码 */
    @Excel(name = "仓库条码")
    private String cBarCode;

    /** 盘点周期 */
    @Excel(name = "盘点周期")
    private Long inventoryCycle;

    /** 盘点周期单位 */
    @Excel(name = "盘点周期单位")
    private String inventoryCycleUnit;

    /** 备注 */
    @Excel(name = "备注")
    private String memo;

    /** 上次盘点日期 */
    @Excel(name = "上次盘点日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date lastCheckDate;

    /** 创建人 */
    @Excel(name = "创建人")
    private String createUser;

    /** 创建时间 */
    @Excel(name = "创建时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date createDate;

    /** 修改人 */
    @Excel(name = "修改人")
    private String modifyUser;

    /** 修改时间 */
    @Excel(name = "修改时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date modifyDate;

    /** 1:启用，0:停用 */
    @Excel(name = "1:启用，0:停用")
    private Long isEnable;

    /** 1:删除，0:正常 */
    @Excel(name = "1:删除，0:正常")
    private Long isDelete;

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

    @JsonIgnore
    private Integer pageIndex;

    @JsonIgnore
    private Integer pageSize;
}
