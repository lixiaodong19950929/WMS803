package com.wms.base.staEmp.service.impl;

import java.util.Date;
import java.util.List;

import com.wms.framework.util.ShiroUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.wms.base.staEmp.mapper.StaEmpMapper;
import com.wms.base.staEmp.domain.StaEmp;
import com.wms.base.staEmp.service.IStaEmpService;
import com.wms.common.core.text.Convert;

/**
 * 操作人员Service业务层处理
 * 
 * @author assassin
 * @date 2021-03-23
 */
@Service
public class StaEmpServiceImpl implements IStaEmpService 
{
    @Autowired
    private StaEmpMapper staEmpMapper;

    /**
     * 查询操作人员
     * 
     * @param id 操作人员ID
     * @return 操作人员
     */
    @Override
    public StaEmp selectStaEmpById(Long id)
    {
        return staEmpMapper.selectStaEmpById(id);
    }

    /**
     * 查询操作人员列表
     * 
     * @param staEmp 操作人员
     * @return 操作人员
     */
    @Override
    public List<StaEmp> selectStaEmpList(StaEmp staEmp)
    {
        return staEmpMapper.selectStaEmpList(staEmp);
    }

    /**
     * 新增操作人员
     * 
     * @param staEmp 操作人员
     * @return 结果
     */
    @Override
    public int insertStaEmp(StaEmp staEmp)
    {
        staEmp.setCreateuser(ShiroUtils.getLoginName());
        staEmp.setCreatedate(new Date());
        return staEmpMapper.insertStaEmp(staEmp);
    }

    /**
     * 修改操作人员
     * 
     * @param staEmp 操作人员
     * @return 结果
     */
    @Override
    public int updateStaEmp(StaEmp staEmp)
    {
        return staEmpMapper.updateStaEmp(staEmp);
    }

    /**
     * 删除操作人员对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteStaEmpByIds(String ids)
    {
        return staEmpMapper.deleteStaEmpByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除操作人员信息
     * 
     * @param id 操作人员ID
     * @return 结果
     */
    public int deleteStaEmpById(Long id)
    {
        return staEmpMapper.deleteStaEmpById(id);
    }
}
