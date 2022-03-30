package com.wms.base.running.service.impl;

import com.wms.base.running.domain.Running;
import com.wms.base.running.mapper.RunningMapper;
import com.wms.base.running.service.IRunningService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.wms.common.core.text.Convert;

import java.util.List;

/**
 * 任务执行Service业务层处理
 * 
 * @author assassin
 * @date 2021-09-16
 */
@Service
public class RunningServiceImpl implements IRunningService
{
    @Autowired
    private RunningMapper runningMapper;

    /**
     * 查询任务执行
     * 
     * @param id 任务执行ID
     * @return 任务执行
     */
    @Override
    public Running selectRunningById(Long id)
    {
        return runningMapper.selectRunningById(id);
    }

    /**
     * 查询任务执行列表
     * 
     * @param running 任务执行
     * @return 任务执行
     */
    @Override
    public List<Running> selectRunningList(Running running)
    {
        return runningMapper.selectRunningList(running);
    }

    /**
     * 新增任务执行
     * 
     * @param running 任务执行
     * @return 结果
     */
    @Override
    public int insertRunning(Running running)
    {
        return runningMapper.insertRunning(running);
    }

    /**
     * 修改任务执行
     * 
     * @param running 任务执行
     * @return 结果
     */
    @Override
    public int updateRunning(Running running)
    {
        return runningMapper.updateRunning(running);
    }

    /**
     * 删除任务执行对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteRunningByIds(String ids)
    {
        return runningMapper.deleteRunningByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除任务执行信息
     * 
     * @param id 任务执行ID
     * @return 结果
     */
    public int deleteRunningById(Long id)
    {
        return runningMapper.deleteRunningById(id);
    }
}
