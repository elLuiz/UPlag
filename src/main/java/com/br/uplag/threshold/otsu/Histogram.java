package com.br.uplag.threshold.otsu;

import lombok.Getter;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

@Getter
public class Histogram {
    private Map<Integer, HistogramDTO> histogramMap;

    public Histogram() {
        histogramMap = new LinkedHashMap<>();
        histogramMap.put(0, new HistogramDTO());
        histogramMap.put(1, new HistogramDTO());
        histogramMap.put(2, new HistogramDTO());
        histogramMap.put(3, new HistogramDTO());
        histogramMap.put(4, new HistogramDTO());
        histogramMap.put(5, new HistogramDTO());
        histogramMap.put(6, new HistogramDTO());
        histogramMap.put(7, new HistogramDTO());
        histogramMap.put(8, new HistogramDTO());
        histogramMap.put(9, new HistogramDTO());
    }

    public void createHistogram(Collection<Double> similarityValues) {
        for (Double similarity : similarityValues) {
            double position = similarity / 10;
            BigDecimal bigDecimal = new BigDecimal(String.valueOf(position));
            int key = bigDecimal.intValue();
            if (key == 10)
                incrementValue(9, similarityValues.size());
            else
                incrementValue(key, similarityValues.size());
        }
    }

    private void incrementValue(int position, int collectionSize) {
        HistogramDTO histogramDTO = histogramMap.get(position);
        histogramDTO.incrementOccurrences();
        histogramDTO.calculateProbability(collectionSize);
    }
}