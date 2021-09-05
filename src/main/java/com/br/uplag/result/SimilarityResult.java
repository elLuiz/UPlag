package com.br.uplag.result;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class SimilarityResult {
    private final Map<String, Double> similarityMap;
    private final Map<String, DocumentStatistics> documentStatisticsMap;

    public SimilarityResult(Map<String, Double> similarityMap, Map<String, DocumentStatistics> documentStatisticsMap) {
        this.similarityMap = similarityMap;
        this.documentStatisticsMap = documentStatisticsMap;
    }

    public void displaySimilarityResults() {
        Map<String, Double> documentsSimilarityMap = sortMapDescending();
        for (Map.Entry<String, Double> fileEntry : documentsSimilarityMap.entrySet()) {
            if (fileEntry.getValue() >= 50) {
                System.out.println(fileEntry.getKey() + " -> " + fileEntry.getValue() + "%");
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
        System.out.println("Containment of " + documentOne + " in " + documentTwo + " : " + documentAStatistics.getContainment() * 100 + "%");
    }
}