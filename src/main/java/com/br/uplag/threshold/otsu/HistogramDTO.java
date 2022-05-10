package com.br.uplag.threshold.otsu;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
public class HistogramDTO {
    private Integer occurrences;
    private Double probability;

    public HistogramDTO() {
        this.occurrences = 0;
        this.probability = 0.0;
    }

    public static Map<Integer, HistogramDTO> buildHistogram() {
        Map<Integer, HistogramDTO> histogram = new HashMap<>();
        histogram.put(0, new HistogramDTO());
        histogram.put(1, new HistogramDTO());
        histogram.put(2, new HistogramDTO());
        histogram.put(3, new HistogramDTO());
        histogram.put(4, new HistogramDTO());
        histogram.put(5, new HistogramDTO());
        histogram.put(6, new HistogramDTO());
        histogram.put(7, new HistogramDTO());
        histogram.put(8, new HistogramDTO());
        histogram.put(9, new HistogramDTO());

        return histogram;
    }

    public void incrementOccurrences() {
        this.occurrences++;
    }

    public void calculateProbability(int collectionSize) {
        this.probability = (double) this.occurrences / collectionSize;
    }
}
