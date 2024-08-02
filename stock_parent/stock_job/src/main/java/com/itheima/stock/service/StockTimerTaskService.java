package com.itheima.stock.service;

import java.util.List;

public interface StockTimerTaskService {
    void getInnerMarketInfo();
    /**
     * 定义获取分钟级股票数据
     */
    void getStockRtIndex();
}
