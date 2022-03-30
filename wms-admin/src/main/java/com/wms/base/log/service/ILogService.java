package com.wms.base.log.service;

import com.wms.base.log.domain.TaskLog;
import java.util.List;

/**
 * 任务日志Service接口
 * 
 * @author assassin
 * @date 2021-06-07
 */
public interface ILogService 
{
    /**
     * 查询任务日志
     * 
     * @param rowid 任务日志ID
     * @return 任务日志
     */
    public TaskLog selectLogById(Long rowid);

    /**
     * 查询任务日志列表
     * 
     * @param taskLog 任务日志
     * @return 任务日志集合
     */
    public List<TaskLog> selectLogList(TaskLog taskLog);

    /**
     * 新增任务日志
     * 
     * @param taskLog 任务日志
     * @return 结果
     */
    public int insertLog(TaskLog taskLog);

    /**
     * 修改任务日志
     * 
     * @param taskLog 任务日志
     * @return 结果
     */
    public int updateLog(TaskLog taskLog);

    /**
     * 批量删除任务日志
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteLogByIds(String ids);

    /**
     * 删除任务日志信息
     * 
     * @param rowid 任务日志ID
     * @return 结果
     */
    public int deleteLogById(Long rowid);
}
