package com.wms.base.running.service;


import com.wms.base.running.domain.Running;

import java.util.List;

/**
 * 任务执行Service接口
 * 
 * @author assassin
 * @date 2021-09-16
 */
public interface IRunningService 
{
    /**
     * 查询任务执行
     * 
     * @param id 任务执行ID
     * @return 任务执行
     */
    public Running selectRunningById(Long id);

    /**
     * 查询任务执行列表
     * 
     * @param running 任务执行
     * @return 任务执行集合
     */
    public List<Running> selectRunningList(Running running);

    /**
     * 新增任务执行
     * 
     * @param running 任务执行
     * @return 结果
     */
    public int insertRunning(Running running);

    /**
     * 修改任务执行
     * 
     * @param running 任务执行
     * @return 结果
     */
    public int updateRunning(Running running);

    /**
     * 批量删除任务执行
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteRunningByIds(String ids);

    /**
     * 删除任务执行信息
     * 
     * @param id 任务执行ID
     * @return 结果
     */
    public int deleteRunningById(Long id);
}
