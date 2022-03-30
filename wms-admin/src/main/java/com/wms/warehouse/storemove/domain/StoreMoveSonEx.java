package com.wms.warehouse.storemove.domain;

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
public class StoreMoveSonEx {
    private static final long serialVersionUID = 1L;

    /**
     * $column.columnComment
     */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private Long id;

    /**
     * 任务单的行号
     */
    private Long rowId;

    /**
     * 任务号
     */
    private String taskCode;

    /**
     * 任务名称
     */
    @Excel(name = "任务名称")
    private String taskName;

    /**
     * 仓库编码
     */
    @Excel(name = "仓库编码")
    private String whCode;

    /**
     * 产品代号
     */
    @Excel(name = "产品代号")
    private String productCode;

    /**
     * 产品编号
     */
    @Excel(name = "产品编号")
    private String productNum;


    /**
     * 产品名称
     */
    @Excel(name = "产品名称")
    private String productName;

    /**
     * 产品型号
     */
    @Excel(name = "产品型号")
    private String productModel;

    /**
     * 数量
     */
    @Excel(name = "数量")
    private Double quantity;

    /**
     * 起始库位
     */
    @Excel(name = "起始库位")
    private String startCode;

    /**
     * 目的库位
     */
    @Excel(name = "目的库位")
    private String endCode;

    /**
     * 完成状态
     */
    @Excel(name = "完成状态")
    private String taskState;

    /**
     * 备注
     */
    @Excel(name = "备注")
    private String memo;

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
     * 备用
     */
    @Excel(name = "备用")
    private String f1;

    /**
     * 备用
     */
    @Excel(name = "备用")
    private String f2;

    /**
     * 备用
     */
    @Excel(name = "备用")
    private String f3;

    /**
     * 备用
     */
    @Excel(name = "备用")
    private Long f4;

    /**
     * 1:启用，0:停用
     */
    @Excel(name = "1:启用，0:停用")
    private Long isEnable;

    /**
     * 1:删除，0:正常
     */
    @Excel(name = "1:删除，0:正常")
    private Long isDelete;

    private String otherProductCode;
}
