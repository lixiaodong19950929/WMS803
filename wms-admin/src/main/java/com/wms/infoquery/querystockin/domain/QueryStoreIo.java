package com.wms.infoquery.querystockin.domain;

import com.wms.common.annotation.Excel;
import com.wms.common.core.domain.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
public class QueryStoreIo extends BaseEntity {
    /** 自增 */
    @Excel(name = "自增")
    private Long id;

    /** 任务类型 */
    private String tasktype;

    /** 任务号 */
    private String taskcode;

    /** 任务名称 */
    @Excel(name = "任务名称")
    private String taskname;

    /** 任务描述 */
    @Excel(name = "任务描述")
    private String productdetail;

    /** 供应商信息 */
    @Excel(name = "供应商信息")
    private String veninfo;

    /** 制单人 */
    @Excel(name = "制单人")
    private String maker;

    /** 生产订单编号 */
    @Excel(name = "生产订单编号")
    private String mpocode;

    /** 采购订单编号 */
    @Excel(name = "采购订单编号")
    private String ordercode;

    /** 仓库编码 */
    @Excel(name = "仓库编码")
    private String whcode;

    /** 总数量 */
    @Excel(name = "总数量")
    private Long quantity;

    /** 任务状态 */
    @Excel(name = "任务状态")
    private String taskstate;

    /** 备注 */
    @Excel(name = "备注")
    private String note;

    /** 创建人 */
    @Excel(name = "创建人")
    private String createuser;

    /** 创建时间 */
    @Excel(name = "创建时间", width = 30, dateFormat = "yyyy-MM-dd")
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

    /** 任务单的行号 */
    private Long rowid;

    /** 产品编码 */
    private String productcode;

    /** 产品名称 */
    private String productname;

    /** 产品型号 */
    private String productmodel;

    /** 唯一标识 */
    private String guid;

    /** 备注 */
    private String memo;

    /** 仓库名称 */
    private String whname;

    private String startTime;

    private String endTime;

    /** 创建时间 */
    @Excel(name = "创建时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date soncreatedate;

    private String sern;

    private String otherProductCode;

//    private Long taskId;

















}
