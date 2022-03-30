package com.wms.warehouse.storecheck.service.impl;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import com.wms.base.product.domain.Product;
import com.wms.base.product.mapper.ProductMapper;
import com.wms.base.storehouse.domain.Storehouse;
import com.wms.base.warehouse.domain.Warehouse;
import com.wms.base.warehouse.mapper.WarehouseMapper;
import com.wms.common.core.domain.AjaxResult;
import com.wms.common.utils.StringUtils;
import com.wms.common.utils.ToolsUtils;
import com.wms.common.utils.poi.ExcelUtil;
import com.wms.framework.util.ShiroUtils;
import com.wms.framework.web.service.DictService;
import com.wms.infoquery.querystockstatus.domain.Stock;
import com.wms.infoquery.querystockstatus.mapper.StockMapper;
import com.wms.system.domain.SysDictData;
import com.wms.warehouse.storecheck.domain.CheckResult;
import com.wms.warehouse.storecheck.domain.StoreCheckExcel;
import com.wms.warehouse.storecheck.domain.StoreCheckSon;
import com.wms.warehouse.storecheck.mapper.StoreCheckSonMapper;
import com.wms.warehouse.storeio.domain.Rgv;
import com.wms.warehouse.storeio.domain.Stacker;
import com.wms.warehouse.storeio.mapper.RgvMapper;
import com.wms.warehouse.storeio.mapper.StackerMapper;
import com.wms.warehouse.storemove.domain.StoreMove;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.wms.warehouse.storecheck.mapper.StoreCheckMapper;
import com.wms.warehouse.storecheck.domain.StoreCheck;
import com.wms.warehouse.storecheck.service.IStoreCheckService;
import com.wms.common.core.text.Convert;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

/**
 * 盘点主Service业务层处理
 * 
 * @author assassin
 * @date 2020-02-10
 */
@Service
public class StoreCheckServiceImpl implements IStoreCheckService 
{
    @Autowired
    private StoreCheckMapper storeCheckMapper;

    @Autowired
    private StoreCheckSonMapper storeCheckSonMapper;

    @Autowired
    private StackerMapper stackerMapper;

    @Autowired
    private RgvMapper rgvMapper;

    @Autowired
    private WarehouseMapper warehouseMapper;

    @Autowired
    private StockMapper stockMapper;

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private DictService dictService;

    /**
     * 查询盘点主
     * 
     * @param taskcode 盘点主ID
     * @return 盘点主
     */
    @Override
    public StoreCheck selectStoreCheckById(String taskcode)
    {
        return storeCheckMapper.selectStoreCheckById(taskcode);
    }

    /**
     * 查询盘点主列表
     * 
     * @param storeCheck 盘点主
     * @return 盘点主
     */
    @Override
    public List<StoreCheck> selectStoreCheckList(StoreCheck storeCheck)
    {
        return storeCheckMapper.selectStoreCheckList(storeCheck);
    }

