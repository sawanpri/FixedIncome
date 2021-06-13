package com.jeff.domain;

import org.json.simple.JSONArray;

import java.util.*;
import java.util.stream.Stream;

public class PubSubHandler {

    private static Hashtable<Topic, List<Subscriber>> subscribers = new Hashtable<>();

    public static void registerSubscriber(Topic t, Subscriber subscriber){
        if(!subscribers.containsKey(t)){
            List<Subscriber> list = new ArrayList<>();
            list.add(subscriber);
            subscribers.put(t, list);
        }else {
            subscribers.get(t).add(subscriber);
        }

        System.out.println("Subscribers registered for topic "+ t.name()+" : "
                +subscribers.get(t).stream().count());
    }

    public static void publishMessage(Topic t, List tradeList){
        System.out.println("Message published for topic : "+t.name());
        subscribers.get(t).forEach(a -> a.notify(tradeList));

    }


}
