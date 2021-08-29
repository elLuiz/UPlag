package com.br.uplag.weight;

import com.br.uplag.util.StringUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NormalizedWeight extends Weight{
    private static final Double SMOOTHING_FACTOR = 0.4;

    public NormalizedWeight(Map<String, Map<String, Integer>> invertedIndexMap, List<String> programs) {
        super(invertedIndexMap, programs);
    }

    @Override
    public Map<String, Map<String, Double>> calculateTermWeight() {
        Map<String, Integer> documentsMaxTF = findTheMaxTermFrequencyInDocument();
        for (Map.Entry<String, Map<String, Integer>> invertedIndexEntry : invertedIndexMap.entrySet()) {
            for (String document : programs) {
                document = StringUtil.getFileNameAfterLastSlash(document);
                Integer termFrequency = invertedIndexEntry.getValue().get(document);
                termFrequency = termFrequency == null ? 0 : termFrequency;
                int maxTF = documentsMaxTF.get(document) == null ? 0 : documentsMaxTF.get(document) ;
                this.setTermFrequencyInCollection(invertedIndexEntry.getValue().size());
                Double weight = calculateWeight(termFrequency, maxTF);
                insertTermWeightIntoMap(invertedIndexEntry, document, weight);
            }
        }
        return termWeightMap;
    }

    public double calculateWeight(int tf, int maxTF) {
        if (maxTF > 0)
            return (SMOOTHING_FACTOR + (1 - SMOOTHING_FACTOR) * ((float) tf / maxTF)) * calculateIDF();
        return 0.0F;
    }

    public Map<String, Integer> findTheMaxTermFrequencyInDocument() {
        Map<String, Integer> documentByMaxTermFrequency = new HashMap<>();
        for (Map<String, Integer> documentTermOccurrences : invertedIndexMap.values()) {
            for (Map.Entry<String, Integer> termOccurrence : documentTermOccurrences.entrySet()) {
                Integer value = termOccurrence.getValue();
                String documentName = termOccurrence.getKey();
                if (documentByMaxTermFrequency.get(documentName) == null) {
                    documentByMaxTermFrequency.put(documentName, value);
                } else {
                    Integer documentMaxTF = documentByMaxTermFrequency.get(documentName);
                    if (value > documentMaxTF)
                        documentByMaxTermFrequency.put(documentName, value);
                }
            }
        }

        return documentByMaxTermFrequency;
    }
}
