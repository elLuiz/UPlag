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
        Map<String, Double> documentsSimilarityMap = sortDescendingBySimilarity();
        int count = 0;
        LOGGER.log(Level.INFO, "Threshold: {0}%", threshold);
        for (Map.Entry<String, Double> fileEntry : documentsSimilarityMap.entrySet()) {
            if (fileEntry.getValue() > threshold) {
                LOGGER.log(Level.INFO, "{0} -> {1}%", new Object[]{fileEntry.getKey(), DoubleUtil.prettifyDouble(fileEntry.getValue(), PLACES)});
                displayStatisticsForPair(new Pair(fileEntry.getKey()));
                count++;
            }
        }
        LOGGER.log(Level.INFO, "Number of possible plagiarisms: {0}", count);
    }

    // font: https://www.geeksforgeeks.org/sorting-a-hashmap-according-to-values/
    private Map<String, Double> sortDescendingBySimilarity() {
        List<Map.Entry<String, Double>> entryList = new ArrayList<>(similarityMap.entrySet());
        entryList.sort((value1, value2) -> value2.getValue().compareTo(value1.getValue()));
        Map<String, Double> resultMap = new HashMap<>();
        for (Map.Entry<String, Double> entry : entryList) {
            resultMap.put(entry.getKey(), entry.getValue());
        }

        return resultMap;
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
        LOGGER.info("------------------------------------------------------");
    }

    private void displayNumberOfTokens(String filename, DocumentStatistics documentAStatistics) {
        LOGGER.log(Level.INFO, "Document {0}: {1}", new Object[]{filename, documentAStatistics});
    }

    private void displayContainment(String documentOne, String documentTwo, DocumentStatistics documentAStatistics) {
        LOGGER.log(Level.INFO, "Containment of {0} in {1} -> {2}%", new Object[]{documentOne, documentTwo, DoubleUtil.prettifyDouble(documentAStatistics.getContainment() * 100, PLACES)});
    }

}