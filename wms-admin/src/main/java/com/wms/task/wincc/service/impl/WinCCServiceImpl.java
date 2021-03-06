package com.wms.task.wincc.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.wms.base.device.domain.DeviceEx;
import com.wms.base.device.mapper.DeviceExMapper;
import com.wms.base.product.domain.Product;
import com.wms.base.product.mapper.ProductMapper;
import com.wms.base.region.domain.RegionEx;
import com.wms.base.region.mapper.RegionExMapper;
import com.wms.base.storehouse.domain.StorehouseEx;
import com.wms.base.storehouse.mapper.StorehouseExMapper;
import com.wms.base.tray.domain.TrayEx;
import com.wms.base.tray.mapper.TrayExMapper;
import com.wms.base.warehouse.domain.WarehouseEx;
import com.wms.base.warehouse.mapper.WarehouseExMapper;
import com.wms.common.core.domain.AjaxResult;
import com.wms.common.pagebean.PageBean;
import com.wms.common.utils.JsonUtils;
import com.wms.common.utils.StringUtils;
import com.wms.common.utils.ToolsUtils;
import com.wms.common.utils.http.HttpUtil2;
import com.wms.infoquery.querystockin.domain.StoreIoEx;
import com.wms.infoquery.querystockin.domain.StoreIoSonEx;
import com.wms.infoquery.querystockin.mapper.InsertOrderIdMapper;
import com.wms.infoquery.querystockin.mapper.QueryStockInExMapper;
import com.wms.infoquery.querystockstatus.domain.Stock;
import com.wms.infoquery.querystockstatus.domain.StockEx;
import com.wms.infoquery.querystockstatus.mapper.StockExMapper;
import com.wms.task.http.RemoteUtils;
import com.wms.task.model.WmsDataReq;
import com.wms.task.utils.TimeUtils;
import com.wms.task.wincc.pojo.NoticeDto;
import com.wms.task.wincc.pojo.SernEntity;
import com.wms.task.wincc.pojo.StoreCheckEntity;
import com.wms.task.wincc.pojo.StoreIoEntity;
import com.wms.task.wincc.service.WinCCService;
import com.wms.warehouse.sern.domain.Sern;
import com.wms.warehouse.storecheck.domain.StoreCheckEx;
import com.wms.warehouse.storecheck.domain.StoreCheckSonEx;
import com.wms.warehouse.storecheck.mapper.StoreCheckExMapper;
import com.wms.warehouse.storeio.mapper.StoreIoMapper;
import com.wms.warehouse.storemove.domain.StoreMoveEx;
import com.wms.warehouse.storemove.domain.StoreMoveSonEx;
import com.wms.warehouse.storemove.mapper.StoreMoveExMapper;
import lombok.extern.slf4j.Slf4j;

import org.apache.velocity.runtime.directive.Foreach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Slf4j
@Service
public class WinCCServiceImpl implements WinCCService {
    private final static String REMOTE_HOST = "http://192.168.1.102";
    //    private final static String REMOTE_HOST = "http://dev.sunnyintell.com";
    private final static String apiNotice = "/microsaasapi/datainterface/api3/httpEntrance/json/WCS/WCS_NOTICE";
    private final static String apiData = "/microsaasapi/datainterface/api3/httpEntrance/json/WCS/WCS_GET_INOUTDATA";

    private final RemoteUtils remoteUtils;
    private final QueryStockInExMapper queryStockInExMapper;
    private final StoreMoveExMapper storeMoveExMapper;
    private final StoreCheckExMapper storeCheckExMapper;
    private final WarehouseExMapper warehouseExMapper;
    private final RegionExMapper regionExMapper;
    private final StorehouseExMapper storehouseExMapper;
    private final TrayExMapper trayExMapper;
    private final StockExMapper stockExMapper;
    private final DeviceExMapper deviceExMapper;
    private final InsertOrderIdMapper insertOrderIdMapper;


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

    @Autowired
    public WinCCServiceImpl(
            RemoteUtils remoteUtils,
            QueryStockInExMapper queryStockInExMapper,
            StoreMoveExMapper storeMoveExMapper,
            StoreCheckExMapper storeCheckExMapper,
            WarehouseExMapper warehouseExMapper,
            RegionExMapper regionExMapper,
            StorehouseExMapper storehouseExMapper,
            TrayExMapper trayExMapper,
            StockExMapper stockExMapper,
            DeviceExMapper deviceExMapper,
            InsertOrderIdMapper insertOrderIdMapper) {
        this.remoteUtils = remoteUtils;
        this.queryStockInExMapper = queryStockInExMapper;
        this.storeMoveExMapper = storeMoveExMapper;
        this.storeCheckExMapper = storeCheckExMapper;
        this.warehouseExMapper = warehouseExMapper;
        this.regionExMapper = regionExMapper;
        this.storehouseExMapper = storehouseExMapper;
        this.trayExMapper = trayExMapper;
        this.stockExMapper = stockExMapper;
        this.deviceExMapper = deviceExMapper;
        this.insertOrderIdMapper = insertOrderIdMapper;
    }

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private StoreIoMapper storeIoMapper;



    @Override
    @Async
    public Object noticeEx(NoticeDto noticeDto) {
        return null;
    }

    public static void main(String[] args) {
        WmsDataReq wmsDataReq=new WmsDataReq("1","11111",TimeUtils.nowInt());
        System.out.println(JsonUtils.toJson(new WmsDataReq(wmsDataReq)));
    }

