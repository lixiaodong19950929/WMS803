package com.wms.base.storehouse.domain;


import lombok.Getter;
import lombok.Setter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.wms.common.annotation.Excel;
import com.wms.common.core.domain.BaseEntity;
import lombok.*;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class StorehouseEx extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 自增列
     */
    @Excel(name = "自增列")
    private Long id;

    /**
     * 仓库编码
     */
    private String whcode;

    /**
     * 仓库名称
     */
    private String whname;

    /**
     * 区域编码
     */
    private String regioncode;

    /**
     * 区域名称
     */
    private String regionname;



    /**
     * 库位编码
     */
    @Excel(name = "库位编码")
    private String storehousecode;

    /**
     * 库位名
     */
    @Excel(name = "库位名")
    private String storehousename;

    /**
     * 库位状态
     */
    @Excel(name = "库位状态")
    private Long storehousestate;

    /**
     * 托盘号
     */
    @Excel(name = "托盘号")
    private String traycode;

    /**
     * 堆垛机号
     */
    @Excel(name = "堆垛机号")
    private String devicecode;

    /**
     * RGV
     */
    @Excel(name = "RGV")
    private String rgvdevicecode;

    /**
     * 库位说明
     */
    @Excel(name = "库位说明")
    private String storehouseexplain;

    /**
     * 备注
     */
    @Excel(name = "备注")
    private String storehousememo;

    /**
     * 创建人
     */
    @Excel(name = "创建人")
    private String createuser;

    /**
     * 创建时间
     */
    @Excel(name = "创建时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date createdate;

    /**
     * 1:启用，0:停用
     */
    @Excel(name = "1:启用，0:停用")
    private Long isenable;

    /**
     * 1:删除，0:正常
     */
    @Excel(name = "1:删除，0:正常")
    private Long isdelete;

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

    /**
     * 数量
     */
    @Excel(name = "数量")
    private Long quantity;

    @Excel(name = "产品编号")
    private String productcode;

    @Excel(name = "产品名称")
    private String productname;

    /**
     * 最大箱数
     */
    @Excel(name = "最大箱数")
    private Long maxxhao;

    /**
     * 存放箱数
     */
    @Excel(name = "存放箱数")
    private Long havexhao;

    /**
     * 允许存放箱数
     */
    @Excel(name = "允许存放箱数")
    private Long yunxuxhao;

    @JsonIgnore
    private Integer pageIndex;

    @JsonIgnore
    private Integer pageSize;
}
