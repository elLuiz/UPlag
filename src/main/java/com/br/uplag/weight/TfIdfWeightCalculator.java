package com.br.uplag.weight;

import com.br.uplag.util.StringUtil;

import java.util.List;
import java.util.Map;

public class TfIdfWeightCalculator extends TermWeightCalculator {
    public TfIdfWeightCalculator(Map<String, Map<String, Integer>> invertedIndexMap, List<String> programs) {
        super(invertedIndexMap, programs);
    }

    @Override
    public Map<String, Map<String, Double>> calculateTermWeight() {
        for (Map.Entry<String, Map<String, Integer>> invertedIndexEntry : invertedIndexMap.entrySet()) {
            for (String document : programs) {
                document = StringUtil.getFileNameAfterLastSlash(document);
                Integer termFrequency = invertedIndexEntry.getValue().get(document);
                termFrequency = termFrequency == null ? 0 : termFrequency;
                this.setTermFrequencyInCollection(invertedIndexEntry.getValue().size());
                Double weight = calculateWeight(termFrequency);
                insertTermWeightIntoMap(invertedIndexEntry, document, weight);
            }
        }
        return termWeightMap;
    }

    public double calculateWeight(int termFrequency) {
        return termFrequency > 0 ? (termFrequency) * calculateIDF() :  0.0;
    }
}