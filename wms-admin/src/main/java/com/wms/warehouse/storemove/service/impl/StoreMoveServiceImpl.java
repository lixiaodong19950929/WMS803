package com.wms.warehouse.storemove.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import com.wms.base.product.domain.Product;
import com.wms.base.product.mapper.ProductMapper;
import com.wms.base.storehouse.domain.Storehouse;
import com.wms.base.storehouse.mapper.StorehouseMapper;
import com.wms.base.warehouse.domain.Warehouse;
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
import com.wms.warehouse.storeio.domain.Rgv;
import com.wms.warehouse.storeio.domain.Stacker;
import com.wms.warehouse.storeio.domain.StoreIo;
import com.wms.warehouse.storeio.domain.StoreIoSon;
import com.wms.warehouse.storeio.mapper.RgvMapper;
import com.wms.warehouse.storeio.mapper.StackerMapper;
import com.wms.warehouse.storemove.domain.StoreMove;
import com.wms.warehouse.storemove.domain.StoreMoveExcel;
import com.wms.warehouse.storemove.domain.StoreMoveSon;
import com.wms.warehouse.storemove.mapper.StoreMoveSonMapper;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.formula.functions.T;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.wms.warehouse.storemove.mapper.StoreMoveMapper;
import com.wms.warehouse.storemove.service.IStoreMoveService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;


/**
 * 移库主Service业务层处理
 *
 * @author assassin
 * @date 2020-01-16
 */
@Service
public class StoreMoveServiceImpl implements IStoreMoveService
{
    @Autowired
    private StoreMoveMapper storeMoveMapper;

    @Autowired
    private StoreMoveSonMapper storeMoveSonMapper;

    @Autowired
    private StorehouseMapper storehouseMapper;

    @Autowired
    private StackerMapper stackerMapper;

    @Autowired
    private RgvMapper rgvMapper;

    @Autowired
    private WarehouseMapper warehouseMapper;

    @Autowired
    private DictService dictService;

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private StockMapper stockMapper;

    /**
     * 查询移库主
     *
     * @param tasktype 移库主ID
     * @return 移库主
     */
    @Override
    public StoreMove selectStoreMoveById(String taskcode)
    {
        return storeMoveMapper.selectStoreMoveById(taskcode);
    }

    /**
     * 查询移库主列表
     *
     * @param storeMove 移库主
     * @return 移库主
     */
    @Override
    public List<StoreMove> selectStoreMoveList(StoreMove storeMove)
    {
        return storeMoveMapper.selectStoreMoveList(storeMove);
    }

