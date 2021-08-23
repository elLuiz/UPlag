package weight;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class NormalizedWeight extends Weight{
    private static final Double SMOOTHING_FACTOR = 0.4;
    public NormalizedWeight(Map<String, Map<String, Integer>> invertedIndexMap, Integer collectionSize) {
        super(invertedIndexMap, collectionSize);
    }

    @Override
    public Map<String, Map<String, Double>> calculateTermWeight() {
        Map<String, Integer> documentsMaxTF = findTheMaxTermFrequencyInDocument();
        Map<String, Map<String, Double>> termWeightMap = new LinkedHashMap<>();
        for (Map.Entry<String, Map<String, Integer>> invertedIndexEntry : invertedIndexMap.entrySet()) {
            for (Map.Entry<String, Integer> termFrequencyMap : invertedIndexEntry.getValue().entrySet()) {
                String document = termFrequencyMap.getKey();
                Integer termFrequency = termFrequencyMap.getValue();
                int maxTF = documentsMaxTF.get(document);
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
