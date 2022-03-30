package com.wms.warehouse.storeio.controller;

import com.alibaba.druid.support.json.JSONUtils;
import com.wms.base.product.domain.Product;
import com.wms.base.product.service.IProductService;
import com.wms.base.storehouse.service.IStorehouseService;
import com.wms.base.warehouse.service.IWarehouseService;
import com.wms.base.customer.domain.Customer;
import com.wms.base.customer.service.ICustomerService;
import com.wms.common.annotation.Log;
import com.wms.common.core.controller.BaseController;
import com.wms.common.core.domain.AjaxResult;
import com.wms.common.core.page.TableDataInfo;
import com.wms.common.enums.BusinessType;
import com.wms.common.utils.JsonUtils;
import com.wms.common.utils.StringUtils;
import com.wms.common.utils.ToolsUtils;
import com.wms.common.utils.poi.ExcelUtil;
import com.wms.framework.util.ShiroUtils;
import com.wms.warehouse.sern.domain.Barcodelist;
import com.wms.warehouse.sern.service.BarcodelistService;
import com.wms.warehouse.sern.domain.TrayCodeDetail;
import com.wms.warehouse.sern.service.TrayCodeDetailService;
import com.wms.warehouse.storeio.domain.StoreIo;
import com.wms.warehouse.storeio.domain.StoreIoExcel;
import com.wms.warehouse.storeio.domain.StoreIoSon;
import com.wms.warehouse.storeio.service.IStoreIoService;
import com.wms.base.storeson.domain.StoreSon;
import com.wms.base.storeson.service.IStoreSonService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 出入库主Controller
 *
 * @author assassin
 * @date 2019-12-30
 */
@Controller
@RequestMapping("/warehouse/storeio")
public class StoreIoController extends BaseController {
    private String prefix = "warehouse/storeio";


    @Autowired
    private IStoreIoService storeIoService;

    @Autowired
    private IWarehouseService warehouseService;

    @Autowired
    private IProductService productService;

    @Autowired
    private BarcodelistService barcodelistService;

    @Autowired
    private IStorehouseService storehouseService;

    @Autowired
    private IStoreSonService IStoreSonService;

    @Autowired
    private ICustomerService ICustomerService;

    @Autowired
    private TrayCodeDetailService TrayCodeDetailService;

    /**
     * 入库列表
     *
     * @return
     */
    @RequiresPermissions("warehouse:storein:view")
    @GetMapping("/storein")
    public String storein() {
        return prefix + "/storein";
    }

    /**
     * 条码明细-产品类型
     */
    @GetMapping("/sernInfo/{taskcode}/{taskstate}")
    public String detailSern(@PathVariable("taskcode") String taskcode,@PathVariable("taskstate") String taskstate, ModelMap mmap) {
        StoreIo storeIo = storeIoService.selectStoreIoById(taskcode);
        mmap.put("taskcode", taskcode);
        mmap.put("sernInfo", storeIo);
        mmap.put("taskstate", taskstate);
        return prefix + "/sernInfo";
    }

    @RequiresPermissions("warehouse:storein:view")
    @RequestMapping("/sernInfo2/{rowid}/{taskcode}/{quantity}/{taskstate}/{productCode}/{productName}/{productBatch}")
    public String selectProducts(@PathVariable("rowid") Long rowid, @PathVariable("taskcode") String taskcode, @PathVariable("quantity") int quantity,@PathVariable("taskstate") int taskstate,@PathVariable("productCode") String productCode,@PathVariable("productName") String productName,@PathVariable("productBatch") String productBatch,ModelMap mmap) {
        mmap.put("rowid", rowid);
        mmap.put("taskcode", taskcode);
        mmap.put("quantity", quantity);
        mmap.put("taskstate", taskstate);
        mmap.put("productCode", productCode);
        mmap.put("productName", productName);
        mmap.put("productBatch", productBatch);
        List<Barcodelist> barcodelists = barcodelistService.selectProduct(rowid, taskcode);
        mmap.put("Barcodelist", barcodelists);
        return prefix + "/sernInfo2";
    }

    /**
     * 条码维护列表（lxd-测试）
     */
    @PostMapping("/sernInfo2list")
    @ResponseBody
    public TableDataInfo sernInfo2list(Barcodelist barcodelist)
    {
        startPage();
        List<Barcodelist> list = barcodelistService.selectProduct(barcodelist.getRowId(), barcodelist.getTaskCode());
        return getDataTable(list);
    }


