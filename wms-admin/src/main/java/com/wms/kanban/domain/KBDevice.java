package com.wms.kanban.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
public class KBDevice {
    // 设备序号id
    private Long id;

    // 设备名称
    private String deviceName;

    // 设备编码
    private String deviceCode;

    // 设备类型
    private String deviceType;

    // 设备状态
    private String deviceState;

    //时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createDate;
}
