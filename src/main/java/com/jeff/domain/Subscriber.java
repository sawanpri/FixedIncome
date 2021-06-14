package com.jeff.domain;
import com.github.underscore.lodash.U;
import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import org.slf4j.*;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

public class Subscriber {

    List<Trade> tradeList = new LinkedList<Trade>();

    private String name;

    Consumer<List<Trade>> tradeConsumer= list -> {
        tradeList.addAll(list);
        getLargestTradeBySize();
        getAveragePriceForSymbol();
        getTradesByGroup("status","X");
    };

    public Subscriber(io.reactivex.Observable publisher, String name) {
        this.name=name;
        System.out.println("Subscriber registered" + name);
        publisher.subscribe(tradeConsumer);
    }


    public void getAveragePriceForSymbol(){
        System.out.println("Printing Average price");

        io.reactivex.Observable.fromIterable(this.tradeList)
                .groupBy(trades -> trades.symbol)
                .flatMapSingle(io.reactivex.Observable::toList)
                .map(tradesGroup -> {
                    Map<String, Double> tradeMap=new HashMap<String, Double>();
                    tradeMap.put(tradesGroup.get(0).symbol, U.average(tradesGroup, trade -> trade.price));
                    return tradeMap;
                })
                .subscribe(System.out::println);
    }

    public void getLargestTradeBySize(){
        System.out.println("Printing Largest trade by size");

        io.reactivex.Observable.fromIterable(this.tradeList)
                .groupBy(trades -> trades.symbol)
                .flatMapSingle(io.reactivex.Observable::toList)
                .map(tradesGroup -> tradesGroup.stream().max(Comparator.comparing(Trade::getSize)))
                .subscribe(trade -> System.out.println(trade.get().symbol +" "+ trade.get().toString()));
    }

    public void getTradesByGroup(String column, String value){
        System.out.println("Printing Trades by group");

        io.reactivex.Observable.fromIterable(this.tradeList)
                .filter(trade -> U.pluck(Arrays.asList(trade),column).get(0).equals(value))
                .groupBy(trades -> trades.symbol)
                .flatMapSingle(Observable::toList)
                .subscribe(System.out::println);

    }

//    public Logger logger= LoggerFactory.getLogger(Subscriber.class);
//    private List<Trade> tradeListFinal = new LinkedList<Trade>();
//    private Map<String, List<Trade>> largestTradeBySizeMap = new HashMap<>();
//    private Map<String, BigDecimal> averagePriceMap = new HashMap<>();
//    private Map<String, List<Trade>> tradesByGroupMap = new HashMap<>();
//    private String name;
//    Topic topic;
//
//    public Subscriber(Topic t, String name) {
//        this.name = name;
//        topic=t;
//        PubSubHandler.registerSubscriber(t, this);
//
//    }
//
//    void notify(List tradeList){
//        tradeListFinal.addAll(tradeList);
//        calculate();
//    }
//
//    void calculate(){
//        logger.info("Calculation started for "+this.name+" at "+ LocalDateTime.now());
//        largestTradeBySizeMap = getLargestTradeBySize(tradeListFinal);
//        for(Map.Entry entry: largestTradeBySizeMap.entrySet()){
//            logger.debug("Subscriber : "+this.name+" Symbol- "+entry.getKey()+" Size- "+entry.getValue().toString());
//        }
//
//        averagePriceMap = getAveragePriceForSymbol(tradeListFinal);
//        for(Map.Entry entry: averagePriceMap.entrySet()){
//            logger.debug("Subscriber : "+this.name+" Symbol- "+entry.getKey()+" Avg Price- "+entry.getValue());
//        }
//
//        tradesByGroupMap = getTradesbyGroup(tradeListFinal,"status", "X");
//        for(Map.Entry entry: tradesByGroupMap.entrySet()){
//            logger.debug("Subscriber : "+this.name+" Symbol- "+entry.getKey()+" Trades for status X are- "+entry.getValue().toString());
//        }
//
//        logger.info("Calculation completed for "+this.name+" at "+LocalDateTime.now());
//    }
//
//
//    @Override
//    public void customCalculate(List<Trade> tradeList) {
//
//    }
//
//    public List<Trade> getTradeListFinal() {
//        return tradeListFinal;
//    }
//
//    public Map<String, List<Trade>> getLargestTradeBySizeMap() {
//        return largestTradeBySizeMap;
//    }
//
//    public Map<String, BigDecimal> getAveragePriceMap() {
//        return averagePriceMap;
//    }
//
//    public Map<String, List<Trade>> getTradesByGroupMap() {
//        return tradesByGroupMap;
//    }
//
//    public String getName() {
//        return name;
//    }
}
