package com.wms.base.region.domain;

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
public class RegionEx extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 自增列
     */
    @Excel(name = "自增列")
    private Long id;

    /**
     * 仓库编码
     */
    private String whCode;

    /**
     * 区域编码
     */
    private String regionCode;

    /**
     * 区域名称
     */
    @Excel(name = "区域名称")
    private String regionName;

    /**
     * 区域说明
     */
    @Excel(name = "区域说明")
    private String regionExplain;

    /**
     * 备注
     */
    @Excel(name = "备注")
    private String regionMemo;

    /**
     * 创建人
     */
    @Excel(name = "创建人")
    private String createUser;

    /**
     * 创建日期
     */
    @Excel(name = "创建日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date createDate;

    /**
     * 1:启用，0:停用
     */
    @Excel(name = "1:启用，0:停用")
    private Long isEnable;

    /**
     * 1:删除，0:正常
     */
    @Excel(name = "1:删除，0:正常")
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
