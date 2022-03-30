package com.wms.base.warehouse.mapper;

import com.wms.base.warehouse.domain.WarehouseEx;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WarehouseExMapper {
    List<WarehouseEx> selectWarehousePage(WarehouseEx warehouseEx);

    Integer selectWarehouseTotalCount();
}
