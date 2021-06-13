package com.jeff.domain;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface CalculatorService {

    default Map<String, BigDecimal> getAveragePriceForSymbol(List<Trade> tradeList){
        Map<String, BigDecimal> map = new HashMap<>();
        Map<String, BigDecimal> averagePriceMap = new HashMap<>();

        tradeList.forEach(a -> {
            if(map.containsKey(a.symbol)){
                map.put(a.symbol, map.get(a.symbol).add(a.price));
            }else{
                map.put(a.symbol, a.price);
            }
        });



        for(Map.Entry entry : map.entrySet()){
            MathContext precision = new MathContext(4);
            BigDecimal count = BigDecimal.valueOf(tradeList.stream().filter(a -> a.symbol.equals(entry.getKey())).count());
            BigDecimal price = new BigDecimal(entry.getValue().toString(), precision).divide(count, precision);
            averagePriceMap.put(entry.getKey().toString(), price);
        }
        return averagePriceMap;
    }

    default Map<String, List<Trade>> getLargestTradeBySize(List<Trade> tradeList){

        Map<String, List<Trade>> tradeBySizeMap = new HashMap<>();
        tradeList.forEach(a -> {
            if(tradeBySizeMap.containsKey(a.symbol)){
                int size = tradeBySizeMap.get(a.symbol).stream().findFirst().get().size;
                if( size < a.size) {
                    List<Trade> tList = new ArrayList<>();
                    tList.add(a);
                    tradeBySizeMap.put(a.symbol, tList);
                }
                else if(size == a.size){
                    tradeBySizeMap.get(a.symbol).add(a);

                }
            }else{
                List<Trade> tList = new ArrayList<>();
                tList.add(a);
                tradeBySizeMap.put(a.symbol, tList);
            }
        });


        return tradeBySizeMap;
    }

    default Map<String, List<Trade>> getTradesbyGroup(List<Trade> tradeList, String column, String value){

        Map<String, List<Trade>> tradesMap = new HashMap<>();
        tradeList.forEach(trade -> {
            if (tradesMap.containsKey(trade.symbol)) {
                if (column.equals("price")) {
                    if (trade.price.equals(value)) {
                        tradesMap.get(trade.symbol).add(trade);
                    }
                } else if (column.equals("size")) {
                    if (trade.size == Integer.valueOf(value)) {
                        tradesMap.get(trade.symbol).add(trade);
                    }
                } else if (column.equals("status")) {
                    if (trade.status.equals(value)) {
                        tradesMap.get(trade.symbol).add(trade);
                    }
                } else {
                    if (trade.timestamp.equals(value)) {
                        tradesMap.get(trade.symbol).add(trade);
                    }
                }
            } else {
                List<Trade> list = new ArrayList<>();
                list.add(trade);
                if (column.equals("price")) {
                    if (trade.price.equals(value)) {
                        tradesMap.put(trade.symbol, list);
                    }
                } else if (column.equals("size")) {
                    if (trade.size == Integer.valueOf(value)) {
                        tradesMap.put(trade.symbol, list);
                    }
                } else if (column.equals("status")) {
                    if (trade.status.equals(value)) {
                        tradesMap.put(trade.symbol, list);
                    }
                } else {
                    if (trade.timestamp.equals(value)) {
                        tradesMap.put(trade.symbol, list);
                    }

                }
            }
        });
        return tradesMap;
    }


    void customCalculate(List<Trade> tradeList);
}
