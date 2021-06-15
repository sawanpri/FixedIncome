package com.jeff.service;

import com.jeff.domain.Topic;
import com.jeff.domain.Trade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.*;

public class PubSubHandler {

    private static Logger logger = LoggerFactory.getLogger(PubSubHandler.class);
    static Hashtable<Topic, List<Subscriber>> subscribers = new Hashtable<>();

    public static void registerSubscriber(Topic topic, Subscriber subscriber){
        if(!subscribers.containsKey(topic)){
            List<Subscriber> list = new ArrayList<>();
            list.add(subscriber);
            subscribers.put(topic, list);
        }else {
            subscribers.get(topic).add(subscriber);
        }

    }

    /*
    * Publish message to registered subscribers for specific topic
    * @topic topic enum value
    * @tradeList List of trading data to publish
    * */
    public static void publishMessage(Topic topic, List<Trade> tradeList) {
        if(!subscribers.isEmpty()) {
            logger.info("Message published to "+ subscribers.get(topic).stream().count() +" Subscribers registered for topic "+ topic.name());
            subscribers.get(topic).forEach(sub -> new Thread(() -> sub.notify(tradeList)).start());
        }

    }

    public static Hashtable<Topic, List<Subscriber>> getSubscribers() {
        return subscribers;
    }
}