    /**
     * 新增移库主
     *
     * @param storeMove 移库主
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int insertStoreMove(StoreMove storeMove)
    {
        //初始化堆垛机、RGV任务表
        List<Stacker> stackerList=new ArrayList<>();
        List<Rgv> rgvList=new ArrayList<>();

        //将入库表内容写入入库子表
        for (StoreMoveSon storeMoveSon:storeMove.getStoreMoveSonList()){
            storeMoveSon.setCreateuser(storeMove.getCreateuser());
            storeMoveSon.setCreatedate(new Date());
            storeMoveSon.setTaskcode(storeMove.getTaskcode());

        }

        //批量入库子表
        storeMoveSonMapper.insertStoreMoveSonList(storeMove.getStoreMoveSonList());

//        //将入库子表写入任务表中
//        for (StoreMoveSon storeMoveSon:storeMove.getStoreMoveSonList()){
//            //保存堆垛机、RGV数据
//            Stacker stacker=new Stacker(storeMoveSon.getRowid(),storeMove.getTasktype(),storeMove.getTaskcode(),storeMoveSon.getEndcode(),"0",storeMove.getCreateuser(),new Date());
//            Rgv rgv=new Rgv(storeMoveSon.getRowid(),storeMove.getTasktype(),storeMove.getTaskcode(),"0",storeMove.getCreateuser(),new Date());
//            //拆分入库子表，生成任务数据堆垛机、RGV，根据数量生成相对应的条数
//            for (int i=0;i<storeMoveSon.getQuantity();i++){
//                stackerList.add(stacker);
//                rgvList.add(rgv);
//            }
//
//        }
//
//
//        //新增堆垛机、RGV任务表
//        stackerMapper.insertStackerList(stackerList);
//        rgvMapper.insertRgvList(rgvList);
        //新增移库主表
        return storeMoveMapper.insertStoreMove(storeMove);
    }

    /**
     * 修改移库主
     *
     * @param storeMove 移库主
     * @return 结果
     */
//    @Override
//    @Transactional(rollbackFor = Exception.class)
//    public int updateStoreMove(StoreMove storeMove,String userName)
//    {
//        //初始化新增子表内容
//        List<StoreMoveSon> insertSons=new ArrayList<>();
//
//        //初始化修改子表内容
//        List<StoreMoveSon> updateSons=new ArrayList<>();
//
//        //初始化新增任务表内容
//        List<Rgv>  insertRgv=new ArrayList<>();
//        List<Stacker> insertStacker=new ArrayList<>();
//
//        //初始化删除任务表内容
//        List<Rgv>  deleteRgv=new ArrayList<>();
//        List<Stacker> deleteStacker=new ArrayList<>();
//
//        //判断入库任务状态，验证是否需要修改内容
//        StoreMove info=storeMoveMapper.selectStoreMoveByIdAndList(storeMove);
//        if (!info.getTaskstate().equals("0")){
//            return 0;
//        }
//        Long maxRowId=info.getMaxRowId();
//        //根据子表有无id进行更新或修改的判断
//        for (StoreMoveSon storeMoveSon: storeMove.getStoreMoveSonList()){
//            //id不存在为新增数据
//            if (storeMoveSon.getId()==null){
//                storeMoveSon.setRowid(++maxRowId);
//                storeMoveSon.setCreateuser(userName);
//                storeMoveSon.setCreatedate(new Date());
//                storeMoveSon.setTaskcode(storeMove.getTaskcode());
//                insertSons.add(storeMoveSon);
////                //保存堆垛机、RGV数据
////                Stacker stacker=new Stacker(storeMoveSon.getRowid(),storeMove.getTasktype(),storeMove.getTaskcode(),storeMoveSon.getEndcode(),"0",storeMove.getCreateuser(),new Date());
////                Rgv rgv=new Rgv(storeMoveSon.getRowid(),storeMove.getTasktype(),storeMove.getTaskcode(),"0",storeMove.getCreateuser(),new Date());
////                //拆分入库子表，生成任务数据堆垛机、RGV，根据数量生成相对应的条数
////                for (int i=0;i<storeMoveSon.getQuantity();i++){
////                    insertStacker.add(stacker);
////                    insertRgv.add(rgv);
////                }
//            }else{
//                Iterator iterator =info.getStoreMoveSonList().iterator();
//                //id存在是，根据是否修改元数据来进行修改和删除判断
//                while (iterator.hasNext()){
//                    StoreMoveSon ioSon= (StoreMoveSon) iterator.next();
//
//                    //id相同，证明此数据为修改或未修改数据，都可以表示为修改数据
//                    if (storeMoveSon.getId()==ioSon.getId()){
//                        updateSons.add(storeMoveSon);
//
////                        //修改后数量大于原数量
////                        if (storeMoveSon.getQuantity()>ioSon.getQuantity()) {
////                            for (int i = 0; i < Long.hashCode(storeMoveSon.getQuantity() - ioSon.getQuantity()); i++) {
////                                //新增RGV、堆垛机任务
////                                Stacker stacker=new Stacker(storeMoveSon.getRowid(),storeMove.getTasktype(),storeMove.getTaskcode(),storeMoveSon.getEndcode(),"0",storeMove.getCreateuser(),new Date());
////                                Rgv rgv=new Rgv(storeMoveSon.getRowid(),storeMove.getTasktype(),storeMove.getTaskcode(),"0",storeMove.getCreateuser(),new Date());
////                                insertStacker.add(stacker);
////                                insertRgv.add(rgv);
////                            }
////                        }else if(storeMoveSon.getQuantity()<ioSon.getQuantity()){
////                            //修改后数量小于原数量，删除任务表内容
////                            //删除RGV、堆垛机任务
////                            Stacker stacker=new Stacker(storeMoveSon.getRowid(),storeMove.getTasktype(),storeMove.getTaskcode(),storeMoveSon.getEndcode(),"0",storeMove.getCreateuser(),new Date());
////                            Rgv rgv=new Rgv(storeMoveSon.getRowid(),storeMove.getTasktype(),storeMove.getTaskcode(),"0",storeMove.getCreateuser(),new Date());
////                            deleteStacker.add(stacker);
////                            deleteRgv.add(rgv);
////                        }
//                        //移除修改数据，剩余的原子表数据即为已删除数据
//                        iterator.remove();
//                    }
//                }
//            }
//        }
//
//        //新增入库子表
//        if (insertSons.size()>0) {
//            storeMoveSonMapper.insertStoreMoveSonList(insertSons);
//        }
//
//        //修改入库子表
//        if (updateSons.size()>0) {
//            storeMoveSonMapper.updateStoreMoveSonList(updateSons);
//        }
//
//        //删除入库子表
//        if (info.getStoreMoveSonList().size()>0) {
//            storeMoveSonMapper.deleteStoreMoveSonByIdList(info.getStoreMoveSonList());
//        }
//
//        //操作堆垛机、RGV任务表
//        //新增任务表
////        if (insertStacker.size()>0) {
////            stackerMapper.insertStackerList(insertStacker);
////        }
////        if (insertRgv.size()>0) {
////            rgvMapper.insertRgvList(insertRgv);
////        }
////
////        //删除任务表，即删除与子表数量不对应的任务数据
////        if (deleteStacker.size()>0) {
////            stackerMapper.deleteStackerByList(deleteStacker);
////        }
////        if (deleteRgv.size()>0) {
////            rgvMapper.deleteRgvByList(deleteRgv);
////        }
////
////        //根据子表数据删除任务表,即删除子表一条数据对应拆分的全部任务
////        if (info.getStoreMoveSonList().size()>0) {
////            stackerMapper.deleteStackerByStoreMoveSon(info.getStoreMoveSonList(),userName);
////            rgvMapper.deleteRgvByStoreMoveSon(info.getStoreMoveSonList(),userName);
////        }
//
//
//
//        //修改主表内容
//        return storeMoveMapper.updateStoreMove(storeMove);
//    }
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateStoreMove(StoreMove storeMove,String userName)
    {
        //初始化新增子表内容
        List<StoreMoveSon> insertSons=new ArrayList<>();

        //初始化修改子表内容
        List<StoreMoveSon> updateSons=new ArrayList<>();

        //初始化新增任务表内容
        List<Rgv>  insertRgv=new ArrayList<>();
        List<Stacker> insertStacker=new ArrayList<>();

        //初始化删除任务表内容
        List<Rgv>  deleteRgv=new ArrayList<>();
        List<Stacker> deleteStacker=new ArrayList<>();

        //判断入库任务状态，验证是否需要修改内容
        StoreMove info=storeMoveMapper.selectStoreMoveByIdAndList(storeMove);
        if (!info.getTaskstate().equals("0")){
            return 0;
        }
        Long maxRowId=info.getMaxRowId();
        //根据子表有无id进行更新或修改的判断
        for (StoreMoveSon storeMoveSon: storeMove.getStoreMoveSonList()){
            //id不存在为新增数据
            if (storeMoveSon.getId()==null){
                storeMoveSon.setRowid(++maxRowId);
                storeMoveSon.setCreateuser(userName);
                storeMoveSon.setCreatedate(new Date());
                storeMoveSon.setTaskcode(storeMove.getTaskcode());
                insertSons.add(storeMoveSon);
//                //保存堆垛机、RGV数据
//                Stacker stacker=new Stacker(storeMoveSon.getRowid(),storeMove.getTasktype(),storeMove.getTaskcode(),storeMoveSon.getEndcode(),"0",storeMove.getCreateuser(),new Date());
//                Rgv rgv=new Rgv(storeMoveSon.getRowid(),storeMove.getTasktype(),storeMove.getTaskcode(),"0",storeMove.getCreateuser(),new Date());
//                //拆分入库子表，生成任务数据堆垛机、RGV，根据数量生成相对应的条数
//                for (int i=0;i<storeMoveSon.getQuantity();i++){
//                    insertStacker.add(stacker);
//                    insertRgv.add(rgv);
//                }
            }else{
                storeMoveSon.setRowid(++maxRowId);
                storeMoveSon.setCreateuser(userName);
                storeMoveSon.setCreatedate(new Date());
                storeMoveSon.setTaskcode(storeMove.getTaskcode());
                insertSons.add(storeMoveSon);
            }
        }

        //删除入库子表
        if (info.getStoreMoveSonList().size()>0) {
            storeMoveSonMapper.deleteStoreMoveSonByIdList(info.getStoreMoveSonList());
        }

        //新增入库子表
        if (insertSons.size()>0) {
            storeMoveSonMapper.insertStoreMoveSonList(insertSons);
        }

        //修改入库子表
        if (updateSons.size()>0) {
            storeMoveSonMapper.updateStoreMoveSonList(updateSons);
        }



        //操作堆垛机、RGV任务表
        //新增任务表
//        if (insertStacker.size()>0) {
//            stackerMapper.insertStackerList(insertStacker);
//        }
//        if (insertRgv.size()>0) {
//            rgvMapper.insertRgvList(insertRgv);
//        }
//
//        //删除任务表，即删除与子表数量不对应的任务数据
//        if (deleteStacker.size()>0) {
//            stackerMapper.deleteStackerByList(deleteStacker);
//        }
//        if (deleteRgv.size()>0) {
//            rgvMapper.deleteRgvByList(deleteRgv);
//        }
//
//        //根据子表数据删除任务表,即删除子表一条数据对应拆分的全部任务
//        if (info.getStoreMoveSonList().size()>0) {
//            stackerMapper.deleteStackerByStoreMoveSon(info.getStoreMoveSonList(),userName);
//            rgvMapper.deleteRgvByStoreMoveSon(info.getStoreMoveSonList(),userName);
//        }



        //修改主表内容
        return storeMoveMapper.updateStoreMove(storeMove);
    }

