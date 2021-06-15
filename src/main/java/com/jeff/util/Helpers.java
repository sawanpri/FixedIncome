package com.jeff.util;

import com.jeff.domain.Trade;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Helpers {
    final static MathContext precision = new MathContext(4);


    public static BigDecimal average(final List<Trade> list, final Function<Trade, BigDecimal> func) {
        BigDecimal sum = sum(list, func);
        if (sum == null) {
            return null;
        }

        return sum.divide(BigDecimal.valueOf(list.size()),4, BigDecimal.ROUND_UP);
    }

    public static BigDecimal sum(final List<Trade> list, final Function<Trade, BigDecimal> func) {
        BigDecimal result = BigDecimal.ZERO;
        for (final Trade item : list) {
            result = result.add(func.apply(item));
        }
        return result;
    }

    public static List<Trade> maxAll(final List<Trade> list, final Predicate<Trade> func) {
        return list.stream().filter(func).collect(Collectors.toList());
    }

    public static Trade max(final List<Trade> list, final Function<Trade, Integer> func) {
        return Collections.max(list, Comparator.comparing(func::apply));
    }

    public static String pluck(final Trade trade, String propertyName) throws NoSuchFieldException, IllegalAccessException {
        return trade.getClass().getField(propertyName).get(trade).toString();
    }

}
