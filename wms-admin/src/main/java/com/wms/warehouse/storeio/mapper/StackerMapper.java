package com.wms.warehouse.storeio.mapper;

import com.wms.warehouse.storecheck.domain.StoreCheckSon;
import com.wms.warehouse.storeio.domain.Stacker;
import com.wms.warehouse.storeio.domain.StoreIoSon;
import com.wms.warehouse.storemove.domain.StoreMoveSon;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 堆垛机任务Mapper接口
 * 
 * @author assassin
 * @date 2020-01-03
 */
public interface StackerMapper 
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
     * 删除堆垛机任务
     * 
     * @param id 堆垛机任务ID
     * @return 结果
     */
    public int deleteStackerById(Long id);

    /**
     * 批量删除堆垛机任务
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteStackerByIds(String[] ids);

    int insertStackerList(List<Stacker> stackerList);

    int deleteStackerByList(List<Stacker> deleteStacker);

    int deleteStackerByStoreIoSon(@Param("list") List<StoreIoSon> storeIoSonList, @Param("userName") String userName);

    int deleteStackerByTaskCodes(String[] toStrArray);

    int  cancelStackerByTaskCodes(String[] toStrArray);

    int  executeStackerByTaskCodes(String[] toStrArray);

    int restartStackerByTaskCodes(String[] toStrArray);

    int deleteStackerByStoreMoveSon(@Param("list")List<StoreMoveSon> storeMoveSonList, @Param("userName") String userName);

    int deleteStackerByStoreCheckSon(@Param("list") List<StoreCheckSon> storeCheckSonList, @Param("userName") String userName);
}
