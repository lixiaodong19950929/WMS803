package com.wms.warehouse.storemove.domain;

import com.wms.common.annotation.Excel;
import com.wms.common.core.domain.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Date;

/**
 * 移库子对象 T_Base_StoreMoveSon
 * 
 * @author assassin
 * @date 2020-01-16
 */
@Getter
@Setter
public class StoreMoveExcel extends BaseEntity
{
    private static final long serialVersionUID = 1L;


    /** 任务类型 */
    private String tasktype;

    /** 任务号 */
    @Excel(name = "任务号")
    private String taskcode;

    /** 任务名称 */
    @Excel(name = "任务名称")
    private String taskname;

    /** 任务描述 */
    @Excel(name = "任务描述")
    private String productdetail;

    /** 制单人 */
    @Excel(name = "制单人")
    private String maker;

    /** 仓库编码 */
    @Excel(name = "仓库编码")
    private String whcode;

    /** 仓库名称 */
    @Excel(name = "仓库名称")
    private String whname;

    /** 总数量 */
    @Excel(name = "总数量")
    private Long quantity;

    /** 任务状态 */
    @Excel(name = "任务状态")
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

    /** 任务单的行号 */
    @Excel(name = "任务单行号")
    private Long rowid;

    /** 仓库编码 */
    @Excel(name = "目的仓库编码")
    private String endwhcode;

    /** 产品代号 */
    @Excel(name = "产品代号")
    private String productcode;

    /** 产品名称 */
    @Excel(name = "产品名称")
    private String productname;

    /** 产品型号 */
    @Excel(name = "产品型号")
    private String productmodel;

    /** 数量 */
    @Excel(name = "数量")
    private Long peoductquantity;

    /** 起始库位 */
    @Excel(name = "起始库位")
    private String startcode;

    /** 目的库位 */
    @Excel(name = "目的库位")
    private String endcode;

    /** 完成状态 */
    @Excel(name = "完成状态")
    private String status;

    /** 备注 */
    @Excel(name = "备注")
    private String productmemo;

    /** 目的库位 */
//    @Excel(name = "目的库位名称")
//    private String endname;

    /** 仓库编码 */
    @Excel(name = "目的仓库名称")
    private String endwhname;

}
