package com.br.uplag.similarity;

import java.util.Map;

public class DiceSimilarity extends CodeSimilarity {
    public DiceSimilarity(Map<String, Map<String, Double>> weightMap) {
        super(weightMap);
    }

    @Override
    protected double getDistance(double firstDocumentDistance, double secondDocumentDistance) {
        return firstDocumentDistance + secondDocumentDistance;
    }

    @Override
    // @Formula: dice(D1, D2) = 2 * |X ∩ Y| / (|X| + |Y|)
    public Double calculateSimilarity(Double dotProduct, Double distance) {
        return 2 * dotProduct / distance;
    }
}
