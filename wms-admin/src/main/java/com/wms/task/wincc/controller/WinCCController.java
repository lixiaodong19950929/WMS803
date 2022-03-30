package com.wms.task.wincc.controller;

import com.wms.base.product.domain.Product;
import com.wms.common.core.controller.BaseController;
import com.wms.common.core.domain.AjaxResult;
import com.wms.common.utils.JsonUtils;
import com.wms.common.utils.StringUtils;
import com.wms.task.utils.ResponseUtils;
import com.wms.task.wincc.pojo.NoticeDto;
import com.wms.task.wincc.service.WinCCService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.naming.NameNotFoundException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;

/**
 * Created by Administrator on 2020/2/21.
 */
@Slf4j
@Controller
@RequestMapping("/api/wms")
public class WinCCController extends BaseController {
    private static final int DATA_STOCK_IN = 1;
    private static final int DATA_STOCK_OUT = 2;
    private static final int DATA_STOCK_TRANSFER = 3;
    private static final int DATA_STOCK_CHECK = 4;
    private static final int DATA_WAREHOUSE = 5;
    private static final int ORDER_SYNC = 5;
    private static final int DATA_WAREHOUSE_ZONE = 6;
    private static final int DATA_WAREHOUSE_CELL = 7;
    private static final int DATA_TRAY = 8;
    private static final int DATA_STOCK = 9;
    private static final int DATA_DEVICE = 10;
    private static final int DATA_PRODUCT = 11;

    private final WinCCService winCCService;

    @Autowired
    public WinCCController(WinCCService winCCService) {
        this.winCCService = winCCService;
    }

    @PostMapping("/notice_ex")
    @ResponseBody
    public Object noticeEx(@RequestHeader(value = "appId") String appId,
                           @RequestHeader(value = "appToken") String appToken,
                           @RequestBody NoticeDto noticeDto) throws Exception {

        log.info("########舜宇消息传入#######");
        log.info(noticeDto.toString());
        if (!"sunny".equals(appId))
            return AjaxResult.error("传入appId值有误");

        //根据sunny生成的秘钥
        if (!"533c5ba8368075db8f6ef201546bd71a".equals(appToken))
            return AjaxResult.error("传入秘钥有误");

        if (noticeDto==null) {
            return AjaxResult.error("传入参数失败");
        }

        if(noticeDto.getNoticeType()==null){
            return AjaxResult.error("数据类型不能为空");
        }
        if(StringUtils.isEmpty(noticeDto.getOuterOrderId())){
            return AjaxResult.error("订单编号不能为空");
        }
        //订单号写入数据库
        winCCService.insertOuterOrderId(noticeDto.getOuterOrderId());

        //异步调用舜宇数据接口
        winCCService.executeAsync(noticeDto);

//        switch (noticeDto.getNoticeType()) {
//            case DATA_STOCK_IN: {
//                return winCCService.noticeExStockIn(noticeDto);
//            }
//            case DATA_STOCK_OUT: {
//                return winCCService.noticeExStockOut(noticeDto);
//            }
//            case DATA_STOCK_TRANSFER: {
//                return winCCService.noticeExStockTransfer(noticeDto);
//            }
//            case DATA_STOCK_CHECK: {
//                return winCCService.noticeExStockCheck(noticeDto);
//            }
//            case ORDER_SYNC: {
//                return winCCService.noticeExOrderSync(noticeDto);
//            }
//        }
        return ResponseUtils.ok();
    }

