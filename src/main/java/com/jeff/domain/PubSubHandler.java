package com.jeff.domain;

import java.util.*;

public class PubSubHandler {

    public static Hashtable<Topic, List<Subscriber>> subscribers = new Hashtable<>();

    public static void registerSubscriber(Topic t, Subscriber subscriber){
        if(!subscribers.containsKey(t)){
            List<Subscriber> list = new ArrayList<>();
            list.add(subscriber);
            subscribers.put(t, list);
        }else {
            subscribers.get(t).add(subscriber);
        }

    }

    public static void publishMessage(Topic t, List tradeList){
        System.out.println("Message published for topic : "+t.name());
        System.out.println("Subscribers registered for topic "+ t.name()+" : "
                +subscribers.get(t).stream().count());
        subscribers.get(t).forEach(a -> {
            new Thread(() -> {
                a.notify(tradeList);

            }).start();
            //a.notify(tradeList);

        });

    }


}