    /**
     * 删除移库主对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteStoreMoveByIds(String ids)
    {
        //修改子表内容
        storeMoveSonMapper.deleteStoreMoveSonByTaskCodes(Convert.toStrArray(ids));

        //修改任务表内容
//        stackerMapper.deleteStackerByTaskCodes(Convert.toStrArray(ids));
//        rgvMapper.deleteRgvByTaskCodes(Convert.toStrArray(ids));

        //修改主表内容
        return storeMoveMapper.deleteStoreMoveByIds(Convert.toStrArray(ids));
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
        storeMoveSonMapper.cancelStoreMoveSonByTaskCodes(Convert.toStrArray(taskcodes));

        //修改任务表任务状态为已取消
        stackerMapper.cancelStackerByTaskCodes(Convert.toStrArray(taskcodes));
        rgvMapper.cancelRgvByTaskCodes(Convert.toStrArray(taskcodes));

        //修改主表内容任务状态为已取消
        return storeMoveMapper.cancelStoreMoveByTaskCodes(Convert.toStrArray(taskcodes));
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
        storeMoveSonMapper.restartStoreMoveSonByTaskCodes(Convert.toStrArray(taskcodes));

        //修改任务表任务状态为未执行
        stackerMapper.restartStackerByTaskCodes(Convert.toStrArray(taskcodes));
        rgvMapper.restartRgvByTaskCodes(Convert.toStrArray(taskcodes));

        //修改主表内容任务状态为未执行
        return storeMoveMapper.restartStoreMoveByTaskCodes(Convert.toStrArray(taskcodes));
    }



    /**
     * 删除移库主信息
     * 
     * @param tasktype 移库主ID
     * @return 结果
     */
    public int deleteStoreMoveById(String tasktype)
    {
        return storeMoveMapper.deleteStoreMoveById(tasktype);
    }

