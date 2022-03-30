package com.wms.base.tray.mapper;

import com.wms.base.tray.domain.TrayEx;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TrayExMapper {
    List<TrayEx> selectTrayPage(TrayEx trayEx);

    Integer selectTrayTotalCount();
}