    @Override
    @Async
    @Transactional(rollbackFor = Exception.class)
    public Object noticeExStockIn(NoticeDto noticeDto) throws Exception {
        List<StoreIoEx> storeIoExList =
                queryStockInExMapper.selectStoreIoPage(StoreIoEx
                        .builder()
                        .outerOrderId(noticeDto.getOuterOrderId())
                        .pageIndex(1)
                        .pageSize(5)
                        .build());
        if(storeIoExList.size() > 0){
            return AjaxResult.error("?????????????????????");
        }else{
            //?????????????????????????????????????????????????????????3
            for(int n=0;n<3;n++){
                //???????????????????????????
                log.info("#########????????????????????????######");
//                ApiData apiData = remoteUtils.httpProxy(REMOTE_HOST, ApiData.class);
//                Response<Object> response =
//                        apiData.apiData(
//                                WmsDataReq.builder()
//                                        .data_type("1")
//                                        .OuterOrderId(noticeDto.getOuterOrderId())
//                                        .timestamp(TimeUtils.nowInt())
//                                        .build()
//                        ).execute();
                //??????????????????????????????
//                Map linkedTreeMap=(LinkedTreeMap)response.body()
                JSONObject jsonObject=new JSONObject();
                jsonObject.put("OuterOrderId",noticeDto.getOuterOrderId());
                jsonObject.put("data_type","1");
                jsonObject.put("timestamp",TimeUtils.nowInt());
                String message= HttpUtil2.sendPost(REMOTE_HOST+apiData,jsonObject);
                log.info(message);
                Map linkedTreeMap=JsonUtils.json2map(message);
                if (!linkedTreeMap.get("state").toString().equals("200")){
                    log.info(linkedTreeMap.get("message").toString());
                    return AjaxResult.error(linkedTreeMap.get("message").toString());
                }
                List result=(ArrayList)linkedTreeMap.get("result");
                Map map=(Map)result.get(0);
                //?????????????????????????????????
                StoreIoEx storeIoEx=new StoreIoEx();
                storeIoEx.setOuterOrderId((String)map.get("OuterOrderId"));
                storeIoEx.setOutTaskCode((String)map.get("TaskCode"));
                storeIoEx.setProductDetail((String)map.get("ProductDetail"));
                storeIoEx.setMPoCode((String)map.get("MPoCode"));
                storeIoEx.setOrderCode((String)map.get("OrderCode"));
                storeIoEx.setQuantity(Double.valueOf(map.get("Quantity").toString()));
                storeIoEx.setVenInfo((String)map.get("VenInfo"));
                storeIoEx.setMaker((String)map.get("Maker"));
                storeIoEx.setWhCode((String)map.get("WhCode"));
                storeIoEx.setTaskCode(ToolsUtils.getOrdersId("SIT"));
                storeIoEx.setTaskName((String)map.get("TaskName"));
                storeIoEx.setTaskType("1");
                storeIoEx.setTaskState("0");
                storeIoEx.setF1((String)map.get("f1"));
                storeIoEx.setCreateDate(new Date());
                List<Sern> sernlist=new ArrayList<>();
                List<StoreIoSonEx> storeIoSonExList=  JsonUtils.linkedMapTypeListToObjectList((ArrayList)map.get("detail"), StoreIoSonEx.class);
                int rowId=1;
                for(StoreIoSonEx storeIoSonEx:storeIoSonExList){
                    //??????????????????????????????????????????????????????????????????,
                    //
                    // ????????????????????????????????????????????????????????????
                    storeIoSonEx.setTaskCode(storeIoEx.getTaskCode());
                    storeIoSonEx.setRowId((long)rowId++);
                    storeIoSonEx.setCreateUser(storeIoEx.getMaker());
                    storeIoSonEx.setCreateDate(new Date());
                    storeIoSonEx.setOtherProductCode(storeIoSonEx.getProductCode());
//                    Sern sern=new Sern();
//                    sern.setRowid(storeIoSonEx.getRowId());
//                    sern.setTaskcode(storeIoEx.getTaskCode());
//                    sern.setProductcode(storeIoSonEx.getProductNum());
//                    sern.setOtherproductcode(storeIoSonEx.getProductCode());
//                    sernlist.add(sern);

                    //?????????????????????????????????????????????????????????

                }
                //??????????????????
                queryStockInExMapper.insertStoreIo(storeIoEx);
                //??????????????????
                if (storeIoSonExList.size()>0) {
                    queryStockInExMapper.insertStoreIoSonList(storeIoSonExList);
                    //???????????????
//                    queryStockInExMapper.insertSernList(sernlist);
                }


                return AjaxResult.success();
            }
            return null;

        }
    }

    @Override
    @Async
    @Transactional(rollbackFor = Exception.class)
    public Object noticeExStockOut(NoticeDto noticeDto) {
        List<StoreIoEx> storeIoExList =
                queryStockInExMapper.selectStoreIoPage(StoreIoEx
                        .builder()
                        .outerOrderId(noticeDto.getOuterOrderId())
                        .pageIndex(1)
                        .pageSize(5)
                        .build());
        if(storeIoExList.size() > 0){
            return "?????????????????????";
        }else{
            //?????????????????????????????????????????????????????????3
            for(int n=0;n<3;n++){
                try {
//                    ApiData apiData = remoteUtils.httpProxy(REMOTE_HOST, ApiData.class);
//                    Response<Object> response =
//                            apiData.apiData(
//                                    WmsDataReq.builder()
//                                            .data_type("2")
//                                            .OuterOrderId(noticeDto.getOuterOrderId())
//                                            .timestamp(TimeUtils.nowInt())
//                                            .build()
//                            ).execute();
                    JSONObject jsonObject=new JSONObject();
                    jsonObject.put("OuterOrderId",noticeDto.getOuterOrderId());
                    jsonObject.put("data_type","2");
                    jsonObject.put("timestamp",TimeUtils.nowInt());
                    String message= HttpUtil2.sendPost(REMOTE_HOST+apiData,jsonObject);
                    //??????????????????????????????
                    log.info(message);
                    Map linkedTreeMap=JsonUtils.json2map(message);
                    if (!linkedTreeMap.get("state").toString().equals("200")){
                        log.info(linkedTreeMap.get("message").toString());
                        return AjaxResult.error(linkedTreeMap.get("message").toString());
                    }
                    List result=(ArrayList)linkedTreeMap.get("result");
                    Map map=(Map)result.get(0);
                    //?????????????????????????????????
                    StoreIoEx storeIoEx=new StoreIoEx();
                    storeIoEx.setOuterOrderId((String)map.get("OuterOrderId"));
                    storeIoEx.setOutTaskCode((String)map.get("TaskCode"));
                    storeIoEx.setProductDetail((String)map.get("ProductDetail"));
                    storeIoEx.setMPoCode((String)map.get("MPoCode"));
                    storeIoEx.setOrderCode((String)map.get("OrderCode"));
                    storeIoEx.setQuantity(Double.valueOf(map.get("Quantity").toString()));
                    storeIoEx.setVenInfo((String)map.get("VenInfo"));
                    storeIoEx.setMaker((String)map.get("Maker"));
                    storeIoEx.setWhCode((String)map.get("WhCode"));
                    storeIoEx.setTaskCode(ToolsUtils.getOrdersId("SIT"));
                    storeIoEx.setTaskName((String)map.get("TaskName"));
                    storeIoEx.setTaskType("2");
                    storeIoEx.setTaskState("0");
                    storeIoEx.setF1((String)map.get("f1"));
                    storeIoEx.setCreateDate(new Date());
                    List<Sern> sernlist=new ArrayList<>();
                    List<StoreIoSonEx> storeIoSonExList=  JsonUtils.linkedMapTypeListToObjectList((ArrayList)map.get("detail"), StoreIoSonEx.class);
                    int rowId=1;
                    for(StoreIoSonEx storeIoSonEx:storeIoSonExList){
                        //??????????????????????????????????????????????????????????????????,
                        //
                        // ????????????????????????????????????????????????????????????
                        storeIoSonEx.setTaskCode(storeIoEx.getTaskCode());
                        storeIoSonEx.setRowId((long)rowId++);
                        storeIoSonEx.setCreateUser(storeIoEx.getMaker());
                        storeIoSonEx.setCreateDate(new Date());
                        storeIoSonEx.setOtherProductCode(storeIoSonEx.getProductCode());
//                        Sern sern=new Sern();
//                        sern.setRowid(storeIoSonEx.getRowId());
//                        sern.setTaskcode(storeIoEx.getTaskCode());
//                        sern.setProductcode(storeIoSonEx.getProductNum());
//                        sern.setOtherproductcode(storeIoSonEx.getProductCode());
//                        sernlist.add(sern);
                    }
                    //??????????????????
                    queryStockInExMapper.insertStoreIo(storeIoEx);
                    //??????????????????
                    if (storeIoSonExList.size()>0) {
                        queryStockInExMapper.insertStoreIoSonList(storeIoSonExList);
                        //???????????????
//                        queryStockInExMapper.insertSernList(sernlist);
                    }

                    return AjaxResult.success();
                } catch (Exception e) {
                    log.error("noticeExStockOut???Exception: {}", e.getMessage());
                    throw new RuntimeException("noticeExStockOut???Exception: " + e.getMessage());
                }
            }
            return AjaxResult.error("????????????");
        }

    }

