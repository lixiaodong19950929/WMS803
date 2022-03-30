package com.wms.warehouse.storeio.domain;

import com.wms.common.annotation.Excel;
import com.wms.common.core.domain.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Date;

/**
 * 出入库导出
 * 
 * @author assassin
 * @date 2020-02-10
 */
@Getter
@Setter
public class StoreIoExcel extends BaseEntity
{
    private static final long serialVersionUID = 1L;
//
//    /** 任务单子表-唯一标识 */
//    @Excel(name = "任务单子表-唯一标识")
//    private Long id;
//
//    /** 任务单的行号 */
//    private Long rowid;
//
//    /** 任务号 */
//    private String taskcode;

    /** 任务类型 */
    private String tasktype;

    /** 任务号 */
    @Excel(name = "任务号")
    private String taskcode;

    /** 任务名称 */
    @Excel(name = "任务名称")
    private String taskname;

    /** 放置层数 */
//    @Excel(name = "放置层数")
    private String layer;

    /** 任务描述 */
    @Excel(name = "任务描述")
    private String productdetail;

    /** 供应商信息 */
//    @Excel(name = "供应商信息")
    private String veninfo;

    /** 制单人 */
    @Excel(name = "制单人")
    private String maker;

    /** 生产订单编号 */
//    @Excel(name = "生产订单编号")
    private String mpocode;

    /** 采购订单编号 */
//    @Excel(name = "采购订单编号")
    private String ordercode;

    /** 仓库编码 */
    @Excel(name = "仓库编码")
    private String whcode;

    /** 仓库名称 */
    @Excel(name = "仓库名称")
    private String whname;

    @Excel(name = "入库方式")
    private Long intype;

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

    /** 产品编码 */
    @Excel(name = "产品编码")
    private String productcode;

    /** 产品名称 */
    @Excel(name = "产品名称")
    private String productname;

    /** 产品型号 */
//    @Excel(name = "产品型号")
//    private String productmodel;

    /** 产品批次 */
    @Excel(name = "产品批次")
    private String productbatch;

    /** 数量 */
    @Excel(name = "数量")
    private Long productquantity;

    /** 任务状态 */
    @Excel(name = "任务状态")
    private String producttaskstate;

    /** 备注 */
    @Excel(name = "产品备注")
    private String productmemo;



}
