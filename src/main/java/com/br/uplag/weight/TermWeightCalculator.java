package com.br.uplag.weight;

import com.br.uplag.util.StringUtil;

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

    public Map<String, Map<String, Double>> calculateTermWeight() {
        for (Map.Entry<String, Map<String, Integer>> invertedIndexEntry : invertedIndexMap.entrySet()) {
            for (String document : programs) {
                document = StringUtil.getFileNameAfterLastSlash(document);
                this.setTermFrequencyInCollection(invertedIndexEntry.getValue().size());
                Double weight = calculateWeight(getTermFrequencyInDocument(invertedIndexEntry, document));
                insertTermWeightIntoMap(invertedIndexEntry, document, weight);
            }
        }
        return termWeightMap;
    }

    private Integer getTermFrequencyInDocument(Map.Entry<String, Map<String, Integer>> invertedIndexEntry, String document) {
        Integer termFrequency = invertedIndexEntry.getValue().get(document);
        return termFrequency == null ? 0 : termFrequency;
    }

    protected void insertTermWeightIntoMap(Map.Entry<String, Map<String, Integer>> invertedIndexEntry, String document, Double weight) {
        Map<String, Double> documentWeightMap = new HashMap<>();
        documentWeightMap.put(invertedIndexEntry.getKey(), weight);
        if (termWeightMap.get(document) == null)
            termWeightMap.put(document, documentWeightMap);
        else
            termWeightMap.get(document).put(invertedIndexEntry.getKey(), weight);
    }

    public void setTermFrequencyInCollection(Integer termFrequencyInCollection) {
        this.termFrequencyInCollection = termFrequencyInCollection;
    }

    protected abstract double calculateWeight(int termFrequency);

    protected double calculateIDF() {
        return Math.log10((double) collectionSize / termFrequencyInCollection);
    }
}