package com.wms.base.storehouse.domain;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.wms.common.annotation.Excel;
import com.wms.common.core.domain.BaseEntity;
import java.util.Date;

/**
 * 库位对象 T_Base_Storehouse
 * 
 * @author dkwms
 * @date 2020-01-03
 */
@Getter
@Setter
public class Storehouse extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 自增列 */
    @Excel(name = "自增列")
    private Long id;

    /** 仓库编码 */
    private String whcode;

    /** 仓库名称 */
    private String whname;

    /** 区域编码 */
    private String regioncode;

    /** 区域名称 */
    private String regionname;

    /** 库位编码 */
    private String storehousecode;

    /** 库位名 */
    @Excel(name = "库位名")
    private String storehousename;

    /** 库位状态 */
    @Excel(name = "库位状态")
    private Long storehousestate;

    /** 托盘号 */
    @Excel(name = "托盘号")
    private String traycode;

    /** 库位说明 */
    @Excel(name = "库位说明")
    private String storehouseexplain;

    /** 备注 */
    @Excel(name = "备注")
    private String memo;

    /** 创建人 */
    @Excel(name = "创建人")
    private String createuser;

    /** 创建时间 */
    @Excel(name = "创建时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date createdate;

    /** 1:启用，0:停用 */
    @Excel(name = "1:启用，0:停用")
    private Long isenable;

    /** 1:删除，0:正常 */
    @Excel(name = "1:删除，0:正常")
    private Long isdelete;

    /** 备用字段 */
    @Excel(name = "备用字段")
    private String f1;

    /** 备用字段 */
    @Excel(name = "备用字段")
    private String f2;

    /** 备用字段 */
    @Excel(name = "备用字段")
    private String f3;

    /** 备用字段 */
    @Excel(name = "备用字段")
    private Long f4;

    @Excel(name = "产品数量")
    private Long quantity;

    @Excel(name = "产品编号")
    private String productcode;

    @Excel(name = "产品名称")
    private String productname;

    private Long sums;


    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)

        .append("id", getId())

        .append("whcode", getWhcode())

        .append("regioncode", getRegioncode())

        .append("storehousecode", getStorehousecode())

        .append("storehousename", getStorehousename())

        .append("storehousestate", getStorehousestate())

        .append("traycode", getTraycode())

        .append("storehouseexplain", getStorehouseexplain())

        .append("memo", getMemo())

        .append("createuser", getCreateuser())

        .append("createdate", getCreatedate())

        .append("isenable", getIsenable())

        .append("isdelete", getIsdelete())

        .append("f1", getF1())

        .append("f2", getF2())

        .append("f3", getF3())

        .append("f4", getF4())

        .append("quantity",getQuantity())

        .append("productcode",getProductcode())

        .append("productname",getProductname())

        .append("sums",getSums())

            .toString();
    }
}