    @Override
    public List<Storehouse> selectByStoreHouse(Storehouse storehouse) {
        return storehouseMapper.selectByStoreHouse(storehouse);
    }

    /**
     * 导出模板
     * @param storeMoveModel
     * @return
     */
    @Override
    public AjaxResult exportStoreModel(String storeMoveModel) {

//        String[] fields={"仓库名称","任务名称","任务描述","制单人","备注","初始库位+产品编号+产品名称+产品型号+产品批次","数量","目标库位","产品备注"};
        String[] fields={"任务名称","任务描述","制单人","备注","初始库位","产品型号","目标库位"};
//        List<StoreMove> warehouseAndStockList=warehouseMapper.selectWarehouseAndStoreStock();
//        List<String> storehouseList=storehouseMapper.selectStorehouseModelList();
//        String[] shString=new String[storehouseList.size()];
//        storehouseList.toArray(shString);
//        List<List<String>> productList=new ArrayList<>();
//        List<String> warehouseList=new ArrayList<>();
//        for (StoreMove storeMove: warehouseAndStockList){
//            warehouseList.add(storeMove.getWhname());
//            List<String> stringList=new ArrayList<>();
//            stringList.add(storeMove.getWhname());
//            for (Stock stock:storeMove.getStockList()){
//                stringList.add(stock.getProductname());
//            }
//            productList.add(stringList);
//        }

        ExcelUtil<StoreMove> util = new ExcelUtil<StoreMove>(StoreMove.class);
        return util.exportStoreMoveModel(fields,storeMoveModel);
    }


//    @Override
//    @Transactional(rollbackFor = Exception.class)
//    public AjaxResult importExcel(MultipartFile file, String taskType) throws IOException {
//        int rowcount=0;
//
//        //初始化入库表、入库子表、任务表
//        List<StoreMove> storeMoveList=new ArrayList<>();
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
//        String[] fields={"仓库名称","任务名称","任务描述","制单人","备注","初始库位+产品编号+产品名称+产品型号+产品批次","数量","目标库位","产品备注"};
//        Row titleRow=sheet.getRow(0);
//        for (int i=0;i<fields.length;i++){
//            if (!fields[i].equals(StringUtils.getString(titleRow.getCell(i)).trim())){
//                return AjaxResult.error("模板内容与导入模板不同，请重新生成模板填充内容！");
//            }
//        }
//
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
//            String maker= StringUtils.getString(row.getCell(3)).trim();//制单人
//            String remark= StringUtils.getString(row.getCell(4)).trim();//备注
//            String[] productinfo= StringUtils.getString(row.getCell(5)).trim().split("\\+");//产品信息
//            String quantity= StringUtils.getString(row.getCell(6)).trim();//产品数量
//            String[] WScode= StringUtils.getString(row.getCell(7)).trim().split("\\+");//仓库+库位
//            String productmemo= StringUtils.getString(row.getCell(8)).trim();//产品备注
//
//            if (Whcode[0].length()!=2 && taskname=="" && productdetail=="" && maker=="" && remark=="" && productinfo.length!=5 && quantity=="" && WScode.length!=2 && productmemo==""){
//                if (rowindex==1){
//                   return AjaxResult.error("表格内容为空！");
//                }else{
//                    break;
//                }
//
//            }
//
//
//            for (SysDictData model : base_product_model){
//                if (productinfo[3].equals(model.getDictLabel())){
//                    productinfo[3] = model.getDictValue();
//                    break;
//                }
//            }
//
//            //任务名称
//            if (StringUtils.isEmpty(taskname)){
//                return AjaxResult.error("第"+(rowindex+1)+"行，【任务名称】不能为空!");
//            }
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
//            if ((long) Double.parseDouble(quantity)<=0){
//                return AjaxResult.error("第"+(rowindex+1)+"行，【产品数量】不能为0！");
//            }
//
//            //校验仓库和产品是否正确
//            if (Whcode.length!=2){
//                return AjaxResult.error("第"+(rowindex+1)+"行，【仓库名称】格式错误!");
//            }
//            if (productinfo.length!=5){
//                return AjaxResult.error("第"+(rowindex+1)+"行，【初始库位+产品编号+产品名称+产品型号+产品批次】格式错误!");
//            }
//            if (WScode.length!=4){
//                return AjaxResult.error("第"+(rowindex+1)+"行，【目标库位】格式错误!");
//            }
//
//            Warehouse warehouse=warehouseMapper.selectWarehouseById(Whcode[0]);
//
//            Storehouse storehouse=storehouseMapper.selectByWhAndStore(WScode[0],WScode[2]);
//            if (warehouse==null){
//                return AjaxResult.error("第"+(rowindex+1)+"行，【仓库名称】错误，请检查或重新生成模板!");
//            }
//
//            if (storehouse==null){
//                return AjaxResult.error("第"+(rowindex+1)+"行，【目标库位】错误，请检查或重新生成模板!");
//            }
//            Stock stock=stockMapper.selectProductByStoreStock(new Stock(productinfo[0],productinfo[1],productinfo[2],productinfo[4],productinfo[3],Whcode[0]));
//            if (stock==null){
//                return AjaxResult.error("第"+(rowindex+1)+"行，【初始库位+产品编号+产品名称+产品型号+产品批次】库存不存在，请检查或重新生成模板!");
//            }else{
//                if ((long) Double.parseDouble(quantity)>stock.getQuantity()){
//                    return AjaxResult.error("第"+(rowindex+1)+"行，【初始库位+产品编号+产品名称+产品型号+产品批次】库存不足，请检查或重新生成模板!");
//                }
//            }
//            //初始化任务子表集合
//            List<StoreMoveSon> storeMoveSonList=new ArrayList<>();
//            if (storeMoveList.size()>0){
//                StoreMove storeMove=null;
//                //循环校验是否存在任务单
//                for (StoreMove info:storeMoveList){
//                    //如果任务名称+制单人+仓库编码相同时表示已存在任务单
//                    if (info.getTaskname().equals(taskname) && info.getMaker().equals(maker) && info.getWhcode().equals(Whcode[0])){
//                        storeMove=info;
//                        break;
//                    }
//                }
//                //如果任务单为空，表示不存在
//                if (storeMove==null){
//                    storeMove=new StoreMove(taskType, ToolsUtils.getOrdersId("SME"),taskname,productdetail,maker,warehouse.getWhcode(),warehouse.getWhname(),(long) Double.parseDouble(quantity),remark, ShiroUtils.getSysUser().getUserName(),new Date());
//                    storeMoveSonList.add(new StoreMoveSon(1L,storeMove.getTaskcode(),storeMove.getTaskname(),storeMove.getWhcode(),stock.getStorehousecode(),stock.getProductcode(),stock.getProductname(),stock.getProductmodel(),(long) Double.parseDouble(quantity),storehouse.getStorehousecode(),productmemo,ShiroUtils.getSysUser().getUserName(),new Date(),storehouse.getStorehousename(),warehouse.getWhname()));
//                    storeMove.setStoreMoveSonList(storeMoveSonList);
//                    storeMoveList.add(storeMove);
//                }else{
//                    boolean isPresence=false;
//                    //任务单已存在，循环校验任务单是否存在该产品，
//                    for (StoreMoveSon storeMoveSon:storeMove.getStoreMoveSonList()){
//                        //存在即将任务总数量、产品数量增加
//                        if (storeMoveSon.getProductcode().equals(stock.getProductcode())){
//                            storeMove.setQuantity(storeMove.getQuantity()+(long) Double.parseDouble(quantity));
//                            storeMoveSon.setQuantity(storeMoveSon.getQuantity()+(long) Double.parseDouble(quantity));
//                            isPresence=true;
//                            break;
//                        }
//                    }
//                    //isPresence为false时，表示产品不存在，增加任务单产品总数量，新增任务子表
//                    if (!isPresence){
//                        storeMove.setQuantity(storeMove.getQuantity()+(long) Double.parseDouble(quantity));
//                        storeMoveSonList=storeMove.getStoreMoveSonList();
//                        storeMoveSonList.add(new StoreMoveSon(storeMove.getStoreMoveSonList().size()+1L,storeMove.getTaskcode(),storeMove.getTaskname(),storeMove.getWhcode(),stock.getStorehousecode(),stock.getProductcode(),stock.getProductname(),stock.getProductmodel(),(long) Double.parseDouble(quantity),storehouse.getStorehousecode(),productmemo,ShiroUtils.getSysUser().getUserName(),new Date(),storehouse.getStorehousename(),warehouse.getWhname()));
//                        storeMove.setStoreMoveSonList(storeMoveSonList);
//                    }
//                }
//            }else{
//                //即导入第一行
//
//                StoreMove storeMove=new StoreMove(taskType, ToolsUtils.getOrdersId("SME"),taskname,productdetail,maker,warehouse.getWhcode(),warehouse.getWhname(),(long) Double.parseDouble(quantity),remark, ShiroUtils.getSysUser().getUserName(),new Date());
//                storeMoveSonList.add(new StoreMoveSon(1L,storeMove.getTaskcode(),storeMove.getTaskname(),storeMove.getWhcode(),stock.getStorehousecode(),stock.getProductcode(),stock.getProductname(),stock.getProductmodel(),(long) Double.parseDouble(quantity),storehouse.getStorehousecode(),productmemo,ShiroUtils.getSysUser().getUserName(),new Date(),storehouse.getStorehousename(),warehouse.getWhname()));
//                storeMove.setStoreMoveSonList(storeMoveSonList);
//                storeMoveList.add(storeMove);
//            }
//
//        }
//
//        //初始化任务子表集合
//        List<StoreMoveSon> storeMoveSonList=new ArrayList<>();
//        for (StoreMove storeMove:storeMoveList){
//            for (StoreMoveSon  storeMoveSon:storeMove.getStoreMoveSonList()){
//                storeMoveSonList.add(storeMoveSon);
//                //保存堆垛机、RGV数据
//                Stacker stacker=new Stacker(storeMoveSon.getRowid(),storeMove.getTasktype(),storeMove.getTaskcode(),storeMoveSon.getEndcode(),"0",storeMove.getCreateuser(),new Date());
//                Rgv rgv=new Rgv(storeMoveSon.getRowid(),storeMove.getTasktype(),storeMove.getTaskcode(),"0",storeMove.getCreateuser(),new Date());
//                //拆分入库子表，生成任务数据堆垛机、RGV，根据数量生成相对应的条数
//                for (int i=0;i<storeMoveSon.getQuantity();i++){
//                    stackerList.add(stacker);
//                    rgvList.add(rgv);
//                }
//
//            }
//        }
//        if (storeMoveList.size()>0) {
//            //新增入库主表
//            storeMoveMapper.insertStoreMoveList(storeMoveList);
//        }
//        if (storeMoveSonList.size()>0) {
//            //批量入库子表
//            storeMoveSonMapper.insertStoreMoveSonList(storeMoveSonList);
//        }
//        if (stackerList.size()>0) {
//            //新增堆垛机、RGV任务表
//            stackerMapper.insertStackerList(stackerList);
//        }
//        if (rgvList.size()>0) {
//            rgvMapper.insertRgvList(rgvList);
//        }
//        return AjaxResult.success();
//    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public AjaxResult importExcel(MultipartFile file, String taskType) throws IOException {
        int rowcount=0;

