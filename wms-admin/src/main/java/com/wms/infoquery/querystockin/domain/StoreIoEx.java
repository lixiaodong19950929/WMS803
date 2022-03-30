package com.wms.infoquery.querystockin.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.wms.common.annotation.Excel;
import com.wms.common.core.domain.BaseEntity;
import com.wms.warehouse.sern.domain.Sern;
import lombok.*;

import java.util.Date;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StoreIoEx extends BaseEntity {
    /**
     * 自增
     */
    @Excel(name = "自增")
    private Long id;

    /**
     * 任务类型
     */
    private String taskType;


    /**
     * 订单类型
     */
    private String outerOrderId;

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
     * 任务描述
     */
    @Excel(name = "任务描述")
    private String productDetail;

    /**
     * 供应商信息
     */
    @Excel(name = "供应商信息")
    private String venInfo;

    /**
     * 制单人
     */
    @Excel(name = "制单人")
    private String maker;

    /**
     * 生产订单编号
     */
    @Excel(name = "生产订单编号")
    private String mPoCode;

    /**
     * 采购订单编号
     */
    @Excel(name = "采购订单编号")
    private String orderCode;

    /**
     * 仓库编码
     */
    @Excel(name = "仓库编码")
    private String whCode;

    /**
     * 总数量
     */
    @Excel(name = "总数量")
    private Double quantity;

    /**
     * 任务状态
     */
    @Excel(name = "任务状态")
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

    /**
     * 任务单的行号
     */
    private Long rowId;

    private List<StoreIoSonEx> storeIoSonExList;

    private List<StoreIoSonEx> detail;

    private List<Sern> sernList;

    @JsonIgnore
    private Integer pageIndex;

    @JsonIgnore
    private Integer pageSize;

    private String OutTaskCode;


    public StoreIoEx(String taskType, String taskCode, String taskName, String productDetail, String venInfo, String maker, String mPoCode, String orderCode, String whCode, Double quantity, String taskState, String memo, String createUser, Date createDate) {
        this.taskType = taskType;
        this.taskCode = taskCode;
        this.taskName = taskName;
        this.productDetail = productDetail;
        this.venInfo = venInfo;
        this.maker = maker;
        this.mPoCode = mPoCode;
        this.orderCode = orderCode;
        this.whCode = whCode;
        this.quantity = quantity;
        this.taskState = taskState;
        this.memo = memo;
        this.createUser = createUser;
        this.createDate = createDate;
    }

}
