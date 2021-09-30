package com.br.uplag.util;

import java.util.List;

public class MedianResultUtil {
    public static void displayMedianValue(List<Double> collection) {
       int half = (int) Math.floor((float) collection.size() / 2);
        if (collection.size() % 2 == 0) {
            Double firstDouble = collection.get(half);
            Double secondDouble = collection.get(half + 1);
            Double median = (firstDouble + secondDouble) / 2;
            System.out.println(median);
        } else
            System.out.println(collection.get(half));
    }
}
