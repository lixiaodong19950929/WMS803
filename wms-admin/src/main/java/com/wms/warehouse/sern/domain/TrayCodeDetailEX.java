package com.wms.warehouse.sern.domain;


import lombok.Getter;
import lombok.Setter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.wms.common.annotation.Excel;
import com.wms.common.core.domain.BaseEntity;
import lombok.*;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class TrayCodeDetailEX extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 自增列
     */
    @Excel(name = "自增列")
    private Long id;

    /** 任务号 */
    @Excel(name = "任务号")
    private String taskcode;

    /** 库位名称 */
    @Excel(name = "库位名称")
    private String storehousename;

    /** 库位编码 */
    @Excel(name = "库位编码")
    private String storehousecode;

    /** 托盘 */
    @Excel(name = "托盘")
    private String traycode;

    /** 产品代号 */
    @Excel(name = "产品代号")
    private String productcode;

    /** 产品名称 */
    @Excel(name = "产品名称")
    private String productname;

    /** 产品批次 */
    @Excel(name = "产品批次")
    private String productbatch;

    /** 条码 */
    @Excel(name = "条码")
    private String sern;

    /** 创建时间 */
//    @Excel(name = "创建时间", width = 30, dateFormat = "yyyy-MM-dd")
//    private Date createdate;
    @Excel(name = "创建时间")
    private String createdate;

    /** 箱号 */
    @Excel(name = "箱号")
    private String Xhao;

    /** 备用字段 */
    @Excel(name = "扫描批次")
    private String Batch;

    /** 完整条码 */
    @Excel(name = "完整条码")
    private String fullsern;

    /** 数量 */
    @Excel(name = "数量")
    private String Quantity;


}
