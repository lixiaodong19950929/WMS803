package com.wms.base.warehouse.service.impl;

import java.util.List;

import com.wms.base.warehouse.domain.Warehouse;
import com.wms.base.warehouse.mapper.WarehouseMapper;
import com.wms.common.constant.BaseConstants;
import com.wms.common.core.text.Convert;
import com.wms.common.utils.StringUtils;
import com.wms.common.utils.ToolsUtils;
import com.wms.base.warehouse.service.IWarehouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 仓库Service业务层处理
 * 
 * @author åºå®¢
 * @date 2019-12-27
 */
@Service
public class WarehouseServiceImpl implements IWarehouseService
{
    @Autowired

    private WarehouseMapper warehouseMapper;

    /**
     * 查询仓库
     * 
     * @param whcode 仓库ID
     * @return 仓库
     */
    @Override
    public Warehouse selectWarehouseById(String whcode)
    {
        return warehouseMapper.selectWarehouseById(whcode);
    }

    /**
     * 查询仓库列表
     * 
     * @param warehouse 仓库
     * @return 仓库
     */
    @Override
    public List<Warehouse> selectWarehouseList(Warehouse warehouse)
    {
        return warehouseMapper.selectWarehouseList(warehouse);
    }

    /**
     * 新增仓库
     * 
     * @param warehouse 仓库
     * @return 结果
     */
    @Override
    public int insertWarehouse(Warehouse warehouse)
    {
        String Whcode=warehouseMapper.selectLastWhCode();
        if (StringUtils.isEmpty(Whcode)){
            warehouse.setWhcode(ToolsUtils.getNewDeviceNo("WH","0000"));
        }else{
            warehouse.setWhcode(ToolsUtils.getNewDeviceNo("WH",Whcode.substring(2)));
        }
        return warehouseMapper.insertWarehouse(warehouse);
    }

    /**
     * 修改仓库
     * 
     * @param warehouse 仓库
     * @return 结果
     */
    @Override
    public int updateWarehouse(Warehouse warehouse)
    {
        return warehouseMapper.updateWarehouse(warehouse);
    }

    /**
     * 删除仓库对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteWarehouseByIds(String ids)
    {
        return warehouseMapper.deleteWarehouseByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除仓库信息
     * 
     * @param whcode 仓库ID
     * @return 结果
     */
    public int deleteWarehouseById(String whcode)
    {
        return warehouseMapper.deleteWarehouseById(whcode);
    }

    @Override
    public String checkCbarCode(Warehouse warehouse) {
        Long id=StringUtils.isNull(warehouse.getId())?-1L:warehouse.getId();
        Warehouse info=warehouseMapper.checkCbarCode(warehouse.getCbarcode());
        if (StringUtils.isNotNull(info) && info.getId().longValue()!=id.longValue()){
            return BaseConstants.WAREHOUSE_CBARCODE_NOT_UNIQUE;
        }
        return BaseConstants.WAREHOUSE_CBARCODE_UNIQUE;
    }

    @Override
    public List<Warehouse> selectWarehouseDict(){
        return warehouseMapper.selectWarehouseDict();
    }
}
