package com.itheima.stock.mapper;

import com.itheima.stock.pojo.domain.Stock4EvrDayDomain;
import com.itheima.stock.pojo.domain.Stock4MinuteDomain;
import com.itheima.stock.pojo.domain.StockUpdownDomain;
import com.itheima.stock.pojo.entity.StockRtInfo;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
* @author jiawenduo
* @description 针对表【stock_rt_info(个股详情信息表)】的数据库操作Mapper
* @createDate 2024-08-02 20:27:20
* @Entity com.itheima.stock.pojo.entity.StockRtInfo
*/
public interface StockRtInfoMapper {

    int deleteByPrimaryKey(Long id);

    int insert(StockRtInfo record);

    int insertSelective(StockRtInfo record);

    StockRtInfo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(StockRtInfo record);

    int updateByPrimaryKey(StockRtInfo record);

    List<StockUpdownDomain> getNewestStockInfo(Date curDate);

    List<Map> getStockUpdownCount(@Param("startDate") Date openTime,@Param("endDate") Date curTime,@Param("flag") int flag);

    /**
     * 统计指定时间点下，各个涨跌区间内股票的个数
     * @param avlDate
     * @return
     */
    List<Map> stockUpDownScopeCount(@Param("avlDate") Date avlDate);

    /**
     * 根据时间范围查询指定股票的交易流水
     * @param stockCode 股票code
     * @param startTime 起始时间
     * @param endTime 终止时间
     * @return
     */
    List<Stock4MinuteDomain> getStockInfoByCodeAndDate(@Param("stockCode") String stockCode,
                                                       @Param("startTime") Date startTime,
                                                       @Param("endTime") Date endTime);

    List<Stock4EvrDayDomain> getStock4DkLine(@Param("stockCode")String stockCode,@Param("startDate") Date startDate,@Param("endDate") Date endDate);

    /**
     * 批量插入功能
     * @param stockRtInfoList
     */
    int insertBatch(List<StockRtInfo> stockRtInfoList);
}
