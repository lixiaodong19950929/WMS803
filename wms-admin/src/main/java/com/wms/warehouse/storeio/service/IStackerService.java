package com.wms.warehouse.storeio.service;

import com.wms.warehouse.storeio.domain.Stacker;
import java.util.List;

/**
 * 堆垛机任务Service接口
 * 
 * @author assassin
 * @date 2020-01-03
 */
public interface IStackerService 
{
    /**
     * 查询堆垛机任务
     * 
     * @param id 堆垛机任务ID
     * @return 堆垛机任务
     */
    public Stacker selectStackerById(Long id);

    /**
     * 查询堆垛机任务列表
     * 
     * @param stacker 堆垛机任务
     * @return 堆垛机任务集合
     */
    public List<Stacker> selectStackerList(Stacker stacker);

    /**
     * 新增堆垛机任务
     * 
     * @param stacker 堆垛机任务
     * @return 结果
     */
    public int insertStacker(Stacker stacker);

    /**
     * 修改堆垛机任务
     * 
     * @param stacker 堆垛机任务
     * @return 结果
     */
    public int updateStacker(Stacker stacker);

    /**
     * 批量删除堆垛机任务
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteStackerByIds(String ids);

    /**
     * 删除堆垛机任务信息
     * 
     * @param id 堆垛机任务ID
     * @return 结果
     */
    public int deleteStackerById(Long id);
}
