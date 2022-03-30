package com.wms.warehouse.sern.service.impl;

import com.wms.warehouse.sern.domain.TrayCodeDetail;
import com.wms.warehouse.sern.domain.TrayCodeDetailEX;
import com.wms.warehouse.sern.mapper.TrayCodeDetailMapper;
import com.wms.warehouse.sern.service.TrayCodeDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.wms.common.core.text.Convert;

import java.util.ArrayList;
import java.util.List;
@Service
public class TrayCodeDetailServiceImpl implements TrayCodeDetailService {

    @Autowired
    private TrayCodeDetailMapper trayCodeDetailMapper;

    /**
     * 查询条码信息明细
     *
     * @param id 条码信息明细ID
     * @return 条码信息明细
     */
    @Override
    public TrayCodeDetail selectTrayCodeDetailById(Long id)
    {
        return trayCodeDetailMapper.selectTrayCodeDetailById(id);
    }

    /**
     * 查询条码信息明细列表
     *
     * @param trayCodeDetail 条码信息明细
     * @return 条码信息明细
     */
    @Override
    public List<TrayCodeDetail> selectTrayCodeDetailList(TrayCodeDetail trayCodeDetail)
    {
        return trayCodeDetailMapper.selectTrayCodeDetailList(trayCodeDetail);
    }

    /**
     * 新增条码信息明细
     *
     * @param trayCodeDetail 条码信息明细
     * @return 结果
     */
    @Override
    public int insertTrayCodeDetail(TrayCodeDetail trayCodeDetail)
    {
        return trayCodeDetailMapper.insertTrayCodeDetail(trayCodeDetail);
    }

    /**
     * 修改条码信息明细
     *
     * @param trayCodeDetail 条码信息明细
     * @return 结果
     */
    @Override
    public int updateTrayCodeDetail(TrayCodeDetail trayCodeDetail)
    {
        return trayCodeDetailMapper.updateTrayCodeDetail(trayCodeDetail);
    }

    /**
     * 删除条码信息明细对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteTrayCodeDetailByIds(String ids)
    {
        return trayCodeDetailMapper.deleteTrayCodeDetailByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除条码信息明细信息
     *
     * @param id 条码信息明细ID
     * @return 结果
     */
    public int deleteTrayCodeDetailById(Long id)
    {
        return trayCodeDetailMapper.deleteTrayCodeDetailById(id);
    }

    @Override
    public List<TrayCodeDetail> selectBarcodeListForOut(String productBatch, String productCode) {
        System.out.println(productBatch);
        System.out.println(productCode);
        return trayCodeDetailMapper.selectBarcodeListForOut(productBatch,productCode);
    }

    /**
     * 出库添加产品后显示产品对应批次的库存数量
     */
    @Override
    public List<TrayCodeDetail> selectProductListForSumsByCodeBatch()
    {
        return trayCodeDetailMapper.selectProductListForSumsByCodeBatch();
    }

    @Override
    public List<TrayCodeDetailEX> selectTrayCodeDetailExcelList() {
        return trayCodeDetailMapper.selectTrayCodeDetailExcelList();
    }

    ////库存导入时根据库位号逐条更新
    @Override
    public int deleteAndInsertTraycodeDetailByStorehouseCode(List<TrayCodeDetailEX> trayCodeDetailEXList) {
        if (trayCodeDetailEXList.size()>0){
            System.out.println("676767676767676767");
            deleteAllTraycodeDetail();
            for(int i = 0;i < trayCodeDetailEXList.size(); i++){
//                storehouseMapper.updateListByStorehouseCode(storehouseExList.get(i));
                    trayCodeDetailMapper.insertTrayCodeDetailEX(trayCodeDetailEXList.get(i));
//                ArrayList<Object> a = new ArrayList<>();
            }

            return 1;
        }
        return 0;
    }

    @Override
    public List<TrayCodeDetailEX> deleteAllTraycodeDetail() {
        return trayCodeDetailMapper.deleteAllTraycodeDetail();
    }

}
