package com.wms.base.tray.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.wms.common.annotation.Excel;
import com.wms.common.core.domain.BaseEntity;
import lombok.*;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TrayEx extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 自增列
     */
    @Excel(name = "自增列")
    private Long id;

    /**
     * 托盘编码
     */
    @Excel(name = "托盘编码")
    private String trayCode;

    /**
     * 对应RFID编码
     */
    @Excel(name = "对应RFID编码}")
    private String rfidCode;

    /**
     * 库位表的ID列
     */
    @Excel(name = "库位表的ID列")
    private Long storeId;

    /**
     * 托盘状态（0：空闲；1：占用：2：故障）
     */
    @Excel(name = "$托盘状态")
    private Long trayState;

    /**
     * 托盘规格尺寸
     */
    @Excel(name = "托盘规格尺寸")
    private String traySize;

    /**
     * 备注
     */
    @Excel(name = "备注")
    private String trayMemo;

    /**
     * 创建人
     */
    @Excel(name = "创建人")
    private String createUser;

    /**
     * 创建时间
     */
    @Excel(name = "创建时间")
    private Date createDate;

    /**
     * 是否启用
     */
    @Excel(name = "是否启用")
    private Long isEnable;

    /**
     * 是否删除
     */
    @Excel(name = "是否删除")
    private Long isDelete;

    /**
     * 备用字段
     */
    @Excel(name = "备用字段")
    private String f1;

    /**
     * 备用字段
     */
    @Excel(name = "备用字段")
    private String f2;

    /**
     * 备用字段
     */
    @Excel(name = "备用字段")
    private String f3;

    /**
     * 备用字段
     */
    @Excel(name = "备用字段")
    private Long f4;

    @JsonIgnore
    private Integer pageIndex;

    @JsonIgnore
    private Integer pageSize;
}
