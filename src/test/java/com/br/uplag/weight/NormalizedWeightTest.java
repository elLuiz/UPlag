package com.br.uplag.weight;

import index.InvertedIndex;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import reader.CReader;
import reader.Reader;
import util.FileInputUtil;
import weight.NormalizedWeight;

import java.util.*;

@RunWith(JUnit4.class)
public class NormalizedWeightTest {

    @Test
    public void shouldFindTheMaxTFInEachDocument() {
        Map<String, Map<String, Integer>> weightMap = new HashMap<>();
        Map<String, Integer> funMap = new HashMap<>();
        funMap.put("doc1", 6);
        weightMap.put("FUN", funMap);
        Map<String, Integer> assignMap = new HashMap<>();
        assignMap.put("doc1", 2);
        weightMap.put("ASSIGN", assignMap);
        Map<String, Integer> funMapDoc2 = new HashMap<>();
        funMapDoc2.put("doc3", 10);
        weightMap.put("FUNC", funMapDoc2);
        NormalizedWeight normalizedWeight = new NormalizedWeight(weightMap, 2);
        Assert.assertEquals(Arrays.asList(10, 6), new ArrayList<>(normalizedWeight.findTheMaxTermFrequencyInDocument().values()));
    }

    @Test
    public void shouldGetTermsWeight() {
        List<String> files = FileInputUtil.walkTroughDirectory("src/test/resources", "c");
        Reader reader = new CReader();
        Map<String, String> stringStringMap = reader.startReadingInputFiles(files);
        InvertedIndex invertedIndex = new InvertedIndex();
        invertedIndex.createInvertedIndex(stringStringMap);
        NormalizedWeight normalizedWeight = new NormalizedWeight(invertedIndex.getInvertedIndex(), files.size());
        normalizedWeight.calculateTermWeight();
    }
}
