package com.wms.infoquery.querystockstatus.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.wms.common.annotation.Excel;
import com.wms.common.core.domain.BaseEntity;
import lombok.*;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StockEx extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 自增列
     */
    @Excel(name = "自增列")
    private Long id;

    /**
     * 产品类型
     */
    @Excel(name = "产品类型")
    private String productType;

    /**
     * 产品编码
     */
    @Excel(name = "产品编码")
    private String productCode;

    /**
     * 产品名称
     */
    @Excel(name = "产品名称")
    private String productName;

    /**
     * 产品编号
     */
    @Excel(name = "产品编号")
    private String productNum;

    /**
     * 产品批次
     */
    @Excel(name = "产品批次")
    private String productBatch;

    /**
     * 产品型号
     */
    @Excel(name = "产品型号")
    private String productModel;

    /**
     * 规格型号
     */
    @Excel(name = "规格型号")
    private String specificationType;

    /**
     * 数量
     */
    @Excel(name = "数量")
    private Integer quantity;

    /**
     * 备注
     */
    @Excel(name = "备注")
    private String memo;

    private String whCode;

    private String storehouseCode;

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
     * 产品条形码
     */
    private String sern;

    private Integer topLimit = 0;

    private Integer lowerLimit = 0;

    @JsonIgnore
    private Integer pageIndex;

    @JsonIgnore
    private Integer pageSize;
}
