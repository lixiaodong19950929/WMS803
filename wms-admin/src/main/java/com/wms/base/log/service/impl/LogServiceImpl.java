package com.wms.base.log.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.wms.base.log.mapper.LogMapper;
import com.wms.base.log.domain.TaskLog;
import com.wms.base.log.service.ILogService;
import com.wms.common.core.text.Convert;

/**
 * 任务日志Service业务层处理
 * 
 * @author assassin
 * @date 2021-06-07
 */
@Service
public class LogServiceImpl implements ILogService 
{
    @Autowired
    private LogMapper logMapper;

    /**
     * 查询任务日志
     * 
     * @param rowid 任务日志ID
     * @return 任务日志
     */
    @Override
    public TaskLog selectLogById(Long rowid)
    {
        return logMapper.selectLogById(rowid);
    }

    /**
     * 查询任务日志列表
     * 
     * @param taskLog 任务日志
     * @return 任务日志
     */
    @Override
    public List<TaskLog> selectLogList(TaskLog taskLog)
    {
        return logMapper.selectLogList(taskLog);
    }

    /**
     * 新增任务日志
     * 
     * @param taskLog 任务日志
     * @return 结果
     */
    @Override
    public int insertLog(TaskLog taskLog)
    {
        return logMapper.insertLog(taskLog);
    }

    /**
     * 修改任务日志
     * 
     * @param taskLog 任务日志
     * @return 结果
     */
    @Override
    public int updateLog(TaskLog taskLog)
    {
        return logMapper.updateLog(taskLog);
    }

    /**
     * 删除任务日志对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteLogByIds(String ids)
    {
        return logMapper.deleteLogByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除任务日志信息
     * 
     * @param rowid 任务日志ID
     * @return 结果
     */
    public int deleteLogById(Long rowid)
    {
        return logMapper.deleteLogById(rowid);
    }
}
