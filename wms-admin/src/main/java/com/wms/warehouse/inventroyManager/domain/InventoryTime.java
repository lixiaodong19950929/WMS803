package com.wms.warehouse.inventroyManager.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class InventoryTime {
    // 日期
    @JsonFormat(pattern = "MM-dd")
    private String createTime;

    // 类型和数量集合
    private TypeAndCount typeAndCount;
}
