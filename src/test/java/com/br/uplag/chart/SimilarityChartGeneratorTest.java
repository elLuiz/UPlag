package com.br.uplag.chart;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.File;

@RunWith(MockitoJUnitRunner.class)
public class SimilarityChartGeneratorTest {
    private SimilarityChartGenerator similarityChartGenerator;

    @Before
    public void init() {
        similarityChartGenerator = new SimilarityChartGenerator("src/test/resources/");
    }

    @Test
    public void shouldCreateChart() {
        double[] similarities = new double[10];
        similarities[0] = 99.1;
        similarities[1] = 0.02;
        similarities[2] = 3;
        similarities[3] = 15.32;
        similarities[4] = 50.12;
        similarities[5] = 56.21;
        similarities[6] = 52;
        similarities[7] = 68.2;
        similarities[8] = 40;
        similarities[9] = 96.41;
        similarityChartGenerator.createBarChart(similarities);
        Assert.assertEquals(true, new File("src/test/resources/histogram.png").isFile());
    }

    @Test
    public void shouldThrowExceptionWhenPathDoesNotExist() {
        SimilarityChartGenerator similarityChartGenerator = new SimilarityChartGenerator("src/whaa/tasa");
        similarityChartGenerator.createBarChart(new double[10]);
    }
}
