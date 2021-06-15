package com.jeff.service;

import com.jeff.domain.Topic;

import java.util.List;

public class Publisher{

    private Topic topic;

    public Publisher(Topic t) {
        topic = t;
    }

    public void publish(List tradeList) {
        PubSubHandler.publishMessage(topic, tradeList);
    }
}
