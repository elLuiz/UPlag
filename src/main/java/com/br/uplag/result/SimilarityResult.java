package com.br.uplag.result;

import com.br.uplag.threshold.ClassVariance;
import com.br.uplag.threshold.Histogram;
import com.br.uplag.util.DoubleUtil;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class SimilarityResult {
    public static final int PLACES = 2;
    private final Map<String, Double> similarityMap;
    private final Map<String, DocumentStatistics> documentStatisticsMap;
    private final Double threshold;

    public SimilarityResult(Map<String, Double> similarityMap, Map<String, DocumentStatistics> documentStatisticsMap, Double threshold) {
        this.similarityMap = similarityMap;
        this.documentStatisticsMap = documentStatisticsMap;
        if (threshold == null) {
            ClassVariance classVariance = new ClassVariance(similarityMap.values());
            classVariance.calculateCumulativeProbability();
            this.threshold = classVariance.getOtsuThreshold().findMaxThresholdValue();
        } else
            this.threshold = threshold;
    }

    public void displaySimilarityResults() {
        Histogram histogram = new Histogram();
        histogram.createHistogram(similarityMap.values());
        Map<String, Double> documentsSimilarityMap = sortMapDescending();
        for (Map.Entry<String, Double> fileEntry : documentsSimilarityMap.entrySet()) {
            if (fileEntry.getValue() >= threshold) {
                System.out.println(fileEntry.getKey() + " -> " + DoubleUtil.prettifyDouble(fileEntry.getValue(), PLACES) + "%");
                displayStatisticalDataWithinDocumentsPairs(fileEntry.getKey());
            }
        }
    }

    // font: https://www.geeksforgeeks.org/sorting-a-hashmap-according-to-values/
    public Map<String, Double> sortMapDescending() {
        List<Map.Entry<String, Double>> entryList = new LinkedList<>(similarityMap.entrySet());
        entryList.sort((value1, value2) -> value2.getValue().compareTo(value1.getValue()));
        Map<String, Double> resultMap = new LinkedHashMap<>();
        for (Map.Entry<String, Double> entry : entryList) {
            resultMap.put(entry.getKey(), entry.getValue());
        }

        return resultMap;
    }

    public void displayStatisticalDataWithinDocumentsPairs(String documentPairString) {
        String[] split = documentPairString.split(", ");
        String documentOne = split[0].replace("(", "");
        String documentTwo = split[1].replace(")", "");
        DocumentStatistics documentAStatistics = documentStatisticsMap.get(documentOne);
        DocumentStatistics documentBStatistics = documentStatisticsMap.get(documentTwo);
        documentAStatistics.calculateContainment(documentBStatistics);
        documentBStatistics.calculateContainment(documentAStatistics);
        displayNumberOfTokens(documentOne, documentAStatistics);
        displayNumberOfTokens(documentTwo, documentBStatistics);
        displayContainment(documentOne, documentTwo, documentAStatistics);
        displayContainment(documentTwo, documentOne, documentBStatistics);
        System.out.println("------------------------------------------------------------------");
    }

    private void displayNumberOfTokens(String document, DocumentStatistics documentAStatistics) {
        System.out.println(document + " : " + documentAStatistics.toString());
    }

    private void displayContainment(String documentOne, String documentTwo, DocumentStatistics documentAStatistics) {
        System.out.println("Containment of " + documentOne + " in " + documentTwo + " : " + DoubleUtil.prettifyDouble(documentAStatistics.getContainment() * 100, PLACES) + "%");
    }

}