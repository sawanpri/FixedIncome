package com.jeff.domain;
import org.slf4j.*;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

public class Subscriber implements CalculatorService{

    public Logger logger= LoggerFactory.getLogger(Subscriber.class);
    private List<Trade> tradeListFinal = new LinkedList<Trade>();
    private Map<String, List<Trade>> largestTradebySizeMap = new HashMap<>();
    private Map<String, BigDecimal> averagePriceMap = new HashMap<>();
    private Map<String, List<Trade>> tradesByGroupMap = new HashMap<>();
    private String name;
    Topic topic;

    public Subscriber(Topic t, String name) {
        this.name = name;
        topic=t;
        PubSubHandler.registerSubscriber(t, this);

    }

    void notify(List tradeList){
        tradeListFinal.addAll(tradeList);
        calculate();
    }

    void calculate(){
        logger.info("Calculation started for "+this.name+" at "+ LocalDateTime.now());
        largestTradebySizeMap = getLargestTradeBySize(tradeListFinal);
        for(Map.Entry entry: largestTradebySizeMap.entrySet()){
            logger.debug("Subscriber : "+this.name+" Symbol- "+entry.getKey()+" Size- "+entry.getValue().toString());
        }

        averagePriceMap = getAveragePriceForSymbol(tradeListFinal);
        for(Map.Entry entry: averagePriceMap.entrySet()){
            logger.debug("Subscriber : "+this.name+" Symbol- "+entry.getKey()+" Avg Price- "+entry.getValue());
        }

        tradesByGroupMap = getTradesbyGroup(tradeListFinal,"status", "X");
        for(Map.Entry entry: tradesByGroupMap.entrySet()){
            logger.debug("Subscriber : "+this.name+" Symbol- "+entry.getKey()+" Trades for status X are- "+entry.getValue().toString());
        }

        logger.info("Calculation completed for "+this.name+" at "+LocalDateTime.now());
    }


    @Override
    public void customCalculate(List<Trade> tradeList) {

    }

    public List<Trade> getTradeListFinal() {
        return tradeListFinal;
    }

    public Map<String, List<Trade>> getLargestTradebySizeMap() {
        return largestTradebySizeMap;
    }

    public Map<String, BigDecimal> getAveragePriceMap() {
        return averagePriceMap;
    }

    public Map<String, List<Trade>> getTradesByGroupMap() {
        return tradesByGroupMap;
    }

    public String getName() {
        return name;
    }
}
