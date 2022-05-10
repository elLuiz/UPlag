package com.br.uplag.threshold.otsu;

import lombok.Getter;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Map;

@Getter
public class Histogram {
    private final Map<Integer, HistogramDTO> histogramMap;

    public Histogram() {
        histogramMap = HistogramDTO.buildHistogram();
    }

    public void createHistogram(Collection<Double> similarityValues) {
        for (Double similarity : similarityValues) {
            int key = getHistogramIndexBySimilarity(similarity);
            if (key == 10)
                incrementValue(9, similarityValues.size());
            else
                incrementValue(key, similarityValues.size());
        }
    }

    private int getHistogramIndexBySimilarity(Double similarity) {
        double position = similarity / 10;
        BigDecimal bigDecimal = new BigDecimal(String.valueOf(position));
        return bigDecimal.intValue();
    }

    private void incrementValue(int position, int collectionSize) {
        HistogramDTO histogramDTO = histogramMap.get(position);
        histogramDTO.incrementOccurrences();
        histogramDTO.calculateProbability(collectionSize);
    }
}