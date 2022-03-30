package com.wms.base.device.service.impl;

import com.wms.base.device.domain.Device;
import com.wms.base.device.mapper.DeviceMapper;
import com.wms.base.device.service.IDeviceService;
import com.wms.common.constant.BaseConstants;
import com.wms.common.core.text.Convert;
import com.wms.common.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 设备Service业务层处理
 * 
 * @author dkwms
 * @date 2020-01-08
 */
@Service
public class DeviceServiceImpl implements IDeviceService
{
    @Autowired
    private DeviceMapper deviceMapper;

    /**
     * 查询设备
     * 
     * @param id 设备ID
     * @return 设备
     */
    @Override
    public Device selectDeviceById(String id)
    {
        return deviceMapper.selectDeviceById(id);
    }

    /**
     * 查询设备列表
     * 
     * @param device 设备
     * @return 设备
     */
    @Override
    public List<Device> selectDeviceList(Device device)
    {
        return deviceMapper.selectDeviceList(device);
    }

    /**
     * 新增设备
     * 
     * @param device 设备
     * @return 结果
     */
    @Override
    public int insertDevice(Device device)
    {
        return deviceMapper.insertDevice(device);
    }

    /**
     * 修改设备
     * 
     * @param device 设备
     * @return 结果
     */
    @Override
    public int updateDevice(Device device)
    {
        return deviceMapper.updateDevice(device);
    }

    /**
     * 删除设备对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteDeviceByIds(String ids)
    {
        return deviceMapper.deleteDeviceByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除设备信息
     * 
     * @param devicecode 设备ID
     * @return 结果
     */
    public int deleteDeviceById(String devicecode)
    {
        return deviceMapper.deleteDeviceById(devicecode);
    }

    @Override
    public String checkDeviceCode(Device device) {
        Long userId = StringUtils.isNull(device.getId()) ? -1L : device.getId();
        Device info= deviceMapper.checkDeviceCode(device.getDevicecode());
        if (StringUtils.isNotNull(info) && info.getId().longValue() != userId.longValue()){
            return BaseConstants.DEVICE_DEVICECODE_NOT_UNIQUE;
        }
        return BaseConstants.DEVICE_DEVICECODE_UNIQUE;
    }
}
