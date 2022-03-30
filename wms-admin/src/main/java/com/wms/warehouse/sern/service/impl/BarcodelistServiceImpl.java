package com.wms.warehouse.sern.service.impl;

import com.wms.warehouse.sern.domain.Barcodelist;
import com.wms.warehouse.sern.mapper.BarcodelistMapper;
import com.wms.warehouse.sern.service.BarcodelistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BarcodelistServiceImpl implements BarcodelistService {
    @Autowired
    private BarcodelistMapper barcodelistMapper;

    @Override
    public List<Barcodelist> BarcodeList() {
        return barcodelistMapper.BarcodeList();
    }

    @Override
    public int insertBardCodeList(Barcodelist barcodelist) {
        return barcodelistMapper.insertBardCodeList(barcodelist);
    }

    @Override
    public int insertBardCodeLists(List<Barcodelist> barcodelists) {
//        barcodelistMapper.deleteBarcodeList(barcodelist.getRowId(),barcodelist.getTaskCode());
        if (barcodelists.size()>0){
            deleteBarcodeList(barcodelists.get(0).getRowId(),barcodelists.get(0).getTaskCode());
            System.out.println(barcodelists.get(0).getSern());
            //如果sern是空 说明 带过来的数据 只是包含 行号和任务号，用来删除
            if(barcodelists.get(0).getSern().isEmpty()){
                return 1;
            }
            return barcodelistMapper.insertBardCodeLists(barcodelists);
        }
        return 0;
    }

    //出库选择条码完插入barcodelist
    @Override
    public int insertBardCodeLists_out_select_Barcode(List<Barcodelist> barcodelists) {
//        barcodelistMapper.deleteBarcodeList(barcodelist.getRowId(),barcodelist.getTaskCode());
        if (barcodelists.size()>0){
            System.out.println(barcodelists.get(0).getTaskCode());
            deleteBarcodeList(barcodelists.get(0).getRowId(),barcodelists.get(0).getTaskCode());
            //如果sern是空 说明 带过来的数据 只是包含 行号和任务号，用来删除
            if(barcodelists.get(0).getSern().isEmpty()){
                return 1;
            }
            System.out.println(barcodelists.get(0).getXhao());
            System.out.println(barcodelists.get(0).getBatch());
            return barcodelistMapper.insertBardCodeLists_out_select_Barcode(barcodelists);
        }
        return 0;
    }

    @Override
    public List<Barcodelist> selectProduct(Long rowid, String taskcode) {
        return barcodelistMapper.selectProduct(rowid, taskcode);
    }

    @Override
    public List<Barcodelist> selectProductByTaskCode(String taskcode) {
        System.out.println(taskcode);
        return barcodelistMapper.selectProductByTaskCode(taskcode);
    }

    @Override
    public int deleteBarcodeListId(Long id) {
        return barcodelistMapper.deleteBarcodeListId(id);
    }

    @Override
    public List<Barcodelist> deleteBarcodeList(Long rowid, String taskcode) {
        return barcodelistMapper.deleteBarcodeList(rowid,taskcode);
    }
}
