package com.wms.base.storehouse.mapper;

import com.wms.base.storehouse.domain.StorehouseEx;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StorehouseExMapper {
    List<StorehouseEx> selectStorehousePage(StorehouseEx storehouseEx);

    Integer selectStorehouseTotalCount();
}