    @RequiresPermissions("warehouse:storein:BarCodeList")
    @Log(title = "条码明细", businessType = BusinessType.INSERT)
    @PostMapping("/sernInfoAdd")
    @ResponseBody
    public AjaxResult addBarCodeList(@RequestParam("list") String list) throws Exception {
        List<Barcodelist> barcodelist=JsonUtils.json2list(list,Barcodelist.class);
//        System.out.println(barcodelist.get(0).getRowId());
//        barcodelistService.deleteBarcodeList(barcodelist.get(0).getRowId(),barcodelist.get(0).getTaskCode());
        return toAjax(barcodelistService.insertBardCodeLists(barcodelist));
    }

    /**
     * 出库列表
     *
     * @return
     */
    @RequiresPermissions("warehouse:storeout:view")
    @GetMapping("/storeout")
    public String storeout() {
        return prefix + "/StoreOut";
    }

    /**
     * 出入库列表-指定库位-选择要维护的产品类型（1）
     *
     * @return
     */
    @RequiresPermissions("warehouse:storeout:view")
    @GetMapping("/selectKW_product/{taskcode}/{IO}")
    public String selectKW_product(@PathVariable("taskcode") String taskcode,@PathVariable("IO") String IO, ModelMap mmap) {
        StoreIo storeIo = storeIoService.selectStoreIoById(taskcode);
        mmap.put("storeIo", storeIo);
        mmap.put("IO",IO);
        return prefix + "/selectKW_product";
    }

    /**
     * 出库按产品-选择条码-选择要维护的产品类型
     *
     * @return
     */
    @RequiresPermissions("warehouse:storeout:view")
    @GetMapping("/selectBarcode_product/{taskcode}/{taskstate}/{IO}")
    public String selectBarcode_product(@PathVariable("taskcode") String taskcode,@PathVariable("taskstate") String taskstate,@PathVariable("IO") String IO, ModelMap mmap) {
        StoreIo storeIo = storeIoService.selectStoreIoById(taskcode);
        mmap.put("storeIo", storeIo);
        mmap.put("taskcode", taskcode);
        mmap.put("taskstate", taskstate);
        mmap.put("IO",IO);
        return prefix + "/selectBarcode_product";
    }

    /**
     * 出库按产品-选择条码-选择要维护的产品类型后-跳转页面到（选择该产品、该批次下的产品条码）
     *
     * @return
     */
    @RequiresPermissions("warehouse:storeout:view")
    @GetMapping("/open_out_selectBarcode/{productcode}/{quantity}/{rowid}/{taskcode}/{productbatch}")
    public String open_out_selectBarcode(@PathVariable("productcode") String productcode,@PathVariable("quantity") int quantity,@PathVariable("rowid") int rowid,@PathVariable("taskcode") String taskcode,@PathVariable("productbatch") String productbatch, ModelMap mmap) {
//        StoreIo storeIo = storeIoService.selectStoreIoById(taskcode);
        mmap.put("quantity", quantity);
        mmap.put("productcode", productcode);
        mmap.put("rowid", rowid);
        mmap.put("taskcode",taskcode);
        mmap.put("productbatch",productbatch);
        return prefix + "/open_out_selectBarcode";
    }

    /**
     * 出库按产品-选择条码-选择要维护的产品类型后-跳转页面到（选择该产品、该批次下的产品条码）
     *
     * @return
     */
//    @RequiresPermissions("warehouse:storeout:view")
    @GetMapping("/open_out_selectBarcode_OnlyRead/{productcode}/{quantity}/{rowid}/{taskcode}/{productbatch}")
    public String open_out_selectBarcode_OnlyRead(@PathVariable("productcode") String productcode,@PathVariable("quantity") int quantity,@PathVariable("rowid") int rowid,@PathVariable("taskcode") String taskcode,@PathVariable("productbatch") String productbatch, ModelMap mmap) {
//        StoreIo storeIo = storeIoService.selectStoreIoById(taskcode);
        mmap.put("productcode", productcode);
        mmap.put("rowid", rowid);
        mmap.put("taskcode",taskcode);
        mmap.put("productbatch",productbatch);
        return prefix + "/open_out_selectBarcode_OnlyRead";
    }