    /**
     * 新增盘点主
     * 
     * @param storeCheck 盘点主
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int insertStoreCheck(StoreCheck storeCheck)
    {
        //初始化堆垛机、RGV任务表
        List<Stacker> stackerList=new ArrayList<>();
        List<Rgv> rgvList=new ArrayList<>();



        //将入库表内容写入入库子表
        for (StoreCheckSon storeCheckSon:storeCheck.getStoreCheckSonList()){
            storeCheckSon.setCreateuser(storeCheck.getCreateuser());
            storeCheckSon.setCreatedate(new Date());
            storeCheckSon.setTaskcode(storeCheck.getTaskcode());
            storeCheckSon.setWhcode(storeCheck.getWhcode());

        }

        //批量入库子表
        storeCheckSonMapper.insertStoreCheckSonList(storeCheck.getStoreCheckSonList());
//        //将入库子表写入任务表中
//        for (StoreCheckSon storeCheckSon:storeCheck.getStoreCheckSonList()){
//            //保存堆垛机、RGV数据
//            Stacker stacker=new Stacker(storeCheckSon.getRowid(),storeCheck.getTasktype(),storeCheck.getTaskcode(),storeCheckSon.getStorehousecode(),"0",storeCheck.getCreateuser(),new Date());
//            Rgv rgv=new Rgv(storeCheckSon.getRowid(),storeCheck.getTasktype(),storeCheck.getTaskcode(),"0",storeCheck.getCreateuser(),new Date());
//            //拆分入库子表，生成任务数据堆垛机、RGV，根据数量生成相对应的条数
//            for (int i=0;i<storeCheckSon.getQuantity();i++){
//                stackerList.add(stacker);
//                rgvList.add(rgv);
//            }
//
//        }
//        //新增堆垛机、RGV任务表
//        stackerMapper.insertStackerList(stackerList);
//        rgvMapper.insertRgvList(rgvList);
        //新增入库主表
        return storeCheckMapper.insertStoreCheck(storeCheck);
    }

    /**
     * 修改盘点主
     * 
     * @param storeCheck 盘点主
     * @param userName
     * @return 结果
     */
//    @Override
//    @Transactional(rollbackFor = Exception.class)
//    public int updateStoreCheck(StoreCheck storeCheck, String userName)
//    {
//        //初始化新增子表内容
//        List<StoreCheckSon> insertSons=new ArrayList<>();
//
//        //初始化修改子表内容
//        List<StoreCheckSon> updateSons=new ArrayList<>();
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
//        StoreCheck info=storeCheckMapper.selectStoreCheckByIdAndList(storeCheck);
//        if (!info.getTaskstate().equals("0")){
//            return 0;
//        }
//        long maxRowId=info.getMaxRowId();
//        //根据子表有无id进行更新或修改的判断
//        for (StoreCheckSon storeCheckSon:storeCheck.getStoreCheckSonList()){
//            //id不存在为新增数据
//            if (storeCheckSon.getId()==null){
//                storeCheckSon.setRowid(++maxRowId);
//                storeCheckSon.setCreateuser(userName);
//                storeCheckSon.setCreatedate(new Date());
//                storeCheckSon.setTaskcode(storeCheck.getTaskcode());
//                insertSons.add(storeCheckSon);
////                //保存堆垛机、RGV数据
////                Stacker stacker=new Stacker(storeCheckSon.getRowid(),storeCheck.getTasktype(),storeCheck.getTaskcode(),storeCheckSon.getStorehousecode(),"0",userName,new Date());
////                Rgv rgv=new Rgv(storeCheckSon.getRowid(),storeCheck.getTasktype(),storeCheck.getTaskcode(),"0",userName,new Date());
////                //拆分入库子表，生成任务数据堆垛机、RGV，根据数量生成相对应的条数
////                for (int i=0;i<storeCheckSon.getQuantity();i++){
////                    insertStacker.add(stacker);
////                    insertRgv.add(rgv);
////                }
//            }else{
//                Iterator iterator =info.getStoreCheckSonList().iterator();
//                //id存在是，根据是否修改元数据来进行修改和删除判断
//                while (iterator.hasNext()){
//                    StoreCheckSon checkSon= (StoreCheckSon) iterator.next();
//
//                    //id相同，证明此数据为修改或未修改数据，都可以表示为修改数据
//                    if (storeCheckSon.getId()==checkSon.getId()){
//                        updateSons.add(storeCheckSon);
//
////                        //修改后数量大于原数量
////                        if (storeCheckSon.getQuantity()>checkSon.getQuantity()) {
////                            for (int i = 0; i < Long.hashCode(storeCheckSon.getQuantity() - checkSon.getQuantity()); i++) {
////                                //新增RGV、堆垛机任务
////                                Stacker stacker=new Stacker(checkSon.getRowid(),storeCheck.getTasktype(),storeCheck.getTaskcode(),checkSon.getStorehousecode(),"0",userName,new Date());
////                                Rgv rgv=new Rgv(checkSon.getRowid(),storeCheck.getTasktype(),storeCheck.getTaskcode(),"0",userName,new Date());
////                                insertStacker.add(stacker);
////                                insertRgv.add(rgv);
////                            }
////                        }else if(storeCheckSon.getQuantity()<checkSon.getQuantity()){
////                            //修改后数量小于原数量，删除任务表内容
////                            //删除RGV、堆垛机任务
////                            Stacker stacker=new Stacker(checkSon.getRowid(),storeCheck.getTaskcode(),userName,new Date(),checkSon.getQuantity() - storeCheckSon.getQuantity());
////                            Rgv rgv=new Rgv(checkSon.getRowid(),storeCheck.getTaskcode(),userName,new Date(),checkSon.getQuantity() - storeCheckSon.getQuantity());
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
//            storeCheckSonMapper.insertStoreCheckSonList(insertSons);
//        }
//
//        //修改入库子表
//        if (updateSons.size()>0) {
//            storeCheckSonMapper.updateStoreCheckSonList(updateSons);
//        }
//
//        //删除入库子表
//        if (info.getStoreCheckSonList().size()>0) {
//            storeCheckSonMapper.deleteStoreCheckSonByIdList(info.getStoreCheckSonList());
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
////        if (info.getStoreCheckSonList().size()>0) {
////            stackerMapper.deleteStackerByStoreCheckSon(info.getStoreCheckSonList(),userName);
////            rgvMapper.deleteRgvByStoreCheckSon(info.getStoreCheckSonList(),userName);
////        }
//
//
//
//        //修改主表内容
//        return storeCheckMapper.updateStoreCheck(storeCheck);
//    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateStoreCheck(StoreCheck storeCheck, String userName)
    {
        //初始化新增子表内容
        List<StoreCheckSon> insertSons=new ArrayList<>();

        //初始化修改子表内容
        List<StoreCheckSon> updateSons=new ArrayList<>();

        //初始化新增任务表内容
        List<Rgv>  insertRgv=new ArrayList<>();
        List<Stacker> insertStacker=new ArrayList<>();

        //初始化删除任务表内容
        List<Rgv>  deleteRgv=new ArrayList<>();
        List<Stacker> deleteStacker=new ArrayList<>();

        //判断入库任务状态，验证是否需要修改内容
        StoreCheck info=storeCheckMapper.selectStoreCheckByIdAndList(storeCheck);
//        if (!info.getTaskstate().equals("0")){
//            return 0;
//        }
        long maxRowId=info.getMaxRowId();
        //根据子表有无id进行更新或修改的判断
        for (StoreCheckSon storeCheckSon:storeCheck.getStoreCheckSonList()){
            //id不存在为新增数据
            if (storeCheckSon.getId()==null){
                storeCheckSon.setRowid(++maxRowId);
                storeCheckSon.setCreateuser(userName);
                storeCheckSon.setCreatedate(new Date());
                storeCheckSon.setTaskcode(storeCheck.getTaskcode());
                insertSons.add(storeCheckSon);
            }else{
                storeCheckSon.setRowid(++maxRowId);
                storeCheckSon.setCreateuser(userName);
                storeCheckSon.setCreatedate(new Date());
                storeCheckSon.setTaskcode(storeCheck.getTaskcode());
                insertSons.add(storeCheckSon);
            }
        }

        //删除入库子表
        if (info.getStoreCheckSonList().size()>0) {
            storeCheckSonMapper.deleteStoreCheckSonByIdList(info.getStoreCheckSonList());
        }

        //新增入库子表
        if (insertSons.size()>0) {
            storeCheckSonMapper.insertStoreCheckSonList(insertSons);
        }

        //修改入库子表
        if (updateSons.size()>0) {
            storeCheckSonMapper.updateStoreCheckSonList(updateSons);
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
//        if (info.getStoreCheckSonList().size()>0) {
//            stackerMapper.deleteStackerByStoreCheckSon(info.getStoreCheckSonList(),userName);
//            rgvMapper.deleteRgvByStoreCheckSon(info.getStoreCheckSonList(),userName);
//        }



        //修改主表内容
        return storeCheckMapper.updateStoreCheck(storeCheck);
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
//        storeCheckSonMapper.cancelStoreCheckSonByTaskCodes(Convert.toStrArray(taskcodes));

        //修改任务表任务状态为已取消
        stackerMapper.cancelStackerByTaskCodes(Convert.toStrArray(taskcodes));
        rgvMapper.cancelRgvByTaskCodes(Convert.toStrArray(taskcodes));

        //修改主表内容任务状态为已取消
        return storeCheckMapper.cancelStoreCheckByTaskCodes(Convert.toStrArray(taskcodes));
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
//        storeCheckSonMapper.restartStoreCheckSonByTaskCodes(Convert.toStrArray(taskcodes));

        //修改任务表任务状态为未执行
        stackerMapper.restartStackerByTaskCodes(Convert.toStrArray(taskcodes));
        rgvMapper.restartRgvByTaskCodes(Convert.toStrArray(taskcodes));

        //修改主表内容任务状态为未执行
        return storeCheckMapper.restartStoreCheckByTaskCodes(Convert.toStrArray(taskcodes));
    }

//    @Override
//    @Transactional(rollbackFor = Exception.class)
//    public AjaxResult importExcel(MultipartFile file,String taskType) throws IOException {
//        int rowcount=0;
//
//        //初始化入库表、入库子表、任务表
//        List<StoreCheck> storeCheckList=new ArrayList<>();
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
//        String[] fields={"仓库名称","任务名称","任务描述","制单人","备注","初始库位+产品编号+产品名称+产品型号+产品批次","数量","产品备注"};
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
//            String productmemo= StringUtils.getString(row.getCell(7)).trim();//产品备注
//
//            if (Whcode[0]=="" && taskname=="" && productdetail=="" && maker=="" && remark=="" && productinfo[0]=="" && quantity=="" && productmemo==""){
//                if (rowindex==1){
//                    return AjaxResult.error("表格内容为空！");
//                }else{
//                    break;
//                }
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
//            if (quantity.equals("0.0")){
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
//
//            Warehouse warehouse=warehouseMapper.selectWarehouseById(Whcode[0]);
//
//            if (warehouse==null){
//                return AjaxResult.error("第"+(rowindex+1)+"行，【仓库名称】错误，请检查或重新生成模板!");
//            }
//
//            Stock stock=stockMapper.selectProductByStoreStock(new Stock(productinfo[0],productinfo[1],productinfo[2],productinfo[4],productinfo[3],Whcode[0]));
//            if (stock==null){
//                return AjaxResult.error("第"+(rowindex+1)+"行，【初始库位+产品编号+产品名称+产品型号+产品批次】库存不存在，请检查或重新生成模板!");
//            }else{
//                if ((long) Double.parseDouble(quantity)>stock.getQuantity()){
//                    return AjaxResult.error("第"+(rowindex+1)+"行，【初始库位+产品编号+产品名称+产品型号+产品批次】库存不足，请检查或重新生成模板!");
//                }
//            }
//            //初始化任务子表集合
//            List<StoreCheckSon> storeCheckSonList=new ArrayList<>();
//            if (storeCheckList.size()>0){
//                StoreCheck storeCheck=null;
//                //循环校验是否存在任务单
//                for (StoreCheck info:storeCheckList){
//                    //如果任务名称+制单人+仓库编码相同时表示已存在任务单
//                    if (info.getTaskname().equals(taskname) && info.getMaker().equals(maker) && info.getWhcode().equals(Whcode[0])){
//                        storeCheck=info;
//                        break;
//                    }
//                }
//                //如果任务单为空，表示不存在
//                if (storeCheck==null){
//                    storeCheck=new StoreCheck(taskType, ToolsUtils.getOrdersId("SCC"),taskname,productdetail,maker,warehouse.getWhcode(),(long) Double.parseDouble(quantity),remark, ShiroUtils.getSysUser().getUserName(),new Date());
//                    storeCheckSonList.add(new StoreCheckSon(1L,storeCheck.getTaskcode(),storeCheck.getTaskname(),storeCheck.getWhcode(),stock.getRegioncode(),stock.getStorehousecode(),stock.getProductcode(),stock.getProductname(),stock.getProductmodel(),(long) Double.parseDouble(quantity),productmemo,ShiroUtils.getSysUser().getUserName(),new Date()));
//                    storeCheck.setStoreCheckSonList(storeCheckSonList);
//                    storeCheckList.add(storeCheck);
//                }else{
//                    boolean isPresence=false;
//                    //任务单已存在，循环校验任务单是否存在该产品，
//                    for (StoreCheckSon storeCheckSon:storeCheck.getStoreCheckSonList()){
//                        //存在即将任务总数量、产品数量增加
//                        if (storeCheckSon.getProductcode().equals(stock.getProductcode())){
//                            storeCheck.setQuantity(storeCheck.getQuantity()+(long) Double.parseDouble(quantity));
//                            storeCheckSon.setQuantity(storeCheckSon.getQuantity()+(long) Double.parseDouble(quantity));
//                            isPresence=true;
//                            break;
//                        }
//                    }
//                    //isPresence为false时，表示产品不存在，增加任务单产品总数量，新增任务子表
//                    if (!isPresence){
//                        storeCheck.setQuantity(storeCheck.getQuantity()+(long) Double.parseDouble(quantity));
////                        storeIo.getStoreIoSonList().add(new StoreIoSonEx(storeIo.getStoreIoSonList().size()+1L,storeIo.getTaskcode(),product.getProductcode(),product.getProductname(),product.getProductmodel(),Long.valueOf(quantity),productmemo,ShiroUtils.getSysUser().getUserName(),new Date()))
//                        storeCheckSonList=storeCheck.getStoreCheckSonList();
//                        storeCheckSonList.add(new StoreCheckSon(storeCheck.getStoreCheckSonList().size()+1L,storeCheck.getTaskcode(),storeCheck.getTaskname(),storeCheck.getWhcode(),stock.getRegioncode(),stock.getStorehousecode(),stock.getProductcode(),stock.getProductname(),stock.getProductmodel(),(long) Double.parseDouble(quantity),productmemo,ShiroUtils.getSysUser().getUserName(),new Date()));
//                        storeCheck.setStoreCheckSonList(storeCheckSonList);
//                    }
//                }
//            }else{
//                //即导入第一行
//
//                StoreCheck storeCheck=new StoreCheck(taskType, ToolsUtils.getOrdersId("SCC"),taskname,productdetail,maker,warehouse.getWhcode(),(long) Double.parseDouble(quantity),remark, ShiroUtils.getSysUser().getUserName(),new Date());
//                storeCheckSonList.add(new StoreCheckSon(1L,storeCheck.getTaskcode(),storeCheck.getTaskname(),storeCheck.getWhcode(),stock.getRegioncode(),stock.getStorehousecode(),stock.getProductcode(),stock.getProductname(),stock.getProductmodel(),(long) Double.parseDouble(quantity),productmemo,ShiroUtils.getSysUser().getUserName(),new Date()));
//                storeCheck.setStoreCheckSonList(storeCheckSonList);
//                storeCheckList.add(storeCheck);
//            }
//
//        }
//
//        //初始化任务子表集合
//        List<StoreCheckSon> storeCheckSonList=new ArrayList<>();
//        for (StoreCheck storeCheck:storeCheckList){
//            for (StoreCheckSon  storeCheckSon:storeCheck.getStoreCheckSonList()){
//                storeCheckSonList.add(storeCheckSon);
//                //保存堆垛机、RGV数据
//                Stacker stacker=new Stacker(storeCheckSon.getRowid(),storeCheck.getTasktype(),storeCheck.getTaskcode(),storeCheckSon.getStorehousecode(),"0",storeCheck.getCreateuser(),new Date());
//                Rgv rgv=new Rgv(storeCheckSon.getRowid(),storeCheck.getTasktype(),storeCheck.getTaskcode(),"0",storeCheck.getCreateuser(),new Date());
//                //拆分入库子表，生成任务数据堆垛机、RGV，根据数量生成相对应的条数
//                for (int i=0;i<storeCheckSon.getQuantity();i++){
//                    stackerList.add(stacker);
//                    rgvList.add(rgv);
//                }
//
//            }
//        }
//        if (storeCheckList.size()>0){
//            //新增入库主表
//            storeCheckMapper.insertStoreCheckList(storeCheckList);
//        }
//        if (storeCheckSonList.size()>0){
//            //批量入库子表
//            storeCheckSonMapper.insertStoreCheckSonList(storeCheckSonList);
//        }
//        if (stackerList.size()>0){
//            //新增堆垛机、RGV任务表
//            stackerMapper.insertStackerList(stackerList);
//        }
//        if (rgvList.size()>0){
//            rgvMapper.insertRgvList(rgvList);
//        }
//
//        return AjaxResult.success();
//    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public AjaxResult importExcel(MultipartFile file,String taskType) throws IOException {
        int rowcount=0;

        //初始化入库表、入库子表、任务表
        List<StoreCheck> storeCheckList=new ArrayList<>();

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

        String[] fields={"任务名称","任务描述","制单人","备注","盘点库位","产品型号","数量","产品备注"};
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
            String storehousecode= StringUtils.getString(row.getCell(4)).trim();//库位
            String productcode= StringUtils.getString(row.getCell(5)).trim();//产品型号
            String quantity= StringUtils.getString(row.getCell(6)).trim();//产品数量
            String productmemo= StringUtils.getString(row.getCell(7)).trim();//产品备注
            if(StringUtils.isNotEmpty(productmemo)) {
                if (row.getCell(7).getCellType() == Cell.CELL_TYPE_NUMERIC) {
                    DecimalFormat df = new DecimalFormat("#");// 转换成整型
                    productmemo = df.format(row.getCell(7).getNumericCellValue());
                } else {
                    productmemo = StringUtils.getString(row.getCell(7)).trim();
                }
            }

            if (taskname=="" && storehousecode=="" && quantity=="" && productmemo==""){
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

            Warehouse warehouse=warehouseMapper.selectWarehouseById(Whcode);
            Product product=productMapper.selectProductByProId(productcode);

            if (warehouse==null){
                return AjaxResult.error("第"+(rowindex+1)+"行，【仓库名称】错误，请检查或重新生成模板!");
            }
            if (product==null){
                return AjaxResult.error("第"+(rowindex+1)+"行，【产品型号】错误，请检查或重新生成模板!");
            }

            //初始化任务子表集合
            List<StoreCheckSon> storeCheckSonList=new ArrayList<>();
            if (storeCheckList.size()>0){
                StoreCheck storeCheck=null;
                //循环校验是否存在任务单
                for (StoreCheck info:storeCheckList){
                    //如果任务名称+制单人+仓库编码相同时表示已存在任务单
                    if (info.getTaskname().equals(taskname) && info.getMaker().equals(maker)){
                        storeCheck=info;
                        break;
                    }
                }
                //如果任务单为空，表示不存在
                if (storeCheck==null){
                    storeCheck=new StoreCheck(taskType, ToolsUtils.getOrdersId("SCC"),taskname,productdetail,maker,warehouse.getWhcode(),(long) Double.parseDouble(quantity),remark, ShiroUtils.getSysUser().getUserName(),new Date());
                    storeCheckSonList.add(new StoreCheckSon(1L,storeCheck.getTaskcode(),storeCheck.getTaskname(),storeCheck.getWhcode(),storehousecode,product.getProductcode(),product.getProductname(),(long) Double.parseDouble(quantity),productmemo,ShiroUtils.getSysUser().getUserName(),new Date()));
                    storeCheck.setStoreCheckSonList(storeCheckSonList);
                    storeCheckList.add(storeCheck);
                }else{
                    boolean isPresence=false;
                    //任务单已存在，循环校验任务单是否存在该产品，
                    for (StoreCheckSon storeCheckSon:storeCheck.getStoreCheckSonList()){
                        //存在即将任务总数量、产品数量增加
                        if (storeCheckSon.getProductcode().equals(product.getProductcode())){
                            storeCheck.setQuantity(storeCheck.getQuantity()+(long) Double.parseDouble(quantity));
                            storeCheckSon.setQuantity(storeCheckSon.getQuantity()+(long) Double.parseDouble(quantity));
                            isPresence=true;
                            break;
                        }
                    }
                    //isPresence为false时，表示产品不存在，增加任务单产品总数量，新增任务子表
                    if (!isPresence){
                        storeCheck.setQuantity(storeCheck.getQuantity()+(long) Double.parseDouble(quantity));
//                        storeIo.getStoreIoSonList().add(new StoreIoSonEx(storeIo.getStoreIoSonList().size()+1L,storeIo.getTaskcode(),product.getProductcode(),product.getProductname(),product.getProductmodel(),Long.valueOf(quantity),productmemo,ShiroUtils.getSysUser().getUserName(),new Date()))
                        storeCheckSonList=storeCheck.getStoreCheckSonList();
                        storeCheckSonList.add(new StoreCheckSon(storeCheck.getStoreCheckSonList().size()+1L,storeCheck.getTaskcode(),storeCheck.getTaskname(),storeCheck.getWhcode(),storehousecode,product.getProductcode(),product.getProductname(),(long) Double.parseDouble(quantity),productmemo,ShiroUtils.getSysUser().getUserName(),new Date()));
                        storeCheck.setStoreCheckSonList(storeCheckSonList);
                    }
                }
            }else{
                //即导入第一行
                StoreCheck storeCheck=new StoreCheck(taskType, ToolsUtils.getOrdersId("SCC"),taskname,productdetail,maker,warehouse.getWhcode(),(long) Double.parseDouble(quantity),remark, ShiroUtils.getSysUser().getUserName(),new Date());
                storeCheckSonList.add(new StoreCheckSon(1L,storeCheck.getTaskcode(),storeCheck.getTaskname(),storeCheck.getWhcode(),storehousecode,product.getProductcode(),product.getProductname(),(long) Double.parseDouble(quantity),productmemo,ShiroUtils.getSysUser().getUserName(),new Date()));
                storeCheck.setStoreCheckSonList(storeCheckSonList);
                storeCheckList.add(storeCheck);
            }

        }

        //初始化任务子表集合
        List<StoreCheckSon> storeCheckSonList=new ArrayList<>();
        for (StoreCheck storeCheck:storeCheckList){
            for (StoreCheckSon  storeCheckSon:storeCheck.getStoreCheckSonList()){
                storeCheckSonList.add(storeCheckSon);
                //保存堆垛机、RGV数据
                Stacker stacker=new Stacker(storeCheckSon.getRowid(),storeCheck.getTasktype(),storeCheck.getTaskcode(),storeCheckSon.getStorehousecode(),"0",storeCheck.getCreateuser(),new Date());
                Rgv rgv=new Rgv(storeCheckSon.getRowid(),storeCheck.getTasktype(),storeCheck.getTaskcode(),"0",storeCheck.getCreateuser(),new Date());
                //拆分入库子表，生成任务数据堆垛机、RGV，根据数量生成相对应的条数
                for (int i=0;i<storeCheckSon.getQuantity();i++){
                    stackerList.add(stacker);
                    rgvList.add(rgv);
                }

            }
        }
        if (storeCheckList.size()>0){
            //新增入库主表
            storeCheckMapper.insertStoreCheckList(storeCheckList);
        }
        if (storeCheckSonList.size()>0){
            //批量入库子表
            storeCheckSonMapper.insertStoreCheckSonList(storeCheckSonList);
        }
        if (stackerList.size()>0){
            //新增堆垛机、RGV任务表
            stackerMapper.insertStackerList(stackerList);
        }
        if (rgvList.size()>0){
            rgvMapper.insertRgvList(rgvList);
        }

        return AjaxResult.success();
    }