    @RequestMapping("/data")
    @ResponseBody
    public Object data(
            @RequestHeader(value = "appId") String appId,
            @RequestHeader(value = "appToken") String appToken,
//            @RequestParam(value = "data_type", required = false) Integer dataType,
////            @RequestParam(value = "outer_order_id", required = false) String orderId,
////            @RequestParam(value = "TaskCode", required = false) String taskCode,
////            @RequestParam(value = "page_index", required = false, defaultValue = "1") Integer pageIndex,
////            @RequestParam(value = "page_size", required = false, defaultValue = "5") Integer pageSize,
            @RequestBody HashMap<String,Object> params) throws Exception {
        log.info("########获取产品执行信息#######");
        int dataType=Integer.valueOf(params.get("notice_type").toString());
        String taskCode=null;
        if(params.get("TaskCode")!=null)
            taskCode=params.get("TaskCode").toString();
        int pageIndex=1;
        int pageSize=10;
//        if (pageIndex < 1)
//            throw new IllegalArgumentException("Page size must be larger than 0!");

        if (!"sunny".equals(appId))
            throw new Exception("传入appId值有误");

        //根据sunny生成的秘钥
        if (!"533c5ba8368075db8f6ef201546bd71a".equals(appToken))
            throw new Exception("传入秘钥有误");

        switch (dataType) {
            case DATA_STOCK_IN:
                return winCCService.dataStockIn(taskCode, pageIndex, pageSize);
            case DATA_STOCK_OUT:
                return winCCService.dataStockOut(taskCode, pageIndex, pageSize);
            case DATA_STOCK_TRANSFER:
                return winCCService.dataStockTransfer(taskCode, pageIndex, pageSize);
            case DATA_STOCK_CHECK:
                return winCCService.dataStockCheck(taskCode, pageIndex, pageSize);
            case DATA_WAREHOUSE:
                return winCCService.dataWareHouse(pageIndex, pageSize);
            case DATA_WAREHOUSE_ZONE:
                return winCCService.dataRegion(pageIndex, pageSize);
            case DATA_WAREHOUSE_CELL:
                return winCCService.dataStorehouse(pageIndex, pageSize);
            case DATA_TRAY:
                return winCCService.dataTray(pageIndex, pageSize);
            case DATA_STOCK:
                return winCCService.dataStock(pageIndex, pageSize);
            case DATA_DEVICE:
                return winCCService.dataDevice(pageIndex, pageSize);
            case DATA_PRODUCT:
                Product product=JsonUtils.map2pojo(params,Product.class);
                return winCCService.dataProduct(product);
        }
        return null;
    }

    @RequestMapping("/notice_wcs")
    @ResponseBody
    public Object noticeWCS( @RequestHeader(value = "appId") String appId,
                             @RequestHeader(value = "appToken") String appToken,
                             @RequestBody NoticeDto noticeDto) throws Exception {
        log.info("########通知WCS执行完成#######");
        if (!"sunny".equals(appId))
            throw new Exception("传入appId值有误");

        //根据sunny生成的秘钥
        if (!"533c5ba8368075db8f6ef201546bd71a".equals(appToken))
            throw new Exception("传入秘钥有误");

//        int noticeType= Integer.valueOf(params.get("notice_type").toString());
//        String taskCode=params.get("TaskCode").toString();
//        int pageIndex=1;
//        int pageSize=10;
//
//        NoticeDto noticeDto=new NoticeDto();
//        noticeDto.setNoticeType(Integer.valueOf(noticeType));

//        String outerOrderId=null;
//        switch (noticeType) {
//            case DATA_STOCK_IN:
//                outerOrderId = winCCService.queryOrderByStockIn(taskCode, pageIndex, pageSize);
//                break;
//            case DATA_STOCK_OUT:
//                outerOrderId = winCCService.queryOrderByStockOut(taskCode, pageIndex, pageSize);
//                break;
//            case DATA_STOCK_TRANSFER:
//                outerOrderId = winCCService.queryOrderByTransfer(taskCode, pageIndex, pageSize);
//                break;
//            case DATA_STOCK_CHECK:
//                outerOrderId = winCCService.queryOrderByStockCheck(taskCode, pageIndex, pageSize);
//                break;
//        }

        winCCService.noticeWCS(noticeDto.getNoticeType().toString(),noticeDto.getTaskCode());

        return ResponseUtils.ok();


    }