    /**
     * 出库按产品-选择条码-选择要维护的产品类型后-获取该产品、该批次下的产品条码
     */
    @PostMapping("/get_out_selectBarcode")
    @ResponseBody
    public TableDataInfo get_out_selectBarcode(TrayCodeDetail traycodedetaillist)
    {
//        startPage();
        List<TrayCodeDetail> list = TrayCodeDetailService.selectBarcodeListForOut(traycodedetaillist.getProductbatch(),traycodedetaillist.getProductcode());
        System.out.println(traycodedetaillist);
        return getDataTable(list);
    }

    /**
     * 出库按产品-选择条码-选择要维护的产品类型后-获取该产品、该批次下已插入到barcodelist中的产品条码
     */
    @PostMapping("/get_out_selectBarcode_YXZ")
    @ResponseBody
    public TableDataInfo get_out_selectBarcode_YXZ(Barcodelist barcodelist)
    {
//        startPage();
        List<Barcodelist> list = barcodelistService.selectProduct(barcodelist.getRowId(),barcodelist.getTaskCode());
        return getDataTable(list);
    }

    @RequiresPermissions("warehouse:storein:BarCodeList")
    @Log(title = "条码明细", businessType = BusinessType.INSERT)
    @PostMapping("/out_select_Barcode_Add")
    @ResponseBody
    public AjaxResult out_select_Barcode_Add(@RequestParam("list") String list) throws Exception {
        List<Barcodelist> barcodelist=JsonUtils.json2list(list,Barcodelist.class);
//        System.out.println(barcodelist.get(0).getRowId());
//        barcodelistService.deleteBarcodeList(barcodelist.get(0).getRowId(),barcodelist.get(0).getTaskCode());
        System.out.println(barcodelist);
        System.out.println(barcodelist.get(0).getBatch());
        System.out.println(barcodelist.get(0).getXhao());
        return toAjax(barcodelistService.insertBardCodeLists_out_select_Barcode(barcodelist));
    }

    /**
     * 出库列表-指定库位（2）
     *
     * @return
     */
    @RequiresPermissions("warehouse:storeout:view")
    @GetMapping("/selectKW_Out/{productcode}/{quantity}/{rowid}/{taskcode}")
    public String selectKW_Out(@PathVariable("productcode") String productcode,@PathVariable("quantity") int quantity,@PathVariable("rowid") int rowid,@PathVariable("taskcode") String taskcode, ModelMap mmap) {
//        StoreIo storeIo = storeIoService.selectStoreIoById(taskcode);
//        mmap.put("storeIo", storeIo);
        mmap.put("productcode",productcode);
        mmap.put("quantity",quantity);
        mmap.put("rowid",rowid);
        mmap.put("taskcode",taskcode);
        return prefix + "/selectKW_Out";
    }

    /**
     * 入库列表-指定库位（2）
     *
     * @return
     */
    @RequiresPermissions("warehouse:storeout:view")
    @GetMapping("/selectKW_In/{productcode}/{quantity}/{rowid}/{taskcode}")
    public String selectKW_In(@PathVariable("productcode") String productcode,@PathVariable("quantity") int quantity,@PathVariable("rowid") int rowid,@PathVariable("taskcode") String taskcode, ModelMap mmap) {
//        StoreIo storeIo = storeIoService.selectStoreIoById(taskcode);
//        mmap.put("storeIo", storeIo);
        mmap.put("productcode",productcode);
        mmap.put("quantity",quantity);
        mmap.put("rowid",rowid);
        mmap.put("taskcode",taskcode);
        return prefix + "/selectKW_In";
    }


    /**
     * 查询入库列表
     */
    @RequiresPermissions("warehouse:storein:list")
    @PostMapping("/listIn")
    @ResponseBody
    public TableDataInfo listIn(StoreIo storeIo) {
        startPage();
        storeIo.setTasktype("1");
        List<StoreIo> list = storeIoService.selectStoreIoList(storeIo);
        return getDataTable(list);
    }

    /**
     * 查询出库列表
     */
    @RequiresPermissions("warehouse:storeout:list")
    @PostMapping("/listOut")
    @ResponseBody
    public TableDataInfo listOut(StoreIo storeIo) {
        startPage();
        storeIo.setTasktype("2");
        List<StoreIo> list = storeIoService.selectStoreIoList(storeIo);
        return getDataTable(list);
    }

