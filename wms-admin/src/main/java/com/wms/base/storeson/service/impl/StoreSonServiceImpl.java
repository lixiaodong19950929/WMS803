package com.wms.base.storeson.service.impl;

import java.util.List;

import com.wms.base.storeson.domain.StoreSon;
import com.wms.base.storeson.mapper.StoreSonMapper;
import com.wms.base.storeson.service.IStoreSonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.wms.common.core.text.Convert;

/**
 * 出库物料明细Service业务层处理
 * 
 * @author assassin
 * @date 2021-08-25
 */
@Service
public class StoreSonServiceImpl implements IStoreSonService
{
    @Autowired
    private StoreSonMapper storeSonMapper;

    /**
     * 查询出库物料明细
     * 
     * @param id 出库物料明细ID
     * @return 出库物料明细
     */
    @Override
    public StoreSon selectStoreSonById(Long id)
    {
        return storeSonMapper.selectStoreSonById(id);
    }

    /**
     * 查询出库物料明细列表
     * 
     * @param storeSon 出库物料明细
     * @return 出库物料明细
     */
    @Override
    public List<StoreSon> selectStoreSonList(StoreSon storeSon)
    {
        return storeSonMapper.selectStoreSonList(storeSon);
    }

    /**
     * 新增出库物料明细
     * 
     * @param storeSon 出库物料明细
     * @return 结果
     */
    @Override
    public int insertStoreSon(StoreSon storeSon)
    {
        return storeSonMapper.insertStoreSon(storeSon);
    }

    /**
     * 修改出库物料明细
     * 
     * @param storeSon 出库物料明细
     * @return 结果
     */
    @Override
    public int updateStoreSon(StoreSon storeSon)
    {
        return storeSonMapper.updateStoreSon(storeSon);
    }

    /**
     * 删除出库物料明细对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteStoreSonByIds(String ids)
    {
        return storeSonMapper.deleteStoreSonByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除出库物料明细信息
     * 
     * @param id 出库物料明细ID
     * @return 结果
     */
    public int deleteStoreSonById(Long id)
    {
        return storeSonMapper.deleteStoreSonById(id);
    }

    @Override
    public int insertStoreSons(List<StoreSon> storeSons) {
        deleteStoreSonByTaskAndPro(storeSons.get(0).getTaskcode(),storeSons.get(0).getProductcode());
        System.out.println("打印！！！");
        System.out.println(storeSons);
        return storeSonMapper.insertStoreSons(storeSons);
    }
    @Override
    public List<StoreSon> deleteStoreSonByTaskAndPro(String taskcode, String productcode) {
        return storeSonMapper.deleteStoreSonByTaskAndPro(taskcode,productcode);
    }

    @Override
    public int executeStoreSonByTaskCodes(String taskcodes) {
        return storeSonMapper.executeStoreSonByTaskCodes(Convert.toStrArray(taskcodes));
    }


    @Override
    public List<StoreSon> getOutTaskInfoByTaskProduct(String taskcode, String productcode) {
        return storeSonMapper.getOutTaskInfoByTaskProduct(taskcode, productcode);
    }

    @Override
    public List<StoreSon> getInTaskInfoByTaskCode(String taskcode) {
        return storeSonMapper.getInTaskInfoByTaskCode(taskcode);
    }
}
