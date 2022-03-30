package com.wms.warehouse.storeio.service;

import com.wms.common.core.domain.AjaxResult;
import com.wms.warehouse.storeio.domain.StoreIo;
import com.wms.warehouse.storeio.domain.StoreIoExcel;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * 出入库主Service接口
 * 
 * @author assassin
 * @date 2019-12-30
 */
public interface IStoreIoService 
{
    /**
     * 查询出入库主
     * 
     * @param taskcode 出入库主ID
     * @return 出入库主
     */
    public StoreIo selectStoreIoById(String taskcode);

    /**
     * 查询出入库主列表
     * 
     * @param storeIo 出入库主
     * @return 出入库主集合
     */
    public List<StoreIo> selectStoreIoList(StoreIo storeIo);

    /**
     * 新增出入库主
     * 
     * @param storeIo 出入库主
     * @return 结果
     */
    public int insertStoreIo(StoreIo storeIo);


    /**
     * 批量删除出入库主
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteStoreIoByIds(String ids);

    /**
     * 删除出入库主信息
     * 
     * @param tasktype 出入库主ID
     * @return 结果
     */
    public int deleteStoreIoById(String tasktype);

    int updateStoreIo(StoreIo storeIo, String userName);

    int cancel(String taskcodes);

    int execute(String taskcodes);

    int restart(String taskcodes);

    AjaxResult exportStoreInModel(String sheetName) throws IOException;

    AjaxResult exportStoreOutModel(String sheetName) throws IOException;

    AjaxResult importInExcel(MultipartFile file, String taskType) throws IOException;

    List<StoreIoExcel> selectStoreIoExcelList(StoreIo storeIo);

    AjaxResult importOutExcel(MultipartFile file, String s) throws IOException;
}
