package com.br.uplag.weight;

import java.util.List;
import java.util.Map;

public class SublinearTFIDFWeightCalculator extends TermWeightCalculator {

    public SublinearTFIDFWeightCalculator(Map<String, Map<String, Integer>> invertedIndexMap, List<String> programs) {
        super(invertedIndexMap, programs);
    }

    @Override
    protected double calculateWeight(int termFrequency) {
        return termFrequency > 0 ? (1 + Math.log10(termFrequency)) * calculateIDF() : 0.0;
    }
}
