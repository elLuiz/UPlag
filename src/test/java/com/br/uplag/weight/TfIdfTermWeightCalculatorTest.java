package com.br.uplag.weight;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.*;

@RunWith(JUnit4.class)
public class TfIdfTermWeightCalculatorTest {
    private TermWeightCalculator termWeightCalculator;

    @Before
    public void init() {
        Map<String, Map<String, Integer>> invertedIndex = generateInvertedIndex();
        termWeightCalculator = new TfIdfWeightCalculator(invertedIndex, getPrograms());
    }

    private Map<String, Map<String, Integer>> generateInvertedIndex() {
        Map<String, Map<String, Integer>> invertedIndex = new LinkedHashMap<>();
        Map<String, Integer> termAFrequency = new HashMap<>();
        termAFrequency.put("doc1", 3);
        termAFrequency.put("doc2", 2);
        termAFrequency.put("doc3", 2);
        Map<String, Integer> termBFrequency = new HashMap<>();
        termBFrequency.put("doc1", 1);
        termBFrequency.put("doc4", 2);
        Map<String, Integer> termCFrequency = new HashMap<>();
        termCFrequency.put("doc2", 1);
        invertedIndex.put("A", termAFrequency);
        invertedIndex.put("B", termBFrequency);
        invertedIndex.put("C", termCFrequency);
        return invertedIndex;
    }

    private List<String> getPrograms() {
        return Arrays.asList("doc1", "doc2", "doc3", "doc4");
    }

    @Test
    public void shouldCalculateTermWeight() {
        Map<String, Map<String, Double>> resultMap = termWeightCalculator.calculateTermWeight();
        List<Double> collectionTermsWeightInDoc1 = Arrays.asList(0.3748162098248998, 0.3010299956639812, 0.0);
        Assert.assertEquals(collectionTermsWeightInDoc1, new ArrayList<>(resultMap.get("doc1").values()));
    }
}
