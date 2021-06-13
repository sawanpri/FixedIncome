package com.jeff.domain;

import jdk.nashorn.internal.ir.annotations.Immutable;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Immutable
public class Trade {

    public Timestamp timestamp;
    public BigDecimal price;
    public int size;
    public String status;
    public String symbol;

    @Override
    public String toString() {
        return "Trade{" +
                "timestamp=" + timestamp +
                ", price=" + price +
                ", size=" + size +
                ", status='" + status + '\'' +
                ", symbol='" + symbol + '\'' +
                '}';
    }
}
