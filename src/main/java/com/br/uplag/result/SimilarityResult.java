package com.br.uplag.result;

import com.br.uplag.threshold.otsu.ClassVariance;
import com.br.uplag.util.DoubleUtil;

import java.util.*;
import java.util.stream.Collectors;

public class SimilarityResult {
    public static final int PLACES = 2;
    private final Map<String, Double> similarityMap;
    private final Map<String, DocumentStatistics> documentStatisticsMap;
    private Double threshold;

    public SimilarityResult(Map<String, Double> similarityMap, Map<String, DocumentStatistics> documentStatisticsMap, Double threshold) {
        this.similarityMap = similarityMap;
        this.documentStatisticsMap = documentStatisticsMap;
        if (threshold == null) {
            setOtsuThreshold(this.similarityMap.values());
        } else
            this.threshold = threshold;
    }

    private void setOtsuThreshold(Collection<Double> similarityValues) {
        ClassVariance classVariance = new ClassVariance(similarityValues);
        classVariance.calculateBetweenClassVariance();
        this.threshold = classVariance.getPredictedThreshold();
    }

    public void displaySimilarityResults() {
        int count = 0;
        System.out.println("Threshold " + threshold);
        for (Map.Entry<String, Double> fileEntry : getPlagiarizedFilesOrderedBySimilarityRank().entrySet()) {
            if (fileEntry.getValue() > threshold) {
                System.out.println(fileEntry.getKey() + " -> " + DoubleUtil.prettifyDouble(fileEntry.getValue(), PLACES) + "%");
                displayStatisticsForPair(new Pair(fileEntry.getKey()));
                count++;
            }
        }
        System.out.println("Number of possible plagiarisms: " + count);
    }

    private Map<String, Double> getPlagiarizedFilesOrderedBySimilarityRank() {
        Map<String, Double> resultMap = new LinkedHashMap<>();
        for (Map.Entry<String, Double> entry : sortDescendingBySimilarity()) {
            resultMap.put(entry.getKey(), entry.getValue());
        }

        return resultMap;
    }

    // @Reference: https://www.geeksforgeeks.org/sorting-a-hashmap-according-to-values/
    private List<Map.Entry<String, Double>> sortDescendingBySimilarity() {
        return similarityMap.entrySet()
                .stream()
                .filter(similarityEntry -> similarityEntry.getValue() > threshold)
                .sorted((value1, value2) -> value2.getValue().compareTo(value1.getValue()))
                .collect(Collectors.toList());
    }

    private void displayStatisticsForPair(Pair pair) {
        DocumentStatistics documentAStatistics = documentStatisticsMap.get(pair.getDocumentOne());
        DocumentStatistics documentBStatistics = documentStatisticsMap.get(pair.getDocumentTwo());
        documentAStatistics.calculateContainment(documentBStatistics);
        documentBStatistics.calculateContainment(documentAStatistics);
        displayNumberOfTokens(pair.getDocumentOne(), documentAStatistics);
        displayNumberOfTokens(pair.getDocumentTwo(), documentBStatistics);
        displayContainment(pair.getDocumentOne(), pair.getDocumentTwo(), documentAStatistics);
        displayContainment(pair.getDocumentTwo(), pair.getDocumentOne(), documentBStatistics);
        System.out.println("--------------------------------------------------------");
    }

    private void displayNumberOfTokens(String filename, DocumentStatistics documentStatistics) {
        System.out.println("Document " + filename + " " + documentStatistics);
    }

    private void displayContainment(String documentOne, String documentTwo, DocumentStatistics documentAStatistics) {
        System.out.println("Containment of " + documentOne + " in " + documentTwo + " -> " + DoubleUtil.prettifyDouble(documentAStatistics.getContainment() * 100, PLACES) + "%");
    }
}