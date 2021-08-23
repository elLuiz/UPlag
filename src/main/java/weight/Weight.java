package weight;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
// TODO: REFACTOR THIS, CREATE A NEW CLASS FOR GETTING THE DOCUMENT FREQUENCIES
// AND THIS WILL BE THE DEPENDENCY THERE
public abstract class Weight {
    protected Map<String, Map<String, Integer>> invertedIndexMap;
    protected Integer collectionSize;
    protected Integer termFrequencyInCollection;
    protected  Map<String, Map<String, Double>> termWeightMap;

    protected Weight(Map<String, Map<String, Integer>> invertedIndexMap, Integer collectionSize) {
        this.invertedIndexMap = invertedIndexMap;
        this.collectionSize = collectionSize;
        termWeightMap = new LinkedHashMap<>();
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
