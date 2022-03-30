package com.wms.common.pagebean;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class PageBean<T> {

    /**当前页*/
    private int currentPage;

    /** 每页显示的条数*/
    @Builder.Default
    private int pageSizes = 6;

    /** 总页数*/
    private int totalPage;

    /** 总记录数*/
    private int totalCount;

    /** 每页显示的数据*/
    private List<T> lists;

    private List<T> result;
}
