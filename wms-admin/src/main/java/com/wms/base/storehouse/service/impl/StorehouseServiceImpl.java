package com.wms.base.storehouse.service.impl;

import com.wms.base.employee.domain.Employee;
import com.wms.base.storehouse.domain.Storehouse;
import com.wms.base.storehouse.domain.StorehouseEx;
import com.wms.base.storehouse.mapper.StorehouseMapper;
import com.wms.base.storehouse.mapper.StorehouseExMapper;
import com.wms.base.storehouse.service.IStorehouseService;
import com.wms.common.constant.BaseConstants;
import com.wms.common.core.text.Convert;
import com.wms.common.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * 库位Service业务层处理
 * 
 * @author dkwms
 * @date 2020-01-03
 */
@Service
public class StorehouseServiceImpl implements IStorehouseService
{
    @Autowired
    private StorehouseMapper storehouseMapper;

    @Autowired
    private StorehouseExMapper storehouseExMapper;

    /**
     * 查询库位
     * 
     * @param id 库位id
     * @return 库位
     */
    @Override
    public Storehouse selectStorehouseById(String id)
    {
        return storehouseMapper.selectStorehouseById(id);
    }

    /**
     * 查询库位列表
     * 
     * @param storehouse 库位
     * @return 库位
     */
    @Override
    public List<Storehouse> selectStorehouseList(Storehouse storehouse)
    {
        return storehouseMapper.selectStorehouseList(storehouse);
    }
    /**
     * 查询库位列表
     *
     * @param storehouse 库位
     * @return 库位
     */
    @Override
    public List<Storehouse> selectStorehouseTrayList(Storehouse storehouse)
    {
        return storehouseMapper.selectStorehouseTrayList(storehouse);
    }

    @Override
    public List<Storehouse> selectStorehouseTrayOutList(Storehouse storehouse) {
        return storehouseMapper.selectStorehouseTrayOutList(storehouse);
    }

    @Override
    public List<Storehouse> selectSums() {
        return storehouseMapper.selectSums();
    }

    @Override
    public List<Storehouse> selectProductList(Storehouse storehouse) {
        return storehouseMapper.selectProductList(storehouse);
    }

    /**
     * 新增库位
     * 
     * @param storehouse 库位
     * @return 结果
     */
    @Override
    public int insertStorehouse(Storehouse storehouse)
    {
        return storehouseMapper.insertStorehouse(storehouse);
    }

    /**
     * 修改库位
     * 
     * @param storehouse 库位
     * @return 结果
     */
    @Override
    public int updateStorehouse(Storehouse storehouse)
    {
        return storehouseMapper.updateStorehouse(storehouse);
    }

    /**
     * 删除库位对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteStorehouseByIds(String ids)
    {
        return storehouseMapper.deleteStorehouseByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除库位信息
     * 
     * @param whcode 库位ID
     * @return 结果
     */
    public int deleteStorehouseById(String whcode)
    {
        return storehouseMapper.deleteStorehouseById(whcode);
    }

    @Override
    public List<Storehouse> getStorehouseByWhcode(String whcode) {
        return storehouseMapper.getStorehouseByWhcode(whcode);
    }

    @Override
    public List<Storehouse> selectStorehouseCode(String regioncode) {
        return storehouseMapper.selectStorehouseCode(regioncode);
    }

    @Override
    public String getStorehouseCode(Storehouse storehouse) {
        Long userId = StringUtils.isNull(storehouse.getId()) ? -1L : storehouse.getId();
        Storehouse info = storehouseMapper.getStorehouseCode(storehouse.getStorehousecode());
        if (StringUtils.isNotNull(info) && info.getId().longValue() != userId.longValue())
        {
            return BaseConstants.STOREHOUSE_STOREHOUSECODE_NOT_UNIQUE;
        }
        return BaseConstants.STOREHOUSE_STOREHOUSECODE_UNIQUE;
    }


    @Override
    public List<StorehouseEx> selectStorehouseExcelList(Storehouse storehouse) {
        return storehouseMapper.selectStorehouseExcelList(storehouse);
    }

    ////库存导入时先删再插入(未使用）
    @Override
    public int deleteAndInsertsList(List<StorehouseEx> storehouseExList) {
//        barcodelistMapper.deleteBarcodeList(barcodelist.getRowId(),barcodelist.getTaskCode());
        if (storehouseExList.size()>0){
            deleteAllStorehouse();
            //由于数据库参数长度限制，将370个数据拆分成四次插入数据库
            List<StorehouseEx> a = new ArrayList<>();
            for(int i = 0;i < 100; i++){
                a.add(storehouseExList.get(i));
            }
            storehouseMapper.insertstorehouseLists(a);
            List<StorehouseEx> b = new ArrayList<>();
            for(int j = 100;j < 200; j++){
                b.add(storehouseExList.get(j));
            }
            storehouseMapper.insertstorehouseLists(b);
            List<StorehouseEx> c = new ArrayList<>();
            for(int k = 200;k < 300; k++){
                c.add(storehouseExList.get(k));
            }
            storehouseMapper.insertstorehouseLists(c);
            List<StorehouseEx> d = new ArrayList<>();
            for(int l = 300;l < 370; l++){
                d.add(storehouseExList.get(l));
            }
            return storehouseMapper.insertstorehouseLists(d);
        }
        return 0;
    }

    ////库存导入时根据库位号逐条更新
    @Override
    public int updateListByStorehouseCode(List<StorehouseEx> storehouseExList) {
        if (storehouseExList.size()>0){
            for(int i = 0;i < storehouseExList.size(); i++){
                storehouseMapper.updateListByStorehouseCode(storehouseExList.get(i));
            }
            return 1;
        }
        return 0;
    }

    @Override
    public List<StorehouseEx> deleteAllStorehouse() {
        return storehouseMapper.deleteAllStorehouse();
    }


}