        //初始化入库表、入库子表、任务表
        List<StoreMove> storeMoveList=new ArrayList<>();

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

        String[] fields={"任务名称","任务描述","制单人","备注","初始库位","产品型号","目标库位"};
        Row titleRow=sheet.getRow(0);
        for (int i=0;i<fields.length;i++){
            if (!fields[i].equals(StringUtils.getString(titleRow.getCell(i)).trim())){
                return AjaxResult.error("模板内容与导入模板不同，请重新生成模板填充内容！");
            }
        }

        for(int rowindex=1;rowindex<rowcount;rowindex++){
            Row row=sheet.getRow(rowindex);
            if (row==null){
                break;
            }
            String Whcode= "WH0004";//仓库编码
            String taskname= StringUtils.getString(row.getCell(0)).trim();//任务名称
            String productdetail= StringUtils.getString(row.getCell(1)).trim();//任务描述
            String maker= StringUtils.getString(row.getCell(2)).trim();//制单人
            String remark= StringUtils.getString(row.getCell(3)).trim();//备注
            String startKW= StringUtils.getString(row.getCell(4)).trim();//初始库位
            String productcode= StringUtils.getString(row.getCell(5)).trim();//产品型号
            String endKW= StringUtils.getString(row.getCell(6)).trim();//目的库位

            if (startKW=="" && endKW==""){
                if (rowindex==1){
                    return AjaxResult.error("表格内容为空！");
                }else{
                    break;
                }
            }

            //任务名称
            if (StringUtils.isEmpty(taskname)){
                return AjaxResult.error("第"+(rowindex+1)+"行，【任务名称】不能为空!");
            }
            //起始库位
            if (StringUtils.isEmpty(startKW)){
                return AjaxResult.error("第"+(rowindex+1)+"行，【起始库位】不能为空!");
            }
            //目的库位
            if (StringUtils.isEmpty(endKW)){
                return AjaxResult.error("第"+(rowindex+1)+"行，【目的库位】不能为空!");
            }

            Warehouse warehouse=warehouseMapper.selectWarehouseById(Whcode);
            Storehouse storehouse=storehouseMapper.selectByWhAndStore(Whcode,endKW);
            Product product=productMapper.selectProductByProId(productcode);
            System.out.println("11111111111111111");
            System.out.println(warehouse.getWhcode());
            System.out.println(warehouse.getWhname());
            System.out.println("2222222222222222222");
            System.out.println(storehouse.getStorehousecode());
            System.out.println(storehouse.getStorehousename());
            System.out.println("3333333333333333333333");
            System.out.println(product.getProductcode());
            System.out.println(product.getProductname());
            if (product==null){
                return AjaxResult.error("第"+(rowindex+1)+"行，【产品型号】错误，请检查或重新生成模板!");
            }
            if (warehouse==null){
                return AjaxResult.error("第"+(rowindex+1)+"行，【仓库名称】错误，请检查或重新生成模板!");
            }

            if (storehouse==null){
                return AjaxResult.error("第"+(rowindex+1)+"行，【目标库位】错误，请检查或重新生成模板!");
            }
            //初始化任务子表集合
            List<StoreMoveSon> storeMoveSonList=new ArrayList<>();
            if (storeMoveList.size()>0){
                StoreMove storeMove=null;
                //循环校验是否存在任务单
                for (StoreMove info:storeMoveList){
                    //如果任务名称+制单人+仓库编码相同时表示已存在任务单
                    if (info.getTaskname().equals(taskname)){
                        storeMove=info;
                        break;
                    }
                }
                if (storeMove==null){
                    storeMove=new StoreMove(taskType, ToolsUtils.getOrdersId("SME"),taskname,productdetail,maker,warehouse.getWhcode(),warehouse.getWhname(),remark, ShiroUtils.getSysUser().getUserName(),new Date());
                    storeMoveSonList.add(new StoreMoveSon(1L,storeMove.getTaskcode(),storeMove.getTaskname(),storeMove.getWhcode(),startKW,product.getProductcode(),product.getProductname(),endKW,ShiroUtils.getSysUser().getUserName(),new Date(),storehouse.getStorehousename(),warehouse.getWhname()));
                    storeMove.setStoreMoveSonList(storeMoveSonList);
                    storeMoveList.add(storeMove);
                }else{
                    storeMoveSonList=storeMove.getStoreMoveSonList();
                    storeMoveSonList.add(new StoreMoveSon(storeMove.getStoreMoveSonList().size()+1L,storeMove.getTaskcode(),storeMove.getTaskname(),storeMove.getWhcode(),startKW,product.getProductcode(),product.getProductname(),endKW,ShiroUtils.getSysUser().getUserName(),new Date(),storehouse.getStorehousename(),warehouse.getWhname()));
                    storeMove.setStoreMoveSonList(storeMoveSonList);
                }

            }else{
                //即导入第一行
                StoreMove storeMove=new StoreMove(taskType, ToolsUtils.getOrdersId("SME"),taskname,productdetail,maker,warehouse.getWhcode(),warehouse.getWhname(),remark, ShiroUtils.getSysUser().getUserName(),new Date());
                storeMoveSonList.add(new StoreMoveSon(1L,storeMove.getTaskcode(),storeMove.getTaskname(),storeMove.getWhcode(),startKW,product.getProductcode(),product.getProductname(),endKW,ShiroUtils.getSysUser().getUserName(),new Date(),storehouse.getStorehousename(),warehouse.getWhname()));
                storeMove.setStoreMoveSonList(storeMoveSonList);
                storeMoveList.add(storeMove);
            }

        }