    /**
     * 导出入库列表
     */
    @RequiresPermissions("warehouse:storein:export")
    @Log(title = "入库任务管理", businessType = BusinessType.EXPORT)
    @PostMapping("/exportIn")
    @ResponseBody
    public AjaxResult exportIn(StoreIo storeIo) {
        storeIo.setTasktype("1");
        List<StoreIoExcel> list = storeIoService.selectStoreIoExcelList(storeIo);
        ExcelUtil<StoreIoExcel> util = new ExcelUtil<StoreIoExcel>(StoreIoExcel.class);
        return util.exportExcel(list, "StoreIoEx");
    }

    /**
     * 导出条码列表（按产品）
     */
    @RequiresPermissions("warehouse:storein:export")
    @Log(title = "条码明细", businessType = BusinessType.EXPORT)
    @PostMapping("/sernInfo2list/exportIn")
    @ResponseBody
    public AjaxResult sernInfo2listExportIn(Barcodelist barcodelist) {
        List<Barcodelist> list = barcodelistService.selectProduct(barcodelist.getRowId(), barcodelist.getTaskCode());
        ExcelUtil<Barcodelist> util = new ExcelUtil<Barcodelist>(Barcodelist.class);
        return util.exportExcel(list, "SernInfo2Ex");
    }
    /**
     * 导出条码列表（入库--任务下所有的）
     */
    @RequiresPermissions("warehouse:storein:export")
    @Log(title = "条码明细", businessType = BusinessType.EXPORT)
    @PostMapping("/sernInfo2list/exportInAll")
    @ResponseBody
    public AjaxResult sernInfo2listExportInAll(Barcodelist barcodelist) {
        List<Barcodelist> list = barcodelistService.selectProductByTaskCode(barcodelist.getTaskCode());
        ExcelUtil<Barcodelist> util = new ExcelUtil<Barcodelist>(Barcodelist.class);
        return util.exportExcel(list, "SernInfo2Ex");
    }

    /**
     * 导出出库列表
     */
    @RequiresPermissions("warehouse:storeout:export")
    @Log(title = "出库任务管理", businessType = BusinessType.EXPORT)
    @PostMapping("/exportOut")
    @ResponseBody
    public AjaxResult exportOut(StoreIo storeIo) {
        storeIo.setTasktype("2");
        List<StoreIoExcel> list = storeIoService.selectStoreIoExcelList(storeIo);
        ExcelUtil<StoreIoExcel> util = new ExcelUtil<StoreIoExcel>(StoreIoExcel.class);
        return util.exportExcel(list, "StoreIoEx");
    }

    /**
     * 新增入库任务管理
     */
    @GetMapping("/addIn")
    public String addIn(ModelMap mmap) {
        mmap.put("whList", warehouseService.selectWarehouseDict());
        return prefix + "/add";
    }

    /**
     * 新增托盘入库任务管理
     */
    @GetMapping("/addInTray")
    public String addInTray(ModelMap mmap) {
        mmap.put("whList", warehouseService.selectWarehouseDict());
        return prefix + "/addInTray";
    }

    /**
     * 新增出库任务管理
     */
    @GetMapping("/addOut")
    public String addOut(ModelMap mmap) {
        mmap.put("whList", warehouseService.selectWarehouseDict());
        mmap.put("customerList",ICustomerService.selectCustomerDict());
        return prefix + "/addOut";
    }

    @PostMapping("/selectSums")
    @ResponseBody
    public AjaxResult getStoreHouse(){
        return AjaxResult.success(storehouseService.selectSums());
    }

    /**
     * 新增托盘入库任务管理
     */
    @GetMapping("/addOutTray")
    public String addOutTray(ModelMap mmap) {
        mmap.put("whList", warehouseService.selectWarehouseDict());
        return prefix + "/addOutTray";
    }

