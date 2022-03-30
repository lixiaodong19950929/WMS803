package com.wms.warehouse.storeio.service.impl;

import java.util.List;

import com.wms.common.core.text.Convert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.wms.warehouse.storeio.mapper.RgvMapper;
import com.wms.warehouse.storeio.domain.Rgv;
import com.wms.warehouse.storeio.service.IRgvService;

/**
 * RGV任务Service业务层处理
 * 
 * @author assassin
 * @date 2020-01-03
 */
@Service
public class RgvServiceImpl implements IRgvService 
{
    @Autowired
    private RgvMapper rgvMapper;

    /**
     * 查询RGV任务
     * 
     * @param id RGV任务ID
     * @return RGV任务
     */
    @Override
    public Rgv selectRgvById(Long id)
    {
        return rgvMapper.selectRgvById(id);
    }

    /**
     * 查询RGV任务列表
     * 
     * @param rgv RGV任务
     * @return RGV任务
     */
    @Override
    public List<Rgv> selectRgvList(Rgv rgv)
    {
        return rgvMapper.selectRgvList(rgv);
    }

    /**
     * 新增RGV任务
     * 
     * @param rgv RGV任务
     * @return 结果
     */
    @Override
    public int insertRgv(Rgv rgv)
    {
        return rgvMapper.insertRgv(rgv);
    }

    /**
     * 修改RGV任务
     * 
     * @param rgv RGV任务
     * @return 结果
     */
    @Override
    public int updateRgv(Rgv rgv)
    {
        return rgvMapper.updateRgv(rgv);
    }

    /**
     * 删除RGV任务对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteRgvByIds(String ids)
    {
        return rgvMapper.deleteRgvByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除RGV任务信息
     * 
     * @param id RGV任务ID
     * @return 结果
     */
    public int deleteRgvById(Long id)
    {
        return rgvMapper.deleteRgvById(id);
    }
}
