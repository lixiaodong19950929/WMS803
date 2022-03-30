package com.wms.warehouse.sern.domain;

import com.wms.common.annotation.Excel;
import com.wms.common.core.domain.BaseEntity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Barcodelist  extends BaseEntity {

    //自增主键
    private Long id;
    //行号
    @Excel(name = "行号")
    private Long rowId;
    //任务号
    @Excel(name = "任务号")
    private String taskCode;
    //产品批次
    @Excel(name = "产品批次")
    private String ProductBatch;
    //产品代号
    @Excel(name = "产品代号")
    private String productCode;
    //产品名称
    @Excel(name = "产品名称")
    private String productName;
    //库位编码
    @Excel(name = "库位编码")
    private String storehouseCode;
    //货架层数：默认为0，货架1层：1，其他层：2
//    @Excel(name = "货架层数")
    private Long layer;
    //条码
    @Excel(name = "条码")
    private String sern;
    //库位名称
//    @Excel(name = "库位名称")
    private String storehouseName;
    //创建时间
    @Excel(name = "创建时间")
    private String createDate;
    //箱号
    @Excel(name = "箱号")
    private String Xhao;
    //批次
    @Excel(name = "扫描批次")
    private String batch;

    //完整条码
    @Excel(name = "箱码")
    private String FullSern;
    //备用字段F2
    private String f2;
    //备用字段F3
    private String f3;
    //备用字段F4
    private String f4;



}
