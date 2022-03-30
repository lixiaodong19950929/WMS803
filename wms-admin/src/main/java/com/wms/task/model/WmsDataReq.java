package com.wms.task.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by Administrator on 2020/3/16.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WmsDataReq {

    @JsonProperty("data_type")
    private String data_type;
    @JsonProperty("orderId")
    private String orderId;
    @JsonProperty("productCodes")
    private String productCodes;
    @JsonProperty("timestamp")
    private Integer timestamp;
    @JsonProperty("OuterOrderId")
    private String OuterOrderId;
    @JsonProperty("notice_type")
    private String notice_type;

    @JsonProperty("jsonStr")
    private WmsDataReq jsonStr;

    public WmsDataReq(String data_type, String OuterOrderId,Integer timestamp ) {
        this.data_type = data_type;
        this.timestamp = timestamp;
        this.OuterOrderId = OuterOrderId;
    }

    public WmsDataReq(WmsDataReq jsonStr) {
        this.jsonStr = jsonStr;
    }
}