    /**
     * 新增保存出入库任务管理
     */
    @RequiresPermissions("warehouse:storeio:add")
    @Log(title = "出入库任务管理", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSaveIn(StoreIo storeIo, @RequestParam("list") String list) throws Exception {
        if (StringUtils.isNotEmpty(list)) {
            storeIo.setStoreIoSonList(JsonUtils.json2list(list, StoreIoSon.class));
        }
        storeIo.setTaskcode(ToolsUtils.getOrdersId("SIT"));
        storeIo.setCreateuser(ShiroUtils.getSysUser().getUserName());
        storeIo.setCreatedate(new Date());
        return toAjax(storeIoService.insertStoreIo(storeIo));
    }

    /**
     * 修改出入库任务管理
     */
    @GetMapping("/edit/{taskcode}")
    public String edit(@PathVariable("taskcode") String taskcode, ModelMap mmap) {
        StoreIo storeIo = storeIoService.selectStoreIoById(taskcode);
        mmap.put("storeIo", storeIo);
        mmap.put("whList", warehouseService.selectWarehouseDict());
        mmap.put("customerList",ICustomerService.selectCustomerDict());
        return prefix + "/edit";
    }

    /**
     * 修改入库任务管理
     */
    @GetMapping("/editTray/{taskcode}")
    public String editTray(@PathVariable("taskcode") String taskcode, ModelMap mmap) {
        StoreIo storeIo = storeIoService.selectStoreIoById(taskcode);
        mmap.put("storeIo", storeIo);
        mmap.put("whList", warehouseService.selectWarehouseDict());
        return prefix + "/editTray";
    }

    /**
     * 修改出库任务管理
     */
    @GetMapping("/editTrayOut/{taskcode}")
    public String editTrayOut(@PathVariable("taskcode") String taskcode, ModelMap mmap) {
        StoreIo storeIo = storeIoService.selectStoreIoById(taskcode);
        mmap.put("storeIo", storeIo);
        mmap.put("whList", warehouseService.selectWarehouseDict());
        return prefix + "/editTrayOut";
    }

    /**
     * 修改保存出入库任务管理
     */
    @RequiresPermissions("warehouse:storeio:edit")
    @Log(title = "出入库任务管理", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(StoreIo storeIo, @RequestParam("list") String list) throws Exception {
        if (StringUtils.isNotEmpty(list)) {
            storeIo.setStoreIoSonList(JsonUtils.json2list(list, StoreIoSon.class));
        }
        return toAjax(storeIoService.updateStoreIo(storeIo, ShiroUtils.getSysUser().getUserName()));
    }

    /**
     * 删除入库
     */
    @RequiresPermissions("warehouse:storeio:remove")
    @Log(title = "入库任务管理", businessType = BusinessType.DELETE)
    @PostMapping("/removeIn")
    @ResponseBody
    public AjaxResult removeIn(String ids) {
        return toAjax(storeIoService.deleteStoreIoByIds(ids));
    }

    /**
     * 删除出库
     */
    @RequiresPermissions("warehouse:storeout:remove")
    @Log(title = "出库任务管理", businessType = BusinessType.DELETE)
    @PostMapping("/removeOut")
    @ResponseBody
    public AjaxResult removeOut(String ids) {
        return toAjax(storeIoService.deleteStoreIoByIds(ids));
    }

    /***
     * 选择产品界面,入库
     */
    @Log(title = "出入库任务管理", businessType = BusinessType.OTHER)
    @GetMapping("/product")
    public String product() {
        return prefix + "/product";
    }


    /***
     * 选择产品界面，出库
     */
    @Log(title = "出入库任务管理", businessType = BusinessType.OTHER)
    @GetMapping("/productOut/{whcode}")
    public String productOut(@PathVariable("whcode") String whcode, ModelMap mmap) {
        mmap.put("whcode", whcode);
        return prefix + "/productOut";
    }


    /***
     * 选择托盘界面，出库
     */
    @Log(title = "出入库任务管理", businessType = BusinessType.OTHER)
    @GetMapping("/productTray/{whcode}")
    public String productTray(@PathVariable("whcode") String whcode, ModelMap mmap) {
        mmap.put("whcode", whcode);
        return prefix + "/productTray";
    }


    /**
     * 入库详情
     */
    @GetMapping("/detail/{taskcode}")
    public String detail(@PathVariable("taskcode") String taskcode, ModelMap mmap) {
        StoreIo storeIo = storeIoService.selectStoreIoById(taskcode);
        mmap.put("storeIo", storeIo);
        mmap.put("whList", warehouseService.selectWarehouseDict());
        mmap.put("customerList",ICustomerService.selectCustomerDict());
        return prefix + "/details";
    }

    /**
     * 出库详情
     */
    @GetMapping("/detailOut/{taskcode}")
    public String detailOut(@PathVariable("taskcode") String taskcode, ModelMap mmap) {
        StoreIo storeIo = storeIoService.selectStoreIoById(taskcode);
        mmap.put("storeIo", storeIo);
        mmap.put("whList", warehouseService.selectWarehouseDict());
        return prefix + "/detailsOut";
    }

    /**
     * 入库任务执行
     */
//    @RequiresPermissions("warehouse:storeio:cancel")
    @Log(title = "任务执行", businessType = BusinessType.EXECUTE)
    @PostMapping("/execute")
    @ResponseBody
    public AjaxResult execute(String taskcodes) {
        return toAjax(storeIoService.execute(taskcodes));
    }

    /**
     * 入库任务下发前  根据任务号查询已经维护的条码
     */
//    @RequiresPermissions("warehouse:storeio:cancel")
    @Log(title = "任务下发", businessType = BusinessType.SELECTBARCODEBYTASKCODE)
    @PostMapping("/selectBarcodeByTaskcode")
    @ResponseBody
    public AjaxResult selectBarcodeByTaskcode(String taskcode) {
        return AjaxResult.success(barcodelistService.selectProductByTaskCode(taskcode));
    }

    /**
     * 入库任务下发前  根据任务号查询已经指定库位的任务明细
     */
//    @RequiresPermissions("warehouse:storeio:cancel")
    @Log(title = "任务下发", businessType = BusinessType.SELECTBARCODEBYTASKCODE)
    @PostMapping("/getInTaskInfoByTaskCode")
    @ResponseBody
    public AjaxResult getInTaskInfoByTaskCode(String taskcode) {
        return AjaxResult.success(IStoreSonService.getInTaskInfoByTaskCode(taskcode));
    }


    /**
     * 取消入库
     */
    @RequiresPermissions("warehouse:storeio:cancel")
    @Log(title = "入库任务管理", businessType = BusinessType.CANCEL)
    @PostMapping("/cancelIn")
    @ResponseBody
    public AjaxResult cancelIn(String taskcodes) {
        return toAjax(storeIoService.cancel(taskcodes));
    }

    /**
     * 取消出库
     */
    @RequiresPermissions("warehouse:storeio:cancel")
    @Log(title = "出库任务管理", businessType = BusinessType.CANCEL)
    @PostMapping("/cancelOut")
    @ResponseBody
    public AjaxResult cancelOut(String taskcodes) {
        return toAjax(storeIoService.cancel(taskcodes));
    }

    /**
     * 重启入库
     */
    @RequiresPermissions("warehouse:storeio:restart")
    @Log(title = "入库任务管理", businessType = BusinessType.RESTART)
    @PostMapping("/restartIn")
    @ResponseBody
    public AjaxResult restartIn(String taskcodes) {
        return toAjax(storeIoService.restart(taskcodes));
    }

    /**
     * 重启出库
     */
    @RequiresPermissions("warehouse:storeio:restart")
    @Log(title = "出库任务管理", businessType = BusinessType.RESTART)
    @PostMapping("/restartOut")
    @ResponseBody
    public AjaxResult restartOut(String taskcodes) {
        return toAjax(storeIoService.restart(taskcodes));
    }

    /**
     * 入库导入
     */
    @RequiresPermissions("warehouse:storein:import")
    @Log(title = "入库任务导入", businessType = BusinessType.IMPORT)
    @PostMapping("/importIn")
    @ResponseBody
    public AjaxResult importIn(MultipartFile file) throws IOException {
        return storeIoService.importInExcel(file, "1");
    }

    /**
     * 出库导入
     */
    @RequiresPermissions("warehouse:storeout:import")
    @Log(title = "出库任务导入", businessType = BusinessType.IMPORT)
    @PostMapping("/importOut")
    @ResponseBody
    public AjaxResult importOut(MultipartFile file) throws IOException {
        return storeIoService.importOutExcel(file, "2");
    }


    /***
     * 导出入库模板
     */
    @Log(title = "模板导出", businessType = BusinessType.EXPORT)
    @GetMapping("/importTemplate")
    @ResponseBody
    public AjaxResult exportModel() throws IOException {

        return storeIoService.exportStoreInModel("入库模板");
    }

    /***
     * 导出出库模板
     */
    @Log(title = "模板导出", businessType = BusinessType.EXPORT)
    @GetMapping("/importTemplateOut")
    @ResponseBody
    public AjaxResult exportModelOut() throws IOException {

        return storeIoService.exportStoreOutModel("出库模板");
    }

    @PostMapping("/productStoreOut")
    @ResponseBody
    public TableDataInfo productStoreOut(Product product) {
        startPage();
        List<Product> productList = new ArrayList<>();
        return getDataTable(productList);
    }


}