    @Override
    @Async
    @Transactional(rollbackFor = Exception.class)
    public Object noticeExStockTransfer(NoticeDto noticeDto) throws Exception {
        List<StoreMoveEx> storeMoveExList =
                storeMoveExMapper.selectStoreMovePage(StoreMoveEx.builder()
                        .outerOrderId(noticeDto.getOuterOrderId())
                        .pageIndex(1)
                        .pageSize(5)
                        .build());
        if(storeMoveExList.size() > 0){
            return "?????????????????????";
        }
        else{
            for(int n=0;n<3;n++){
//                    ApiData apiData = remoteUtils.httpProxy(REMOTE_HOST, ApiData.class);
//                    Response<Object> response =
//                            apiData.apiData(
//                                    WmsDataReq.builder()
//                                            .data_type("3")
//                                            .OuterOrderId(noticeDto.getOuterOrderId())
//                                            .timestamp(TimeUtils.nowInt())
//                                            .build()
//                            ).execute();
                JSONObject jsonObject=new JSONObject();
                jsonObject.put("OuterOrderId",noticeDto.getOuterOrderId());
                jsonObject.put("data_type","3");
                jsonObject.put("timestamp",TimeUtils.nowInt());
                String message= HttpUtil2.sendPost(REMOTE_HOST+apiData,jsonObject);
                log.info(message);
                //??????????????????????????????
                Map linkedTreeMap=JsonUtils.json2map(message);
                if (!linkedTreeMap.get("state").toString().equals("200")){
                    log.info(linkedTreeMap.get("message").toString());
                    return AjaxResult.error(linkedTreeMap.get("message").toString());
                }
                List result=(ArrayList)linkedTreeMap.get("result");
                Map map=(Map)result.get(0);

                StoreMoveEx storeMoveEx = new StoreMoveEx();
                //?????????????????????????????????
                storeMoveEx.setOuterOrderId((String) map.get("OuterOrderId"));
                storeMoveEx.setProductDetail((String) map.get("ProductDetail"));
                storeMoveEx.setQuantity(Double.parseDouble(map.get("Quantity").toString()));
                storeMoveEx.setWhCode((String) map.get("WhCode"));
                storeMoveEx.setTaskCode(ToolsUtils.getOrdersId("SME"));
                storeMoveEx.setMaker((String)map.get("Maker"));
                storeMoveEx.setTaskName((String) map.get("TaskName"));
                storeMoveEx.setTaskType("3");
                storeMoveEx.setTaskState("0");
                storeMoveEx.setCreateDate(new Date());
                int rowId=1;
                List<StoreMoveSonEx> storeIoSonExList=  JsonUtils.linkedMapTypeListToObjectList((ArrayList)map.get("detail"), StoreMoveSonEx.class);
                List<Sern> sernList=new ArrayList<>();
                for (StoreMoveSonEx storeMoveSonEx: storeIoSonExList) {
                    //?????????????????????????????????
                    Stock stock=stockExMapper.selectByStock(storeMoveSonEx.getProductCode(),storeMoveSonEx.getStartCode());
    //                        if (stock==null){
    //                            return AjaxResult.error(storeMoveSonEx.getProductCode()+"???????????????????????????");
    //                        }
                    storeMoveSonEx.setTaskCode(storeMoveEx.getTaskCode());
                    storeMoveSonEx.setRowId((long)rowId++);
                    storeMoveSonEx.setTaskName(storeMoveEx.getTaskName());
                    storeMoveSonEx.setOtherProductCode(storeMoveSonEx.getProductCode());
                    storeMoveSonEx.setWhCode(stock.getWhcode());
                    storeMoveSonEx.setProductCode(stock.getProductcode());
                    storeMoveSonEx.setProductNum(stock.getProductNum());
                    storeMoveSonEx.setProductName(stock.getProductname());
                    storeMoveSonEx.setProductModel(stock.getProductmodel());
                    storeMoveSonEx.setQuantity(Double.valueOf(stock.getQuantity().toString()));
                    storeMoveSonEx.setCreateUser(storeMoveEx.getMaker());
                    storeMoveSonEx.setCreateDate(new Date());

    //                        Sern sern=new Sern();
    //                        sern.setRowid(storeMoveSonEx.getRowId());
    //                        sern.setTaskcode(storeMoveSonEx.getTaskCode());
    //                        sern.setProductcode(storeMoveSonEx.getProductNum());
    //                        sern.setOtherproductcode(storeMoveSonEx.getProductCode());
    //                        sernList.add(sern);
                }
                storeMoveExMapper.insertStoreMove(storeMoveEx);
                //??????????????????
                if (storeIoSonExList.size()>0) {
                    storeMoveExMapper.insertStoreMoveSonList(storeIoSonExList);
                    //???????????????
    //                        queryStockInExMapper.insertSernList(sernList);
                }
                return AjaxResult.success();
            }
            return null;

        }
    }

    @Override
    @Async
    @Transactional(rollbackFor = Exception.class)
    public Object noticeExStockCheck(NoticeDto noticeDto) {
        List<StoreCheckEx> storeCheckExList =
                storeCheckExMapper.selectStoreCheckPage(StoreCheckEx.builder()
                        .OuterOrderId(noticeDto.getOuterOrderId())
                        .pageIndex(1)
                        .pageSize(5)
                        .build());
        if(storeCheckExList.size()>0){
            return "?????????????????????";
        }else{
            for(int n=0;n<3;n++){
                try {
//                    ApiData apiData = remoteUtils.httpProxy(REMOTE_HOST, ApiData.class);
//                    Response<Object> response =
//                            apiData.apiData(
//                                    WmsDataReq.builder()
//                                            .data_type("4")
//                                            .OuterOrderId(noticeDto.getOuterOrderId())
//                                            .timestamp(TimeUtils.nowInt())
//                                            .build()
//                            ).execute();
                    JSONObject jsonObject=new JSONObject();
                    jsonObject.put("OuterOrderId",noticeDto.getOuterOrderId());
                    jsonObject.put("data_type","4");
                    jsonObject.put("timestamp",TimeUtils.nowInt());
                    String message= HttpUtil2.sendPost(REMOTE_HOST+apiData,jsonObject);
                    //??????????????????????????????
                    Map linkedTreeMap=JsonUtils.json2map(message);
                    log.info(message);
                    if (!linkedTreeMap.get("state").toString().equals("200")){
                        log.info(linkedTreeMap.get("message").toString());
                        return AjaxResult.error(linkedTreeMap.get("message").toString());
                    }
                    List result=(ArrayList)linkedTreeMap.get("result");
                    Map map=(Map)result.get(0);
                    StoreCheckEx storeCheckEx = new StoreCheckEx();
                    //?????????????????????????????????
                    storeCheckEx.setOuterOrderId((String) map.get("OuterOrderId"));
                    storeCheckEx.setProductDetail((String) map.get("ProductDetail"));
                    storeCheckEx.setQuantity(Double.parseDouble(map.get("Quantity").toString()));
                    storeCheckEx.setWhCode((String) map.get("WhCode"));
                    storeCheckEx.setTaskCode(ToolsUtils.getOrdersId("SCC"));
                    storeCheckEx.setTaskName((String) map.get("TaskName"));
                    storeCheckEx.setTaskType("4");

                    storeCheckEx.setTaskState("0");
                    storeCheckEx.setCreateDate(new Date());
                    int rowId=1;
                    List<StoreCheckSonEx> storeIoSonExList=  JsonUtils.linkedMapTypeListToObjectList((ArrayList)map.get("detail"), StoreCheckSonEx.class);
                    List<Sern> sernList=new ArrayList<>();
                    for (StoreCheckSonEx storeCheckSonEx: storeIoSonExList) {
                        //?????????????????????????????????
                        storeCheckSonEx.setRowId((long)rowId++);
                        storeCheckSonEx.setTaskCode(storeCheckEx.getTaskCode());
                        storeCheckSonEx.setTaskName(storeCheckEx.getTaskName());
                        storeCheckSonEx.setWhCode(storeCheckEx.getWhCode());
                        storeCheckSonEx.setCreateUser(storeCheckEx.getMaker());
                        storeCheckSonEx.setCreateDate(new Date());
                        storeCheckSonEx.setOtherProductCode(storeCheckSonEx.getProductCode());
//                        Sern sern=new Sern();
//                        sern.setRowid(storeCheckSonEx.getRowId());
//                        sern.setTaskcode(storeCheckSonEx.getTaskCode());
//                        sern.setProductcode(storeCheckSonEx.getProductNum());
//                        sern.setOtherproductcode(storeCheckSonEx.getProductCode());
//                        sernList.add(sern);
                    }

                    storeCheckExMapper.insertStoreCheck(storeCheckEx);
                    if (storeIoSonExList.size()>0){
                        storeCheckExMapper.insertStoreCheckSonList(storeIoSonExList);
                        //???????????????
//                        queryStockInExMapper.insertSernList(sernList);
                    }

                    return AjaxResult.success();
                } catch (Exception e) {
                    log.error("noticeExStockTransfer???Exception: {}", e.getMessage());
                    throw new RuntimeException("noticeExStockTransfer???Exception: " + e.getMessage());
                }
            }
            return null;
        }

    }