    @Override
    public AjaxResult exportStoreModel(String storeCheckModel) {
        String[] fields={"任务名称","任务描述","制单人","备注","盘点库位","产品型号","数量","产品备注"};
//        List<StoreMove> warehouseAndStockList=warehouseMapper.selectWarehouseAndStoreStock();
//        List<List<String>> productList=new ArrayList<>();
//        List<String> warehouseList=new ArrayList<>();
//        for (StoreMove storeCheck: warehouseAndStockList){
//            warehouseList.add(storeCheck.getWhname());
//            List<String> stringList=new ArrayList<>();
//            stringList.add(storeCheck.getWhname());
//            for (Stock stock:storeCheck.getStockList()){
//                stringList.add(stock.getProductname());
//            }
//            productList.add(stringList);
//        }

        ExcelUtil<StoreCheck> util = new ExcelUtil<StoreCheck>(StoreCheck.class);
        return util.exportStoreCheckModel(fields,storeCheckModel);
    }
//    public AjaxResult exportStoreModel(String storeCheckModel) {
//        String[] fields={"仓库名称","任务名称","任务描述","制单人","备注","初始库位+产品编号+产品名称+产品型号+产品批次","数量","产品备注"};
//        List<StoreMove> warehouseAndStockList=warehouseMapper.selectWarehouseAndStoreStock();
//        List<List<String>> productList=new ArrayList<>();
//        List<String> warehouseList=new ArrayList<>();
//        for (StoreMove storeCheck: warehouseAndStockList){
//            warehouseList.add(storeCheck.getWhname());
//            List<String> stringList=new ArrayList<>();
//            stringList.add(storeCheck.getWhname());
//            for (Stock stock:storeCheck.getStockList()){
//                stringList.add(stock.getProductname());
//            }
//            productList.add(stringList);
//        }
//
//        ExcelUtil<StoreCheck> util = new ExcelUtil<StoreCheck>(StoreCheck.class);
//        return util.exportStoreCheckModel(fields,productList,warehouseList,storeCheckModel);
//    }

