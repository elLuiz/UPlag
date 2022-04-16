package com.br.uplag.similarity;

import java.util.Map;

public class CosineSimilarity extends CodeSimilarity{
    public CosineSimilarity(Map<String, Map<String, Double>> weightMap) {
        super(weightMap);
    }

    @Override
    protected double getDistance(double firstDocumentDistance, double secondDocumentDistance) {
        return Math.sqrt(firstDocumentDistance) * Math.sqrt(secondDocumentDistance);
    }

    @Override
    // cos(O) = D1 * D2 / ||D1||*||D2||
    protected Double calculateSimilarity(Double dotProduct, Double distance) {
        return dotProduct / distance;
    }
}
