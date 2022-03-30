package com.wms.warehouse.storemove.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.wms.common.annotation.Excel;
import com.wms.common.core.domain.BaseEntity;
import com.wms.task.wincc.pojo.SernEntity;
import lombok.*;

import java.util.Date;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StoreMoveEx extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 自增列
     */
    @Excel(name = "自增列")
    private Long id;

    /**
     * 任务类型
     */
    private String taskType;

    /**
     * 通知类型
     */
    private String dataType;


    /**
     * 任务号
     */
    private String taskCode;

    /**
     * 舜宇id
     */
    private String outerOrderId;

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
     * 仓库名称
     */
    @Excel(name = "仓库名称")
    private String whName;

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
    private Long isEnable;

    /**
     * 1:删除，0:正常
     */
    private Long isDelete;

    private List<StoreMoveSonEx> storeMoveSonExList;

    private List<StoreMoveSonEx> detail;

    private List<SernEntity> sernList;

    @JsonIgnore
    private Integer pageIndex;

    @JsonIgnore
    private Integer pageSize;
}