    /***
     * 临时处理舜宇单据
     * @param noticeDto
     * @return
     */
    @RequestMapping("/processing")
    @ResponseBody
    public AjaxResult processing(@RequestBody NoticeDto noticeDto){
        if (StringUtils.isEmpty(noticeDto.getOuterOrderId())){
            return AjaxResult.error("舜宇订单号为空");
        }
        if (noticeDto.getNoticeType()==null){
            return AjaxResult.error("订单类型为空");
        }
        return winCCService.processing(noticeDto);
    }


    /**
     * 出入库
     * @param noticeDto
     * @return
     */
    @RequestMapping("/processingIOSync")
    @ResponseBody
    public AjaxResult processingIOSync(@RequestBody NoticeDto noticeDto){
        log.info("\r\n"+"====>"+noticeDto.toString());
//        if (StringUtils.isEmpty(noticeDto.getOuterOrderId())){
//            return AjaxResult.error("舜宇订单号为空");
//        }
        if (noticeDto.getNoticeType()==null){
            return AjaxResult.error("订单类型为空");
        }

        return  winCCService.processingIOSync(noticeDto);
    }

//    /**
//     * @param noticeDto
//     * @Description 测试接口：返回订单信息移库
//     */
//    @PostMapping(value = "/processingMoveSync")
//    @ResponseBody
//    public AjaxResult processingMoveSync(@RequestBody NoticeDto noticeDto){
//        log.info("\r\n"+"====>"+noticeDto.toString());
//        log.info(noticeDto.toString());
//        if (StringUtils.isEmpty(noticeDto.getOuterOrderId())){
//            return AjaxResult.error("舜宇订单号为空");
//        }
//        if (noticeDto.getNoticeType()==null){
//            return AjaxResult.error("订单类型为空");
//        }
//        return winCCService.processingMoveSync(noticeDto);
//    }

    /**
     * @param noticeDto
     * @Description 接口测试 : wcs完成任务后，返回订单编号
     */
    @PostMapping(value = "/processingWcsOkSync")
    @ResponseBody
    public AjaxResult processingWcsOkSync(@RequestBody NoticeDto noticeDto) {
        log.info("\r\n" + "====>" + noticeDto.toString());
        log.info(noticeDto.toString());
//        if (StringUtils.isEmpty(noticeDto.getOuterOrderId())) {
//            return AjaxResult.error("舜宇订单号为空");
//        }
        if (noticeDto.getNoticeType() == null) {
            return AjaxResult.error("订单类型为空");
        }
        return winCCService.processingWcsOkSync(noticeDto);
    }


    /**
     * 移库接口
     * @param noticeDto
     * @return
     */

    @PostMapping(value = "/processingWcsStoreMoveSync")
    @ResponseBody
    public AjaxResult processingWcsStoreMoveSync(@RequestBody NoticeDto noticeDto) {
        log.info("\r\n" + "====>" + noticeDto.toString());
        log.info(noticeDto.toString());
//        if (StringUtils.isEmpty(noticeDto.getOuterOrderId())) {
//            return AjaxResult.error("舜宇订单号为空");
//        }
        if (noticeDto.getNoticeType() == null) {
            return AjaxResult.error("订单类型为空");
        }
        return winCCService.processingWcsStoreMoveSync(noticeDto);
    }

    /**
     * 盘库接口
     * @param noticeDto
     * @return
     */
    @PostMapping(value = "/processingWcsCheckSync")
    @ResponseBody
    public AjaxResult processingWcsCheckSync(@RequestBody NoticeDto noticeDto) {
        log.info("\r\n" + "====>" + noticeDto.toString());
        log.info(noticeDto.toString());
//        if (StringUtils.isEmpty(noticeDto.getOuterOrderId())) {
//            return AjaxResult.error("舜宇订单号为空");
//        }
        if (noticeDto.getNoticeType() == null) {
            return AjaxResult.error("订单类型为空");
        }
        return winCCService.processingWcsCheckSync(noticeDto);
    }
}
