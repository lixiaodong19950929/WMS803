package com.wms.base.region.mapper;

import com.wms.base.region.domain.RegionEx;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RegionExMapper {
    List<RegionEx> selectRegionPage(RegionEx regionEx);

    Integer selectRegionTotalCount();
}
