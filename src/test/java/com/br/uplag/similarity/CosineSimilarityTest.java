package com.br.uplag.similarity;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class CosineSimilarityTest extends SimilarityCalculator {
    private CodeSimilarity codeSimilarity;

    @Before
    public void setUp() {
        this.codeSimilarity = new CosineSimilarity(generateTermWeightMap());
    }

    @Test
    public void shouldCalculateCosineSimilarity() {
        Map<String, Double> documentsSimilarities = codeSimilarity.calculateDocumentSimilarity();
        Set<String> expectedPairs = new HashSet<>();
        expectedPairs.add("(doc1.c, doc2.c)");
        expectedPairs.add("(doc1.c, doc3.c)");
        expectedPairs.add("(doc2.c, doc3.c)");

        Assert.assertEquals(expectedPairs, documentsSimilarities.keySet());
        Assert.assertEquals(Arrays.asList(65.66, 65.39, 87.76), documentsSimilarities.values().stream().map(this::roundDouble).collect(Collectors.toList()));
    }
}