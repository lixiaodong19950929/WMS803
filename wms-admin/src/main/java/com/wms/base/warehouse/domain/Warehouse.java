package com.wms.base.warehouse.domain;

import com.wms.base.product.domain.Product;
import com.wms.common.annotation.Excel;
import com.wms.common.core.domain.BaseEntity;
import com.wms.infoquery.querystockstatus.domain.Stock;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Date;
import java.util.List;

/**
 * 仓库对象 T_Base_Warehouse
 * 
 * @author assassin
 * @date 2019-12-27
 */
@Getter
@Setter
public class Warehouse extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 自增列 */
    @Excel(name = "自增列")
    private Long id;

    /** 仓库编码 */
    private String whcode;

    /** 仓库名称 */
    @Excel(name = "仓库名称")
    private String whname;

    /** 仓库地址 */
    @Excel(name = "仓库地址")
    private String whaddress;

    /** 仓库电话 */
    @Excel(name = "仓库电话")
    private String whphone;

    /** 仓库负责人 */
    @Excel(name = "仓库负责人")
    private String whperson;

    /** 仓库条码 */
    @Excel(name = "仓库条码")
    private String cbarcode;

    /** 盘点周期 */
    @Excel(name = "盘点周期")
    private Long inventorycycle;

    /** 盘点周期单位 */
    @Excel(name = "盘点周期单位")
    private String inventorycycleunit;

    /** 备注 */
    @Excel(name = "备注")
    private String memo;

    /** 上次盘点日期 */
    @Excel(name = "上次盘点日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date lastcheckdate;

    /** 创建人 */
    @Excel(name = "创建人")
    private String createuser;

    /** 创建时间 */
    @Excel(name = "创建时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date createdate;

    /** 修改人 */
    @Excel(name = "修改人")
    private String modifyuser;

    /** 修改时间 */
    @Excel(name = "修改时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date modifydate;

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

    private List<Stock> stockList;


    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
        
        .append("id", getId())
        
        .append("whcode", getWhcode())
        
        .append("whname", getWhname())
        
        .append("whaddress", getWhaddress())
        
        .append("whphone", getWhphone())
        
        .append("whperson", getWhperson())
        
        .append("cbarcode", getCbarcode())
        
        .append("inventorycycle", getInventorycycle())
        
        .append("inventorycycleunit", getInventorycycleunit())
        
        .append("memo", getMemo())
        
        .append("lastcheckdate", getLastcheckdate())
        
        .append("createuser", getCreateuser())
        
        .append("createdate", getCreatedate())
        
        .append("modifyuser", getModifyuser())
        
        .append("modifydate", getModifydate())
        
        .append("isenable", getIsenable())
        
        .append("isdelete", getIsdelete())
        
        .append("f1", getF1())
        
        .append("f2", getF2())
        
        .append("f3", getF3())
        
        .append("f4", getF4())
            .toString();
    }
}
