package com.jeff.domain;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

public class Subscriber{

    List<Trade> list = new LinkedList<Trade>();
    Map<String, Trade> largestTradebySizeMap;
    Map<String, BigDecimal> averagePriceMap;
    Map<String, List<Trade>> tradesByGroupMap;
    String name;
    Topic topic;
    public Subscriber(Topic t, String name) {
        this.name = name;
        topic=t;
        PubSubHandler.registerSubscriber(t, this);

    }

    void notify(List tradeList){
        list.addAll(tradeList);
        calculate();
    }

    void calculate(){
        System.out.println("Calculation started for "+this.name+" at "+ LocalDateTime.now());
        largestTradebySizeMap = getLargestTradeBySize();
        for(Map.Entry entry: largestTradebySizeMap.entrySet()){
            System.out.println("Subscriber : "+this.name+" Symbol- "+entry.getKey()+" Size- "+entry.getValue().toString());
        }

        System.out.println("Getting Average price for any Symbol:");
        averagePriceMap = getAveragePriceForSymbol();
        for(Map.Entry entry: averagePriceMap.entrySet()){
            System.out.println("Subscriber : "+this.name+" Symbol- "+entry.getKey()+" Avg Price- "+entry.getValue());
        }

        System.out.println("Getting trade by Group :");
        tradesByGroupMap = getTradesbyGroup("status", "X");
        for(Map.Entry entry: tradesByGroupMap.entrySet()){
            System.out.println("Subscriber : "+this.name+" Symbol- "+entry.getKey()+" Trades for status X are- "+entry.getValue().toString());
        }

        System.out.println("Calculation completed for "+this.name+" at "+LocalDateTime.now());
    }

    public Map<String, BigDecimal> getAveragePriceForSymbol(){
        HashMap<String, BigDecimal> map = new HashMap<>();
        Map<String, BigDecimal> avgPricelist = new HashMap<>();

        list.stream().forEach(a -> {
            if(map.containsKey(a.symbol)){
                map.put(a.symbol, map.get(a.symbol).add(a.price));
            }else{
                map.put(a.symbol, a.price);

            }
        });

        for(Map.Entry entry : map.entrySet()){
            BigDecimal count = BigDecimal.valueOf(list.stream().filter(a -> a.symbol.equals(entry.getKey())).count());
            BigDecimal price = new BigDecimal(entry.getValue().toString()).divide(count);
            avgPricelist.put(entry.getKey().toString(), price);
        }
        return avgPricelist;
    }

    public HashMap<String, Trade> getLargestTradeBySize(){

        HashMap<String, Trade> tradeMap = new HashMap<>();

        list.stream().forEach(a -> {
            if(tradeMap.containsKey(a.symbol)){
                if(tradeMap.get(a.symbol).size < a.size)
                tradeMap.put(a.symbol, a) ;
            }else{
                tradeMap.put(a.symbol, a);

            }
        });


        return tradeMap;
    }

    public Map<String, List<Trade>> getTradesbyGroup(String column, String value){

        HashMap<String, List<Trade>> tradeMap = new HashMap<>();


        list.stream().forEach(trade -> {
            if (tradeMap.containsKey(trade.symbol)) {
                if (column.equals("price")) {
                    if (trade.price.equals(value)) {
                        tradeMap.get(trade.symbol).add(trade);
                    }
                } else if (column.equals("size")) {
                    if (trade.size == Integer.valueOf(value)) {
                        tradeMap.get(trade.symbol).add(trade);
                    }
                } else if (column.equals("status")) {
                    if (trade.status.equals(value)) {
                        tradeMap.get(trade.symbol).add(trade);
                    }
                } else {
                    if (trade.timestamp.equals(value)) {
                        tradeMap.get(trade.symbol).add(trade);
                    }
                }
            } else {
                List<Trade> list = new ArrayList<>();
                list.add(trade);
                if (column.equals("price")) {
                    if (trade.price.equals(value)) {
                        tradeMap.put(trade.symbol, list);
                    }
                } else if (column.equals("size")) {
                    if (trade.size == Integer.valueOf(value)) {
                        tradeMap.put(trade.symbol, list);
                    }
                } else if (column.equals("status")) {
                    if (trade.status.equals(value)) {
                        tradeMap.put(trade.symbol, list);
                    }
                } else {
                    if (trade.timestamp.equals(value)) {
                        tradeMap.put(trade.symbol, list);
                    }

                }
            }
        });
        return tradeMap;
    }

}
