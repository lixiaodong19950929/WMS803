package com.wms.kanban.domain;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UseCase {

    // 库存数量
    private Integer stockQuantity = 0 ;

    //出库数量
    private Integer outQuantity = 0;

    // 入库数量
    private Integer intoQuantity = 0;

    //使用率,(出库数量 / (出库数量 + 库存数量)*100)%
    private String usageRate = "0";

    // 任务作业未开始的数量
    private Integer notStartQuantity = 0;

    // 任务作业未正在执行的数量
    private Integer runningQuantity = 0;

    // 任务作业未已经完成的数量
    private Integer finishedQuantity = 0;

    // 移库数量
    private Integer moveQuantity = 0;

    // 出入库数量 ,饼图
    private String stockOutPieData;

    public Integer getStockQuantity() {
        return stockQuantity;
    }

    public void setStockQuantity(Integer stockQuantity) {
        this.stockQuantity = stockQuantity;
    }

    public Integer getOutQuantity() {
        return outQuantity;
    }

    public void setOutQuantity(Integer outQuantity) {
        this.outQuantity = outQuantity;
    }

    public Integer getIntoQuantity() {
        return intoQuantity;
    }

    public void setIntoQuantity(Integer intoQuantity) {
        this.intoQuantity = intoQuantity;
    }

    public String getUsageRate() {
        return usageRate;
    }

    public void setUsageRate(String usageRate) {
        this.usageRate = usageRate;
    }

    public Integer getNotStartQuantity() {
        return notStartQuantity;
    }

    public void setNotStartQuantity(Integer notStartQuantity) {
        this.notStartQuantity = notStartQuantity;
    }

    public Integer getRunningQuantity() {
        return runningQuantity;
    }

    public void setRunningQuantity(Integer runningQuantity) {
        this.runningQuantity = runningQuantity;
    }

    public Integer getFinishedQuantity() {
        return finishedQuantity;
    }

    public void setFinishedQuantity(Integer finishedQuantity) {
        this.finishedQuantity = finishedQuantity;
    }

    public Integer getMoveQuantity() {
        return moveQuantity;
    }

    public void setMoveQuantity(Integer moveQuantity) {
        this.moveQuantity = moveQuantity;
    }

    public String getStockOutPieData() {
        return stockOutPieData;
    }

    public void setStockOutPieData(String stockOutPieData) {
        this.stockOutPieData = stockOutPieData;
    }
}
