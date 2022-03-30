package com.wms.warehouse.storeio.service.impl;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import com.wms.base.product.domain.Product;
import com.wms.base.storeson.mapper.StoreSonMapper;
import com.wms.base.warehouse.domain.Warehouse;
import com.wms.base.product.mapper.ProductMapper;
import com.wms.base.warehouse.mapper.WarehouseMapper;
import com.wms.common.core.domain.AjaxResult;
import com.wms.common.core.text.Convert;
import com.wms.common.utils.StringUtils;
import com.wms.common.utils.ToolsUtils;
import com.wms.common.utils.poi.ExcelUtil;
import com.wms.framework.util.ShiroUtils;
import com.wms.framework.web.service.DictService;
import com.wms.infoquery.querystockstatus.domain.Stock;
import com.wms.infoquery.querystockstatus.mapper.StockMapper;
import com.wms.system.domain.SysDictData;
import com.wms.warehouse.storeio.domain.*;
import com.wms.warehouse.storeio.mapper.RgvMapper;
import com.wms.warehouse.storeio.mapper.StackerMapper;
import com.wms.warehouse.storeio.mapper.StoreIoSonMapper;
import com.wms.warehouse.storemove.domain.StoreMove;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.Store;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.wms.warehouse.storeio.mapper.StoreIoMapper;
import com.wms.warehouse.storeio.service.IStoreIoService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

/**
 * 出入库主Service业务层处理
 * 
 * @author assassin
 * @date 2019-12-30
 */
@Service
@Slf4j
public class StoreIoServiceImpl implements IStoreIoService 
{
    @Autowired
    private StoreIoMapper storeIoMapper;

    @Autowired
    private StackerMapper stackerMapper;

    @Autowired
    private RgvMapper rgvMapper;

    @Autowired
    private StoreIoSonMapper storeIoSonMapper;

    @Autowired
    private WarehouseMapper warehouseMapper;

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private DictService dictService;

    @Autowired
    private StockMapper stockMapper;

    @Autowired
    private StoreSonMapper storeSonMapper;

    /**
     * 查询出入库主
     * 
     * @param taskcode 出入库主ID
     * @return 出入库主
     */
    @Override
    public StoreIo selectStoreIoById(String taskcode)
    {
        return storeIoMapper.selectStoreIoById(taskcode);
    }

    /**
     * 查询出入库主列表
     * 
     * @param storeIo 出入库主
     * @return 出入库主
     */
    @Override
    public List<StoreIo> selectStoreIoList(StoreIo storeIo)
    {
        return storeIoMapper.selectStoreIoList(storeIo);
    }

    /**
     * 新增出入库主
     * 
     * @param storeIo 出入库主
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int insertStoreIo(StoreIo storeIo)
        {
            //初始化堆垛机、RGV任务表
//        List<Stacker> stackerList=new ArrayList<>();
//        List<Rgv> rgvList=new ArrayList<>();



        //将入库表内容写入入库子表
        if (storeIo.getStoreIoSonList()!=null){
            for (StoreIoSon storeIoSon:storeIo.getStoreIoSonList()){
                storeIoSon.setCreateuser(storeIo.getCreateuser());
                storeIoSon.setCreatedate(new Date());
                storeIoSon.setTaskcode(storeIo.getTaskcode());

            }

            //批量入库子表
            if (storeIo.getStoreIoSonList().size()>0){
                storeIoSonMapper.insertStoreIoSonList(storeIo.getStoreIoSonList());
            }
        }else{
            if (storeIo.getTasktype().equals("5") || storeIo.getTasktype().equals("6") ){
                List<StoreIoSon> storeIoSonList=new ArrayList<>();
                for(int i =0; i<storeIo.getQuantity();i++){
                    storeIoSonList.add(new StoreIoSon((long)(i+1),storeIo.getTaskcode(),1L,ShiroUtils.getLoginName(),new Date()));
                }
                storeIoSonMapper.insertStoreIoTraySonList(storeIoSonList);
            }
        }
        //新增入库主表
        return storeIoMapper.insertStoreIo(storeIo);
    }

    /**
     * 修改出入库主
     * 
     * @param storeIo 出入库主
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateStoreIo(StoreIo storeIo, String userName)
    {
        //初始化新增子表内容
        List<StoreIoSon> insertSons=new ArrayList<>();

        //初始化修改子表内容
        List<StoreIoSon> updateSons=new ArrayList<>();

        //初始化新增任务表内容
//        List<Rgv>  insertRgv=new ArrayList<>();
//        List<Stacker> insertStacker=new ArrayList<>();

        //初始化删除任务表内容
//        List<Rgv>  deleteRgv=new ArrayList<>();
//        List<Stacker> deleteStacker=new ArrayList<>();

        //判断入库任务状态，验证是否需要修改内容
        StoreIo info=storeIoMapper.selectStoreIoByIdAndList(storeIo);
        if (!info.getTaskstate().equals("0")){
            return 0;
        }
        Long maxRowId=info.getMaxRowId()==null?0:info.getMaxRowId();
        if ((storeIo.getTasktype().equals("1") || storeIo.getTasktype().equals("2")) && storeIo.getStoreIoSonList()!=null) {
            //根据子表有无id进行更新或修改的判断
            for (StoreIoSon storeIoSon : storeIo.getStoreIoSonList()) {
                //id不存在为新增数据
                if (storeIoSon.getId() == null) {
                    storeIoSon.setRowid(++maxRowId);
                    storeIoSon.setCreateuser(userName);
                    storeIoSon.setCreatedate(new Date());
                    storeIoSon.setTaskcode(storeIo.getTaskcode());
                    insertSons.add(storeIoSon);
//                //保存堆垛机、RGV数据
//                Stacker stacker=new Stacker(storeIoSon.getRowid(),storeIo.getTasktype(),storeIo.getTaskcode(),"0",userName,new Date());
//                Rgv rgv=new Rgv(storeIoSon.getRowid(),storeIo.getTasktype(),storeIo.getTaskcode(),"0",userName,new Date());
//                //拆分入库子表，生成任务数据堆垛机、RGV，根据数量生成相对应的条数
//                for (int i=0;i<storeIoSon.getQuantity();i++){
//                    insertStacker.add(stacker);
//                    insertRgv.add(rgv);
//                }
                } else {
                    Iterator iterator = info.getStoreIoSonList().iterator();
                    //id存在是，根据是否修改元数据来进行修改和删除判断
                    while (iterator.hasNext()) {
                        StoreIoSon ioSon = (StoreIoSon) iterator.next();

                        //id相同，证明此数据为修改或未修改数据，都可以表示为修改数据
                        if (storeIoSon.getId().equals(ioSon.getId())) {
                            updateSons.add(storeIoSon);
//
//                        //修改后数量大于原数量
//                        if (storeIoSon.getQuantity()>ioSon.getQuantity()) {
//                            for (int i = 0; i < Long.hashCode(storeIoSon.getQuantity() - ioSon.getQuantity()); i++) {
//                                //新增RGV、堆垛机任务
//                                Stacker stacker=new Stacker(ioSon.getRowid(),storeIo.getTasktype(),storeIo.getTaskcode(),"0",userName,new Date());
//                                Rgv rgv=new Rgv(ioSon.getRowid(),storeIo.getTasktype(),storeIo.getTaskcode(),"0",userName,new Date());
//                                insertStacker.add(stacker);
//                                insertRgv.add(rgv);
//                            }
//                        }else if(storeIoSon.getQuantity()<ioSon.getQuantity()){
//                            //修改后数量小于原数量，删除任务表内容
//                                //删除RGV、堆垛机任务
//                                Stacker stacker=new Stacker(ioSon.getRowid(),storeIo.getTaskcode(),userName,new Date(),ioSon.getQuantity() - storeIoSon.getQuantity());
//                                Rgv rgv=new Rgv(ioSon.getRowid(),storeIo.getTaskcode(),userName,new Date(),ioSon.getQuantity() - storeIoSon.getQuantity());
//                                deleteStacker.add(stacker);
//                                deleteRgv.add(rgv);
//                        }
                            //移除修改数据，剩余的原子表数据即为已删除数据
                            iterator.remove();
                        }
                    }
                }
            }

            //新增入库子表
            if (insertSons.size() > 0) {
                storeIoSonMapper.insertStoreIoSonList(insertSons);
                System.out.println(insertSons);
            }

            //修改入库子表
            if (updateSons.size() > 0) {
                storeIoSonMapper.updateStoreIoSonList(updateSons);
                System.out.println(updateSons);
            }

            //删除入库子表
            if (info.getStoreIoSonList().size() > 0) {
                storeIoSonMapper.deleteStoreIoSonByIdList(info.getStoreIoSonList());
            }
        }else if (storeIo.getTasktype().equals("5")) {
            //传入数量大于查询数量为增加数量
            if (storeIo.getQuantity()>info.getQuantity()){
                for (int i=0;i<storeIo.getQuantity()-info.getQuantity();i++){
                    insertSons.add(new StoreIoSon(info.getQuantity()+i+1,storeIo.getTaskcode(),1L,ShiroUtils.getLoginName(),new Date()));

                }
                //新增入库子表
                if (insertSons.size() > 0) {
                    storeIoSonMapper.insertStoreIoTraySonList(insertSons);
                }
            }else if (storeIo.getQuantity()<info.getQuantity()){//传入数量小于查询数量为减少数量
                Iterator iterator = info.getStoreIoSonList().iterator();
                //id存在是，根据是否修改元数据来进行修改和删除判断
                while (iterator.hasNext()) {
                    StoreIoSon ioSon = (StoreIoSon) iterator.next();
                    //如果行号小于等于传入数量，即为正确须保留数量，去除
                    if(ioSon.getRowid()<=storeIo.getQuantity()){
                        iterator.remove();
                    }
                }
                //删除入库子表
                if (info.getStoreIoSonList().size() > 0) {
                    storeIoSonMapper.deleteStoreIoTraySonByIdList(info.getStoreIoSonList());
                }
            }
        }else if (storeIo.getTasktype().equals("6")) {
            Iterator iterator = storeIo.getStoreIoSonList().iterator();
            while (iterator.hasNext()){
                StoreIoSon ioSon = (StoreIoSon) iterator.next();
                if (ioSon.getId()==null){
                    ioSon.setRowid(++maxRowId);
                    ioSon.setCreateuser(userName);
                    ioSon.setCreatedate(new Date());
                    ioSon.setTaskcode(storeIo.getTaskcode());
                    insertSons.add(ioSon);
                    iterator.remove();
                }
            }
            //删除子表
            if (storeIo.getStoreIoSonList().size() > 0) {
                storeIoSonMapper.deleteStoreIoTraySonByNotList(storeIo.getStoreIoSonList(),storeIo.getTaskcode());
            }else{
                storeIoSonMapper.deleteStoreIoSonByTaskCodes(Convert.toStrArray(storeIo.getTaskcode()));
            }
            //新增子表
            if (insertSons.size() > 0) {
                storeIoSonMapper.insertStoreIoTraySonList(insertSons);
            }
            //修改子表
            if (storeIo.getStoreIoSonList().size() > 0) {
                storeIoSonMapper.updateStoreIoSonList(storeIo.getStoreIoSonList());
            }
        }


        //修改主表内容
        return storeIoMapper.updateStoreIo(storeIo);
    }

    /**
     * 取消任务
     * @param taskcodes
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int cancel(String taskcodes) {
        //设置子表任务状态为已取消
        storeIoSonMapper.cancelStoreIoSonByTaskCodes(Convert.toStrArray(taskcodes));

        //修改任务表任务状态为已取消
        stackerMapper.cancelStackerByTaskCodes(Convert.toStrArray(taskcodes));
        rgvMapper.cancelRgvByTaskCodes(Convert.toStrArray(taskcodes));

        //修改主表内容任务状态为已取消
        return storeIoMapper.cancelStoreIoByTaskCodes(Convert.toStrArray(taskcodes));
    }

    /**
     * 执行任务
     * @param taskcodes
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int execute(String taskcodes) {
        //设置子表任务状态为已执行
        storeIoSonMapper.executeStoreIoSonByTaskCodes(Convert.toStrArray(taskcodes));

        //修改任务表任务状态为已执行
        stackerMapper.executeStackerByTaskCodes(Convert.toStrArray(taskcodes));
        rgvMapper.executeRgvByTaskCodes(Convert.toStrArray(taskcodes));

        //修改任务明细表状态
        storeSonMapper.executeStoreSonByTaskCodes(Convert.toStrArray(taskcodes));

        //修改主表内容任务状态为已执行
        return storeIoMapper.executeStoreIoByTaskCodes(Convert.toStrArray(taskcodes));
    }

    /**
     * 重启已取消的任务
     * @param taskcodes
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int restart(String taskcodes) {
        //设置子表任务状态为未执行
        storeIoSonMapper.restartStoreIoSonByTaskCodes(Convert.toStrArray(taskcodes));

        //修改任务表任务状态为未执行
        stackerMapper.restartStackerByTaskCodes(Convert.toStrArray(taskcodes));
        rgvMapper.restartRgvByTaskCodes(Convert.toStrArray(taskcodes));

        //修改主表内容任务状态为未执行
        return storeIoMapper.restartStoreIoByTaskCodes(Convert.toStrArray(taskcodes));
    }



    /**
     * 删除出入库主对象
     *
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteStoreIoByIds(String ids)
    {
        //修改子表内容
        storeIoSonMapper.deleteStoreIoSonByTaskCodes(Convert.toStrArray(ids));

        //修改任务表内容
        stackerMapper.deleteStackerByTaskCodes(Convert.toStrArray(ids));
        rgvMapper.deleteRgvByTaskCodes(Convert.toStrArray(ids));

        //修改主表内容
        return storeIoMapper.deleteStoreIoByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除出入库主信息
     * 
     * @param tasktype 出入库主ID
     * @return 结果
     */
    public int deleteStoreIoById(String tasktype)
    {
        return storeIoMapper.deleteStoreIoById(tasktype);
    }


