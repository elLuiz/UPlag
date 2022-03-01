package com.br.uplag.weight;

import java.util.List;
import java.util.Map;

public class TfIdfWeightCalculator extends TermWeightCalculator {
    public TfIdfWeightCalculator(Map<String, Map<String, Integer>> invertedIndexMap, List<String> programs) {
        super(invertedIndexMap, programs);
    }

    @Override
    protected double calculateWeight(int termFrequency) {
        return termFrequency > 0 ? (termFrequency) * calculateIDF() :  0.0;
    }
}