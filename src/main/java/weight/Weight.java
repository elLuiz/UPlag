package weight;

import java.util.Map;

public abstract class Weight {
    protected Map<String, Map<String, Integer>> invertedIndexMap;
    protected Integer collectionSize;

    protected Weight(Map<String, Map<String, Integer>> invertedIndexMap, Integer collectionSize) {
        this.invertedIndexMap = invertedIndexMap;
        this.collectionSize = collectionSize;
    }

    protected double calculateIDF(int termFrequency) {
        return Math.log10((double) collectionSize / termFrequency);
    }

    public abstract float calculateWeight(int tf, int maxTF);
    public abstract Map<String, Map<String, Double>> getTermsWeight();
}
