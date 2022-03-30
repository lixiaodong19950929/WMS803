package com.wms.base.device.mapper;

import com.wms.base.device.domain.Device;
import java.util.List;

/**
 * 设备Mapper接口
 * 
 * @author dkwms
 * @date 2020-01-08
 */
public interface DeviceMapper
{
    /**
     * 查询设备
     * 
     * @param id 设备ID
     * @return 设备
     */
    public Device selectDeviceById(String id);

    /**
     * 查询设备列表
     * 
     * @param device 设备
     * @return 设备集合
     */
    public List<Device> selectDeviceList(Device device);

    /**
     * 新增设备
     * 
     * @param device 设备
     * @return 结果
     */
    public int insertDevice(Device device);

    /**
     * 修改设备
     * 
     * @param device 设备
     * @return 结果
     */
    public int updateDevice(Device device);

    /**
     * 删除设备
     * 
     * @param id 设备ID
     * @return 结果
     */
    public int deleteDeviceById(String id);

    /**
     * 批量删除设备
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteDeviceByIds(String[] ids);

    Device checkDeviceCode(String devicecode);
}
