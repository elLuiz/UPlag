package com.br.uplag.result;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class SimilarityResultTest {
    private SimilarityResult similarityResult;
    private Map<String, Double> similarityMap;
    private Map<String, DocumentStatistics> documentStatisticsMap;

    @Before
    public void setUp() {
        similarityMap = new HashMap<>();
        similarityMap.put("(file1, file2)", 34.90);
        similarityMap.put("(file2, file3)", 50.0);

        documentStatisticsMap = new HashMap<>();
        documentStatisticsMap.put("file1", new DocumentStatistics(3, new String[]{"abc", "cde", "ghr"}));
        documentStatisticsMap.put("file2", new DocumentStatistics(4, new String[]{"abc", "ewe", "kds", "sdlk"}));
        documentStatisticsMap.put("file3", new DocumentStatistics(2, new String[]{"abc", "ewe"}));
    }


    @Test
    public void shouldDisplayStatisticsByPair() {
        similarityResult = new SimilarityResult(similarityMap, documentStatisticsMap, 30.0);
        similarityResult.displaySimilarityResults();

        Assert.assertEquals(Double.valueOf(1.0), documentStatisticsMap.get("file3").getContainment());
    }

    @Test
    public void shouldDisplayStatisticsByOtsuThreshold() {
        similarityResult = new SimilarityResult(similarityMap, documentStatisticsMap, null);
        similarityResult.displaySimilarityResults();

        Assert.assertEquals(Double.valueOf(1.0), documentStatisticsMap.get("file3").getContainment());
    }
}