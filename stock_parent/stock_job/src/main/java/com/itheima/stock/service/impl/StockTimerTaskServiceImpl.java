package com.itheima.stock.service.impl;

import com.google.common.collect.Lists;
import com.itheima.stock.mapper.StockBusinessMapper;
import com.itheima.stock.mapper.StockMarketIndexInfoMapper;
import com.itheima.stock.mapper.StockRtInfoMapper;
import com.itheima.stock.pojo.entity.StockMarketIndexInfo;
import com.itheima.stock.pojo.entity.StockRtInfo;
import com.itheima.stock.pojo.vo.StockInfoConfig;
import com.itheima.stock.service.StockTimerTaskService;
import com.itheima.stock.utils.DateTimeUtil;
import com.itheima.stock.utils.IdWorker;
import com.itheima.stock.utils.ParseType;
import com.itheima.stock.utils.ParserStockInfoUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
@Slf4j
public class StockTimerTaskServiceImpl implements StockTimerTaskService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private StockInfoConfig stockInfoConfig;

    @Autowired
    private IdWorker idWorker;

    @Autowired
    private StockMarketIndexInfoMapper stockMarketIndexInfoMapper;

    @Autowired
    private ParserStockInfoUtil parserStockInfoUtil;

    @Autowired
    private StockBusinessMapper stockBusinessMapper;

    @Autowired
    private StockRtInfoMapper stockRtInfoMapper;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Override
    public void getInnerMarketInfo() {
        //1.定义采集的url接口
        String url = stockInfoConfig.getMarketUrl() + String.join(",", stockInfoConfig.getInner());
        //2.调用restTemplate采集数据
        //2.1 组装请求头
        HttpHeaders headers = new HttpHeaders();
        //必须填写，否则数据采集不到
        headers.add("Referer", "https://finance.sina.com.cn/stock/");
        headers.add("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/72.0.3626.121 Safari/537.36");
        //2.2 组装请求对象
        HttpEntity<Object> entity = new HttpEntity<>(headers);
        //2.3 resetTemplate发起请求
        String resString = restTemplate.postForObject(url, entity, String.class);
        //log.info("当前采集的数据：{}",resString);
        //3.数据解析（重要）
//        var hq_str_sh000001="上证指数,3267.8103,3283.4261,3236.6951,3290.2561,3236.4791,0,0,402626660,398081845473,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2022-04-07,15:01:09,00,";
//        var hq_str_sz399001="深证成指,12101.371,12172.911,11972.023,12205.097,11971.334,0.000,0.000,47857870369,524892592190.995,0,0.000,0,0.000,0,0.000,0,0.000,0,0.000,0,0.000,0,0.000,0,0.000,0,0.000,0,0.000,2022-04-07,15:00:03,00";
        String reg = "var hq_str_(.+)=\"(.+)\";";
        //编译表达式,获取编译对象
        Pattern pattern = Pattern.compile(reg);
        //匹配字符串
        Matcher matcher = pattern.matcher(resString);
        ArrayList<StockMarketIndexInfo> list = new ArrayList<>();
        //判断是否有匹配的数值
        while (matcher.find()) {
            //获取大盘的code
            String marketCode = matcher.group(1);
            //获取其它信息，字符串以逗号间隔
            String otherInfo = matcher.group(2);
            //以逗号切割字符串，形成数组
            String[] splitArr = otherInfo.split(",");
            //大盘名称
            String marketName = splitArr[0];
            //获取当前大盘的开盘点数
            BigDecimal openPoint = new BigDecimal(splitArr[1]);
            //前收盘点
            BigDecimal preClosePoint = new BigDecimal(splitArr[2]);
            //获取大盘的当前点数
            BigDecimal curPoint = new BigDecimal(splitArr[3]);
            //获取大盘最高点
            BigDecimal maxPoint = new BigDecimal(splitArr[4]);
            //获取大盘的最低点
            BigDecimal minPoint = new BigDecimal(splitArr[5]);
            //获取成交量
            Long tradeAmt = Long.valueOf(splitArr[8]);
            //获取成交金额
            BigDecimal tradeVol = new BigDecimal(splitArr[9]);
            //时间
            Date curTime = DateTimeUtil.getDateTimeWithoutSecond(splitArr[30] + " " + splitArr[31]).toDate();
            //组装entity对象
            StockMarketIndexInfo info = StockMarketIndexInfo.builder()
                    .id(idWorker.nextId())
                    .marketCode(marketCode)
                    .marketName(marketName)
                    .curPoint(curPoint)
                    .openPoint(openPoint)
                    .preClosePoint(preClosePoint)
                    .maxPoint(maxPoint)
                    .minPoint(minPoint)
                    .tradeVolume(tradeVol)
                    .tradeAmount(tradeAmt)
                    .curTime(curTime)
                    .build();
            //收集封装的对象，方便批量插入
            list.add(info);
        }
        log.info("采集的当前大盘数据：{}", list);
        //批量插入
        if (CollectionUtils.isEmpty(list)) {
            return;
        }
        //TODO 后续完成批量插入功能
        int count = this.stockMarketIndexInfoMapper.insertBatch(list);
        log.info("批量插入了：{}条数据", count);
        //解析的数据批量插入数据库
        //通知后台终端刷新本地缓存，发送的日期数据是告知对方当前更新的股票数据所在时间点
        rabbitTemplate.convertAndSend("stockExchange","inner.market",new Date());
    }

    @Override
    public void getStockRtIndex() {
        //批量获取股票ID集合
        List<String> allCodes = stockBusinessMapper.getAllStockCodes();
        allCodes = allCodes.stream().map(code -> !code.startsWith("6") ? "sh" + code : "sz" + code).collect(Collectors.toList());
        Lists.partition(allCodes, 15).forEach(codes -> {
            String url = stockInfoConfig.getMarketUrl() + String.join(",", codes);
            HttpHeaders headers = new HttpHeaders();
            //必须填写，否则数据采集不到
            headers.add("Referer", "https://finance.sina.com.cn/stock/");
            headers.add("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/72.0.3626.121 Safari/537.36");
            //2.2 组装请求对象
            HttpEntity<Object> httpEntity = new HttpEntity<>(headers);
            ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.GET, httpEntity, String.class);
            int statusCodeValue = responseEntity.getStatusCodeValue();
            if (statusCodeValue == 200) {
                log.error("采集数据失败");
                return;
            }
            String jsData = responseEntity.getBody();
            List<StockRtInfo> list = parserStockInfoUtil.parser4StockOrMarketInfo(jsData, ParseType.ASHARE);
            log.info("采集的数据：{}", list.size());
            int count = stockRtInfoMapper.insertBatch(list);
            log.info("批量插入了：{}条数据", count);
        });
    }
}