    @Override
    @Async
    @Transactional(rollbackFor = Exception.class)
    public Object noticeExOrderSync(NoticeDto noticeDto) {
        return null;
    }

    @Override
    public Object   dataStockIn(String taskCode, Integer pageIndex, Integer pageSize) {
//        List<StoreIoEx> storeIoExList =
//                queryStockInExMapper.selectStoreIoPage(
//                        StoreIoEx.builder()
//                                .taskType("1")
//                                .taskCode(taskCode)
//                                .pageIndex(pageIndex)
//                                .pageSize(pageSize)
//                                .build()
//                );
//
//        int totalCount = queryStockInExMapper.selectStoreIoTotalCount("1");
//        int totalPage = getTotalPage(pageSize, totalCount);
//        return PageBean.<StoreIoEx>builder()
//                .currentPage(pageIndex)
//                .totalPage(totalPage)
//                .totalCount(totalCount)
//                .pageSizes(pageSize)
//                .result(storeIoExList)
//                .build();

        List<StoreIoEntity> storeIoExlist=queryStockInExMapper.selectStoreList(taskCode);
        return AjaxResult.success(storeIoExlist);
    }

    private int getTotalPage(int pageSize, int totalCount) {
        double pageCount = Math.ceil((double) totalCount / (double) pageSize);
        return (new Double(pageCount)).intValue();
    }

    @Override
    public Object dataStockOut(String taskCode, Integer pageIndex, Integer pageSize) {
        List<StoreIoEntity> storeIoExlist=queryStockInExMapper.selectStoreList(taskCode);
        return AjaxResult.success(storeIoExlist);
    }

    @Override
    public Object dataStockTransfer(String taskCode, Integer pageIndex, Integer pageSize) {
//        List<StoreMoveEx> storeMoveExList =
//                storeMoveExMapper.selectStoreMovePage(
//                        StoreMoveEx.builder()
//                                .taskCode(taskCode)
//                                .pageIndex(pageIndex)
//                                .pageSize(pageSize)
//                                .build()
//                );
//        int totalCount = storeMoveExMapper.selectStoreMoveTotalCount();
//        int totalPage = getTotalPage(pageSize, totalCount);
//        return PageBean.<StoreMoveEx>builder()
//                .currentPage(pageIndex)
//                .totalPage(totalPage)
//                .totalCount(totalCount)
//                .pageSizes(pageSize)
//                .result(storeMoveExList)
//                .build();
        List<StoreMoveEx> storeIoExlist=storeMoveExMapper.selectStoreMoveList(taskCode);
        return AjaxResult.success(storeIoExlist);
    }

    @Override
    public Object dataStockCheck(String taskCode, Integer pageIndex, Integer pageSize) {
        List<StoreCheckEntity> storeCheckExlist=storeCheckExMapper.selectStoreCheckList(taskCode);
        return AjaxResult.success(storeCheckExlist);
    }

    @Override
    public Object dataWareHouse(Integer pageIndex, Integer pageSize) {
        List<WarehouseEx> warehouseExList =
                warehouseExMapper.selectWarehousePage(
                        WarehouseEx.builder()
                                .pageIndex(pageIndex)
                                .pageSize(pageSize)
                                .build()
                );

        int totalCount = warehouseExMapper.selectWarehouseTotalCount();
        int totalPage = getTotalPage(pageSize, totalCount);
        return PageBean.<WarehouseEx>builder()
                .currentPage(pageIndex)
                .totalPage(totalPage)
                .totalCount(totalCount)
                .pageSizes(pageSize)
                .result(warehouseExList)
                .build();
    }

    @Override
    public Object dataRegion(Integer pageIndex, Integer pageSize) {
        List<RegionEx> regionExList =
                regionExMapper.selectRegionPage(
                        RegionEx.builder()
                                .pageIndex(pageIndex)
                                .pageSize(pageSize)
                                .build()
                );
        int totalCount = regionExMapper.selectRegionTotalCount();
        int totalPage = getTotalPage(pageSize, totalCount);
        return PageBean.<RegionEx>builder()
                .currentPage(pageIndex)
                .totalPage(totalPage)
                .totalCount(totalCount)
                .pageSizes(pageSize)
                .result(regionExList)
                .build();
    }

    @Override
    public Object dataStorehouse(Integer pageIndex, Integer pageSize) {
        List<StorehouseEx> storehouseExList =
                storehouseExMapper.selectStorehousePage(
                        StorehouseEx.builder()
                                .pageIndex(pageIndex)
                                .pageSize(pageSize)
                                .build()
                );
        int totalCount = storehouseExMapper.selectStorehouseTotalCount();
        int totalPage = getTotalPage(pageSize, totalCount);
        return PageBean.<StorehouseEx>builder()
                .currentPage(pageIndex)
                .totalPage(totalPage)
                .totalCount(totalCount)
                .pageSizes(pageSize)
                .result(storehouseExList)
                .build();
    }

    @Override
    public Object dataTray(Integer pageIndex, Integer pageSize) {
        List<TrayEx> trayExList =
                trayExMapper.selectTrayPage(
                        TrayEx.builder()
                                .pageIndex(pageIndex)
                                .pageSize(pageSize)
                                .build()
                );
        int totalCount = trayExMapper.selectTrayTotalCount();
        int totalPage = getTotalPage(pageSize, totalCount);
        return PageBean.<TrayEx>builder()
                .currentPage(pageIndex)
                .totalPage(totalPage)
                .totalCount(totalCount)
                .pageSizes(pageSize)
                .result(trayExList)
                .build();
    }

    @Override
    public Object dataStock(Integer pageIndex, Integer pageSize) {
        List<StockEx> stockExList =
                stockExMapper.selectStockPage(
                        StockEx.builder()
                                .pageIndex(pageIndex)
                                .pageSize(pageSize)
                                .build()
                );
        int totalCount = stockExMapper.selectStockTotalCount();
        int totalPage = getTotalPage(pageSize, totalCount);
        return PageBean.<StockEx>builder()
                .currentPage(pageIndex)
                .totalPage(totalPage)
                .totalCount(totalCount)
                .pageSizes(pageSize)
                .result(stockExList)
                .build();
    }

