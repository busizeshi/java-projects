package com.itheima.stock;

import com.itheima.stock.mapper.StockBusinessMapper;
import com.itheima.stock.service.StockTimerTaskService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class TestStockTimerService {

    @Autowired
    private StockTimerTaskService stockTimerService;

    @Autowired
    private StockBusinessMapper stockBusinessMapper;

    /**
     * 获取大盘数据
     */
    @Test
    public void test01(){
        stockTimerService.getInnerMarketInfo();
    }

    /**
     * 获取A股数据
     */
    @Test
    public void test02(){
        stockTimerService.getStockRtIndex();
    }
}
