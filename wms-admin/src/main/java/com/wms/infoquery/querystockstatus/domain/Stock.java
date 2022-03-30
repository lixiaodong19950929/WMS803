package com.wms.infoquery.querystockstatus.domain;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.wms.common.annotation.Excel;
import com.wms.common.core.domain.BaseEntity;
import java.util.Date;
import java.util.List;

/**
 * 库存对象 T_Base_Stock
 * 
 * @author assassin
 * @date 2020-01-16
 */
@Getter
@Setter
public class Stock extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 自增列 */
    @Excel(name = "自增列")
    private Long id;

    /** 产品类型 */
    @Excel(name = "产品类型")
    private String producttype;

    /** 产品编码 */
    @Excel(name = "产品编码")
    private String productcode;

    /** 产品名称 */
    @Excel(name = "产品名称")
    private String productname;

    /**
     * 产品编号
     */
    @Excel(name = "产品编号")
    private String productNum;

    /** 产品批次 */
    @Excel(name = "产品批次")
    private String productbatch;

    /** 产品型号 */
    @Excel(name = "产品型号")
    private String productmodel;

    /** 规格型号 */
    @Excel(name = "规格型号")
    private String specification;

    /** 数量 */
    @Excel(name = "数量")
    private Integer quantity;

    /** 备注 */
    @Excel(name = "备注")
    private String memo;

    /** 创建人 */
    @Excel(name = "创建人")
    private String createuser;

    /** 创建时间 */
    @Excel(name = "创建时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date createdate;

    /** 仓库编码 */
    private String whcode;

    /** 仓库名称 */
    @Excel(name = "仓库名称")
    private String whname;

    /** 库位编码 */
    private String storehousecode;

    /** 库位名称 */
    @Excel(name = "库位名称")
    private String storehousename;

    /** 库位-行 */
    private String storehouseline;

    /** 库位-列 */
    private String storehousecolum;

    /** 库位-层 */
    private String storehouselayer;

    /** 产品条形码 */
    private String sern;
    /** 区域编码 */
    private String regioncode;
    /** 上限 */
    private Integer toplimit = 0;

    /** 下限 */
    private Integer lowerlimit = 0;

    /** 差异数量 */
    private Integer differencequantity = 0;

    private List<String> productcodes;

    private String otherProductCode;
    private String trayCode;

    private String kwNum;

    public Stock() {
        super();
    }

    public Stock(String storehousecode,String productcode, String productname, String productbatch, String productmodel,String whcode) {
        this.storehousecode=storehousecode;
        this.productcode = productcode;
        this.productname = productname;
        this.productbatch = productbatch;
        this.productmodel = productmodel;
        this.whcode=whcode;
    }

    public Stock(String productcode, String productname, String productbatch, String productmodel,String whcode) {
        this.productcode = productcode;
        this.productname = productname;
        this.productbatch = productbatch;
        this.productmodel = productmodel;
        this.whcode=whcode;
    }

    public Stock(String productcode, String productname, String productbatch, String productmodel) {
        this.productcode = productcode;
        this.productname = productname;
        this.productbatch = productbatch;
        this.productmodel = productmodel;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)

        .append("id", getId())

        .append("producttype", getProducttype())

        .append("productcode", getProductcode())

        .append("productname", getProductname())

        .append("productbatch", getProductbatch())

        .append("productmodel", getProductmodel())

        .append("specification", getSpecification())

        .append("quantity", getQuantity())

        .append("memo", getMemo())

        .append("createuser", getCreateuser())

        .append("createdate", getCreatedate())

        .append("whcode", getWhcode())

        .append("storehousecode", getStorehousecode())

        .append("kwNum",getKwNum())
            .toString();
    }
}
