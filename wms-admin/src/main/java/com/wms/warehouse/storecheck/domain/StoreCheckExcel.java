package com.wms.warehouse.storecheck.domain;

import com.wms.common.annotation.Excel;
import com.wms.common.core.domain.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Date;
import java.util.List;

/**
 * 盘点主对象 T_Base_StoreCheck
 * 
 * @author assassin
 * @date 2020-02-10
 */
@Getter
@Setter
public class StoreCheckExcel extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 自增主键 */
    @Excel(name = "自增主键")
    private Long id;

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
    private String taskstate;

    /** 任务状态名称 */
    @Excel(name = "任务状态")
    private String taskstatename;

    /** 备注 */
    @Excel(name = "备注")
    private String note;

    /** 创建人 */
    @Excel(name = "创建人")
    private String createuser;

    /** 创建时间 */
    @Excel(name = "创建时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date createdate;

    /** 行号*/
    @Excel(name = "行号")
    private Long rowid;

    /** 库区编码 */
//    @Excel(name = "库区编码")
    private String regioncode;

    /** 库区名称 */
//    @Excel(name = "库区名称")
    private String regionname;

    /** 库位编码 */
    @Excel(name = "库位编码")
    private String storehousecode;

    /** 库位名称 */
    @Excel(name = "库位名称")
    private String storehousename;

    /** 产品编码 */
    @Excel(name = "产品编码")
    private String productcode;

    /** $产品名称 */
    @Excel(name = "产品名称")
    private String productname;

    /** 产品型号名称 */
//    @Excel(name = "产品型号")
    private String productmodelname;

    private String productmodel;

    /** 数量 */
    @Excel(name = "数量")
    private Long productQuantity;

    /** 盘库结果 */
    @Excel(name = "盘库结果")
    private String pdresult;

    /** 产品备注 */
    @Excel(name = "产品备注")
    private String productMemo;


}
