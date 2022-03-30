package com.wms.task.wincc.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.wms.common.annotation.Excel;
import com.wms.common.core.domain.BaseEntity;
import com.wms.infoquery.querystockin.domain.StoreIoSonEx;
import com.wms.warehouse.sern.domain.Sern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StoreIoEntity {
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

    private List<StoreIoSonEx> detail;

    private List<SernEntity> sernList;


}
