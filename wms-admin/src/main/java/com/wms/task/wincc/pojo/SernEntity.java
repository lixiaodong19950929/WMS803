package com.wms.task.wincc.pojo;

import com.wms.common.annotation.Excel;
import com.wms.common.core.domain.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * 产品条码对象 T_Base_Sern
 * 
 * @author assassin
 * @date 2021-03-02
 */
@Getter
@Setter
public class SernEntity
{
    private static final long serialVersionUID = 1L;

    /** 自增列 */
    @Excel(name = "自增列")
    private Long id;

    /** 任务单的行号 */
    @Excel(name = "任务单的行号")
    private Long rowid;

    /** 任务号 */
    @Excel(name = "任务号")
    private String taskcode;

    /** 产品编码 */
    @Excel(name = "产品编码")
    private String productcode;

    private String productBatch;

    /** 产品条码 */
    @Excel(name = "产品条码")
    private String sern;

    /** 托盘号 */
    @Excel(name = "托盘号")
    private String traycode;

    /** 库位号 */
    @Excel(name = "库位号")
    private String storehousecode;

    /** 扫描人 */
    @Excel(name = "扫描人")
    private String scanuser;

    /** 扫描时间 */
    @Excel(name = "扫描时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date scantime;

    /** $column.columnComment */
    @Excel(name = "扫描时间")
    private String guid;
    /** 其他产品编码*/
    private String otherproductcode;
    /**其实库位其他产品条码*/
    private String startOtherproductcode;

    private String startcode;

    private String productNum;

    private String productName;
}