    @Override
    public List<StoreCheckExcel> selectStoreCheckAndSonList(StoreCheck storeCheck) {
        return storeCheckMapper.selectStoreCheckAndSonList(storeCheck);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int execute(String taskcodes) {
        //设置子表任务状态为已下发
        storeCheckSonMapper.executeStoreCheckSonByTaskCodes(Convert.toStrArray(taskcodes));
        //修改任务表任务状态为已下发
        stackerMapper.executeStackerByTaskCodes(Convert.toStrArray(taskcodes));
        rgvMapper.executeRgvByTaskCodes(Convert.toStrArray(taskcodes));
        //修改主表内容任务状态为下发
        return storeCheckMapper.executeStoreCheckByTaskCodes(Convert.toStrArray(taskcodes));
    }

    /**
     * 删除盘点主对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteStoreCheckByIds(String ids)
    {
        //修改子表内容
        storeCheckSonMapper.deleteStoreCheckSonByTaskCodes(Convert.toStrArray(ids));

        //修改任务表内容
        stackerMapper.deleteStackerByTaskCodes(Convert.toStrArray(ids));
        rgvMapper.deleteRgvByTaskCodes(Convert.toStrArray(ids));

        //修改主表内容
        return storeCheckMapper.deleteStoreCheckByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除盘点主信息
     * 
     * @param tasktype 盘点主ID
     * @return 结果
     */
    public int deleteStoreCheckById(String tasktype)
    {
        return storeCheckMapper.deleteStoreCheckById(tasktype);
    }

    /**
     * 查询盘点结果
     *
     * @param taskcode 盘点主ID
     * @return 盘点主
     */
    @Override
    public List<CheckResult> selectCheckResultList(String taskcode)
    {
        return storeCheckMapper.selectCheckResultList(taskcode);
    }
}
