package com.itheima.stock.mapper;

import com.itheima.stock.pojo.domain.InnerMarketDomain;
import com.itheima.stock.pojo.entity.StockMarketIndexInfo;
import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
* @author jiawenduo
* @description 针对表【stock_market_index_info(国内大盘数据详情表)】的数据库操作Mapper
* @createDate 2024-08-02 20:27:20
* @Entity com.itheima.stock.pojo.entity.StockMarketIndexInfo
*/
public interface StockMarketIndexInfoMapper {

    int deleteByPrimaryKey(Long id);

    int insert(StockMarketIndexInfo record);

    int insertSelective(StockMarketIndexInfo record);

    StockMarketIndexInfo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(StockMarketIndexInfo record);

    int updateByPrimaryKey(StockMarketIndexInfo record);

    List<InnerMarketDomain> getMarketInfo(@Param("curDate") Date curDate,@Param("mCodes") List<String> mCodes);

    /**
     * 根据时间范围和指定的大盘id统计每分钟的交易量
     * @param markedIds 大盘id集合
     * @param startTime 交易开始时间
     * @param endTime 结束时间
     * @return
     */
    List<Map> getStockTradeVol(@Param("markedIds") List<String> markedIds,
                               @Param("startTime") Date startTime,
                               @Param("endTime") Date endTime);

    int insertBatch(ArrayList<StockMarketIndexInfo> list);
}
