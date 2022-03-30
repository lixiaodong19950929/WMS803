package com.wms.base.staEmp.mapper;

import com.wms.base.staEmp.domain.StaEmp;
import java.util.List;

/**
 * 操作人员Mapper接口
 * 
 * @author assassin
 * @date 2021-03-23
 */
public interface StaEmpMapper 
{
    /**
     * 查询操作人员
     * 
     * @param id 操作人员ID
     * @return 操作人员
     */
    public StaEmp selectStaEmpById(Long id);

    /**
     * 查询操作人员列表
     * 
     * @param staEmp 操作人员
     * @return 操作人员集合
     */
    public List<StaEmp> selectStaEmpList(StaEmp staEmp);

    /**
     * 新增操作人员
     * 
     * @param staEmp 操作人员
     * @return 结果
     */
    public int insertStaEmp(StaEmp staEmp);

    /**
     * 修改操作人员
     * 
     * @param staEmp 操作人员
     * @return 结果
     */
    public int updateStaEmp(StaEmp staEmp);

    /**
     * 删除操作人员
     * 
     * @param id 操作人员ID
     * @return 结果
     */
    public int deleteStaEmpById(Long id);

    /**
     * 批量删除操作人员
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteStaEmpByIds(String[] ids);
}
