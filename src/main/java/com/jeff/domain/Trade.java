package com.jeff.domain;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.concurrent.BlockingQueue;

public class Trade {

    public Timestamp timestamp;
    public BigDecimal price;
    public int size;
    public String status;
    public String symbol;



}