        //初始化任务子表集合
        List<StoreMoveSon> storeMoveSonList=new ArrayList<>();
        for (StoreMove storeMove:storeMoveList){
            for (StoreMoveSon  storeMoveSon:storeMove.getStoreMoveSonList()){
                storeMoveSonList.add(storeMoveSon);
                //保存堆垛机、RGV数据
                Stacker stacker=new Stacker(storeMoveSon.getRowid(),storeMove.getTasktype(),storeMove.getTaskcode(),storeMoveSon.getEndcode(),"0",storeMove.getCreateuser(),new Date());
                Rgv rgv=new Rgv(storeMoveSon.getRowid(),storeMove.getTasktype(),storeMove.getTaskcode(),"0",storeMove.getCreateuser(),new Date());
                //拆分入库子表，生成任务数据堆垛机、RGV，根据数量生成相对应的条数
//                for (int i=0;i<storeMoveSon.getQuantity();i++){
//                    stackerList.add(stacker);
//                    rgvList.add(rgv);
//                }

            }
        }
        if (storeMoveList.size()>0) {
            //新增入库主表
            storeMoveMapper.insertStoreMoveList(storeMoveList);
        }
        if (storeMoveSonList.size()>0) {
            //批量入库子表
            storeMoveSonMapper.insertStoreMoveSonList(storeMoveSonList);
        }
        if (stackerList.size()>0) {
            //新增堆垛机、RGV任务表
            stackerMapper.insertStackerList(stackerList);
        }
        if (rgvList.size()>0) {
            rgvMapper.insertRgvList(rgvList);
        }
        return AjaxResult.success();
    }

    @Override
    public List<StoreMoveExcel> selectStoreMoveExcelList(StoreMove storeMove) {
        return storeMoveMapper.selectStoreMoveExcelList(storeMove);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int execute(String taskcodes) {
        //修改任务表任务状态为已下发
        storeMoveSonMapper.executeStoreMoveSonByTaskCodes(Convert.toStrArray(taskcodes));
        //修改任务表任务状态为已下发
        stackerMapper.executeStackerByTaskCodes(Convert.toStrArray(taskcodes));
        //修改RGV任务状态为已下发
        rgvMapper.executeRgvByTaskCodes(Convert.toStrArray(taskcodes));
        //修改主表内容任务状态为已下发
        return storeMoveMapper.executeStoreMoveByTaskCodes(Convert.toStrArray(taskcodes));
    }

}