    /**
     * 导出入库模板
     * @param sheetName
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public AjaxResult exportStoreInModel(String sheetName) throws IOException {

//        String[] fields={"任务名称","任务描述","供应商信息","制单人","生产订单编号","采购订单编号","仓库名称","任务备注","产品编号+产品名称+产品型号+产品批次","数量","产品备注"};
//        String[] fields={"任务类型","任务名称","任务描述","制单人","仓库名称","备注","产品代号","产品名称","产品型号","数量"};
        String[] fields={"任务名称","任务描述","制单人","仓库名称","任务备注","产品型号","产品批次","数量","产品备注"};
        List<String> whStringList= warehouseMapper.getCodeAndName();
        String[] whString=new String[whStringList.size()];
        whStringList.toArray(whString);

        List<String> pStringList= productMapper.getCodeAndNameAndModelAndBatch();
        String[] pString=new String[pStringList.size()];
        pStringList.toArray(pString);
        //对产品类型进行转换
        List<SysDictData> base_product_model = dictService.getType("base_product_model");
        for (int i= 0; i<pString.length; i++){
            String[] pstrings = pString[i].split("\\+");
            String s = pstrings[2];
            for (SysDictData model : base_product_model){
                if (s.equals(model.getDictValue())){
                    pstrings[2] = model.getDictLabel();
                    pString[i] = pstrings[0] + "+" + pstrings[1] + "+" + pstrings[2] + "+" + pstrings[3];
                    break;
                }
            }
        }
        ExcelUtil<StoreIo> util = new ExcelUtil<StoreIo>(StoreIo.class);
        return util.exportStoreInModel(fields,whString,pString,sheetName);
    }
/**
     * 导出出库模板
     * @param sheetName
     * @returnouttype
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public AjaxResult exportStoreOutModel(String sheetName) throws IOException {

//        String[] fields={"仓库名称","任务名称","任务描述","供应商信息","制单人","产品编号+产品名称+产品型号+产品批次","生产订单编号","采购订单编号","任务备注","数量","产品备注"};
        String[] fields={"仓库名称","任务名称","任务描述","制单人","任务备注","产品型号","产品批次","数量","产品备注","客户名称","客户编码"};
        List<StoreMove> warehouseAndStockList=warehouseMapper.selectWarehouseAndStock();
        List<List<String>> productList=new ArrayList<>();
        List<String> warehouseList=new ArrayList<>();
        for (StoreMove storeMove: warehouseAndStockList){
            warehouseList.add(storeMove.getWhname());
            List<String> stringList=new ArrayList<>();
            stringList.add(storeMove.getWhname());
            for (Stock stock:storeMove.getStockList()){
                stringList.add(stock.getProductname());
            }
            productList.add(stringList);
        }
        ExcelUtil<StoreIo> util = new ExcelUtil<StoreIo>(StoreIo.class);
        return util.exportStoreOutModel(fields,productList,warehouseList,sheetName);
    }

//    @Override
//    @Transactional(rollbackFor = Exception.class)
//    public AjaxResult importInExcel(MultipartFile file, String taskType) throws IOException {
//        int rowcount=0;
//
//        //初始化入库表、入库子表、任务表
//        List<StoreIo> storeIoList=new ArrayList<>();
//
//        List<Stacker> stackerList=new ArrayList<>();
//        List<Rgv> rgvList=new ArrayList<>();
//
//        String filePath=file.getOriginalFilename();//导入的文件名
//
//        //工作簿
//        Sheet sheet=null;
//
//        //2003版本
//        if(filePath.endsWith(".xls")){
//            POIFSFileSystem fileSystem=new POIFSFileSystem(file.getInputStream());
//            HSSFWorkbook hssfWorkbook=new HSSFWorkbook(fileSystem);
//            sheet=hssfWorkbook.getSheetAt(0);
//        }
//
//        if (filePath.endsWith(".xlsx")){
//            XSSFWorkbook xssfWorkbook=new XSSFWorkbook(file.getInputStream());
//            sheet=xssfWorkbook.getSheetAt(0);
//        }
//
//        if (sheet==null){
//            AjaxResult.error("表格内容为空");
//        }
//
//        rowcount=sheet.getPhysicalNumberOfRows();//获取行数
//
//        if (rowcount<=1){
//            return AjaxResult.error("表格内容为空！");
//        }
//
////        String[] fields={"任务名称","任务描述","供应商信息","制单人","生产订单编号","采购订单编号","仓库名称","任务备注","产品编号+产品名称+产品型号+产品批次","数量","产品备注","摆放层"};
////        String[] fields={"任务类型","任务名称","放置层数","任务描述","制单人","仓库名称","总数量","任务状态","备注"};
//        String[] fields={"任务类型","任务名称","任务描述","制单人","仓库名称","备注","产品代号","产品名称","产品型号","数量"};
//        Row titleRow=sheet.getRow(0);
//        for (int i=0;i<fields.length;i++){
//            if (!fields[i].equals(StringUtils.getString(titleRow.getCell(i)).trim())){
//                return AjaxResult.error("模板内容与导入模板不同，请重新生成模板填充内容！");
//            }
//        }
//        //转换产品类型
//        List<SysDictData> base_product_model = dictService.getType("base_product_model");
//        //转换任务类型
//        List<SysDictData> warehouse_task_type = dictService.getType("warehouse_task_type");
//
//        for(int rowindex=1;rowindex<rowcount;rowindex++){
//            Row row=sheet.getRow(rowindex);
//            if (row==null){
//                break;
//            }
//            String tasktype= StringUtils.getString(row.getCell(0)).trim();//任务类型
//            String taskname= StringUtils.getString(row.getCell(1)).trim();//任务名称
//            String productdetail= StringUtils.getString(row.getCell(2)).trim();//任务描述
//            String maker= StringUtils.getString(row.getCell(3)).trim();//制单人
//            String Whcode= StringUtils.getString(row.getCell(4)).trim();//仓库编码
//            String memo= StringUtils.getString(row.getCell(5)).trim();//备注
//            String productcode= StringUtils.getString(row.getCell(6)).trim();//产品代号
//            String productname= StringUtils.getString(row.getCell(7)).trim();//产品名称
//            String productmodel= StringUtils.getString(row.getCell(8)).trim();//产品型号
//            String quantity= StringUtils.getString(row.getCell(9)).trim();//数量
//            for (SysDictData model : base_product_model){
//                if (productmodel.equals(model.getDictLabel())){
//                    productmodel = model.getDictValue();
//                    break;
//                }
//            }
//            for (SysDictData model : warehouse_task_type){
//                if (tasktype.equals(model.getDictLabel())){
//                    tasktype = model.getDictValue();
//                    break;
//                }
//            }
////            String mpocode= StringUtils.getString(row.getCell(4)).trim();//生产订单编号
////            if(StringUtils.isNotEmpty(mpocode)) {
////                if (row.getCell(4).getCellType() == Cell.CELL_TYPE_NUMERIC) {
////                    DecimalFormat df = new DecimalFormat("#");// 转换成整型
////                    mpocode = df.format(row.getCell(4).getNumericCellValue());
////                } else {
////                    mpocode = StringUtils.getString(row.getCell(4)).trim();
////                }
////            }
////            String ordercode= StringUtils.getString(row.getCell(5)).trim();//采购订单编号
////            if(StringUtils.isNotEmpty(ordercode)) {
////                if (row.getCell(5).getCellType() == Cell.CELL_TYPE_NUMERIC) {
////                    DecimalFormat df = new DecimalFormat("#");// 转换成整型
////                    ordercode = df.format(row.getCell(5).getNumericCellValue());
////                } else {
////                    ordercode = StringUtils.getString(row.getCell(5)).trim();
////                }
////            }
//
////            String[] productinfo= StringUtils.getString(row.getCell(8)).trim().split("\\+");//产品信息
//
////            if(StringUtils.isNotEmpty(quantity)) {
////                if (row.getCell(9).getCellType() == Cell.CELL_TYPE_NUMERIC) {
////                    DecimalFormat df = new DecimalFormat("#");// 转换成整型
////                    quantity = df.format(row.getCell(9).getNumericCellValue());
////                } else {
////                    quantity = StringUtils.getString(row.getCell(9)).trim();
////                }
////            }
//
////            for (SysDictData model : base_product_model){
////                if (productinfo[2].equals(model.getDictLabel())){
////                    productinfo[2] = model.getDictValue();
////                    break;
////                }
////            }
//
//            //任务名称
//            if (StringUtils.isEmpty(taskname)){
//                return AjaxResult.error("第"+(rowindex+1)+"行，【任务名称】不能为空!");
//            }
//            if ((long) Double.parseDouble(quantity)<=0){
//                return AjaxResult.error("第"+(rowindex+1)+"行，【产品数量】不能为0！");
//            }
//
//            //校验仓库和产品是否正确
////            if (Whcode.length!=2){
////                return AjaxResult.error("第"+(rowindex+1)+"行，【仓库名称】格式错误!");
////            }
////            if (productinfo.length!=4){
////                return AjaxResult.error("第"+(rowindex+1)+"行，【产品编号+产品名称+产品型号+产品批次】格式错误!");
////            }
//
//            Warehouse warehouse=warehouseMapper.selectWarehouseById(Whcode);
//            if (warehouse==null){
//                return AjaxResult.error("第"+(rowindex+1)+"行，【仓库名称】错误，请检查或重新生成模板!");
//            }
//            Product product=productMapper.selectProductByProId(productcode);
//            if (product==null){
//                return AjaxResult.error("第"+(rowindex+1)+"行，【产品编号+产品名称+产品型号+产品批次】错误，请检查或重新生成模板!");
//            }
//
//
//            //初始化任务子表集合
//            List<StoreIoSon> storeIoSonList=new ArrayList<>();
//            if (storeIoList.size()>0){
//                StoreIo storeIo=null;
//                //循环校验是否存在任务单
//                for (StoreIo info:storeIoList){
//                    //如果任务名称+供应商信息+制单人+生产订单编号+采购订单编号+仓库编码相同时表示已存在任务单
//                    if (info.getTaskname().equals(taskname) && info.getMaker().equals(maker) && info.getWhcode().equals(Whcode)){
//                        storeIo=info;
//                        break;
//                    }
//                }
//                //如果任务单为空，表示不存在
//                if (storeIo==null){
//                    storeIo=new StoreIo(taskType,ToolsUtils.getOrdersId("SIT"),taskname,productdetail,maker,warehouse.getWhcode(), (long) Double.parseDouble(quantity),ShiroUtils.getSysUser().getUserName(),new Date());
//                    storeIoSonList.add(new StoreIoSon(1L,storeIo.getTaskcode(),product.getProductcode(),product.getProductname(),product.getProductmodel(),(long) Double.parseDouble(quantity),memo,ShiroUtils.getSysUser().getUserName(),new Date()));
//                    storeIo.setStoreIoSonList(storeIoSonList);
//                    storeIoList.add(storeIo);
//                }else{
//                    boolean isPresence=false;
//                    //任务单已存在，循环校验任务单是否存在该产品，
//                    for (StoreIoSon storeIoSon:storeIo.getStoreIoSonList()){
//                        //存在即将任务总数量、产品数量增加
//                        if (storeIoSon.getProductcode().equals(product.getProductcode())){
//                            storeIo.setQuantity(storeIo.getQuantity()+(long) Double.parseDouble(quantity));
//                            storeIoSon.setQuantity(storeIoSon.getQuantity()+(long) Double.parseDouble(quantity));
//                            isPresence=true;
//                            break;
//                        }
//                    }
//                    //isPresence为false时，表示产品不存在，增加任务单产品总数量，新增任务子表
//                    if (!isPresence){
//                        storeIo.setQuantity(storeIo.getQuantity()+(long) Double.parseDouble(quantity));
////                        storeIo.getStoreIoSonList().add(new StoreIoSonEx(storeIo.getStoreIoSonList().size()+1L,storeIo.getTaskcode(),product.getProductcode(),product.getProductname(),product.getProductmodel(),Long.valueOf(quantity),productmemo,ShiroUtils.getSysUser().getUserName(),new Date()))
//                        storeIoSonList=storeIo.getStoreIoSonList();
//                        storeIoSonList.add(new StoreIoSon(1L,storeIo.getTaskcode(),product.getProductcode(),product.getProductname(),product.getProductmodel(),(long) Double.parseDouble(quantity),memo,ShiroUtils.getSysUser().getUserName(),new Date()));
//                        storeIo.setStoreIoSonList(storeIoSonList);
//                    }
//                }
//            }else{
//                //即导入第一行
//
//                StoreIo storeIo=new StoreIo(taskType,ToolsUtils.getOrdersId("SIT"),taskname,productdetail,maker,warehouse.getWhcode(), (long) Double.parseDouble(quantity),ShiroUtils.getSysUser().getUserName(),new Date());
//                storeIoSonList.add(new StoreIoSon(1L,storeIo.getTaskcode(),product.getProductcode(),product.getProductname(),product.getProductmodel(),(long) Double.parseDouble(quantity),memo,ShiroUtils.getSysUser().getUserName(),new Date()));
//                storeIo.setStoreIoSonList(storeIoSonList);
//                System.out.println(storeIo);
//                System.out.println(storeIoSonList);
//                storeIoList.add(storeIo);
//            }
//
//        }
//
//        //初始化任务子表集合
//        List<StoreIoSon> storeIoSonList=new ArrayList<>();
//        for (StoreIo storeIo:storeIoList){
//            for (StoreIoSon storeIoSon:storeIo.getStoreIoSonList()){
//                storeIoSonList.add(storeIoSon);
//                //保存堆垛机、RGV数据
//                Stacker stacker=new Stacker(storeIoSon.getRowid(),storeIo.getTasktype(),storeIo.getTaskcode(),"0",storeIo.getCreateuser(),new Date());
//                Rgv rgv=new Rgv(storeIoSon.getRowid(),storeIo.getTasktype(),storeIo.getTaskcode(),"0",storeIo.getCreateuser(),new Date());
//                //拆分入库子表，生成任务数据堆垛机、RGV，根据数量生成相对应的条数
//                for (int i=0;i<storeIoSon.getQuantity();i++){
//                    stackerList.add(stacker);
//                    rgvList.add(rgv);
//                }
//
//            }
//        }
//        //新增入库主表
//        if (storeIoList.size()>0) {
//            storeIoMapper.insertStoreIoList(storeIoList);
//        }
//        //批量入库子表
//        if (storeIoSonList.size()>0) {
//            storeIoSonMapper.insertStoreIoSonList(storeIoSonList);
//        }
//        //新增堆垛机、RGV任务表
//        if (stackerList.size()>0) {
//            stackerMapper.insertStackerList(stackerList);
//        }
//        if (rgvList.size()>0) {
//            rgvMapper.insertRgvList(rgvList);
//        }
//
//        return AjaxResult.success();
//    }


//    @Override
//    @Transactional(rollbackFor = Exception.class)
//    public AjaxResult importInExcel(MultipartFile file, String taskType) throws IOException {
//        int rowcount=0;
//
//        //初始化入库表、入库子表、任务表
//        List<StoreIo> storeIoList=new ArrayList<>();
//
//        List<Stacker> stackerList=new ArrayList<>();
//        List<Rgv> rgvList=new ArrayList<>();
//
//        String filePath=file.getOriginalFilename();//导入的文件名
//
//        //工作簿
//        Sheet sheet=null;
//
//        //2003版本
//        if(filePath.endsWith(".xls")){
//            POIFSFileSystem fileSystem=new POIFSFileSystem(file.getInputStream());
//            HSSFWorkbook hssfWorkbook=new HSSFWorkbook(fileSystem);
//            sheet=hssfWorkbook.getSheetAt(0);
//        }
//
//        if (filePath.endsWith(".xlsx")){
//            XSSFWorkbook xssfWorkbook=new XSSFWorkbook(file.getInputStream());
//            sheet=xssfWorkbook.getSheetAt(0);
//        }
//
//        if (sheet==null){
//            AjaxResult.error("表格内容为空");
//        }
//
//        rowcount=sheet.getPhysicalNumberOfRows();//获取行数
//
//        if (rowcount<=1){
//            return AjaxResult.error("表格内容为空！");
//        }
//
////        String[] fields={"任务名称","任务描述","供应商信息","制单人","生产订单编号","采购订单编号","仓库名称","任务备注","产品编号+产品名称+产品型号+产品批次","数量","产品备注","摆放层"};
////        String[] fields={"任务名称","任务描述","制单人","仓库名称","任务备注","产品编号+产品名称+产品型号+产品批次","数量","产品备注"};
//        String[] fields={"任务名称","任务描述","制单人","仓库名称","任务备注","产品型号","产品批次","数量","产品备注"};
//        Row titleRow=sheet.getRow(0);
//        for (int i=0;i<fields.length;i++){
//            if (!fields[i].equals(StringUtils.getString(titleRow.getCell(i)).trim())){
//                return AjaxResult.error("模板内容与导入模板不同，请重新生成模板填充内容！");
//            }
//        }
//        //转换产品类型
//        List<SysDictData> base_product_model = dictService.getType("base_product_model");
//
//        for(int rowindex=1;rowindex<rowcount;rowindex++){
//            Row row=sheet.getRow(rowindex);
//            if (row==null){
//                break;
//            }
//            String taskname= StringUtils.getString(row.getCell(0)).trim();//任务名称
//            String productdetail= StringUtils.getString(row.getCell(1)).trim();//任务描述
////            String veninfo= StringUtils.getString(row.getCell(2)).trim();//供应商信息
//            String maker= StringUtils.getString(row.getCell(2)).trim();//制单人
////            String mpocode= StringUtils.getString(row.getCell(4)).trim();//生产订单编号
////            if(StringUtils.isNotEmpty(mpocode)) {
////                if (row.getCell(4).getCellType() == Cell.CELL_TYPE_NUMERIC) {
////                    DecimalFormat df = new DecimalFormat("#");// 转换成整型
////                    mpocode = df.format(row.getCell(4).getNumericCellValue());
////                } else {
////                    mpocode = StringUtils.getString(row.getCell(4)).trim();
////                }
////            }
////            String ordercode= StringUtils.getString(row.getCell(5)).trim();//采购订单编号
////            if(StringUtils.isNotEmpty(ordercode)) {
////                if (row.getCell(5).getCellType() == Cell.CELL_TYPE_NUMERIC) {
////                    DecimalFormat df = new DecimalFormat("#");// 转换成整型
////                    ordercode = df.format(row.getCell(5).getNumericCellValue());
////                } else {
////                    ordercode = StringUtils.getString(row.getCell(5)).trim();
////                }
////            }
//            String[] Whcode= StringUtils.getString(row.getCell(3)).trim().split("\\+");//仓库编码
//            String remark= StringUtils.getString(row.getCell(4)).trim();//备注
////            String[] productinfo= StringUtils.getString(row.getCell(5)).trim().split("\\+");//产品信息
//            String productcode= StringUtils.getString(row.getCell(5)).trim();//产品型号
//            String productbatch= StringUtils.getString(row.getCell(6)).trim();//产品批次
//            if(StringUtils.isNotEmpty(productbatch)) {
//                if (row.getCell(6).getCellType() == Cell.CELL_TYPE_NUMERIC) {
//                    DecimalFormat df = new DecimalFormat("#");// 转换成整型
//                    productbatch = df.format(row.getCell(6).getNumericCellValue());
//                } else {
//                    productbatch = StringUtils.getString(row.getCell(6)).trim();
//                }
//            }
//            String quantity= StringUtils.getString(row.getCell(7)).trim();//产品数量
//            if(StringUtils.isNotEmpty(quantity)) {
//                if (row.getCell(7).getCellType() == Cell.CELL_TYPE_NUMERIC) {
//                    DecimalFormat df = new DecimalFormat("#");// 转换成整型
//                    quantity = df.format(row.getCell(7).getNumericCellValue());
//                } else {
//                    quantity = StringUtils.getString(row.getCell(7)).trim();
//                }
//            }
//            String productmemo= StringUtils.getString(row.getCell(8)).trim();//产品备注
////            String layer= StringUtils.getString(row.getCell(11)).trim();//摆放层
////            String outtype= StringUtils.getString(row.getCell(12)).trim();//出库方式
////            String outrule= StringUtils.getString(row.getCell(13)).trim();//出库规则
////            String customername= StringUtils.getString(row.getCell(14)).trim();//客户名称
////            String customercode= StringUtils.getString(row.getCell(15)).trim();//客户编码
////            for (SysDictData model : base_product_model){
////                if (productinfo[2].equals(model.getDictLabel())){
////                    productinfo[2] = model.getDictValue();
////                    break;
////                }
////            }
//
//            //任务名称
//            if (StringUtils.isEmpty(taskname)){
//                return AjaxResult.error("第"+(rowindex+1)+"行，【任务名称】不能为空!");
//            }
//            //供应商信息
////            if (StringUtils.isEmpty(veninfo)){
////                return AjaxResult.error("第"+(rowindex+1)+"行，【供应商信息】不能为空!");
////            }
//            //制单人
//            if (StringUtils.isEmpty(maker)){
//                return AjaxResult.error("第"+(rowindex+1)+"行，【制单人】不能为空!");
//            }
//            //生产订单编号
////            if (StringUtils.isEmpty(mpocode)){
////                return AjaxResult.error("第"+(rowindex+1)+"行，【生产订单编号】不能为空!");
////            }
//            //采购订单编号
////            if (StringUtils.isEmpty(ordercode)){
////                return AjaxResult.error("第"+(rowindex+1)+"行，【采购订单编号】不能为空!");
////            }
//            //产品数量
//            if (StringUtils.isEmpty(quantity)){
//                return AjaxResult.error("第"+(rowindex+1)+"行，【产品数量】不能为空!");
//            }
//
//            if (!StringUtils.isNumber(quantity)){
//                return AjaxResult.error("第"+(rowindex+1)+"行，【产品数量】格式不正确!");
//            }
//
//            if ((long) Double.parseDouble(quantity)<=0){
//                return AjaxResult.error("第"+(rowindex+1)+"行，【产品数量】不能为0！");
//            }
//
//            //校验仓库和产品是否正确
//            if (Whcode.length!=2){
//                return AjaxResult.error("第"+(rowindex+1)+"行，【仓库名称】格式错误!");
//            }
////            if (productinfo.length!=4){
////                return AjaxResult.error("第"+(rowindex+1)+"行，【产品编号+产品名称+产品型号+产品批次】格式错误!");
////            }
//
//            Warehouse warehouse=warehouseMapper.selectWarehouseById(Whcode[0]);
//            if (warehouse==null){
//                return AjaxResult.error("第"+(rowindex+1)+"行，【仓库名称】错误，请检查或重新生成模板!");
//            }
////            Product product=productMapper.selectProductByInfo(new Product(productinfo[0],productinfo[1],productinfo[3],productinfo[2]));
//            Product product=productMapper.selectProductByProId(productcode);
//            if (product==null){
//                return AjaxResult.error("第"+(rowindex+1)+"行，【产品编号+产品名称+产品型号+产品批次】错误，请检查或重新生成模板!");
//            }
////            if (StringUtils.isEmpty(layer)){
////                return AjaxResult.error("第"+(rowindex+1)+"行，【摆放层】不能为空!");
////            }
////            if (StringUtils.isEmpty(outtype)){
////                return AjaxResult.error("第"+(rowindex+1)+"行，【出库方式】不能为空!");
////            }
////            if (StringUtils.isEmpty(outrule)){
////                return AjaxResult.error("第"+(rowindex+1)+"行，【出库规则】不能为空!");
////            }
////            if (StringUtils.isEmpty(customername)){
////                return AjaxResult.error("第"+(rowindex+1)+"行，【出库方式】不能为空!");
////            }
////            if (StringUtils.isEmpty(customercode)){
////                return AjaxResult.error("第"+(rowindex+1)+"行，【出库规则】不能为空!");
////            }
//            //初始化任务子表集合
//            List<StoreIoSon> storeIoSonList=new ArrayList<>();
////            if (storeIoList.size()>0){
////                StoreIo storeIo=null;
////                //循环校验是否存在任务单
////                for (StoreIo info:storeIoList){
////                    if (info.getTaskname().equals(taskname) && info.getMaker().equals(maker) && info.getWhcode().equals(Whcode[0])){
////                        storeIo=info;
////                        break;
////                    }
////                }
////                //如果任务单为空，表示不存在
////                if (storeIo==null){
//////                    storeIo=new StoreIo(taskType, ToolsUtils.getOrdersId("SIT"),taskname,productdetail,veninfo,maker,mpocode,ordercode,warehouse.getWhcode(),(long) Double.parseDouble(quantity),remark, ShiroUtils.getSysUser().getUserName(),new Date(),(long) Double.parseDouble(layer),(long) Double.parseDouble(outtype),(long) Double.parseDouble(outrule),customername,customercode);
////                    storeIo=new StoreIo(taskType,ToolsUtils.getOrdersId("SIT"),taskname,productdetail,maker,warehouse.getWhcode(), (long) Double.parseDouble(quantity),remark,ShiroUtils.getSysUser().getUserName(),new Date());
////                    storeIoSonList.add(new StoreIoSon(1L,storeIo.getTaskcode(),product.getProductcode(),product.getProductname(),product.getProductmodel(),(long) Double.parseDouble(quantity),productmemo,ShiroUtils.getSysUser().getUserName(),new Date(),productbatch));
////                    storeIo.setStoreIoSonList(storeIoSonList);
////                    storeIoList.add(storeIo);
////                }else{
////                    boolean isPresence=false;
////                    //任务单已存在，循环校验任务单是否存在该产品，
////                    for (StoreIoSon storeIoSon:storeIo.getStoreIoSonList()){
////                        //存在即将任务总数量、产品数量增加
////                        if (storeIoSon.getProductcode().equals(product.getProductcode())){
////                            storeIo.setQuantity(storeIo.getQuantity()+(long) Double.parseDouble(quantity));
////                            storeIoSon.setQuantity(storeIoSon.getQuantity()+(long) Double.parseDouble(quantity));
////                            isPresence=true;
////                            break;
////                        }
////                    }
////                    //isPresence为false时，表示产品不存在，增加任务单产品总数量，新增任务子表
////                    if (!isPresence){
////                        storeIo.setQuantity(storeIo.getQuantity()+(long) Double.parseDouble(quantity));
//////                        storeIo.getStoreIoSonList().add(new StoreIoSonEx(storeIo.getStoreIoSonList().size()+1L,storeIo.getTaskcode(),product.getProductcode(),product.getProductname(),product.getProductmodel(),Long.valueOf(quantity),productmemo,ShiroUtils.getSysUser().getUserName(),new Date()))
////                        storeIoSonList=storeIo.getStoreIoSonList();
////                        storeIoSonList.add(new StoreIoSon(storeIo.getStoreIoSonList().size()+1L,storeIo.getTaskcode(),product.getProductcode(),product.getProductname(),product.getProductmodel(),(long) Double.parseDouble(quantity),productmemo,ShiroUtils.getSysUser().getUserName(),new Date(),productbatch));
////                        storeIo.setStoreIoSonList(storeIoSonList);
////                    }
////                }
////            }else{
////                //即导入第一行
////
////                StoreIo storeIo=new StoreIo(taskType,ToolsUtils.getOrdersId("SIT"),taskname,productdetail,maker,warehouse.getWhcode(), (long) Double.parseDouble(quantity),remark,ShiroUtils.getSysUser().getUserName(),new Date());
////                storeIoSonList.add(new StoreIoSon(1L,storeIo.getTaskcode(),product.getProductcode(),product.getProductname(),product.getProductmodel(),(long) Double.parseDouble(quantity),productmemo,ShiroUtils.getSysUser().getUserName(),new Date(),productbatch));
////                storeIo.setStoreIoSonList(storeIoSonList);
////                storeIoList.add(storeIo);
////            }
//            StoreIo storeIo=new StoreIo(taskType,ToolsUtils.getOrdersId("SIT"),taskname,productdetail,maker,warehouse.getWhcode(), (long) Double.parseDouble(quantity),remark,ShiroUtils.getSysUser().getUserName(),new Date());
//            storeIoSonList.add(new StoreIoSon(1L,storeIo.getTaskcode(),product.getProductcode(),product.getProductname(),product.getProductmodel(),(long) Double.parseDouble(quantity),productmemo,ShiroUtils.getSysUser().getUserName(),new Date(),productbatch));
//            storeIo.setStoreIoSonList(storeIoSonList);
//            storeIoList.add(storeIo);
//
//        }
//
//        //初始化任务子表集合
//        List<StoreIoSon> storeIoSonList=new ArrayList<>();
//        for (StoreIo storeIo:storeIoList){
//            for (StoreIoSon storeIoSon:storeIo.getStoreIoSonList()){
//                storeIoSonList.add(storeIoSon);
//                //保存堆垛机、RGV数据
//                Stacker stacker=new Stacker(storeIoSon.getRowid(),storeIo.getTasktype(),storeIo.getTaskcode(),"0",storeIo.getCreateuser(),new Date());
//                Rgv rgv=new Rgv(storeIoSon.getRowid(),storeIo.getTasktype(),storeIo.getTaskcode(),"0",storeIo.getCreateuser(),new Date());
//                //拆分入库子表，生成任务数据堆垛机、RGV，根据数量生成相对应的条数
//                for (int i=0;i<storeIoSon.getQuantity();i++){
//                    stackerList.add(stacker);
//                    rgvList.add(rgv);
//                }
//
//            }
//        }
//        //新增入库主表
//        if (storeIoList.size()>0) {
//            storeIoMapper.insertStoreIoList(storeIoList);
//        }
//        //批量入库子表
//        if (storeIoSonList.size()>0) {
//            storeIoSonMapper.insertStoreIoSonList(storeIoSonList);
//        }
//        //新增堆垛机、RGV任务表
//        if (stackerList.size()>0) {
//            stackerMapper.insertStackerList(stackerList);
//        }
//        if (rgvList.size()>0) {
//            rgvMapper.insertRgvList(rgvList);
//        }
//
//        return AjaxResult.success();
//    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public AjaxResult importInExcel(MultipartFile file, String taskType) throws IOException {
        int rowcount=0;

        //初始化入库表、入库子表、任务表
        List<StoreIo> storeIoList=new ArrayList<>();

        List<Stacker> stackerList=new ArrayList<>();
        List<Rgv> rgvList=new ArrayList<>();

        String filePath=file.getOriginalFilename();//导入的文件名

        //工作簿
        Sheet sheet=null;

        //2003版本
        if(filePath.endsWith(".xls")){
            POIFSFileSystem fileSystem=new POIFSFileSystem(file.getInputStream());
            HSSFWorkbook hssfWorkbook=new HSSFWorkbook(fileSystem);
            sheet=hssfWorkbook.getSheetAt(0);
        }

        if (filePath.endsWith(".xlsx")){
            XSSFWorkbook xssfWorkbook=new XSSFWorkbook(file.getInputStream());
            sheet=xssfWorkbook.getSheetAt(0);
        }

        if (sheet==null){
            AjaxResult.error("表格内容为空");
        }

        rowcount=sheet.getPhysicalNumberOfRows();//获取行数

        if (rowcount<=1){
            return AjaxResult.error("表格内容为空！");
        }

//        String[] fields={"任务名称","任务描述","供应商信息","制单人","生产订单编号","采购订单编号","仓库名称","任务备注","产品编号+产品名称+产品型号+产品批次","数量","产品备注","摆放层"};
//        String[] fields={"任务名称","任务描述","制单人","仓库名称","任务备注","产品编号+产品名称+产品型号+产品批次","数量","产品备注"};
//        String[] fields={"任务名称","任务描述","制单人","仓库名称","任务备注","产品型号","产品批次","数量","产品备注"};
        String[] fields={"任务号","任务名称","任务描述","制单人","仓库编码","仓库名称","入库方式","总数量","任务状态","备注","创建人","创建时间","产品编码","产品名称","产品批次","数量","任务状态","产品备注"};
        Row titleRow=sheet.getRow(0);
        for (int i=0;i<fields.length;i++){
            if (!fields[i].equals(StringUtils.getString(titleRow.getCell(i)).trim())){
                return AjaxResult.error("模板内容与导入模板不同，请重新生成模板填充内容！");
            }
        }
        //转换产品类型
        List<SysDictData> base_product_model = dictService.getType("base_product_model");

        for(int rowindex=1;rowindex<rowcount;rowindex++){
            Row row=sheet.getRow(rowindex);
            if (row==null){
                break;
            }
            String taskNo= StringUtils.getString(row.getCell(0)).trim();//任务号
            String taskname= StringUtils.getString(row.getCell(1)).trim();//任务名称
            String productdetail= StringUtils.getString(row.getCell(2)).trim();//任务描述
//            String veninfo= StringUtils.getString(row.getCell(2)).trim();//供应商信息
            String maker= StringUtils.getString(row.getCell(3)).trim();//制单人
            if(StringUtils.isNotEmpty(maker)) {
                if (row.getCell(3).getCellType() == Cell.CELL_TYPE_NUMERIC) {
                    DecimalFormat df = new DecimalFormat("#");// 转换成整型
                    maker = df.format(row.getCell(3).getNumericCellValue());
                } else {
                    maker = StringUtils.getString(row.getCell(3)).trim();
                }
            }
//            String ordercode= StringUtils.getString(row.getCell(5)).trim();//采购订单编号
//            if(StringUtils.isNotEmpty(ordercode)) {
//                if (row.getCell(5).getCellType() == Cell.CELL_TYPE_NUMERIC) {
//                    DecimalFormat df = new DecimalFormat("#");// 转换成整型
//                    ordercode = df.format(row.getCell(5).getNumericCellValue());
//                } else {
//                    ordercode = StringUtils.getString(row.getCell(5)).trim();
//                }
//            }
            String Whcode= StringUtils.getString(row.getCell(4)).trim();//仓库编码
            String intype= StringUtils.getString(row.getCell(6)).trim();//入库方式
            if(StringUtils.isNotEmpty(intype)) {
                if (row.getCell(6).getCellType() == Cell.CELL_TYPE_NUMERIC) {
                    DecimalFormat df = new DecimalFormat("#");// 转换成整型
                    intype = df.format(row.getCell(6).getNumericCellValue());
                } else {
                    intype = StringUtils.getString(row.getCell(6)).trim();
                }
            }
            String remark= StringUtils.getString(row.getCell(9)).trim();//备注
//            String[] productinfo= StringUtils.getString(row.getCell(5)).trim().split("\\+");//产品信息
            String productcode= StringUtils.getString(row.getCell(12)).trim();//产品号
            String productbatch= StringUtils.getString(row.getCell(14)).trim();//产品批次
            if(StringUtils.isNotEmpty(productbatch)) {
                if (row.getCell(14).getCellType() == Cell.CELL_TYPE_NUMERIC) {
                    DecimalFormat df = new DecimalFormat("#");// 转换成整型
                    productbatch = df.format(row.getCell(14).getNumericCellValue());
                } else {
                    productbatch = StringUtils.getString(row.getCell(14)).trim();
                }
            }
            String quantity= StringUtils.getString(row.getCell(15)).trim();//产品数量
            if(StringUtils.isNotEmpty(quantity)) {
                if (row.getCell(15).getCellType() == Cell.CELL_TYPE_NUMERIC) {
                    DecimalFormat df = new DecimalFormat("#");// 转换成整型
                    quantity = df.format(row.getCell(15).getNumericCellValue());
                } else {
                    quantity = StringUtils.getString(row.getCell(15)).trim();
                }
            }
            String productmemo= StringUtils.getString(row.getCell(17)).trim();//产品备注
//            String layer= StringUtils.getString(row.getCell(11)).trim();//摆放层
//            String outtype= StringUtils.getString(row.getCell(12)).trim();//出库方式
//            String outrule= StringUtils.getString(row.getCell(13)).trim();//出库规则
//            String customername= StringUtils.getString(row.getCell(14)).trim();//客户名称
//            String customercode= StringUtils.getString(row.getCell(15)).trim();//客户编码
//            for (SysDictData model : base_product_model){
//                if (productinfo[2].equals(model.getDictLabel())){
//                    productinfo[2] = model.getDictValue();
//                    break;
//                }
//            }

            //任务名称
            if (StringUtils.isEmpty(taskname)){
                return AjaxResult.error("第"+(rowindex+1)+"行，【任务名称】不能为空!");
            }
            //供应商信息
//            if (StringUtils.isEmpty(veninfo)){
//                return AjaxResult.error("第"+(rowindex+1)+"行，【供应商信息】不能为空!");
//            }
            //制单人
            if (StringUtils.isEmpty(maker)){
                return AjaxResult.error("第"+(rowindex+1)+"行，【制单人】不能为空!");
            }
            //生产订单编号
//            if (StringUtils.isEmpty(mpocode)){
//                return AjaxResult.error("第"+(rowindex+1)+"行，【生产订单编号】不能为空!");
//            }
            //采购订单编号
//            if (StringUtils.isEmpty(ordercode)){
//                return AjaxResult.error("第"+(rowindex+1)+"行，【采购订单编号】不能为空!");
//            }
            //产品数量
            if (StringUtils.isEmpty(quantity)){
                return AjaxResult.error("第"+(rowindex+1)+"行，【产品数量】不能为空!");
            }

            //入库方式
            if (StringUtils.isEmpty(intype)){
                return AjaxResult.error("第"+(rowindex+1)+"行，【入库方式】不能为空!");
            }

            if (!StringUtils.isNumber(quantity)){
                return AjaxResult.error("第"+(rowindex+1)+"行，【产品数量】格式不正确!");
            }

            if ((long) Double.parseDouble(quantity)<=0){
                return AjaxResult.error("第"+(rowindex+1)+"行，【产品数量】不能为0！");
            }

            //校验仓库和产品是否正确
//            if (Whcode.length!=2){
//                return AjaxResult.error("第"+(rowindex+1)+"行，【仓库名称】格式错误!");
//            }
//            if (productinfo.length!=4){
//                return AjaxResult.error("第"+(rowindex+1)+"行，【产品编号+产品名称+产品型号+产品批次】格式错误!");
//            }

            Warehouse warehouse=warehouseMapper.selectWarehouseById(Whcode);
            if (warehouse==null){
                return AjaxResult.error("第"+(rowindex+1)+"行，【仓库名称】错误，请检查或重新生成模板!");
            }
//            Product product=productMapper.selectProductByInfo(new Product(productinfo[0],productinfo[1],productinfo[3],productinfo[2]));
            Product product=productMapper.selectProductByProId(productcode);
            if (product==null){
                return AjaxResult.error("第"+(rowindex+1)+"行，【产品编号+产品名称+产品型号+产品批次】错误，请检查或重新生成模板!");
            }
            //初始化任务子表集合
            List<StoreIoSon> storeIoSonList=new ArrayList<>();
//            if (storeIoList.size()>0){
//                StoreIo storeIo=null;
//                //循环校验是否存在任务单
//                for (StoreIo info:storeIoList){
//                    if (info.getTaskname().equals(taskname) && info.getMaker().equals(maker) && info.getWhcode().equals(Whcode[0])){
//                        storeIo=info;
//                        break;
//                    }
//                }
//                //如果任务单为空，表示不存在
//                if (storeIo==null){
////                    storeIo=new StoreIo(taskType, ToolsUtils.getOrdersId("SIT"),taskname,productdetail,veninfo,maker,mpocode,ordercode,warehouse.getWhcode(),(long) Double.parseDouble(quantity),remark, ShiroUtils.getSysUser().getUserName(),new Date(),(long) Double.parseDouble(layer),(long) Double.parseDouble(outtype),(long) Double.parseDouble(outrule),customername,customercode);
//                    storeIo=new StoreIo(taskType,ToolsUtils.getOrdersId("SIT"),taskname,productdetail,maker,warehouse.getWhcode(), (long) Double.parseDouble(quantity),remark,ShiroUtils.getSysUser().getUserName(),new Date());
//                    storeIoSonList.add(new StoreIoSon(1L,storeIo.getTaskcode(),product.getProductcode(),product.getProductname(),product.getProductmodel(),(long) Double.parseDouble(quantity),productmemo,ShiroUtils.getSysUser().getUserName(),new Date(),productbatch));
//                    storeIo.setStoreIoSonList(storeIoSonList);
//                    storeIoList.add(storeIo);
//                }else{
//                    boolean isPresence=false;
//                    //任务单已存在，循环校验任务单是否存在该产品，
//                    for (StoreIoSon storeIoSon:storeIo.getStoreIoSonList()){
//                        //存在即将任务总数量、产品数量增加
//                        if (storeIoSon.getProductcode().equals(product.getProductcode())){
//                            storeIo.setQuantity(storeIo.getQuantity()+(long) Double.parseDouble(quantity));
//                            storeIoSon.setQuantity(storeIoSon.getQuantity()+(long) Double.parseDouble(quantity));
//                            isPresence=true;
//                            break;
//                        }
//                    }
//                    //isPresence为false时，表示产品不存在，增加任务单产品总数量，新增任务子表
//                    if (!isPresence){
//                        storeIo.setQuantity(storeIo.getQuantity()+(long) Double.parseDouble(quantity));
////                        storeIo.getStoreIoSonList().add(new StoreIoSonEx(storeIo.getStoreIoSonList().size()+1L,storeIo.getTaskcode(),product.getProductcode(),product.getProductname(),product.getProductmodel(),Long.valueOf(quantity),productmemo,ShiroUtils.getSysUser().getUserName(),new Date()))
//                        storeIoSonList=storeIo.getStoreIoSonList();
//                        storeIoSonList.add(new StoreIoSon(storeIo.getStoreIoSonList().size()+1L,storeIo.getTaskcode(),product.getProductcode(),product.getProductname(),product.getProductmodel(),(long) Double.parseDouble(quantity),productmemo,ShiroUtils.getSysUser().getUserName(),new Date(),productbatch));
//                        storeIo.setStoreIoSonList(storeIoSonList);
//                    }
//                }
//            }else{
//                //即导入第一行
//
//                StoreIo storeIo=new StoreIo(taskType,ToolsUtils.getOrdersId("SIT"),taskname,productdetail,maker,warehouse.getWhcode(), (long) Double.parseDouble(quantity),remark,ShiroUtils.getSysUser().getUserName(),new Date());
//                storeIoSonList.add(new StoreIoSon(1L,storeIo.getTaskcode(),product.getProductcode(),product.getProductname(),product.getProductmodel(),(long) Double.parseDouble(quantity),productmemo,ShiroUtils.getSysUser().getUserName(),new Date(),productbatch));
//                storeIo.setStoreIoSonList(storeIoSonList);
//                storeIoList.add(storeIo);
//            }
            StoreIo storeIo=new StoreIo(taskType,ToolsUtils.getOrdersId("SIT"),taskname,productdetail,maker,warehouse.getWhcode(), (long) Double.parseDouble(quantity),remark,ShiroUtils.getSysUser().getUserName(),new Date(),intype);
            storeIoSonList.add(new StoreIoSon(1L,storeIo.getTaskcode(),product.getProductcode(),product.getProductname(),product.getProductmodel(),(long) Double.parseDouble(quantity),productmemo,ShiroUtils.getSysUser().getUserName(),new Date(),productbatch));
            storeIo.setStoreIoSonList(storeIoSonList);
            storeIoList.add(storeIo);

        }

        //初始化任务子表集合
        List<StoreIoSon> storeIoSonList=new ArrayList<>();
        for (StoreIo storeIo:storeIoList){
            for (StoreIoSon storeIoSon:storeIo.getStoreIoSonList()){
                storeIoSonList.add(storeIoSon);
                //保存堆垛机、RGV数据
                Stacker stacker=new Stacker(storeIoSon.getRowid(),storeIo.getTasktype(),storeIo.getTaskcode(),"0",storeIo.getCreateuser(),new Date());
                Rgv rgv=new Rgv(storeIoSon.getRowid(),storeIo.getTasktype(),storeIo.getTaskcode(),"0",storeIo.getCreateuser(),new Date());
                //拆分入库子表，生成任务数据堆垛机、RGV，根据数量生成相对应的条数
                for (int i=0;i<storeIoSon.getQuantity();i++){
                    stackerList.add(stacker);
                    rgvList.add(rgv);
                }

            }
        }
        //新增入库主表
        if (storeIoList.size()>0) {
            storeIoMapper.insertStoreIoList(storeIoList);
        }
        //批量入库子表
        if (storeIoSonList.size()>0) {
            storeIoSonMapper.insertStoreIoSonList(storeIoSonList);
        }
        //新增堆垛机、RGV任务表
        if (stackerList.size()>0) {
            stackerMapper.insertStackerList(stackerList);
        }
        if (rgvList.size()>0) {
            rgvMapper.insertRgvList(rgvList);
        }

        return AjaxResult.success();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public AjaxResult importOutExcel(MultipartFile file, String taskType) throws IOException {
        int rowcount=0;

        //初始化入库表、入库子表、任务表
        List<StoreIo> storeIoList=new ArrayList<>();

        List<Stacker> stackerList=new ArrayList<>();
        List<Rgv> rgvList=new ArrayList<>();

        String filePath=file.getOriginalFilename();//导入的文件名

        //工作簿
        Sheet sheet=null;

        //2003版本
        if(filePath.endsWith(".xls")){
            POIFSFileSystem fileSystem=new POIFSFileSystem(file.getInputStream());
            HSSFWorkbook hssfWorkbook=new HSSFWorkbook(fileSystem);
            sheet=hssfWorkbook.getSheetAt(0);
        }

        if (filePath.endsWith(".xlsx")){
            XSSFWorkbook xssfWorkbook=new XSSFWorkbook(file.getInputStream());
            sheet=xssfWorkbook.getSheetAt(0);
        }

        if (sheet==null){
            AjaxResult.error("表格内容为空");
        }

        rowcount=sheet.getPhysicalNumberOfRows();//获取行数
        System.out.println(rowcount);
        if (rowcount<=1){
            return AjaxResult.error("表格内容为空！");
        }

//        String[] fields={"仓库名称","任务名称","任务描述","制单人","任务备注","产品编号+产品名称+产品型号+产品批次","数量","产品备注","客户名称","客户编码","出库方式","出库规则"};
//        String[] fields={"仓库名称","任务名称","任务描述","制单人","任务备注","产品型号","产品批次","数量","产品备注","客户名称","客户编码"};
        String[] fields={"任务号","任务名称","任务描述","制单人","仓库编码","仓库名称","总数量","任务状态","备注","创建人","创建时间","产品编码","产品名称","产品批次","数量","任务状态","产品备注"};
        Row titleRow=sheet.getRow(0);
        for (int i=0;i<fields.length;i++){
            if (!fields[i].equals(StringUtils.getString(titleRow.getCell(i)).trim())){
                return AjaxResult.error("模板内容与导入模板不同，请重新生成模板填充内容！");
            }
        }

        //转换产品类型
        List<SysDictData> base_product_model = dictService.getType("base_product_model");
        //转换出库方式
//        List<SysDictData> base_warehouseout_rules = dictService.getType("base_warehouseout_rules");
        //转换出库规则
//        List<SysDictData> base_warehouse_rules = dictService.getType("base_warehouse_rules");

        for(int rowindex=1;rowindex<rowcount;rowindex++){
            Row row=sheet.getRow(rowindex);
            if (row==null){
                break;
            }
            String Whcode= StringUtils.getString(row.getCell(4)).trim();//仓库编码
            String taskname= StringUtils.getString(row.getCell(1)).trim();//任务名称
            String productdetail= StringUtils.getString(row.getCell(2)).trim();//任务描述
            String maker= StringUtils.getString(row.getCell(3)).trim();//制单人
//            String[] productinfo= StringUtils.getString(row.getCell(5)).trim().split("\\+");//产品编号
            String memo= StringUtils.getString(row.getCell(8)).trim();//任务备注
            String productcode= StringUtils.getString(row.getCell(11)).trim();//产品编号
            String productbatch= StringUtils.getString(row.getCell(13)).trim();//批次
            if(StringUtils.isNotEmpty(productbatch)) {
                if (row.getCell(13).getCellType() == Cell.CELL_TYPE_NUMERIC) {
                    DecimalFormat df = new DecimalFormat("#");// 转换成整型
                    productbatch = df.format(row.getCell(13).getNumericCellValue());
                } else {
                    productbatch = StringUtils.getString(row.getCell(13)).trim();
                }
            }
            String quantity= StringUtils.getString(row.getCell(14)).trim();//产品数量
            if(StringUtils.isNotEmpty(quantity)) {
                if (row.getCell(14).getCellType() == Cell.CELL_TYPE_NUMERIC) {
                    DecimalFormat df = new DecimalFormat("#");// 转换成整型
                    quantity = df.format(row.getCell(14).getNumericCellValue());
                } else {
                    quantity = StringUtils.getString(row.getCell(14)).trim();
                }
            }
            String productmemo= StringUtils.getString(row.getCell(16)).trim();//产品备注
            String customername= "";//客户名称
            String customercode= "";//客户编码
//            String layer= StringUtils.getString(row.getCell(11)).trim();//摆放层
//            String outtype= StringUtils.getString(row.getCell(10)).trim();//出库方式
//            String outrule= StringUtils.getString(row.getCell(11)).trim();//出库规则

//            if (Whcode.length!=2 && taskname=="" && productdetail=="" && veninfo=="" && maker=="" && productinfo.length!=4 && mpocode=="" && ordercode=="" && remark==""&& layer==""&& outrule==""&& outtype==""&& customername==""&& customercode==""){
//                if (rowindex==1){
//                    return AjaxResult.error("表格内容为空！");
//                }else{
//                    break;
//                }
//            }
//            for (SysDictData model : base_product_model){
//                if (productinfo[2].equals(model.getDictLabel())){
//                    productinfo[2] = model.getDictValue();
//                    break;
//                }
//            }
//            for (SysDictData model : base_warehouseout_rules){
//                if (outtype.equals(model.getDictLabel())){
//                    outtype = model.getDictValue();
//                    break;
//                }
//            }
//            for (SysDictData model : base_warehouse_rules){
//                if (outrule.equals(model.getDictLabel())){
//                    outrule = model.getDictValue();
//                    break;
//                }
//            }

            //任务名称
//            if (StringUtils.isEmpty(taskname)){
//                return AjaxResult.error("第"+(rowindex+1)+"行，【任务名称】不能为空!");
//            }
            //供应商信息
//            if (StringUtils.isEmpty(veninfo)){
//                return AjaxResult.error("第"+(rowindex+1)+"行，【供应商信息】不能为空!");
//            }
            //制单人
//            if (StringUtils.isEmpty(maker)){
//                return AjaxResult.error("第"+(rowindex+1)+"行，【制单人】不能为空!");
//            }
            //产品数量
            if (StringUtils.isEmpty(quantity)){
                return AjaxResult.error("第"+(rowindex+1)+"行，【产品数量】不能为空!");
            }

            if (!StringUtils.isNumber(quantity)){
                return AjaxResult.error("第"+(rowindex+1)+"行，【产品数量】格式不正确!");
            }

            if ((long) Double.parseDouble(quantity)<=0){
                return AjaxResult.error("第"+(rowindex+1)+"行，【产品数量】不能为0！");
            }

            //校验仓库和产品是否正确
//            if (Whcode.length!=2){
//                return AjaxResult.error("第"+(rowindex+1)+"行，【仓库名称】格式错误!");
//            }
//            if (productinfo.length!=4){
//                return AjaxResult.error("第"+(rowindex+1)+"行，【产品编号+产品名称+产品型号+产品批次】格式错误!");
//            }

            Warehouse warehouse=warehouseMapper.selectWarehouseById(Whcode);
            if (warehouse==null){
                return AjaxResult.error("第"+(rowindex+1)+"行，【仓库名称】错误，请检查或重新生成模板!");
            }
//            Stock stock=stockMapper.selectProductByStock(new Stock(productinfo[0],productinfo[1],productinfo[3],productinfo[2]));
//            if (stock==null){
//                return AjaxResult.error("第"+(rowindex+1)+"行，【产品编号+产品名称+产品型号+产品批次】库存不存在，请检查或重新生成模板!");
//            }else{
//                if ((long) Double.parseDouble(quantity)>stock.getQuantity()){
//                    return AjaxResult.error("第"+(rowindex+1)+"行，【产品编号+产品名称+产品型号+产品批次】库存不足，请检查或重新生成模板!");
//                }
//            }
//            Product product=productMapper.selectProductByInfo(new Product(productinfo[0],productinfo[1],productinfo[3],productinfo[2]));
            Product product=productMapper.selectProductByProId(productcode);
            if (product==null){
                return AjaxResult.error("第"+(rowindex+1)+"行，【产品编号+产品名称+产品型号+产品批次】错误，请检查或重新生成模板!");
            }
            //初始化任务子表集合
            List<StoreIoSon> storeIoSonList=new ArrayList<>();
            if (storeIoList.size()>0){
                StoreIo storeIo=null;
                //循环校验是否存在任务单
                for (StoreIo info:storeIoList){
                    //如果任务名称+供应商信息+制单人+生产订单编号+采购订单编号+仓库编码相同时表示已存在任务单
                    if (info.getTaskname().equals(taskname)  && info.getMaker().equals(maker) && info.getWhcode().equals(Whcode)){
                        storeIo=info;
                        break;
                    }
                }
                //如果任务单为空，表示不存在
                if (storeIo==null){
//                    storeIo=new StoreIo(taskType,ToolsUtils.getOrdersId("SIT"),taskname,productdetail,maker,warehouse.getWhcode(), (long) Double.parseDouble(quantity),memo,ShiroUtils.getSysUser().getUserName(),new Date(),(long) Double.parseDouble(outtype),(long) Double.parseDouble(outrule),customername,customercode);
                    storeIo=new StoreIo(taskType,ToolsUtils.getOrdersId("SIT"),taskname,productdetail,maker,warehouse.getWhcode(), (long) Double.parseDouble(quantity),memo,ShiroUtils.getSysUser().getUserName(),new Date(),customername,customercode);
                    storeIoSonList.add(new StoreIoSon(1L,storeIo.getTaskcode(),product.getProductcode(),product.getProductname(),product.getProductmodel(),(long) Double.parseDouble(quantity),productmemo,ShiroUtils.getSysUser().getUserName(),new Date(),productbatch));
                    storeIo.setStoreIoSonList(storeIoSonList);
                    storeIoList.add(storeIo);
                }else{
                    boolean isPresence=false;
                    //任务单已存在，循环校验任务单是否存在该产品，
                    for (StoreIoSon storeIoSon:storeIo.getStoreIoSonList()){
                        //存在即将任务总数量、产品数量增加
                        if (storeIoSon.getProductcode().equals(product.getProductcode())){
                            storeIo.setQuantity(storeIo.getQuantity()+(long) Double.parseDouble(quantity));
                            storeIoSon.setQuantity(storeIoSon.getQuantity()+(long) Double.parseDouble(quantity));
                            isPresence=true;
                            break;
                        }
                    }
                    //isPresence为false时，表示产品不存在，增加任务单产品总数量，新增任务子表
                    if (!isPresence){
                        storeIo.setQuantity(storeIo.getQuantity()+(long) Double.parseDouble(quantity));
//                        storeIo.getStoreIoSonList().add(new StoreIoSonEx(storeIo.getStoreIoSonList().size()+1L,storeIo.getTaskcode(),product.getProductcode(),product.getProductname(),product.getProductmodel(),Long.valueOf(quantity),productmemo,ShiroUtils.getSysUser().getUserName(),new Date()))
                        storeIoSonList=storeIo.getStoreIoSonList();
                        storeIoSonList.add(new StoreIoSon(storeIo.getStoreIoSonList().size()+1L,storeIo.getTaskcode(),product.getProductcode(),product.getProductname(),product.getProductmodel(),(long) Double.parseDouble(quantity),productmemo,ShiroUtils.getSysUser().getUserName(),new Date(),productbatch));
                        storeIo.setStoreIoSonList(storeIoSonList);
                    }
                }
            }else{
                //即导入第一行
                StoreIo storeIo=new StoreIo(taskType,ToolsUtils.getOrdersId("SIT"),taskname,productdetail,maker,warehouse.getWhcode(), (long) Double.parseDouble(quantity),memo,ShiroUtils.getSysUser().getUserName(),new Date(),customername,customercode);
                storeIoSonList.add(new StoreIoSon(1L,storeIo.getTaskcode(),product.getProductcode(),product.getProductname(),product.getProductmodel(),(long) Double.parseDouble(quantity),productmemo,ShiroUtils.getSysUser().getUserName(),new Date(),productbatch));
                storeIo.setStoreIoSonList(storeIoSonList);
                System.out.println(storeIo);
                System.out.println(storeIoSonList);
                storeIoList.add(storeIo);
            }

        }
        //初始化任务子表集合
        List<StoreIoSon> storeIoSonList=new ArrayList<>();
        for (StoreIo storeIo:storeIoList){
            for (StoreIoSon storeIoSon:storeIo.getStoreIoSonList()){
                storeIoSonList.add(storeIoSon);
                //保存堆垛机、RGV数据
                Stacker stacker=new Stacker(storeIoSon.getRowid(),storeIo.getTasktype(),storeIo.getTaskcode(),"0",storeIo.getCreateuser(),new Date());
                Rgv rgv=new Rgv(storeIoSon.getRowid(),storeIo.getTasktype(),storeIo.getTaskcode(),"0",storeIo.getCreateuser(),new Date());
                //拆分入库子表，生成任务数据堆垛机、RGV，根据数量生成相对应的条数
                for (int i=0;i<storeIoSon.getQuantity();i++){
                    stackerList.add(stacker);
                    rgvList.add(rgv);
                }

            }
        }
        //新增入库主表
        if (storeIoList.size()>0){
            storeIoMapper.insertStoreIoList(storeIoList);
        }
        //批量入库子表
        if (storeIoSonList.size()>0) {
            storeIoSonMapper.insertStoreIoSonList(storeIoSonList);
        }
        //新增堆垛机、RGV任务表
        if (stackerList.size()>0) {
            stackerMapper.insertStackerList(stackerList);
        }
        if (rgvList.size()>0) {
            rgvMapper.insertRgvList(rgvList);
        }
        return AjaxResult.success();
    }


//    @Override
//    @Transactional(rollbackFor = Exception.class)
//    public AjaxResult importOutExcel(MultipartFile file, String taskType) throws IOException {
//        int rowcount=0;
//
//        //初始化入库表、入库子表、任务表
//        List<StoreIo> storeIoList=new ArrayList<>();
//
//        List<Stacker> stackerList=new ArrayList<>();
//        List<Rgv> rgvList=new ArrayList<>();
//
//        String filePath=file.getOriginalFilename();//导入的文件名
//
//        //工作簿
//        Sheet sheet=null;
//
//        //2003版本
//        if(filePath.endsWith(".xls")){
//            POIFSFileSystem fileSystem=new POIFSFileSystem(file.getInputStream());
//            HSSFWorkbook hssfWorkbook=new HSSFWorkbook(fileSystem);
//            sheet=hssfWorkbook.getSheetAt(0);
//        }
//
//        if (filePath.endsWith(".xlsx")){
//            XSSFWorkbook xssfWorkbook=new XSSFWorkbook(file.getInputStream());
//            sheet=xssfWorkbook.getSheetAt(0);
//        }
//
//        if (sheet==null){
//            AjaxResult.error("表格内容为空");
//        }
//
//        rowcount=sheet.getPhysicalNumberOfRows();//获取行数
//        System.out.println(rowcount);
//        if (rowcount<=1){
//            return AjaxResult.error("表格内容为空！");
//        }
//
////        String[] fields={"仓库名称","任务名称","任务描述","制单人","任务备注","产品编号+产品名称+产品型号+产品批次","数量","产品备注","客户名称","客户编码","出库方式","出库规则"};
//        String[] fields={"仓库名称","任务名称","任务描述","制单人","任务备注","产品型号","产品批次","数量","产品备注","客户名称","客户编码"};
//        Row titleRow=sheet.getRow(0);
//        for (int i=0;i<fields.length;i++){
//            if (!fields[i].equals(StringUtils.getString(titleRow.getCell(i)).trim())){
//                return AjaxResult.error("模板内容与导入模板不同，请重新生成模板填充内容！");
//            }
//        }
//
//        //转换产品类型
//        List<SysDictData> base_product_model = dictService.getType("base_product_model");
//        //转换出库方式
////        List<SysDictData> base_warehouseout_rules = dictService.getType("base_warehouseout_rules");
//        //转换出库规则
////        List<SysDictData> base_warehouse_rules = dictService.getType("base_warehouse_rules");
//
//        for(int rowindex=1;rowindex<rowcount;rowindex++){
//            Row row=sheet.getRow(rowindex);
//            if (row==null){
//                break;
//            }
//            String[] Whcode= StringUtils.getString(row.getCell(0)).trim().split("_");//仓库编码
//            String taskname= StringUtils.getString(row.getCell(1)).trim();//任务名称
//            String productdetail= StringUtils.getString(row.getCell(2)).trim();//任务描述
//            String maker= StringUtils.getString(row.getCell(3)).trim();//制单人
////            String[] productinfo= StringUtils.getString(row.getCell(5)).trim().split("\\+");//产品编号
//            String memo= StringUtils.getString(row.getCell(4)).trim();//任务备注
//            String productcode= StringUtils.getString(row.getCell(5)).trim();//产品编号
//            String productbatch= StringUtils.getString(row.getCell(6)).trim();//批次
//            if(StringUtils.isNotEmpty(productbatch)) {
//                if (row.getCell(6).getCellType() == Cell.CELL_TYPE_NUMERIC) {
//                    DecimalFormat df = new DecimalFormat("#");// 转换成整型
//                    productbatch = df.format(row.getCell(6).getNumericCellValue());
//                } else {
//                    productbatch = StringUtils.getString(row.getCell(6)).trim();
//                }
//            }
//            String quantity= StringUtils.getString(row.getCell(7)).trim();//产品数量
//            if(StringUtils.isNotEmpty(quantity)) {
//                if (row.getCell(7).getCellType() == Cell.CELL_TYPE_NUMERIC) {
//                    DecimalFormat df = new DecimalFormat("#");// 转换成整型
//                    quantity = df.format(row.getCell(7).getNumericCellValue());
//                } else {
//                    quantity = StringUtils.getString(row.getCell(7)).trim();
//                }
//            }
//            String productmemo= StringUtils.getString(row.getCell(8)).trim();//产品备注
//            String customername= StringUtils.getString(row.getCell(9)).trim();//客户名称
//            String customercode= StringUtils.getString(row.getCell(10)).trim();//客户编码
////            String layer= StringUtils.getString(row.getCell(11)).trim();//摆放层
////            String outtype= StringUtils.getString(row.getCell(10)).trim();//出库方式
////            String outrule= StringUtils.getString(row.getCell(11)).trim();//出库规则
//
////            if (Whcode.length!=2 && taskname=="" && productdetail=="" && veninfo=="" && maker=="" && productinfo.length!=4 && mpocode=="" && ordercode=="" && remark==""&& layer==""&& outrule==""&& outtype==""&& customername==""&& customercode==""){
////                if (rowindex==1){
////                    return AjaxResult.error("表格内容为空！");
////                }else{
////                    break;
////                }
////            }
////            for (SysDictData model : base_product_model){
////                if (productinfo[2].equals(model.getDictLabel())){
////                    productinfo[2] = model.getDictValue();
////                    break;
////                }
////            }
////            for (SysDictData model : base_warehouseout_rules){
////                if (outtype.equals(model.getDictLabel())){
////                    outtype = model.getDictValue();
////                    break;
////                }
////            }
////            for (SysDictData model : base_warehouse_rules){
////                if (outrule.equals(model.getDictLabel())){
////                    outrule = model.getDictValue();
////                    break;
////                }
////            }
//
//            //任务名称
//            if (StringUtils.isEmpty(taskname)){
//                return AjaxResult.error("第"+(rowindex+1)+"行，【任务名称】不能为空!");
//            }
//            //供应商信息
////            if (StringUtils.isEmpty(veninfo)){
////                return AjaxResult.error("第"+(rowindex+1)+"行，【供应商信息】不能为空!");
////            }
//            //制单人
//            if (StringUtils.isEmpty(maker)){
//                return AjaxResult.error("第"+(rowindex+1)+"行，【制单人】不能为空!");
//            }
//            //产品数量
//            if (StringUtils.isEmpty(quantity)){
//                return AjaxResult.error("第"+(rowindex+1)+"行，【产品数量】不能为空!");
//            }
//
//            if (!StringUtils.isNumber(quantity)){
//                return AjaxResult.error("第"+(rowindex+1)+"行，【产品数量】格式不正确!");
//            }
//
//            if ((long) Double.parseDouble(quantity)<=0){
//                return AjaxResult.error("第"+(rowindex+1)+"行，【产品数量】不能为0！");
//            }
//
//            //校验仓库和产品是否正确
//            if (Whcode.length!=2){
//                return AjaxResult.error("第"+(rowindex+1)+"行，【仓库名称】格式错误!");
//            }
////            if (productinfo.length!=4){
////                return AjaxResult.error("第"+(rowindex+1)+"行，【产品编号+产品名称+产品型号+产品批次】格式错误!");
////            }
//
//            Warehouse warehouse=warehouseMapper.selectWarehouseById(Whcode[0]);
//            if (warehouse==null){
//                return AjaxResult.error("第"+(rowindex+1)+"行，【仓库名称】错误，请检查或重新生成模板!");
//            }
////            Stock stock=stockMapper.selectProductByStock(new Stock(productinfo[0],productinfo[1],productinfo[3],productinfo[2]));
////            if (stock==null){
////                return AjaxResult.error("第"+(rowindex+1)+"行，【产品编号+产品名称+产品型号+产品批次】库存不存在，请检查或重新生成模板!");
////            }else{
////                if ((long) Double.parseDouble(quantity)>stock.getQuantity()){
////                    return AjaxResult.error("第"+(rowindex+1)+"行，【产品编号+产品名称+产品型号+产品批次】库存不足，请检查或重新生成模板!");
////                }
////            }
////            Product product=productMapper.selectProductByInfo(new Product(productinfo[0],productinfo[1],productinfo[3],productinfo[2]));
//            Product product=productMapper.selectProductByProId(productcode);
//            if (product==null){
//                return AjaxResult.error("第"+(rowindex+1)+"行，【产品编号+产品名称+产品型号+产品批次】错误，请检查或重新生成模板!");
//            }
//            //初始化任务子表集合
//            List<StoreIoSon> storeIoSonList=new ArrayList<>();
//            if (storeIoList.size()>0){
//                StoreIo storeIo=null;
//                //循环校验是否存在任务单
//                for (StoreIo info:storeIoList){
//                    //如果任务名称+供应商信息+制单人+生产订单编号+采购订单编号+仓库编码相同时表示已存在任务单
//                    if (info.getTaskname().equals(taskname)  && info.getMaker().equals(maker) && info.getWhcode().equals(Whcode[0])){
//                        storeIo=info;
//                        break;
//                    }
//                }
//                //如果任务单为空，表示不存在
//                if (storeIo==null){
////                    storeIo=new StoreIo(taskType,ToolsUtils.getOrdersId("SIT"),taskname,productdetail,maker,warehouse.getWhcode(), (long) Double.parseDouble(quantity),memo,ShiroUtils.getSysUser().getUserName(),new Date(),(long) Double.parseDouble(outtype),(long) Double.parseDouble(outrule),customername,customercode);
//                    storeIo=new StoreIo(taskType,ToolsUtils.getOrdersId("SIT"),taskname,productdetail,maker,warehouse.getWhcode(), (long) Double.parseDouble(quantity),memo,ShiroUtils.getSysUser().getUserName(),new Date(),customername,customercode);
//                    storeIoSonList.add(new StoreIoSon(1L,storeIo.getTaskcode(),product.getProductcode(),product.getProductname(),product.getProductmodel(),(long) Double.parseDouble(quantity),productmemo,ShiroUtils.getSysUser().getUserName(),new Date(),productbatch));
//                    storeIo.setStoreIoSonList(storeIoSonList);
//                    storeIoList.add(storeIo);
//                }else{
//                    boolean isPresence=false;
//                    //任务单已存在，循环校验任务单是否存在该产品，
//                    for (StoreIoSon storeIoSon:storeIo.getStoreIoSonList()){
//                        //存在即将任务总数量、产品数量增加
//                        if (storeIoSon.getProductcode().equals(product.getProductcode())){
//                            storeIo.setQuantity(storeIo.getQuantity()+(long) Double.parseDouble(quantity));
//                            storeIoSon.setQuantity(storeIoSon.getQuantity()+(long) Double.parseDouble(quantity));
//                            isPresence=true;
//                            break;
//                        }
//                    }
//                    //isPresence为false时，表示产品不存在，增加任务单产品总数量，新增任务子表
//                    if (!isPresence){
//                        storeIo.setQuantity(storeIo.getQuantity()+(long) Double.parseDouble(quantity));
////                        storeIo.getStoreIoSonList().add(new StoreIoSonEx(storeIo.getStoreIoSonList().size()+1L,storeIo.getTaskcode(),product.getProductcode(),product.getProductname(),product.getProductmodel(),Long.valueOf(quantity),productmemo,ShiroUtils.getSysUser().getUserName(),new Date()))
//                        storeIoSonList=storeIo.getStoreIoSonList();
//                        storeIoSonList.add(new StoreIoSon(storeIo.getStoreIoSonList().size()+1L,storeIo.getTaskcode(),product.getProductcode(),product.getProductname(),product.getProductmodel(),(long) Double.parseDouble(quantity),productmemo,ShiroUtils.getSysUser().getUserName(),new Date(),productbatch));
//                        storeIo.setStoreIoSonList(storeIoSonList);
//                    }
//                }
//            }else{
//                //即导入第一行
//                StoreIo storeIo=new StoreIo(taskType,ToolsUtils.getOrdersId("SIT"),taskname,productdetail,maker,warehouse.getWhcode(), (long) Double.parseDouble(quantity),memo,ShiroUtils.getSysUser().getUserName(),new Date(),customername,customercode);
//                storeIoSonList.add(new StoreIoSon(1L,storeIo.getTaskcode(),product.getProductcode(),product.getProductname(),product.getProductmodel(),(long) Double.parseDouble(quantity),productmemo,ShiroUtils.getSysUser().getUserName(),new Date(),productbatch));
//                storeIo.setStoreIoSonList(storeIoSonList);
//                System.out.println(storeIo);
//                System.out.println(storeIoSonList);
//                storeIoList.add(storeIo);
//            }
//
//        }
//        //初始化任务子表集合
//        List<StoreIoSon> storeIoSonList=new ArrayList<>();
//        for (StoreIo storeIo:storeIoList){
//            for (StoreIoSon storeIoSon:storeIo.getStoreIoSonList()){
//                storeIoSonList.add(storeIoSon);
//                //保存堆垛机、RGV数据
//                Stacker stacker=new Stacker(storeIoSon.getRowid(),storeIo.getTasktype(),storeIo.getTaskcode(),"0",storeIo.getCreateuser(),new Date());
//                Rgv rgv=new Rgv(storeIoSon.getRowid(),storeIo.getTasktype(),storeIo.getTaskcode(),"0",storeIo.getCreateuser(),new Date());
//                //拆分入库子表，生成任务数据堆垛机、RGV，根据数量生成相对应的条数
//                for (int i=0;i<storeIoSon.getQuantity();i++){
//                    stackerList.add(stacker);
//                    rgvList.add(rgv);
//                }
//
//            }
//        }
//        //新增入库主表
//        if (storeIoList.size()>0){
//            storeIoMapper.insertStoreIoList(storeIoList);
//        }
//        //批量入库子表
//        if (storeIoSonList.size()>0) {
//            storeIoSonMapper.insertStoreIoSonList(storeIoSonList);
//        }
//        //新增堆垛机、RGV任务表
//        if (stackerList.size()>0) {
//            stackerMapper.insertStackerList(stackerList);
//        }
//        if (rgvList.size()>0) {
//            rgvMapper.insertRgvList(rgvList);
//        }
//        return AjaxResult.success();
//    }


//    @Override
//    @Transactional(rollbackFor = Exception.class)
//    public AjaxResult importOutExcel(MultipartFile file, String taskType) throws IOException {
//        int rowcount=0;
//
//        //初始化入库表、入库子表、任务表
//        List<StoreIo> storeIoList=new ArrayList<>();
//
//        List<Stacker> stackerList=new ArrayList<>();
//        List<Rgv> rgvList=new ArrayList<>();
//
//        String filePath=file.getOriginalFilename();//导入的文件名
//
//        //工作簿
//        Sheet sheet=null;
//
//        //2003版本
//        if(filePath.endsWith(".xls")){
//            POIFSFileSystem fileSystem=new POIFSFileSystem(file.getInputStream());
//            HSSFWorkbook hssfWorkbook=new HSSFWorkbook(fileSystem);
//            sheet=hssfWorkbook.getSheetAt(0);
//        }
//
//        if (filePath.endsWith(".xlsx")){
//            XSSFWorkbook xssfWorkbook=new XSSFWorkbook(file.getInputStream());
//            sheet=xssfWorkbook.getSheetAt(0);
//        }
//
//        if (sheet==null){
//            AjaxResult.error("表格内容为空");
//        }
//
//        rowcount=sheet.getPhysicalNumberOfRows();//获取行数
//
//        if (rowcount<=1){
//            return AjaxResult.error("表格内容为空！");
//        }
//
//        String[] fields={"仓库名称","任务名称","任务描述","供应商信息","制单人","产品编号+产品名称+产品型号+产品批次","生产订单编号","采购订单编号","任务备注","数量","产品备注"};
//        Row titleRow=sheet.getRow(0);
//        for (int i=0;i<fields.length;i++){
//            if (!fields[i].equals(StringUtils.getString(titleRow.getCell(i)).trim())){
//                return AjaxResult.error("模板内容与导入模板不同，请重新生成模板填充内容！");
//            }
//        }
//
//        //转换产品类型
//        List<SysDictData> base_product_model = dictService.getType("base_product_model");
//
//        for(int rowindex=1;rowindex<rowcount;rowindex++){
//            Row row=sheet.getRow(rowindex);
//            if (row==null){
//                break;
//            }
//            String[] Whcode= StringUtils.getString(row.getCell(0)).trim().split("_");//仓库编码
//            String taskname= StringUtils.getString(row.getCell(1)).trim();//任务名称
//            String productdetail= StringUtils.getString(row.getCell(2)).trim();//任务描述
//            String veninfo= StringUtils.getString(row.getCell(3)).trim();//供应商信息
//            String maker= StringUtils.getString(row.getCell(4)).trim();//制单人
//            String[] productinfo= StringUtils.getString(row.getCell(5)).trim().split("\\+");//产品信息
//            String mpocode= StringUtils.getString(row.getCell(6)).trim();//生产订单编号
//
//            if(StringUtils.isNotEmpty(mpocode)){
//                if (row.getCell(6).getCellType() == Cell.CELL_TYPE_NUMERIC) {
//                    DecimalFormat df = new DecimalFormat("#");// 转换成整型
//                    mpocode = df.format(row.getCell(6).getNumericCellValue());
//                } else {
//                    mpocode = StringUtils.getString(row.getCell(6)).trim();
//                }
//            }
//
//            String ordercode= StringUtils.getString(row.getCell(7)).trim();//采购订单编号
//            if(StringUtils.isNotEmpty(ordercode)) {
//                if (row.getCell(7).getCellType() == Cell.CELL_TYPE_NUMERIC) {
//                    DecimalFormat df = new DecimalFormat("#");// 转换成整型
//                    ordercode = df.format(row.getCell(7).getNumericCellValue());
//                } else {
//                    ordercode = StringUtils.getString(row.getCell(7)).trim();
//                }
//            }
//            String remark= StringUtils.getString(row.getCell(8)).trim();//备注
//
//            String quantity= StringUtils.getString(row.getCell(9)).trim();//产品数量
//
//            if(StringUtils.isNotEmpty(quantity)) {
//                if (row.getCell(9).getCellType() == Cell.CELL_TYPE_NUMERIC) {
//                    DecimalFormat df = new DecimalFormat("#");// 转换成整型
//                    quantity = df.format(row.getCell(9).getNumericCellValue());
//                } else {
//                    quantity = StringUtils.getString(row.getCell(9)).trim();
//                }
//            }
//            String productmemo= StringUtils.getString(row.getCell(10)).trim();//产品备注
//            String layer= StringUtils.getString(row.getCell(11)).trim();//摆放层
//            String outtype= StringUtils.getString(row.getCell(12)).trim();//出库方式
//            String outrule= StringUtils.getString(row.getCell(13)).trim();//出库规则
//            String customername= StringUtils.getString(row.getCell(14)).trim();//客户名称
//            String customercode= StringUtils.getString(row.getCell(15)).trim();//客户编码
//            if (Whcode.length!=2 && taskname=="" && productdetail=="" && veninfo=="" && maker=="" && productinfo.length!=4 && mpocode=="" && ordercode=="" && remark==""&& layer==""&& outrule==""&& outtype==""&& customername==""&& customercode==""){
//                if (rowindex==1){
//                    return AjaxResult.error("表格内容为空！");
//                }else{
//                    break;
//                }
//            }
//
//
//            for (SysDictData model : base_product_model){
//                if (productinfo[2].equals(model.getDictLabel())){
//                    productinfo[2] = model.getDictValue();
//                    break;
//                }
//            }
//
//            //任务名称
//            if (StringUtils.isEmpty(taskname)){
//                return AjaxResult.error("第"+(rowindex+1)+"行，【任务名称】不能为空!");
//            }
//            //供应商信息
//            if (StringUtils.isEmpty(veninfo)){
//                return AjaxResult.error("第"+(rowindex+1)+"行，【供应商信息】不能为空!");
//            }
//            //制单人
//            if (StringUtils.isEmpty(maker)){
//                return AjaxResult.error("第"+(rowindex+1)+"行，【制单人】不能为空!");
//            }
//            //生产订单编号
//            if (StringUtils.isEmpty(mpocode)){
//                return AjaxResult.error("第"+(rowindex+1)+"行，【生产订单编号】不能为空!");
//            }
//            //采购订单编号
//            if (StringUtils.isEmpty(ordercode)){
//                return AjaxResult.error("第"+(rowindex+1)+"行，【采购订单编号】不能为空!");
//            }
//            //产品数量
//            if (StringUtils.isEmpty(quantity)){
//                return AjaxResult.error("第"+(rowindex+1)+"行，【产品数量】不能为空!");
//            }
//
//            if (!StringUtils.isNumber(quantity)){
//                return AjaxResult.error("第"+(rowindex+1)+"行，【产品数量】格式不正确!");
//            }
//
//            if ((long) Double.parseDouble(quantity)<=0){
//                return AjaxResult.error("第"+(rowindex+1)+"行，【产品数量】不能为0！");
//            }
//
//            //校验仓库和产品是否正确
//            if (Whcode.length!=2){
//                return AjaxResult.error("第"+(rowindex+1)+"行，【仓库名称】格式错误!");
//            }
//            if (productinfo.length!=4){
//                return AjaxResult.error("第"+(rowindex+1)+"行，【产品编号+产品名称+产品型号+产品批次】格式错误!");
//            }
//
//            Warehouse warehouse=warehouseMapper.selectWarehouseById(Whcode[0]);
//            if (warehouse==null){
//                return AjaxResult.error("第"+(rowindex+1)+"行，【仓库名称】错误，请检查或重新生成模板!");
//            }
//            Stock stock=stockMapper.selectProductByStock(new Stock(productinfo[0],productinfo[1],productinfo[3],productinfo[2]));
//            if (stock==null){
//                return AjaxResult.error("第"+(rowindex+1)+"行，【产品编号+产品名称+产品型号+产品批次】库存不存在，请检查或重新生成模板!");
//            }else{
//                if ((long) Double.parseDouble(quantity)>stock.getQuantity()){
//                    return AjaxResult.error("第"+(rowindex+1)+"行，【产品编号+产品名称+产品型号+产品批次】库存不足，请检查或重新生成模板!");
//                }
//            }
//            Product product=productMapper.selectProductByInfo(new Product(productinfo[0],productinfo[1],productinfo[3],productinfo[2]));
//            if (product==null){
//                return AjaxResult.error("第"+(rowindex+1)+"行，【产品编号+产品名称+产品型号+产品批次】错误，请检查或重新生成模板!");
//            }
//            //初始化任务子表集合
//            List<StoreIoSon> storeIoSonList=new ArrayList<>();
//            if (storeIoList.size()>0){
//                StoreIo storeIo=null;
//                //循环校验是否存在任务单
//                for (StoreIo info:storeIoList){
//                    //如果任务名称+供应商信息+制单人+生产订单编号+采购订单编号+仓库编码相同时表示已存在任务单
//                    if (info.getTaskname().equals(taskname) && info.getVeninfo().equals(veninfo) && info.getMaker().equals(maker) && info.getMpocode().equals(mpocode)
//                            && info.getOrdercode().equals(ordercode) && info.getWhcode().equals(Whcode[0])){
//                        storeIo=info;
//                        break;
//                    }
//                }
//                //如果任务单为空，表示不存在
//                if (storeIo==null){
//                    storeIo=new StoreIo(taskType, ToolsUtils.getOrdersId("SIT"),taskname,productdetail,veninfo,maker,mpocode,ordercode,warehouse.getWhcode(),(long) Double.parseDouble(quantity),remark, ShiroUtils.getSysUser().getUserName(),new Date(),(long) Double.parseDouble(layer),(long) Double.parseDouble(outtype),(long) Double.parseDouble(outrule),customername,customercode);
//                    storeIoSonList.add(new StoreIoSon(1L,storeIo.getTaskcode(),product.getProductcode(),product.getProductname(),product.getProductmodel(),(long) Double.parseDouble(quantity),productmemo,ShiroUtils.getSysUser().getUserName(),new Date()));
//                    storeIo.setStoreIoSonList(storeIoSonList);
//                    storeIoList.add(storeIo);
//                }else{
//                    boolean isPresence=false;
//                    //任务单已存在，循环校验任务单是否存在该产品，
//                    for (StoreIoSon storeIoSon:storeIo.getStoreIoSonList()){
//                        //存在即将任务总数量、产品数量增加
//                        if (storeIoSon.getProductcode().equals(product.getProductcode())){
//                            storeIo.setQuantity(storeIo.getQuantity()+(long) Double.parseDouble(quantity));
//                            storeIoSon.setQuantity(storeIoSon.getQuantity()+(long) Double.parseDouble(quantity));
//                            isPresence=true;
//                            break;
//                        }
//                    }
//                    //isPresence为false时，表示产品不存在，增加任务单产品总数量，新增任务子表
//                    if (!isPresence){
//                        storeIo.setQuantity(storeIo.getQuantity()+(long) Double.parseDouble(quantity));
////                        storeIo.getStoreIoSonList().add(new StoreIoSonEx(storeIo.getStoreIoSonList().size()+1L,storeIo.getTaskcode(),product.getProductcode(),product.getProductname(),product.getProductmodel(),Long.valueOf(quantity),productmemo,ShiroUtils.getSysUser().getUserName(),new Date()))
//                        storeIoSonList=storeIo.getStoreIoSonList();
//                        storeIoSonList.add(new StoreIoSon(storeIo.getStoreIoSonList().size()+1L,storeIo.getTaskcode(),product.getProductcode(),product.getProductname(),product.getProductmodel(),(long) Double.parseDouble(quantity),productmemo,ShiroUtils.getSysUser().getUserName(),new Date()));
//                        storeIo.setStoreIoSonList(storeIoSonList);
//                    }
//                }
//            }else{
//                //即导入第一行
//
//                StoreIo storeIo=new StoreIo(taskType,ToolsUtils.getOrdersId("SIT"),taskname,productdetail,veninfo,maker,mpocode,ordercode,warehouse.getWhcode(), (long) Double.parseDouble(quantity),remark,ShiroUtils.getSysUser().getUserName(),new Date(),(long) Double.parseDouble(layer),(long) Double.parseDouble(outtype),(long) Double.parseDouble(outrule),customername,customercode);
//                storeIoSonList.add(new StoreIoSon(1L,storeIo.getTaskcode(),product.getProductcode(),product.getProductname(),product.getProductmodel(),(long) Double.parseDouble(quantity),productmemo,ShiroUtils.getSysUser().getUserName(),new Date()));
//                storeIo.setStoreIoSonList(storeIoSonList);
//                storeIoList.add(storeIo);
//            }
//
//        }
//
//        //初始化任务子表集合
//        List<StoreIoSon> storeIoSonList=new ArrayList<>();
//        for (StoreIo storeIo:storeIoList){
//            for (StoreIoSon storeIoSon:storeIo.getStoreIoSonList()){
//                storeIoSonList.add(storeIoSon);
//                //保存堆垛机、RGV数据
//                Stacker stacker=new Stacker(storeIoSon.getRowid(),storeIo.getTasktype(),storeIo.getTaskcode(),"0",storeIo.getCreateuser(),new Date());
//                Rgv rgv=new Rgv(storeIoSon.getRowid(),storeIo.getTasktype(),storeIo.getTaskcode(),"0",storeIo.getCreateuser(),new Date());
//                //拆分入库子表，生成任务数据堆垛机、RGV，根据数量生成相对应的条数
//                for (int i=0;i<storeIoSon.getQuantity();i++){
//                    stackerList.add(stacker);
//                    rgvList.add(rgv);
//                }
//
//            }
//        }
//        //新增入库主表
//        if (storeIoList.size()>0){
//            storeIoMapper.insertStoreIoList(storeIoList);
//        }
//        //批量入库子表
//        if (storeIoSonList.size()>0) {
//            storeIoSonMapper.insertStoreIoSonList(storeIoSonList);
//        }
//        //新增堆垛机、RGV任务表
//        if (stackerList.size()>0) {
//            stackerMapper.insertStackerList(stackerList);
//        }
//        if (rgvList.size()>0) {
//            rgvMapper.insertRgvList(rgvList);
//        }
//
//        return AjaxResult.success();
//    }

    @Override
    public List<StoreIoExcel> selectStoreIoExcelList(StoreIo storeIo) {
        return storeIoMapper.selectStoreIoExcelList(storeIo);
    }
}
