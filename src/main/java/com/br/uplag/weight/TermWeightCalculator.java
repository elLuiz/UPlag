package com.br.uplag.weight;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class TermWeightCalculator {
    protected Map<String, Map<String, Integer>> invertedIndexMap;
    protected List<String> programs;
    protected Integer collectionSize;
    protected Integer termFrequencyInCollection;
    protected  Map<String, Map<String, Double>> termWeightMap;

    protected TermWeightCalculator(Map<String, Map<String, Integer>> invertedIndexMap, List<String> programs) {
        this.invertedIndexMap = invertedIndexMap;
        this.programs = programs;
        this.collectionSize = programs.size();
        termWeightMap = new HashMap<>();
    }

    protected double calculateIDF() {
        return Math.log10((double) collectionSize / termFrequencyInCollection);
    }

    protected void insertTermWeightIntoMap(Map.Entry<String, Map<String, Integer>> invertedIndexEntry, String document, Double weight) {
        Map<String, Double> documentWeightMap = new HashMap<>();
        documentWeightMap.put(invertedIndexEntry.getKey(), weight);
        if (termWeightMap.get(document) == null)
            termWeightMap.put(document, documentWeightMap);
        else
            termWeightMap.get(document).put(invertedIndexEntry.getKey(), weight);
    }

    public abstract Map<String, Map<String, Double>> calculateTermWeight();

    public void setTermFrequencyInCollection(Integer termFrequencyInCollection) {
        this.termFrequencyInCollection = termFrequencyInCollection;
    }
}