    @Override
    public Object dataDevice(Integer pageIndex, Integer pageSize) {
        List<DeviceEx> deviceExList =
                deviceExMapper.selectDevicePage(
                        DeviceEx.builder()
                                .pageIndex(pageIndex)
                                .pageSize(pageSize)
                                .build()
                );
        int totalCount = deviceExMapper.selectDeviceTotalCount();
        int totalPage = getTotalPage(pageSize, totalCount);
        return PageBean.<DeviceEx>builder()
                .currentPage(pageIndex)
                .totalPage(totalPage)
                .totalCount(totalCount)
                .pageSizes(pageSize)
                .result(deviceExList)
                .build();
    }

    @Override
    public String queryOrderByStockIn(String taskCode,Integer pageIndex, Integer pageSize){
        List<StoreIoEx> storeIoExList=
                queryStockInExMapper.selectStoreIoPage(
                        StoreIoEx.builder()
                                .taskType("1")
                                .taskCode(taskCode)
                                .pageIndex(pageIndex)
                                .pageSize(pageSize)
                                .build()
                );
        return storeIoExList.size()>0?storeIoExList.get(0).getOuterOrderId():null;
    }

    @Override
    public String queryOrderByStockOut(String taskCode,Integer pageIndex, Integer pageSize){
        List<StoreIoEx> storeIoExList=
                queryStockInExMapper.selectStoreIoPage(
                        StoreIoEx.builder()
                                .taskType("2")
                                .taskCode(taskCode)
                                .pageIndex(pageIndex)
                                .pageSize(pageSize)
                                .build()
                );
        return storeIoExList.size()>0?storeIoExList.get(0).getOuterOrderId():null;
    }

    @Override
    public String queryOrderByTransfer(String taskCode,Integer pageIndex, Integer pageSize){
        List<StoreMoveEx> storeMoveExList =
                storeMoveExMapper.selectStoreMovePage(
                        StoreMoveEx.builder()
                                .taskType("3")
                                .taskCode(taskCode)
                                .pageIndex(pageIndex)
                                .pageSize(pageSize)
                                .build()
                );
        return storeMoveExList.size()>0?storeMoveExList.get(0).getOuterOrderId():null;
    }

    public String queryOrderByStockCheck(String taskCode,Integer pageIndex, Integer pageSize){
        List<StoreCheckEx> storeCheckExList =
                storeCheckExMapper.selectStoreCheckPage(
                        StoreCheckEx.builder()
                                .taskCode("4")
                                .taskCode(taskCode)
                                .pageIndex(pageIndex)
                                .pageSize(pageSize)
                                .build()
                );
        return storeCheckExList.size()>0?storeCheckExList.get(0).getOuterOrderId():null;
    }




    /**
     * wcs???????????????????????????????????????
     * @param noticeType
     * @param taskCode
     * @return
     */

    @Override
    public Object noticeWCS(String noticeType,String taskCode) {
        for(int n=1;n<=3;n++){
            try{
//                ApiData apiData = remoteUtils.httpProxy(REMOTE_HOST, ApiData.class);
//                Response<Object> response =
//                        apiData.apiNotice(
//                                WmsDataReq.builder()
//                                        .notice_type(noticeType)
//                                        .OuterOrderId(outerOrderId)
//                                        .timestamp(TimeUtils.nowInt())
//                                        .build()
//                        ).execute();

                JSONObject jsonObject=new JSONObject();
                jsonObject.put("TaskCode",taskCode);
                jsonObject.put("notice_type",noticeType);
                jsonObject.put("timestamp",TimeUtils.nowInt());
                String message= HttpUtil2.sendPost(REMOTE_HOST+apiNotice,jsonObject);
//                Map linkedTreeMap=JsonUtils.json2map(message);
                System.out.println(message);
                return null;

            } catch (Exception e) {
                log.error("noticeExStockTransfer???Exception: {}", e.getMessage());
                throw new RuntimeException("noticeExStockTransfer???Exception: " + e.getMessage());
            }

        }
        return null;

    }

    @Override
    @Async
    public Object insertOuterOrderId(String outerOrderId){

        insertOrderIdMapper.insertOuterOrderId(outerOrderId);
        return null;
    }

    @Override
    @Async("asyncServiceExecutor")
    public Object executeAsync(NoticeDto noticeDto) throws Exception {
        switch (noticeDto.getNoticeType()) {
            case DATA_STOCK_IN: {
                return noticeExStockIn(noticeDto);
            }
            case DATA_STOCK_OUT: {
                return noticeExStockOut(noticeDto);
            }
            case DATA_STOCK_TRANSFER: {
                return noticeExStockTransfer(noticeDto);
            }
            case DATA_STOCK_CHECK: {
                return noticeExStockCheck(noticeDto);
            }
            case ORDER_SYNC: {
                return noticeExOrderSync(noticeDto);
            }
        }
        return null;
    }


    /***
     * ???????????????????????????????????????????????????
     * @param noticeDto
     * @return
     */
    @Override
    public AjaxResult processing(NoticeDto noticeDto) {

        switch (noticeDto.getNoticeType()) {
            case DATA_STOCK_IN: {
                return processingIn(noticeDto);
            }
            case DATA_STOCK_OUT: {
                return processingOut(noticeDto);
            }
            case DATA_STOCK_TRANSFER: {
                return processingMove(noticeDto);
            }
            case DATA_STOCK_CHECK: {
                return processingCheck(noticeDto);
            }
            case 5: {
                return processingIOSync(noticeDto);
            }
            case 6: {
                return processingIOSync(noticeDto);
            }

        }
        return AjaxResult.success();
    }



    @Override
    public Object dataProduct(Product product) {
        if (StringUtils.isEmpty(product.getProjectNum())){
            return AjaxResult.error("????????????????????????");
        }
        if (StringUtils.isEmpty(product.getProductname())){
            return AjaxResult.error("????????????????????????");
        }
        if (StringUtils.isEmpty(product.getProductmodel())){
            return AjaxResult.error("????????????????????????");
        }
        product.setProductcode(ToolsUtils.getOrdersId("CP"));
        if (productMapper.insertProduct(product)>0){
            return AjaxResult.success(product);
        }
        return AjaxResult.error();
    }

    /**
     * ??????????????????
     * @param noticeDto
     * @return
     */
    private AjaxResult processingIn(NoticeDto noticeDto) {

        //??????????????????????????????????????????
        StoreIoEntity storeIoEx=queryStockInExMapper.selectStoreIoByOther(noticeDto.getOuterOrderId());
        //?????????????????????????????????????????????
        List<Stock> stockList=queryStockInExMapper.selectStockNullList();
        List<Stock> writeStockList=new ArrayList<>();
        Iterator it=stockList.iterator();
        for (SernEntity sern:storeIoEx.getSernList()){
            if (it.hasNext()){
                Stock stock= (Stock) it.next();
                //???????????????????????????????????????????????????????????????????????????
                sern.setSern(ToolsUtils.getRandNum(10));
                sern.setTraycode(ToolsUtils.getRandNum(10));
                sern.setStorehousecode(stock.getStorehousecode());
                sern.setScanuser("????????????");
                sern.setScantime(new Date());
                queryStockInExMapper.updateSern(sern);
                //????????????????????????????????????
                stock.setSern(sern.getSern());
                stock.setOtherProductCode(sern.getOtherproductcode());
                queryStockInExMapper.updateStock(stock);

                //???????????????????????????????????????
                StoreIoSonEx storeIoSon=new StoreIoSonEx();
                storeIoSon.setTaskCode(storeIoEx.getTaskCode());
                storeIoSon.setTaskState("2");
                storeIoSon.setRowId(sern.getRowid());
                storeIoSon.setSern(sern.getSern());
                queryStockInExMapper.updateIoSon(storeIoSon);
            }
        }
        storeIoEx.setTaskState("2");
        queryStockInExMapper.updateIo(storeIoEx);

        JSONObject jsonObject=new JSONObject();
        jsonObject.put("TaskCode",storeIoEx.getTaskCode());
        jsonObject.put("notice_type","1");
        jsonObject.put("timestamp",TimeUtils.nowInt());
        String message= HttpUtil2.sendPost(REMOTE_HOST+apiNotice,jsonObject);
//                Map linkedTreeMap=JsonUtils.json2map(message);
        System.out.println(message);
        return AjaxResult.success(storeIoEx);

    }

