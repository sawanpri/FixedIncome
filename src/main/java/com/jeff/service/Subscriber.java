package com.jeff.service;

import com.jeff.domain.Topic;
import com.jeff.domain.Trade;
import com.jeff.util.CalculatorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Subscriber implements CalculatorService {

    public Logger logger = LoggerFactory.getLogger(Subscriber.class);
    Topic topic;
    private List<Trade> tradeListFinal = new LinkedList<Trade>();
    private Map<String, List<Trade>> largestTradeBySizeMap = new HashMap<>();
    private Map<String, BigDecimal> averagePriceMap = new HashMap<>();
    private Map<String, List<Trade>> tradesByGroupMap = new HashMap<>();
    private String name;

    public Subscriber(Topic t, String name) {
        this.name = name;
        topic = t;
        PubSubHandler.registerSubscriber(t, this);

    }

    void notify(List tradeList) {
        tradeListFinal.addAll(tradeList);
        calculate();
    }

    void calculate() {
        logger.info("Calculation started for " + this.name + " at " + LocalDateTime.now());
        largestTradeBySizeMap = getLargestTradeBySize(tradeListFinal);
        for (Map.Entry entry : largestTradeBySizeMap.entrySet()) {
            logger.debug("Subscriber : " + this.name + " Symbol- " + entry.getKey() + " Size- " + entry.getValue().toString());
        }

        averagePriceMap = getAveragePriceForSymbol(tradeListFinal);
        for (Map.Entry entry : averagePriceMap.entrySet()) {
            logger.debug("Subscriber : " + this.name + " Symbol- " + entry.getKey() + " Avg Price- " + entry.getValue());
        }

        tradesByGroupMap = getTradesByGroup(tradeListFinal, "status", "X");
        for (Map.Entry entry : tradesByGroupMap.entrySet()) {
            logger.debug("Subscriber : " + this.name + " Symbol- " + entry.getKey() + " Trades for status X are- " + entry.getValue().toString());
        }

        logger.info("Calculation completed for " + this.name + " at " + LocalDateTime.now());
    }

    @Override
    public void customCalculate(List<Trade> tradeList) {
    }

    public List<Trade> getTradeListFinal() {
        return tradeListFinal;
    }

    public Map<String, List<Trade>> getLargestTradeBySizeMap() {
        return largestTradeBySizeMap;
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
