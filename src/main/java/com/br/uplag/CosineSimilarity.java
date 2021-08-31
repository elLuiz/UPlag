package com.br.uplag;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class CosineSimilarity {
    private final Map<String, Map<String, Double>> weightMap;

    public CosineSimilarity(Map<String, Map<String, Double>> weightMap) {
        this.weightMap = weightMap;
    }

    public void calculateSimilarity() {
        Set<String> documents = weightMap.keySet();
        Map<String, Double> documentsSimilarityMap = new LinkedHashMap<>();
        for (String document : documents) {
            for (String pair : documents) {
                if (!document.equals(pair)) {
                    List<Double> firstDocumentWeights = new ArrayList<>(weightMap.get(document).values());
                    List<Double> secondDocumentWeights = new ArrayList<>(weightMap.get(pair).values());
                    double dotProduct = calculateDotProducts(firstDocumentWeights, secondDocumentWeights);
                    double euclidian = calculateEuclidianDistance(firstDocumentWeights, secondDocumentWeights);
                    double similarity = dotProduct / euclidian;
                    String documentPair = "(" + document + ", " + pair + ")";
                    if (documentsSimilarityMap.get("(" + pair + ", " + document + ")") == null)
                        documentsSimilarityMap.put(documentPair, similarity * 100);
                }
            }
        }
        displayResultsAboveThreshold(documentsSimilarityMap);
    }

    public void displayResultsAboveThreshold(Map<String, Double> documentsSimilarityMap) {
        for (Map.Entry<String, Double> fileEntry : documentsSimilarityMap.entrySet()) {
            if (fileEntry.getValue() >= 50) {
                System.out.println(fileEntry.getKey() + "->" + fileEntry.getValue());

            }
        }
    }

    public Double calculateDotProducts(List<Double> firstDocument, List<Double> secondDocument) {
        double sum = 0.0;
        for (int i = 0; i < firstDocument.size(); i++)
            sum += firstDocument.get(i) * secondDocument.get(i);
        return sum;
    }

    public Double calculateEuclidianDistance(List<Double> firstDocument, List<Double> secondDocument) {
        double euclidianFirstDocument = 0.0;
        double euclidianSecondDocument = 0.0;
        for (int i = 0; i < firstDocument.size(); i++) {
            euclidianFirstDocument += Math.pow(firstDocument.get(i), 2);
            euclidianSecondDocument += Math.pow(secondDocument.get(i), 2);
        }

        return Math.sqrt(euclidianFirstDocument) * Math.sqrt(euclidianSecondDocument);
    }
}
