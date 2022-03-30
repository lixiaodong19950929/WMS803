package com.wms.base.device.mapper;

import com.wms.base.device.domain.DeviceEx;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DeviceExMapper {
    List<DeviceEx> selectDevicePage(DeviceEx deviceEx);

    Integer selectDeviceTotalCount();
}
