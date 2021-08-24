package com.br.uplag.weight;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

@RunWith(JUnit4.class)
public class TfIdfWeightTest {
    private Weight weight;

    @Before
    public void init() {
        Map<String, Map<String, Integer>> invertedIndex = new LinkedHashMap<>();
        Map<String, Integer> tfA = new LinkedHashMap<>();
        tfA.put("doc1", 3);
        tfA.put("doc2", 2);
        tfA.put("doc3", 2);
        Map<String, Integer> tfB = new LinkedHashMap<>();
        tfB.put("doc1", 1);
        tfB.put("doc4", 2);
        Map<String, Integer> tfC = new LinkedHashMap<>();
        tfC.put("doc2", 1);
        invertedIndex.put("A", tfA);
        invertedIndex.put("B", tfB);
        invertedIndex.put("C", tfC);
        weight = new TfIdfWeight(invertedIndex, 4);
    }

    @Test
    public void shouldCalculateTermWeight() {
        Map<String, Map<String, Double>> resultMap = weight.calculateTermWeight();
        Assert.assertEquals(Arrays.asList(0.18454966338194143, 0.3010299956639812), new ArrayList<>(resultMap.get("doc1").values()));
    }
}
