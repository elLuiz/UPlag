package com.br.uplag.similarity;

import java.util.List;
import java.util.Map;

public class CosineSimilarity extends CodeSimilarity{
    public CosineSimilarity(Map<String, Map<String, Double>> weightMap) {
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

        return Math.sqrt(euclidianFirstDocument) * Math.sqrt(euclidianSecondDocument);
   }

    @Override
    public Double calculateSimilarity(Double dotProduct, Double distance) {
        return dotProduct / distance;
    }
}
