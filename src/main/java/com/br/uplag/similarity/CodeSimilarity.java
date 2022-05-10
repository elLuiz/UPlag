package com.br.uplag.similarity;

import com.br.uplag.result.Pair;
import com.br.uplag.util.CollectionUtil;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public abstract class CodeSimilarity {
    protected final Map<String, Map<String, Double>> weightMap;

     protected CodeSimilarity(Map<String, Map<String, Double>> weightMap) {
        this.weightMap = weightMap;
    }

    public Map<String, Double> calculateDocumentSimilarity() {
        Set<String> documents = weightMap.keySet();
        Map<String, Double> documentsSimilarityMap = new LinkedHashMap<>();
        for (String document : documents) {
            for (String document2 : documents) {
                if (!document.equals(document2)) {
                    List<Double> firstDocumentWeights = CollectionUtil.convertDoubleCollectionToArraylist(weightMap.get(document).values());
                    List<Double> secondDocumentWeights = CollectionUtil.convertDoubleCollectionToArraylist(weightMap.get(document2).values());
                    double dotProduct = calculateDotProducts(firstDocumentWeights, secondDocumentWeights);
                    double distance = calculateDistance(firstDocumentWeights, secondDocumentWeights);
                    double similarity = calculateSimilarity(dotProduct, distance);
                    if (!containsPair(documentsSimilarityMap, document, document2))
                        documentsSimilarityMap.put(Pair.buildPairsTemplate(document, document2), getSimilarityPercentage(similarity));
                }
            }
        }
        return documentsSimilarityMap;
    }

    protected Double calculateDotProducts(List<Double> firstDocument, List<Double> secondDocument) {
        double sum = 0.0;
        for (int i = 0; i < firstDocument.size(); i++)
            sum += firstDocument.get(i) * secondDocument.get(i);
        return sum;
    }

    private double calculateDistance(List<Double> firstDocumentTermWeights, List<Double> secondDocumentTermWeights) {
        double distanceOne = 0.0;
        double distanceTwo = 0.0;
        for (int i = 0; i < firstDocumentTermWeights.size(); i++) {
            distanceOne += Math.pow(firstDocumentTermWeights.get(i), 2);
            distanceTwo += Math.pow(secondDocumentTermWeights.get(i), 2);
        }

        return getDistance(distanceOne, distanceTwo);
    }

    protected abstract double getDistance(double firstDocumentDistance, double secondDocumentDistance);

    protected abstract Double calculateSimilarity(Double dotProduct, Double distance);

    private boolean containsPair(Map<String, Double> documentsSimilarityMap, String document, String document2) {
        return documentsSimilarityMap.get(Pair.buildPairsTemplate(document2, document)) != null;
    }

    private double getSimilarityPercentage(double similarity) {
        return similarity * 100;
    }
}