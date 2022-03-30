package com.wms.warehouse.inventroyManager.domain;

import com.wms.common.annotation.Excel;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * @program: wms
 * @description: 库存管理bean
 * @author: 刺客
 * @create: 2020-01-17 11:11
 */
@Getter
@Setter
public class InventroyManager {

    private static final long serialVersionUID = 1L;

    /** 自增列 */
    @Excel(name = "自增列")
    private Long id;

    /** 仓库编码 */
    private String whcode;

    /** 仓库名称 */
    private String whname;

    /** 区域编码 */
    private String regioncode;

    /** 区域名称 */
    private String regionname;

    /** 库位编码 */
    private String storehousecode;

    /** 库位名 */
    @Excel(name = "库位名")
    private String storehousename;

    /** 库位状态 */
    @Excel(name = "库位状态")
    private Long storehousestate;

    /** 托盘号 */
    @Excel(name = "托盘号")
    private String traycode;

    /** 库位说明 */
    @Excel(name = "库位说明")
    private String storehouseexplain;

    /** 备注 */
    @Excel(name = "备注")
    private String memo;

    /** 创建人 */
    @Excel(name = "创建人")
    private String createuser;

    /** 创建时间 */
    @Excel(name = "创建时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date createdate;

    /** 1:启用，0:停用 */
    @Excel(name = "1:启用，0:停用")
    private Long isenable;

    /** 1:删除，0:正常 */
    @Excel(name = "1:删除，0:正常")
    private Long isdelete;

    /** 产品类型 */
    @Excel(name = "产品类型")
    private String producttype;

    /** 产品编码 */
    @Excel(name = "产品编码")
    private String productcode;

    /** 产品名称 */
    @Excel(name = "产品名称")
    private String productname;

    /** 产品批次 */
    @Excel(name = "产品批次")
    private String productbatch;

    /** 产品型号 */
    @Excel(name = "产品型号")
    private String productmodel;

    /** 规格型号 */
    @Excel(name = "规格型号")
    private String specification;

    /** 数量 */
    @Excel(name = "数量")
    private Long quantity;

    /** 创建时间 */
    @Excel(name = "开始时间", width = 30, dateFormat = "yyyy-MM-dd")
    private String beginCreatedate;

    /** 创建时间 */
    @Excel(name = "结束时间", width = 30, dateFormat = "yyyy-MM-dd")
    private String endCreatedate;

    /** RGV小车号 */
    @Excel(name = "RGV小车号")
    private String rgvdevicecode;

    /** 堆垛机任务号 */
    @Excel(name = "堆垛机任务号")
    private String devicecode;


    /** 查询条件 */
    private String key;

    private String xAxis;

    private String axisData;

    private String seriesData;

    private String createTime;
    // 任务类型
    private Integer tasktype;


}
