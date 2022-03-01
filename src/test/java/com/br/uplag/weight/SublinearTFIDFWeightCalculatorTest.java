package com.br.uplag.weight;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@RunWith(JUnit4.class)
public class SublinearTFIDFWeightCalculatorTest {
    private TermWeightCalculator termWeightCalculator;

    @Before
    public void setUp() {
        this.termWeightCalculator = new SublinearTFIDFWeightCalculator(generateInvertedIndex(), getProgramsList());
    }

    @Test
    public void shouldGetTermsWeight() {
        Map<String, Map<String, Double>> termWeight = termWeightCalculator.calculateTermWeight();
        Set<String> termsKeyset = new HashSet<>();
        termsKeyset.add("one");
        termsKeyset.add("two");

        Assert.assertEquals(termsKeyset, termWeight.keySet());
        Assert.assertTrue(termWeight.get("one").values().stream().anyMatch(weight -> weight > 0.0));
        Assert.assertTrue(termWeight.get("two").values().stream().anyMatch(weight -> weight > 0.0));
    }

    private List<String> getProgramsList() {
        return List.of("one.c", "two.c");
    }

    private Map<String, Map<String, Integer>> generateInvertedIndex() {
        Map<String, Integer> termOneFrequency = new HashMap<>();
        termOneFrequency.put("one", 4);
        Map<String, Integer> termTwoFrequency = new HashMap<>();
        termTwoFrequency.put("two", 7);

        Map<String, Map<String, Integer>> invertedIndex = new HashMap<>();
        invertedIndex.put("A", termOneFrequency);
        invertedIndex.put("B", termTwoFrequency);

        return invertedIndex;
    }
}