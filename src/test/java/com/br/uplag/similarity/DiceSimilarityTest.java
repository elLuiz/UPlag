package com.br.uplag.similarity;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public class DiceSimilarityTest extends SimilarityCalculator {
    private CodeSimilarity codeSimilarity;

    @Before
    public void setUp() {
        this.codeSimilarity = new DiceSimilarity(generateTermWeightMap());
    }

    @Test
    public void shouldCalculateSimilarity() {
        Map<String, Double> diceSimilarity = codeSimilarity.calculateDocumentSimilarity();
        Assert.assertEquals(Arrays.asList(32.35, 22.08, 80.7), diceSimilarity.values().stream().map(this::roundDouble).collect(Collectors.toList()));
    }
}