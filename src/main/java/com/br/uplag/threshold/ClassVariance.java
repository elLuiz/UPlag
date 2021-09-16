package com.br.uplag.threshold;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.br.uplag.util.DoubleListUtil.*;

public class ClassVariance {
    private final Map<Integer, HistogramDTO> histogramDTOMap;
    private Double firstClassCumulativeProbabilities;
    private Double secondClassCumulativeProbabilities;
    private Double firstClassVariance;
    private Double secondClassVariance;
    private Double meanOfFirstClass;
    private Double meanOfSecondClass;
    private final OtsuThreshold otsuThreshold;

    public ClassVariance(Collection<Double> similarityVector) {
        Histogram histogram = new Histogram();
        histogram.createHistogram(similarityVector);
        histogramDTOMap =  histogram.getHistogramMap();
        this.firstClassCumulativeProbabilities = 0.0;
        this.secondClassCumulativeProbabilities = 0.0;
        this.firstClassVariance = 0.0;
        this.secondClassVariance = 0.0;
        this.meanOfFirstClass = 0.0;
        this.meanOfSecondClass = 0.0;
        otsuThreshold = new OtsuThreshold();
    }

    public void calculateCumulativeProbability() {
        List<Double> probabilities = getDoubles();
        for (int index = 0; index <= 9; index++) {
            List<Double> classOneList = getSubListList(probabilities, 0, index + 1);
            List<Double> classTwoList = getSubListList(probabilities, index + 1, 10);
            firstClassCumulativeProbabilities = sumList(classOneList);
            secondClassCumulativeProbabilities = sumList(classTwoList);
            meanOfFirstClass = calculateMean(classOneList, firstClassCumulativeProbabilities);
            meanOfSecondClass = calculateMean(classTwoList, secondClassCumulativeProbabilities);
            firstClassVariance = calculateVariance(classOneList, meanOfFirstClass, firstClassCumulativeProbabilities);
            secondClassVariance = calculateVariance(classTwoList, meanOfSecondClass, secondClassCumulativeProbabilities);
            otsuThreshold.storeThreshold(calculateWithinClassVariance(), calculateBetweenClassVariance());
        }
    }

    private List<Double> getDoubles() {
        List<HistogramDTO> histogramValues = new ArrayList<>(histogramDTOMap.values());
        return histogramValues.stream().map(HistogramDTO::getProbability).collect(Collectors.toList());
    }

    public Double calculateWithinClassVariance() {
        return firstClassCumulativeProbabilities * firstClassVariance + secondClassCumulativeProbabilities * secondClassVariance;
    }

    public Double calculateBetweenClassVariance() {
        return firstClassCumulativeProbabilities * secondClassCumulativeProbabilities * Math.pow((meanOfFirstClass - meanOfSecondClass), 2);
    }

    public OtsuThreshold getOtsuThreshold() {
        return otsuThreshold;
    }
}