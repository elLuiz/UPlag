package com.br.uplag.threshold.otsu;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HistogramDTO {
    private Integer occurrences;
    private Double probability;

    public HistogramDTO() {
        this.occurrences = 0;
        this.probability = 0.0;
    }

    public void incrementOccurrences() {
        this.occurrences++;
    }

    public void calculateProbability(int collectionSize) {
        this.probability = (double) this.occurrences / collectionSize;
    }
}
