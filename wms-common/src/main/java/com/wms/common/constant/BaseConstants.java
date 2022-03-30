package com.wms.common.constant;

/**
 * @program: wms
 * @description: 基础数据校验返回结果码
 * @author: 刺客
 * @create: 2019-12-26 15:36
 */
public class BaseConstants {

    /** 员工编号是否唯一返回结果码 */
    public static final String EMPLOYEE_CODE_UNIQUE="0";
    public static final String EMPLOYEE_CODE_NOT_UNIQUE="1";
    /** 员工电话是否唯一返回结果码*/
    public static final String EMPLOYEE_PHONE_NOT_UNIQUE ="1";
    public static final String EMPLOYEE_PHONE_UNIQUE = "0";

    /** 仓库条码是否唯一返回结果码 */
    public static final String WAREHOUSE_CBARCODE_NOT_UNIQUE = "1";
    public static final String WAREHOUSE_CBARCODE_UNIQUE = "0";

    /** 库位编码是否唯一返回结果码 */
    public static final String STOREHOUSE_STOREHOUSECODE_NOT_UNIQUE = "1";
    public static final String STOREHOUSE_STOREHOUSECODE_UNIQUE = "0";

    /** 产品编码是否唯一返回结果码 */
    public static final String PRODUCT_PRODUCTCODE_NOT_UNIQUE = "1";
    public static final String PRODUCT_PRODUCTCODE_UNIQUE = "0";

    /** 物料编码是否唯一返回结果码 */
    public static final String MATERIAL_MATERIALCODE_NOT_UNIQUE = "1";
    public static final String MATERIAL_MATERIALCODE_UNIQUE = "0";

    /** 物料设备是否唯一返回结果码 */
    public static final String DEVICE_DEVICECODE_NOT_UNIQUE = "1";
    public static final String DEVICE_DEVICECODE_UNIQUE = "0";
}
