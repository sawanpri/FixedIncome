package com.jeff.domain;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.math.BigDecimal;
import java.util.*;

public class Subscriber{

    List<Trade> list = new LinkedList<Trade>();
    Topic topic;
    public Subscriber(Topic t) {
        topic=t;
        PubSubHandler.registerSubscriber(t, this);

    }

    void notify(List tradeList){
        list.addAll(tradeList);
        calculate();
    }

    void calculate(){
        list.forEach(a -> System.out.println(a.symbol));
    }

    public BigDecimal getAveragePriceForSymbol(){
        HashMap<String, BigDecimal> map = new HashMap<>();

        list.stream().forEach(a -> {
            if(map.containsKey(a.symbol)){
                map.put(a.symbol, map.get(a.symbol).add(a.price));
            }else{
                map.put(a.symbol, a.price);

            }
        });

        for(Map.Entry entry : map.entrySet()){
            BigDecimal count = BigDecimal.valueOf(list.stream().filter(a -> a.symbol.equals(entry.getKey())).count());
            System.out.println("count is "+count);
            BigDecimal price = new BigDecimal(entry.getValue().toString()).divide(count);
            System.out.println("Average price for symbol "+entry.getKey()+" is "+price);
        }
        return new BigDecimal("0");
    }

    public Trade getLargestTradeBySize(){

        return null;
    }

    public List<Trade> getTradesbyGroup(String column){

        return new ArrayList<>();
    }

}
