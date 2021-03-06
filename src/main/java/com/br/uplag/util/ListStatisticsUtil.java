package com.br.uplag.util;

import java.util.Collections;
import java.util.List;

public class ListStatisticsUtil {
    private ListStatisticsUtil() {}

    public static List<Double> getSublist(List<Double> probabilities, int startPosition, int index) {
        if (startPosition >= index)
            return Collections.singletonList(probabilities.get(startPosition - 1));
        return probabilities.subList(startPosition, index);
    }

    public static Double sumList(List<Double> doubles) {
        return doubles.stream().mapToDouble(Double::doubleValue).sum();
    }

    public static Double calculateMean(List<Double> classDoubleList, Double classCumulativeProbability) {
        double weightedProbabilities = ListStatisticsUtil.multiplyListOfProbabilities(classDoubleList);
        if (classCumulativeProbability > 0.0)
            return weightedProbabilities / classCumulativeProbability;
        return 0.0;
    }

    public static Double multiplyListOfProbabilities(List<Double> doubles) {
        double sum = 0.0;
        for (int index = 0; index < doubles.size(); index++) {
            sum += index * doubles.get(index);
        }
        return sum;
    }

    public static Double calculateVariance(List<Double> classDoubleList, Double classMean) {
        double sum = 0.0;
        for (int index = 0; index < classDoubleList.size(); index++) {
            sum += Math.pow((index - classMean), 2) * classDoubleList.get(index);
        }

        return sum;
    }
}
