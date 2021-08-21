package weight;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class NormalizedWeight extends Weight{
    public NormalizedWeight(Map<String, Map<String, Integer>> invertedIndexMap, Integer collectionSize) {
        super(invertedIndexMap, collectionSize);
    }

    @Override
    public Map<String, Map<String, Double>> getTermsWeight() {
        Map<String, Integer> documentsMaxTF = findTheMaxTermFrequencyInDocument();
        Map<String, Map<String, Double>> termWeightMap = new LinkedHashMap<>();
        for (Map.Entry<String, Map<String, Integer>> invertedIndexEntry : invertedIndexMap.entrySet()) {
            for (Map.Entry<String, Integer> termFrequencyMap : invertedIndexEntry.getValue().entrySet()) {
                String document = termFrequencyMap.getKey();
                Integer termFrequency = termFrequencyMap.getValue();
                int maxTF = documentsMaxTF.get(document);
                Double weight = calculateWeight(termFrequency, maxTF) * calculateIDF(termFrequency);
                Map<String, Double> documentWeightMap = new HashMap<>();
                documentWeightMap.put(invertedIndexEntry.getKey(), weight);
                if (termWeightMap.get(document) == null)
                    termWeightMap.put(document, documentWeightMap);
                else
                    termWeightMap.get(document).put(invertedIndexEntry.getKey(), weight);
            }
        }
        return termWeightMap;
    }

    @Override
    public float calculateWeight(int tf, int maxTF) {
        if (maxTF > 0)
            return (float) (0.4 + 0.6 * ((float) tf / maxTF));
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
