package com.wms.base.employee.domain;

import com.wms.common.annotation.Excel;
import com.wms.common.core.domain.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import java.util.Date;

/**
 * 员工对象 T_Base_Employee
 * 
 * @author wms
 * @date 2019-12-25
 */
@Getter
@Setter
public class Employee extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 自增列 */
    @Excel(name = "自增列")
    private Long id;

    /** 员工编码 */
    private String employeecode;

    /** 员工姓名 */
    @Excel(name = "员工姓名")
    private String employeename;

    /** 员工性别 */
    @Excel(name = "员工性别")
    private String sex;

    /** 部门编码 */
    @Excel(name = "部门编码")
    private String deptcode;

    /** 部门名称 */
    @Excel(name = "部门名称")
    private String deptname;

    /** 岗位 */
    @Excel(name = "岗位")
    private String post;

    /** 员工电话 */
    @Excel(name = "员工电话")
    private String tel;

    /** 员工密码 */
    @Excel(name = "员工密码")
    private String password;

    /** 出生日期 */
//    @Excel(name = "出生日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date birthday;

    /** 政治面貌 */
    @Excel(name = "政治面貌")
    private String politiccountenance;

    /** 邮箱 */
    @Excel(name = "邮箱")
    private String email;

    /** 家庭住址 */
    @Excel(name = "家庭住址")
    private String address;

    /** 备注 */
    @Excel(name = "备注")
    private String memo;

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

    /** 岗位名称 */
    @Excel(name = "岗位名称")
    private String postName;

    private Integer age = 0;



    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
        
        .append("id", getId())
        
        .append("employeecode", getEmployeecode())
        
        .append("employeename", getEmployeename())
        
        .append("sex", getSex())
        
        .append("deptcode", getDeptcode())
        
        .append("deptname", getDeptname())
        
        .append("post", getPost())
        
        .append("tel", getTel())
        
        .append("password", getPassword())
        
        .append("birthday", getBirthday())
        
        .append("politiccountenance", getPoliticcountenance())
        
        .append("email", getEmail())
        
        .append("address", getAddress())
        
        .append("memo", getMemo())
        
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
