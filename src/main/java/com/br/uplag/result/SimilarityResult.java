package com.br.uplag.result;

import com.br.uplag.threshold.otsu.ClassVariance;
import com.br.uplag.util.DoubleUtil;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class SimilarityResult {
    private static final Logger LOGGER = Logger.getLogger(SimilarityResult.class.getSimpleName());

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
        LOGGER.log(Level.INFO, "Threshold: {0}%", threshold);
        for (Map.Entry<String, Double> fileEntry : getPlagiarizedFilesOrderedBySimilarityRank().entrySet()) {
            if (fileEntry.getValue() > threshold) {
                LOGGER.log(Level.INFO, "{0} -> {1}%", new Object[]{fileEntry.getKey(), DoubleUtil.prettifyDouble(fileEntry.getValue(), PLACES)});
                displayStatisticsForPair(new Pair(fileEntry.getKey()));
                count++;
            }
        }
        LOGGER.log(Level.INFO, "Number of possible plagiarisms: {0}", count);
    }

    private Map<String, Double> getPlagiarizedFilesOrderedBySimilarityRank() {
        Map<String, Double> resultMap = new HashMap<>();
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
        LOGGER.info("------------------------------------------------------");
    }

    private void displayNumberOfTokens(String filename, DocumentStatistics documentAStatistics) {
        LOGGER.log(Level.INFO, "Document {0}: {1}", new Object[]{filename, documentAStatistics});
    }

    private void displayContainment(String documentOne, String documentTwo, DocumentStatistics documentAStatistics) {
        LOGGER.log(Level.INFO, "Containment of {0} in {1} -> {2}%", new Object[]{documentOne, documentTwo, DoubleUtil.prettifyDouble(documentAStatistics.getContainment() * 100, PLACES)});
    }

}