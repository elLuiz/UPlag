package com.br.uplag.weight;

import java.util.Map;

public class TfIdfWeight extends Weight{
    public TfIdfWeight(Map<String, Map<String, Integer>> invertedIndexMap, Integer collectionSize) {
        super(invertedIndexMap, collectionSize);
    }

    @Override
    public Map<String, Map<String, Double>> calculateTermWeight() {
        for (Map.Entry<String, Map<String, Integer>> invertedIndexEntry : invertedIndexMap.entrySet()) {
            for (Map.Entry<String, Integer> termFrequencyMap : invertedIndexEntry.getValue().entrySet()) {
                String document = termFrequencyMap.getKey();
                Integer termFrequency = termFrequencyMap.getValue();
                this.setTermFrequencyInCollection(invertedIndexEntry.getValue().size());
                Double weight = calculateWeight(termFrequency);
                insertTermWeightIntoMap(invertedIndexEntry, document, weight);
            }
        }
        return termWeightMap;
    }

    public double calculateWeight(int termFrequency) {
        return (1 + Math.log10(termFrequency))*calculateIDF();
    }
}
