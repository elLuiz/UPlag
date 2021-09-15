package com.br.uplag.similarity;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public abstract class CodeSimilarity {
    protected final Map<String, Map<String, Double>> weightMap;

     protected CodeSimilarity(Map<String, Map<String, Double>> weightMap) {
        this.weightMap = weightMap;
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
                    similarity = similarity * 100;
                    if (documentsSimilarityMap.get("(" + pair + ", " + document + ")") == null)
                        documentsSimilarityMap.put(documentPair, similarity);
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
