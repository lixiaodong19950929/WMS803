package com.wms.task.wincc.service;

import com.wms.base.product.domain.Product;
import com.wms.common.core.domain.AjaxResult;
import com.wms.task.wincc.pojo.NoticeDto;

import java.io.IOException;

/**
 * Created by Administrator on 2020/2/21.
 */
public interface WinCCService {
    Object noticeEx(NoticeDto noticeDto);

    Object noticeExStockIn(NoticeDto noticeDto) throws Exception;

    Object noticeExStockOut(NoticeDto noticeDto);

    Object noticeExStockTransfer(NoticeDto noticeDto) throws Exception;

    Object noticeExStockCheck(NoticeDto noticeDto);

    Object noticeExOrderSync(NoticeDto noticeDto);

    Object dataStockIn(String taskCode, Integer pageIndex, Integer pageSize);

    Object dataStockOut(String orderId, Integer pageIndex, Integer pageSize);

    Object dataStockTransfer(String orderId, Integer pageIndex, Integer pageSize);

    Object dataStockCheck(String orderId, Integer pageIndex, Integer pageSize);

    Object dataWareHouse(Integer pageIndex, Integer pageSize);

    Object dataRegion(Integer pageIndex, Integer pageSize);

    Object dataStorehouse(Integer pageIndex, Integer pageSize);

    Object dataTray(Integer pageIndex, Integer pageSize);

    Object dataStock(Integer pageIndex, Integer pageSize);

    Object dataDevice(Integer pageIndex, Integer pageSize);

    Object insertOuterOrderId(String outerOrderId);

    Object executeAsync(NoticeDto noticeDto) throws Exception;

    Object noticeWCS(String noticeType,String taskCode);

    String queryOrderByStockIn(String taskCode, Integer pageIndex, Integer pageSize);

    String queryOrderByStockOut(String taskCode, Integer pageIndex, Integer pageSize);

    String queryOrderByTransfer(String taskCode, Integer pageIndex, Integer pageSize);

    String queryOrderByStockCheck(String taskCode, Integer pageIndex, Integer pageSize);

    AjaxResult processing(NoticeDto noticeDto);

    Object dataProduct(Product product);

    /**
     * 返回订单信息入库
     * @param noticeDto
     * @return
     */
    AjaxResult processingIOSync(NoticeDto noticeDto);

    /**
     * 返回订单信息移库
     * @param noticeDto
     * @return
     */
    AjaxResult processingMoveSync(NoticeDto noticeDto);

    /**
     * wcs完成任务后，返回订单编号
     * @param noticeDto
     * @return
     */
    AjaxResult processingWcsOkSync(NoticeDto noticeDto);

    AjaxResult processingWcsCheckSync(NoticeDto noticeDto);
    AjaxResult processingWcsStoreMoveSync(NoticeDto noticeDto);




}
