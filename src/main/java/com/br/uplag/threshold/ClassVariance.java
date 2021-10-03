package com.br.uplag.threshold;


import com.br.uplag.util.ListStatisticsUtil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ClassVariance {
    private final Map<Integer, HistogramDTO> histogramDTOMap;
    private final OtsuThreshold otsuThreshold;

    public ClassVariance(Collection<Double> similarityVector) {
        Histogram histogram = new Histogram();
        histogram.createHistogram(similarityVector);
        histogramDTOMap =  histogram.getHistogramMap();
        otsuThreshold = new OtsuThreshold();
    }

    // reference: http://www.labbookpages.co.uk/software/imgProc/otsuThreshold.html
    public void calculateBetweenClassVariance() {
        double sumBackground = 0.0;
        double firstClassWeight = 0.0;
        List<Double> probabilities = getProbabilities();
        double sum = ListStatisticsUtil.multiplyListOfProbabilities(probabilities);
        for (int index = 0; index <= 9; index++) {
            firstClassWeight += probabilities.get(index);
            double secondClassWeight = 1 - firstClassWeight;
            sumBackground += index * probabilities.get(index);
            if (firstClassWeight > 0.0 && secondClassWeight > 0.0) {
                double meanBackground = sumBackground / firstClassWeight;
                double meanForeground = (sum - sumBackground) / secondClassWeight;
                double betweenClassVariance = firstClassWeight * secondClassWeight * (meanBackground - meanForeground) * (meanBackground - meanForeground);
                otsuThreshold.storeThreshold(betweenClassVariance);
            }
        }
    }

    private List<Double> getProbabilities() {
        List<HistogramDTO> histogramValues = new ArrayList<>(histogramDTOMap.values());
        return histogramValues.stream().map(HistogramDTO::getProbability).collect(Collectors.toList());
    }

    public OtsuThreshold getOtsuThreshold() {
        return otsuThreshold;
    }
}