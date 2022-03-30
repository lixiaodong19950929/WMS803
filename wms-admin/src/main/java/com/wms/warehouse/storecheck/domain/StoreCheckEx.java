package com.wms.warehouse.storecheck.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.wms.common.annotation.Excel;
import lombok.*;

import java.util.Date;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StoreCheckEx {
    private static final long serialVersionUID = 1L;

    /**
     * 自增主键
     */
    @Excel(name = "自增主键")
    private Long id;

    /**
     * 任务类型
     */
    private String taskType;

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
     * 制单人
     */
    @Excel(name = "制单人")
    private String maker;

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
    private String note;

    /**
     * 舜宇订单id
     */
    @Excel(name = "舜宇订单id")
    private String OuterOrderId;

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

    private List<StoreCheckSon> storeCheckSonExList;

    private List<StoreCheckSon> detail;

    @JsonIgnore
    private Integer pageIndex;

    @JsonIgnore
    private Integer pageSize;
}
