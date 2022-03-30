package com.wms.warehouse.storeio.mapper;

import com.wms.warehouse.storecheck.domain.StoreCheckSon;
import com.wms.warehouse.storeio.domain.Rgv;
import com.wms.warehouse.storeio.domain.StoreIoSon;
import com.wms.warehouse.storemove.domain.StoreMoveSon;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * RGV任务Mapper接口
 * 
 * @author assassin
 * @date 2020-01-03
 */
public interface RgvMapper 
{
    /**
     * 查询RGV任务
     * 
     * @param id RGV任务ID
     * @return RGV任务
     */
    public Rgv selectRgvById(Long id);

    /**
     * 查询RGV任务列表
     * 
     * @param rgv RGV任务
     * @return RGV任务集合
     */
    public List<Rgv> selectRgvList(Rgv rgv);

    /**
     * 新增RGV任务
     * 
     * @param rgv RGV任务
     * @return 结果
     */
    public int insertRgv(Rgv rgv);

    /**
     * 修改RGV任务
     * 
     * @param rgv RGV任务
     * @return 结果
     */
    public int updateRgv(Rgv rgv);

    /**
     * 删除RGV任务
     * 
     * @param id RGV任务ID
     * @return 结果
     */
    public int deleteRgvById(Long id);

    /**
     * 批量删除RGV任务
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteRgvByIds(String[] ids);

    int insertRgvList(List<Rgv> rgvList);

    int deleteRgvByList(List<Rgv> deleteRgv);

    int deleteRgvByStoreIoSon(@Param("list") List<StoreIoSon> storeIoSonList, @Param("userName") String userName);

    int deleteRgvByTaskCodes(String[] toStrArray);

    int cancelRgvByTaskCodes(String[] toStrArray);

    int executeRgvByTaskCodes(String[] toStrArray);

    int restartRgvByTaskCodes(String[] toStrArray);

    int deleteRgvByStoreMoveSon(@Param("list")List<StoreMoveSon> storeMoveSonList,  @Param("userName") String userName);

    int deleteRgvByStoreCheckSon(@Param("list")List<StoreCheckSon> storeCheckSonList, @Param("userName")String userName);
}
