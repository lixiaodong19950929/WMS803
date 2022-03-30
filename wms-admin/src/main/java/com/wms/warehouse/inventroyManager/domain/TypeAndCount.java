package com.wms.warehouse.inventroyManager.domain;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class TypeAndCount {
    // 类型
    private Integer tasktype;

    // 入库数量
    private Integer InCount = 0;

    // 出库数量
    private Integer outCount = 0;
}
