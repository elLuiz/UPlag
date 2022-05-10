package com.br.uplag.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class DoubleUtil {
    private DoubleUtil() {}

    // Reference: https://stackoverflow.com/questions/2808535/round-a-double-to-2-decimal-places
    public static Double prettifyDouble(Double input, int places) {
        BigDecimal bigDecimal = BigDecimal.valueOf(input);
        bigDecimal = bigDecimal.setScale(places, RoundingMode.UP);
        return bigDecimal.doubleValue();
    }
}