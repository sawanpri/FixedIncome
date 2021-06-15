package com.jeff.util;

import com.jeff.domain.Trade;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public interface CalculatorService {

    default Map<String, BigDecimal> getAveragePriceForSymbol(List<Trade> tradeList) {
        Map<String, BigDecimal> averagePriceMap = new HashMap<>();

        tradeList.stream().collect(Collectors.groupingBy(trade -> trade.symbol))
                .forEach((symbol, tradesGroup) -> {
                    averagePriceMap.put(symbol, Helpers.average(tradesGroup, trade -> trade.price));
                });

        return averagePriceMap;
    }

    default Map<String, List<Trade>> getLargestTradeBySize(List<Trade> tradeList) {
        Map<String, List<Trade>> tradeBySizeMap = new HashMap<>();

        tradeList.stream().collect(Collectors.groupingBy(trade -> trade.symbol))
                .forEach((symbol, tradesGroup) -> {
                    Trade maxTrade = Helpers.max(tradesGroup, trade -> trade.size);
                    tradeBySizeMap.put(symbol, Helpers.maxAll(tradesGroup, trade -> trade.size == maxTrade.size));
                });

        return tradeBySizeMap;
    }

    default Map<String, List<Trade>> getTradesByGroup(List<Trade> tradeList, String column, String value) {
        List<Trade> filteredList = tradeList.stream().filter(trade -> {
            try {
                return Helpers.pluck(trade, column).equals(value);
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }).collect(Collectors.toList());

        return filteredList.stream().collect(Collectors.groupingBy(trade -> trade.symbol));
    }

    void customCalculate(List<Trade> tradeList);
}
