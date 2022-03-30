package com.wms.task.wincc.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * Created by Administrator on 2020/2/21.
 */
@Data
public class NoticeDto {
    @JsonProperty("notice_type")
    private Integer noticeType;

    private String orderId;

    //极创订单号
    private String TaskCode;

    //舜宇订单号
    @JsonProperty("OuterOrderId")
    private String outerOrderId;

    @Override
    public String toString() {
        return "NoticeDto{" +
                "noticeType=" + noticeType +
                ", orderId='" + orderId + '\'' +
                ", TaskCode='" + TaskCode + '\'' +
                ", outerOrderId='" + outerOrderId + '\'' +
                '}';
    }
}
