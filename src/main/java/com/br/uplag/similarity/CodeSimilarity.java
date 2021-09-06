package com.br.uplag.similarity;

import java.util.*;

public abstract class CodeSimilarity {
    protected final Map<String, Map<String, Double>> weightMap;
    private final Integer threshold;

    protected CodeSimilarity(Map<String, Map<String, Double>> weightMap) {
        this.weightMap = weightMap;
        this.threshold = 50;
    }

    protected CodeSimilarity(Map<String, Map<String, Double>> weightMap, Integer threshold) {
        this.weightMap = weightMap;
        this.threshold = threshold;
    }

    public Map<String, Double> calculateSimilarity() {
        Set<String> documents = weightMap.keySet();
        Map<String, Double> documentsSimilarityMap = new LinkedHashMap<>();
        for (String document : documents) {
            for (String pair : documents) {
                if (!document.equals(pair)) {
                    List<Double> firstDocumentWeights = new ArrayList<>(weightMap.get(document).values());
                    List<Double> secondDocumentWeights = new ArrayList<>(weightMap.get(pair).values());
                    double dotProduct = calculateDotProducts(firstDocumentWeights, secondDocumentWeights);
                    double distance = calculateDistance(firstDocumentWeights, secondDocumentWeights);
                    double similarity = calculateSimilarity(dotProduct, distance);
                    String documentPair = "(" + document + ", " + pair + ")";
                    if (documentsSimilarityMap.get("(" + pair + ", " + document + ")") == null && similarity*100 >= threshold)
                        documentsSimilarityMap.put(documentPair, similarity * 100);
                }
            }
        }
        return documentsSimilarityMap;
    }

    public Double calculateDotProducts(List<Double> firstDocument, List<Double> secondDocument) {
        double sum = 0.0;
        for (int i = 0; i < firstDocument.size(); i++)
            sum += firstDocument.get(i) * secondDocument.get(i);
        return sum;
    }

    public abstract Double calculateDistance(List<Double> firstDocument, List<Double> secondDocument);

    public abstract Double calculateSimilarity(Double dotProduct, Double distance);
}
