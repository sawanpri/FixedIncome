package com.jeff.domain;

import java.util.List;

public class Publisher{

    Topic topic;
    public Publisher(Topic t) {
        topic = t;
    }

    public void publish(List tradeList){
        PubSubHandler.publishMessage(topic, tradeList);
    }
}
