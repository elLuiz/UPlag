package com.br.uplag.result;

import com.br.uplag.threshold.otsu.ClassVariance;
import com.br.uplag.util.DoubleUtil;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SimilarityResult {
    private static final Logger LOGGER = Logger.getLogger(SimilarityResult.class.getSimpleName());

    public static final int PLACES = 2;
    private final Map<String, Double> similarityMap;
    private final Map<String, DocumentStatistics> documentStatisticsMap;
    private final Double threshold;

    public SimilarityResult(Map<String, Double> similarityMap, Map<String, DocumentStatistics> documentStatisticsMap, Double threshold) {
        this.similarityMap = similarityMap;
        this.documentStatisticsMap = documentStatisticsMap;
        if (threshold == null) {
            ClassVariance classVariance = new ClassVariance(similarityMap.values());
            classVariance.calculateBetweenClassVariance();
            this.threshold = classVariance.getPredictedThreshold();
        } else
            this.threshold = threshold;
    }

    public void displaySimilarityResults() {
        Map<String, Double> documentsSimilarityMap = sortMapDescending();
        int count = 0;
        LOGGER.log(Level.INFO, "Threshold: {0}%", threshold);
        for (Map.Entry<String, Double> fileEntry : documentsSimilarityMap.entrySet()) {
            if (fileEntry.getValue() > threshold) {
                LOGGER.log(Level.INFO, "{0} -> {1}%", new Object[]{fileEntry.getKey(), DoubleUtil.prettifyDouble(fileEntry.getValue(), PLACES)});
                displayStatisticalDataWithinDocumentsPairs(fileEntry.getKey());
                count++;
            }
        }
        LOGGER.log(Level.INFO, "Number of possible plagiarisms: {0}", count);
    }

    // font: https://www.geeksforgeeks.org/sorting-a-hashmap-according-to-values/
    public Map<String, Double> sortMapDescending() {
        List<Map.Entry<String, Double>> entryList = new ArrayList<>(similarityMap.entrySet());
        entryList.sort((value1, value2) -> value2.getValue().compareTo(value1.getValue()));
        Map<String, Double> resultMap = new HashMap<>();
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