package com.br.uplag.similarity;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

public class SimilarityCalculator {
    protected Map<String, Map<String, Double>> generateTermWeightMap() {
        Map<String, Double> termAWeight = new HashMap<>();
        termAWeight.put("ABC", 0.931);
        termAWeight.put("LAX", 0.0);
        termAWeight.put("XCA", 0.80);

        Map<String, Double> termBWeight = new HashMap<>();
        termBWeight.put("ABC", 0.0021);
        termBWeight.put("LAX", 0.0);
        termBWeight.put("XCA", 0.3234);

        Map<String, Double> termCWeight = new HashMap<>();
        termCWeight.put("ABC", 0.02320);
        termCWeight.put("LAX", 0.100);
        termCWeight.put("XCA", 0.1872480);

        Map<String, Map<String, Double>> weightMap = new HashMap<>();
        weightMap.put("doc1.c", termAWeight);
        weightMap.put("doc2.c", termBWeight);
        weightMap.put("doc3.c", termCWeight);

        return weightMap;
    }

    protected double roundDouble(double value) {
        var decimalFormat = new DecimalFormat("#.##");
        return Double.parseDouble(decimalFormat.format(value));
    }
}
