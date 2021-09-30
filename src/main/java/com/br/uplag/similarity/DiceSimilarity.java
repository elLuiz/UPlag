package com.br.uplag.similarity;

import java.util.List;
import java.util.Map;

public class DiceSimilarity extends CodeSimilarity{
    public DiceSimilarity(Map<String, Map<String, Double>> weightMap) {
        super(weightMap);
    }


    @Override
    public Double calculateDistance(List<Double> firstDocument, List<Double> secondDocument) {
        double euclidianFirstDocument = 0.0;
        double euclidianSecondDocument = 0.0;
        for (int i = 0; i < firstDocument.size(); i++) {
            euclidianFirstDocument += Math.pow(firstDocument.get(i), 2);
            euclidianSecondDocument += Math.pow(secondDocument.get(i), 2);
        }

        return euclidianFirstDocument + euclidianSecondDocument;
    }

    @Override
    public Double calculateSimilarity(Double dotProduct, Double distance) {
        return 2 * dotProduct / distance;
    }
}
