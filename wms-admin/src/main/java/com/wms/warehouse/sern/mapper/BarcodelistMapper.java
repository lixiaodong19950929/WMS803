package com.wms.warehouse.sern.mapper;

import com.wms.warehouse.sern.domain.Barcodelist;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface BarcodelistMapper {
    //条码明细表查询
    public List<Barcodelist> BarcodeList();

    //新增条码明细
    public int insertBardCodeList(Barcodelist barcodelist);

    public int insertBardCodeLists(List<Barcodelist> barcodelists);

    public int insertBardCodeLists_out_select_Barcode(List<Barcodelist> barcodelists);

    //根据行号和条码查询条码明细表中已经存入的条码
    public List<Barcodelist> selectProduct(@Param("rowId") Long rowid, @Param("taskCode")String taskcode);

    //根据任务号查询条码明细表中已经存入的条码
    public List<Barcodelist> selectProductByTaskCode(@Param("taskcode")String taskcode);

    public int deleteBarcodeListId(@Param("rowid")Long id);

    public List<Barcodelist> deleteBarcodeList(@Param("rowid") Long rowid, @Param("taskcode")String taskcode);
}
