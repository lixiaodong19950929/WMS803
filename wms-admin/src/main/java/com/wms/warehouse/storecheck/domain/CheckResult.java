package com.wms.warehouse.storecheck.domain;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.wms.common.annotation.Excel;
import com.wms.common.core.domain.BaseEntity;
import java.util.Date;
import java.util.List;

/**
 * 盘点结果对象 T_Base_StoreCheck
 *
 * @author assassin
 * @date 2020-02-10
 */
@Getter
@Setter
public class CheckResult extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /** 自增主键 */
    @Excel(name = "自增主键")
    private Long id;

    /** 任务号 */
    private String TaskCode;

    /** 库位号 */
    @Excel(name = "库位号")
    private String StorehouseCode;

    /** 托盘号 */
    private String TrayCode;

    /** 箱号 */
    private String Xhao;

    /** 批次 */
    private String Batch;

    /** 完整条码 */
    private String FullSern;

    /** 盘点结果 */
    private String PdResult;

    /** 创建时间 */
    @Excel(name = "创建时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date CreateDate;

    /** 创建人 */
    @Excel(name = "创建人")
    private String CreateUser;

    public CheckResult() {
        super();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)

                .append("id", getId())
                .append("TaskCode", getTaskCode())
                .append("StorehouseCode", getStorehouseCode())
                .append("TrayCode", getTrayCode())
                .append("Xhao", getXhao())
                .append("Batch", getBatch())
                .append("FullSern", getFullSern())
                .append("PdResult", getPdResult())
                .append("CreateDate", getCreateDate())
                .append("CreateUser", getCreateUser())

                .toString();
    }

}
