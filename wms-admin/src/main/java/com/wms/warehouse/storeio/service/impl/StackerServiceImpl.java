package com.wms.warehouse.storeio.service.impl;

import java.util.List;

import com.wms.common.core.text.Convert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.wms.warehouse.storeio.mapper.StackerMapper;
import com.wms.warehouse.storeio.domain.Stacker;
import com.wms.warehouse.storeio.service.IStackerService;

/**
 * 堆垛机任务Service业务层处理
 * 
 * @author assassin
 * @date 2020-01-03
 */
@Service
public class StackerServiceImpl implements IStackerService 
{
    @Autowired
    private StackerMapper stackerMapper;

    /**
     * 查询堆垛机任务
     * 
     * @param id 堆垛机任务ID
     * @return 堆垛机任务
     */
    @Override
    public Stacker selectStackerById(Long id)
    {
        return stackerMapper.selectStackerById(id);
    }

    /**
     * 查询堆垛机任务列表
     * 
     * @param stacker 堆垛机任务
     * @return 堆垛机任务
     */
    @Override
    public List<Stacker> selectStackerList(Stacker stacker)
    {
        return stackerMapper.selectStackerList(stacker);
    }

    /**
     * 新增堆垛机任务
     * 
     * @param stacker 堆垛机任务
     * @return 结果
     */
    @Override
    public int insertStacker(Stacker stacker)
    {
        return stackerMapper.insertStacker(stacker);
    }

    /**
     * 修改堆垛机任务
     * 
     * @param stacker 堆垛机任务
     * @return 结果
     */
    @Override
    public int updateStacker(Stacker stacker)
    {
        return stackerMapper.updateStacker(stacker);
    }

    /**
     * 删除堆垛机任务对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteStackerByIds(String ids)
    {
        return stackerMapper.deleteStackerByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除堆垛机任务信息
     * 
     * @param id 堆垛机任务ID
     * @return 结果
     */
    public int deleteStackerById(Long id)
    {
        return stackerMapper.deleteStackerById(id);
    }
}