    /**
     * ??????????????????
     * @param noticeDto
     * @return
     */
    private AjaxResult processingOut(NoticeDto noticeDto) {
        //??????????????????????????????????????????
        StoreIoEntity storeIoEx=queryStockInExMapper.selectStoreIoByOther(noticeDto.getOuterOrderId());
        //??????????????????
        List<Stock> stockList=queryStockInExMapper.selectByStockOutList(storeIoEx.getTaskCode());
        Iterator it=stockList.iterator();
        for(SernEntity sern:storeIoEx.getSernList()){
            if (it.hasNext()){
                Stock stock= (Stock) it.next();
                //???????????????????????????????????????????????????????????????????????????
                sern.setSern(stock.getSern());
                sern.setTraycode(ToolsUtils.getRandNum(10));
                sern.setStorehousecode(stock.getStorehousecode());
                sern.setScanuser("????????????");
                sern.setScantime(new Date());
                queryStockInExMapper.updateSern(sern);
                //????????????
                queryStockInExMapper.updateStockNull(stock);
                //???????????????????????????????????????
                StoreIoSonEx storeIoSon=new StoreIoSonEx();
                storeIoSon.setTaskCode(storeIoEx.getTaskCode());
                storeIoSon.setTaskState("2");
                storeIoSon.setRowId(sern.getRowid());
                storeIoSon.setSern(stock.getSern());
                queryStockInExMapper.updateIoSon(storeIoSon);
            }
        }
        storeIoEx.setTaskState("2");
        queryStockInExMapper.updateIo(storeIoEx);
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("TaskCode",storeIoEx.getTaskCode());
        jsonObject.put("notice_type","2");
        jsonObject.put("timestamp",TimeUtils.nowInt());
        String message= HttpUtil2.sendPost(REMOTE_HOST+apiNotice,jsonObject);
//                Map linkedTreeMap=JsonUtils.json2map(message);
        System.out.println(message);
        return AjaxResult.success(storeIoEx);
    }

    /**
     * ??????????????????
     * @param noticeDto
     * @return
     */
    private AjaxResult processingMove(NoticeDto noticeDto) {
        //??????????????????????????????????????????
        StoreMoveEx storeMoveEx=storeMoveExMapper.selectStoreMoveByOtherList(noticeDto.getOuterOrderId());
        List<StoreMoveSonEx> storeMoveSonExList=storeMoveExMapper.selectStoreMoveSonList(storeMoveEx.getTaskCode());
        for(StoreMoveSonEx storeMoveSonEx:storeMoveSonExList){

            //????????????????????????
            Stock stock=storeMoveExMapper.selectStock(storeMoveSonEx.getStartCode());
            //??????????????????????????????
            queryStockInExMapper.updateStockNull(stock);
            //???????????????????????????
            stock.setStorehousecode(storeMoveSonEx.getEndCode());
            queryStockInExMapper.updateStock(stock);
            //???????????????????????????????????????????????????????????????????????????
            SernEntity sern=new SernEntity();
            sern.setTaskcode(storeMoveSonEx.getTaskCode());
            sern.setRowid(storeMoveSonEx.getRowId());
            sern.setSern(stock.getSern());
            sern.setTraycode(ToolsUtils.getRandNum(10));
            sern.setStorehousecode(stock.getStorehousecode());
            sern.setScanuser("????????????");
            sern.setScantime(new Date());
            queryStockInExMapper.updateSern(sern);
            //????????????????????????
            storeMoveSonEx.setTaskState("2");
            storeMoveExMapper.updateMoveSon(storeMoveSonEx);

        }
        storeMoveEx.setTaskState("2");
        storeMoveExMapper.updateMove(storeMoveEx);
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("TaskCode",storeMoveEx.getTaskCode());
        jsonObject.put("notice_type","3");
        jsonObject.put("timestamp",TimeUtils.nowInt());
        String message= HttpUtil2.sendPost(REMOTE_HOST+apiNotice,jsonObject);
//                Map linkedTreeMap=JsonUtils.json2map(message);
        System.out.println(message);
        return AjaxResult.success(storeMoveEx);
    }


    /**
     * ??????????????????
     * @param noticeDto
     * @return
     */
    private AjaxResult processingCheck(NoticeDto noticeDto) {
        //??????????????????????????????????????????
        StoreCheckEntity storeCheckEntity=storeCheckExMapper.selectStoreCheckByOuther(noticeDto.getOuterOrderId());
        List<StoreCheckSonEx> storeCheckSonExList=storeCheckExMapper.selectStoreCheckSonList(storeCheckEntity.getTaskCode());
        for(StoreCheckSonEx storeCheckSonEx:storeCheckSonExList){
            //??????????????????
            Stock stock=storeMoveExMapper.selectStock(storeCheckSonEx.getStorehouseCode());
            //????????????????????????
            if (stock!=null && storeCheckSonEx.getOtherProductCode().equals(stock.getOtherProductCode())){
                storeCheckSonEx.setPdQuantity(storeCheckSonEx.getQuantity().toString());
                storeCheckSonEx.setPdResult("1");
            }else{
                storeCheckSonEx.setPdQuantity("0");
                storeCheckSonEx.setPdResult("0");
            }
            //???????????????????????????????????????????????????????????????????????????
            if (stock!=null){
                SernEntity sern=new SernEntity();
                sern.setTaskcode(storeCheckSonEx.getTaskCode());
                sern.setRowid(storeCheckSonEx.getRowId());
                sern.setSern(stock.getSern());
                sern.setTraycode(ToolsUtils.getRandNum(10));
                sern.setStorehousecode(stock.getStorehousecode());
                sern.setScanuser("????????????");
                sern.setScantime(new Date());
                queryStockInExMapper.updateSern(sern);
            }
            storeCheckExMapper.updateSon(storeCheckSonEx);

        }
        storeCheckEntity.setTaskState("2");
        storeCheckExMapper.updateCheck(storeCheckEntity);
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("TaskCode",storeCheckEntity.getTaskCode());
        jsonObject.put("notice_type","4");
        jsonObject.put("timestamp",TimeUtils.nowInt());
        String message= HttpUtil2.sendPost(REMOTE_HOST+apiNotice,jsonObject);
//                Map linkedTreeMap=JsonUtils.json2map(message);
        System.out.println(message);
        return AjaxResult.success(storeCheckEntity);
    }


