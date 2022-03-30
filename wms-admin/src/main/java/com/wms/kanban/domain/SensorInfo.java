package com.wms.kanban.domain;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * @author diaoyang
 * @date 2020/4/2 16:37
 * @quotes 乐于生活，高于生活
 */
@Data
public class SensorInfo{

    private Integer id;
    //传感器编码
    private String sensorCode;
   // 传感器名称
    private String sensorName;
    // 传感器数量
    private Integer sensorNumber;
    // 传感器正常数量
    private Integer formalNumber;
    // 传感器异常数量
    private Integer exceptionNumber;

}
