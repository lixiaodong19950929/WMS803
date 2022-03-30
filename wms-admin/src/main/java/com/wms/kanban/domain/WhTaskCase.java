package com.wms.kanban.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
public class WhTaskCase {

    // 任务代号 1.入库，2.出库
    private String taskType;
    // 起点位置 对应库位编码
    private String startPoint;
    // 重点位置，对应库位编码
    private String endPoint;
    // 库位编码
    private String storehouseCode;
    // 库位名称
    private String storehouseName;
    // 任务号
    private String taskCode;
    // 任务名称
    private String taskName;
    // 产品编号
    private String productCode;
    // 产品名称
    private String productName;
    // 创建时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createDate;
}