    /**
     * ????????????????????????
     */
    public AjaxResult processingMoveSync(NoticeDto noticeDto) {
        //1.???????????????????????????????????????????????????????????????????????????
//        StoreIoEx storeIoEx = new StoreIoEx("2", ToolsUtils.getOrdersId("SIC"), "WCS??????", "????????????", "test", "test", ToolsUtils.getOrdersId("MPC"), ToolsUtils.getOrdersId("ODC"), "WH0003", 1.0, "2", "??????", "WCS", new Date());
//        //?????????????????????????????????????????????
//
//        List<Stock> stockList = queryStockInExMapper.selectStockNullList();//???????????????????????????
//
//        List<Sern> sernList = new ArrayList<>();//??????????????????
//
//        List<StoreIoSonEx> storeIoSonExList = new ArrayList<>();//
//        Iterator it = stockList.iterator();
//
//        for (int i = 0; i < storeIoEx.getQuantity(); i++) {
//            if (it.hasNext()) {
//                Stock stock = (Stock) it.next();
//                //???????????????????????????????????????????????????????????????????????????????????????????????????,????????????
//                StoreIoSonEx storeIoSonEx = new StoreIoSonEx();
//                storeIoSonEx.setTaskCode(storeIoEx.getTaskCode());
//                storeIoSonEx.setRowId((long) i + 1);
//                storeIoSonEx.setCreateUser(storeIoEx.getMaker());
//                storeIoSonEx.setCreateDate(new Date());
//                storeIoSonEx.setOtherProductCode(ToolsUtils.getOrdersId("AQ"));
//                storeIoSonEx.setTaskState("2");
//
//                //???????????????????????????????????????????????????????????????????????????
//                Sern sern = new Sern();
//                sern.setRowid(storeIoSonEx.getRowId());
//                sern.setTaskcode(storeIoEx.getTaskCode());
//                sern.setProductcode(storeIoSonEx.getProductNum());
//                sern.setOtherproductcode(storeIoSonEx.getProductCode());
//                sern.setSern(ToolsUtils.getRandNum(10));
//                sern.setTraycode(ToolsUtils.getRandNum(10));
//                sern.setStorehousecode(stock.getStorehousecode());
//                sern.setScanuser("????????????");
//                sern.setScantime(new Date());
//                sernList.add(sern);
//
//                storeIoSonEx.setSern(sern.getSern());
//                storeIoSonExList.add(storeIoSonEx);
//
//                //????????????????????????????????????
//                stock.setSern(sern.getSern());
//                stock.setOtherProductCode(sern.getOtherproductcode());
//                queryStockInExMapper.updateStock(stock);
//            }
//        }
//        queryStockInExMapper.insertSernList(sernList);
//        queryStockInExMapper.insertStoreIoSonList(storeIoSonExList);
//        queryStockInExMapper.insertStoreIo(storeIoEx);

        //2.???????????????????????????????????????????????????????????????????????????????????????????????????????????????

        //                Sern sern=new Sern();
        //                sern.setTaskcode(storeIoEx.getTaskCode());
        //                List<Sern>  serns= sernMapper.selectSernList(sern);
        List<StoreIoEntity> storeIoExlist = queryStockInExMapper.selectStoreList(noticeDto.getTaskCode());

        //    List<StoreIoEntity> storeIoExlist = queryStockInExMapper.selectStoreList(storeIoEx.getTaskCode());
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("result", storeIoExlist);
        jsonObject.put("notice_type", "1");
        jsonObject.put("timestamp", TimeUtils.nowInt());
        String message = HttpUtil2.sendPost(REMOTE_HOST + apiNotice+"_TASK", jsonObject);
        log.info("??????????????????????????????"+message);
//        System.out.println(message);

        //3.???????????????????????????????????????????????????????????????

//        try {
//            Map linkedTreeMap = JsonUtils.json2map(message);
//            //???????????????outorderid
//            String outorderid = linkedTreeMap.get("outorderid").toString();
//            storeIoEx.setOuterOrderId(outorderid);
//            queryStockInExMapper.insertStoreIo(storeIoEx);
//        } catch (Exception e) {
//            log.info("?????????????????????");
//            e.printStackTrace();
//        }
//        return AjaxResult.success(storeIoEx);
        return AjaxResult.success(jsonObject);
    }


    /***
     * ???????????????????????????????????????????????????????????????????????????????????????????????????
     * @param noticeDto
     * @return
     */
    public AjaxResult processingIOSync(NoticeDto noticeDto) {

        //1.???????????????????????????????????????????????????????????????????????????

        //StoreIoEx storeIoEx = new StoreIoEx(noticeDto.getNoticeType() == 5 ? "1" : "2", ToolsUtils.getOrdersId("SIC"), "WCS??????", "????????????", "test", "test", ToolsUtils.getOrdersId("MPC"), ToolsUtils.getOrdersId("ODC"), "WH0003", 1.0, "2", "??????", "WCS", new Date());
//        StoreIoEx storeIoEx = new StoreIoEx();
//        storeIoEx.setTaskCode(noticeDto.getTaskCode());
//        storeIoEx.setTaskType(noticeDto.getNoticeType().toString());
//        Object o = dataStockOut(noticeDto.getTaskCode(), 1, 5);
        // List<StoreIoEntity> storeIoExlist=queryStockInExMapper.selectStoreList(noticeDto.getTaskCode());

//        storeIoEx = storeIoExes.get(1);
//
//        //?????????????????????????????????????????????
//        List<Stock> stockList = queryStockInExMapper.selectStockNullList();//???????????????????????????
//
//        List<Sern> sernList = new ArrayList<>();//??????????????????
//
//        List<StoreIoSonEx> storeIoSonExList = new ArrayList<>();//
//        Iterator it = stockList.iterator();
//
//        for (int i = 0; i < storeIoEx.getQuantity(); i++) {
//            if (it.hasNext()) {
//                Stock stock = (Stock) it.next();
//                //???????????????????????????????????????????????????????????????????????????????????????????????????,????????????
//                StoreIoSonEx storeIoSonEx = new StoreIoSonEx();
//                storeIoSonEx.setTaskCode(storeIoEx.getTaskCode());
//                storeIoSonEx.setRowId((long) i + 1);
//                storeIoSonEx.setCreateUser(storeIoEx.getMaker());
//                storeIoSonEx.setCreateDate(new Date());
//                storeIoSonEx.setOtherProductCode(ToolsUtils.getOrdersId("AQ"));
//                storeIoSonEx.setTaskState("2");
//
//                //???????????????????????????????????????????????????????????????????????????
//                Sern sern = new Sern();
//                sern.setRowid(storeIoSonEx.getRowId());
//                sern.setTaskcode(storeIoEx.getTaskCode());
//                sern.setProductcode(storeIoSonEx.getProductNum());
//                sern.setOtherproductcode(storeIoSonEx.getProductCode());
//                sern.setSern(ToolsUtils.getRandNum(10));
//                sern.setTraycode(ToolsUtils.getRandNum(10));
//                sern.setStorehousecode(stock.getStorehousecode());
//                sern.setScanuser("????????????");
//                sern.setScantime(new Date());
//                sernList.add(sern);
//
//                storeIoSonEx.setSern(sern.getSern());
//                storeIoSonExList.add(storeIoSonEx);
//
//                //????????????????????????????????????
//                stock.setSern(sern.getSern());
//                stock.setOtherProductCode(sern.getOtherproductcode());
//                queryStockInExMapper.updateStock(stock);
//            }
//        }
//        queryStockInExMapper.insertSernList(sernList);
//        queryStockInExMapper.insertStoreIoSonList(storeIoSonExList);
//        queryStockInExMapper.insertStoreIo(storeIoEx);
//
//        //2.???????????????????????????????????????????????????????????????????????????????????????????????????????????????
//
//
//        //??????
//       // List<StoreIoEntity> storeIoExlist = queryStockInExMapper.selectStoreList(storeIoEx.getTaskCode());

        List<StoreIoEntity> storeIoExlist = queryStockInExMapper.selectStoreList(noticeDto.getTaskCode());
//        //???????????????.??????????????????
//        for(StoreIoEntity storeIoEntity :storeIoExlist){
//            if(null != storeIoEntity.getSernList()){
//                for(SernEntity sernEntity : storeIoEntity.getSernList()){
//                    log.info("????????????????????????"+sernEntity.getProductcode());
//                    Product product = productMapper.getProductCode(sernEntity.getProductcode());
//                    log.info("???????????????"+product.toString()+"-------------");
//                    if(product == null ){
//                        sernEntity.setProductName("???????????????");
//                    }else {
//                        sernEntity.setProductName(product.getProductname());
//                        sernEntity.setProductNum(product.getProductNum());
//                    }
//                    sernEntity.setProductName(product.getProductname().toString());
//                }
//            }
//        }

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("result", storeIoExlist);
//        jsonObject.put("notice_type", noticeDto.getNoticeType() == 5 ? "1" : "2");
        jsonObject.put("notice_type", noticeDto.getNoticeType());
        jsonObject.put("timestamp", TimeUtils.nowInt());
        log.info("????????????????????????????????????"+jsonObject.toJSONString());
        String message = HttpUtil2.sendPost(REMOTE_HOST + apiNotice+"_TASK", jsonObject);
        log.info("????????????URL"+REMOTE_HOST + apiNotice+"_TASK");
        log.info("?????????????????????"+message);
        //3.???????????????????????????????????????????????????????????????

        try {
            Map linkedTreeMap = JsonUtils.json2map(message);
            log.info("??????????????????map???"+linkedTreeMap.toString());
            log.info("result:"+linkedTreeMap.get("result"));
            //???????????????outorderid
            String outorderid = (String) linkedTreeMap.get("result");
            log.info("outorderid" + outorderid);
            if(outorderid != null) {
                StoreIoEntity storeIoEntity = storeIoExlist.get(0);
                storeIoEntity.setOuterOrderId(outorderid);
                queryStockInExMapper.updateIoOuterOrderId(storeIoEntity);
            }
        } catch (Exception e) {
            log.info("?????????????????????");
            e.printStackTrace();
        }
//        return AjaxResult.success(storeIoEx);
        return AjaxResult.success(message);
    }

