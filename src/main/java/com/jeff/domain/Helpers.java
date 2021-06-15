package com.jeff.domain;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Helpers {

    public static Double average(final List<Trade> list, final Function<Trade, BigDecimal> func) {
        BigDecimal sum = sum(list, func);
        if (sum == null) {
            return null;
        }
        return sum.doubleValue() / list.size();
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
