package com.wms.kanban.domain;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class TaskStacker {
    // 设备型号
    private String deviceModel;
    // 任务类型 1 入库、2 出库
    private String taskType;
    // 任务单的行号
    private Integer rowId;
    // 目的地
    private String endPoint;
    // 任务状态 1.未执行、2.执行中、3.已完成
    private String taskState;

    // 设备编号
    private String deviceCode;

}