    /**
     * ?????????????????????????????????????????????????????????????????????????????????????????????
     * @param noticeDto
     * @return
     */
    public AjaxResult processingWcsStoreMoveSync(NoticeDto noticeDto){
        AjaxResult ajaxResult = new AjaxResult();
        List<StoreMoveEx> storeMoveExlist=storeMoveExMapper.selectStoreMoveList(noticeDto.getTaskCode());
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("result", storeMoveExlist);
        jsonObject.put("notice_type", noticeDto.getNoticeType());
        jsonObject.put("timestamp", TimeUtils.nowInt());
        log.info("?????????????????????????????????"+jsonObject.toJSONString());
        String message = HttpUtil2.sendPost(REMOTE_HOST + apiNotice+"_TASK", jsonObject);

        log.info("????????????URL"+REMOTE_HOST + apiNotice+"_TASK");
        log.info("?????????????????????"+message);
        //3.???????????????????????????????????????????????????????????????

        try {
            Map linkedTreeMap = JsonUtils.json2map(message);
            log.info("??????????????????map???"+linkedTreeMap.toString());
            log.info("result:"+linkedTreeMap.get("result"));
            String outorderid = (String) linkedTreeMap.get("result");
            if(outorderid != null) {
                log.info("outorderid:" + outorderid);
                //??????????????????outorderid
                StoreMoveEx storeMoveEx = storeMoveExlist.get(0);
                storeMoveEx.setOuterOrderId(outorderid);
                storeMoveExMapper.updateMoveOuterOrderId(storeMoveEx);
            }
        } catch (Exception e) {
            log.info("?????????????????????");
            e.printStackTrace();
        }
        return AjaxResult.success(message) ;


    }


    /**
     * ?????????????????????????????????????????????????????????????????????????????????????????????
     * @param noticeDto
     * @return
     */
    public AjaxResult processingWcsCheckSync(NoticeDto noticeDto){

        List<StoreCheckEntity> storeCheckExlist=storeCheckExMapper.selectStoreCheckList(noticeDto.getTaskCode());
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("result", storeCheckExlist);
        jsonObject.put("notice_type", noticeDto.getNoticeType());
        jsonObject.put("timestamp", TimeUtils.nowInt());
        log.info("???????????????????????????:"+jsonObject.toJSONString());
        String message = HttpUtil2.sendPost(REMOTE_HOST + apiNotice+"_TASK", jsonObject);

        try {
            Map linkedTreeMap = JsonUtils.json2map(message);
            log.info("??????????????????map???"+linkedTreeMap.toString());
            log.info("result:"+linkedTreeMap.get("result"));
            String outorderid = (String) linkedTreeMap.get("result");
            if(outorderid != null) {
                log.info("outorderid:" + outorderid);
                //????????????

            }
        } catch (Exception e) {
            log.info("?????????????????????");
            e.printStackTrace();
        }
        return AjaxResult.success(message) ;

    }



    /**
     * wcs????????????????????????????????????????????????
     * ?????????????????????,??????????????????
     * @param noticeDto
     * @return
     */


    public AjaxResult processingWcsOkSync(NoticeDto noticeDto){
        //1.?????????????????????????????????
        String state = "";
        //???????????????
        if (noticeDto.getNoticeType() == 1 || noticeDto.getNoticeType()==2 ){
            List<StoreIoEntity> storeIoExes = queryStockInExMapper.selectStoreList(noticeDto.getTaskCode());
            //2.???????????????????????????MPoCode
            if (null == storeIoExes || storeIoExes.size() < 1) {
                return AjaxResult.success("??????????????????????????????");
            } else {
                state = storeIoExes.get(0).getTaskState();
            }
            //???????????????????????????
            if (StringUtils.isEmpty(storeIoExes.get(0).getOuterOrderId())){
                return processingIOSync(noticeDto);
            }
        }else if(noticeDto.getNoticeType() == 3){
            List<StoreMoveEx> storeMoveExes = storeMoveExMapper.selectStoreMoveList(noticeDto.getTaskCode());
            //2.????????????????????????MPoCode
            if (null == storeMoveExes || storeMoveExes.size() < 1 ) {
                return AjaxResult.success("???????????????????????????");
            } else {
                state = storeMoveExes.get(0).getTaskState();
            }
            //???????????????????????????
            if (StringUtils.isEmpty(storeMoveExes.get(0).getOuterOrderId())){
                return processingWcsStoreMoveSync(noticeDto);
            }
        }else if(noticeDto.getNoticeType() == 4){
            List<StoreCheckEntity> storeCheckEntities = storeCheckExMapper.selectStoreCheckList(noticeDto.getTaskCode());
            if(null == storeCheckEntities || storeCheckEntities.size() < 1){
                return AjaxResult.success("????????????????????????");
            }else {
                state = storeCheckEntities.get(0).getTaskState();
            }
        }

        //3.???????????????
        JSONObject jsonObject = new JSONObject();
        if("2".equals(state)){
            jsonObject.put("state","??????");
        }else {
            jsonObject.put("state","?????????");
        }

        jsonObject.put("notice_type", String.valueOf(noticeDto.getNoticeType()));
        jsonObject.put("TaskCode",noticeDto.getTaskCode());
        jsonObject.put("timestamp", TimeUtils.nowInt());
        String message = HttpUtil2.sendPost(REMOTE_HOST + apiNotice, jsonObject);

        return AjaxResult.success(message);
    }

}


