package com.wms.warehouse.sern.mapper;

import com.wms.warehouse.sern.domain.TrayCodeDetail;
import com.wms.warehouse.sern.domain.TrayCodeDetailEX;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TrayCodeDetailMapper {
    /**
     * 查询条码信息明细
     *
     * @param id 条码信息明细ID
     * @return 条码信息明细
     */
    public TrayCodeDetail selectTrayCodeDetailById(Long id);

    /**
     * 查询条码信息明细列表
     *
     * @param trayCodeDetail 条码信息明细
     * @return 条码信息明细集合
     */
    public List<TrayCodeDetail> selectTrayCodeDetailList(TrayCodeDetail trayCodeDetail);

    /**
     * 新增条码信息明细
     *
     * @param trayCodeDetail 条码信息明细
     * @return 结果
     */
    public int insertTrayCodeDetail(TrayCodeDetail trayCodeDetail);

    /**
     * 修改条码信息明细
     *
     * @param trayCodeDetail 条码信息明细
     * @return 结果
     */
    public int updateTrayCodeDetail(TrayCodeDetail trayCodeDetail);

    /**
     * 删除条码信息明细
     *
     * @param id 条码信息明细ID
     * @return 结果
     */
    public int deleteTrayCodeDetailById(Long id);

    /**
     * 批量删除条码信息明细
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteTrayCodeDetailByIds(String[] ids);

    //根据任务号和批次和产品编码查询已经存入的条码
    public List<TrayCodeDetail> selectBarcodeListForOut(@Param("productBatch") String productBatch, @Param("productCode")String productCode);

    //出库添加产品后显示产品对应批次的库存数量
    public List<TrayCodeDetail> selectProductListForSumsByCodeBatch();

    //导出所有的产品库存
    public List<TrayCodeDetailEX> selectTrayCodeDetailExcelList();

    //库存导入时根据库位号逐条更新
    public int deleteAndInsertTraycodeDetailByStorehouseCode(List<TrayCodeDetailEX> trayCodeDetailEXList);

    public List<TrayCodeDetailEX> deleteAllTraycodeDetail();

    public int insertTrayCodeDetailEX(TrayCodeDetailEX TrayCodeDetailEX);
}
