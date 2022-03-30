package com.wms.base.tray.service.impl;

import com.wms.base.tray.domain.Tray;
import com.wms.base.tray.mapper.TrayMapper;
import com.wms.common.core.text.Convert;
import com.wms.base.tray.service.ITrayService;
import com.wms.common.utils.ToolsUtils;
import org.apache.shiro.util.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

/**
 * 托盘Service业务层处理
 * 
 * @author dkwms
 * @date 2020-01-07
 */
@Service
public class TrayServiceImpl implements ITrayService
{
    @Autowired
    private TrayMapper trayMapper;

    /**
     * 查询托盘
     * 
     * @param id 托盘ID
     * @return 托盘
     */
    @Override
    public Tray selectTrayById(Long id)
    {
        return trayMapper.selectTrayById(id);
    }

    /**
     * 查询托盘列表
     * 
     * @param tray 托盘
     * @return 托盘
     */
    @Override
    public List<Tray> selectTrayList(Tray tray)
    {
        return trayMapper.selectTrayList(tray);
    }

    /**
     * 新增托盘
     *  托盘编码格式  TC + 0001，依次叠加
     * @param tray 托盘
     * @return 结果
     */
    @Override
    public int insertTray(Tray tray)
    {
        String trayCOde = trayMapper.selectLastTrayCode();
        String newCode = trayCOde.substring(2);
        // 设置托盘编码
        tray.setTraycode(ToolsUtils.getNewDeviceNo("TC" , newCode));
        return trayMapper.insertTray(tray);
    }

    /**
     * 修改托盘
     * 
     * @param tray 托盘
     * @return 结果
     */
    @Override
    public int updateTray(Tray tray)
    {
        return trayMapper.updateTray(tray);
    }

    /**
     * 删除托盘对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteTrayByIds(String ids)
    {
        return trayMapper.deleteTrayByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除托盘信息
     * 
     * @param id 托盘ID
     * @return 结果
     */
    public int deleteTrayById(Long id)
    {
        return trayMapper.deleteTrayById(id);
    }


    /**
     * 查询所有的托盘
     * @param trayCode
     * @return
     */
    @Override
    public List<Tray> selectAllTray(String trayCode) {
        List<Tray> trayList = trayMapper.selectTrayList(new Tray());
        if (!trayList.isEmpty()) {
            for (Tray tray : trayList) {
                if (tray.getTraycode().equals(trayCode)) {
                    tray.setFlag(true);
                }
            }
        }

        return trayList;
    }

    @Override
    public String selectTrayByStoreHouseCode(String storeHouseCode) {
        Tray tray = new Tray();
        tray.setStorehousecode(storeHouseCode);
        List<Tray> trayList = trayMapper.selectTrayList(tray);
        if (CollectionUtils.isEmpty(trayList)) {
            return new String();
        }
        String string = "";
        for (Tray tray2 : trayList) {
            string += tray2.getTraycode() + ",";
        }
        if (string.length() > 0) {
            string = string.substring(0,string.length() - 1);
        }
        return string;
    }
}
