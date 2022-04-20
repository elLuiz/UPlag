package com.br.uplag.result;


import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class DocumentStatisticsTest {

    @Test
    public void shouldCalculateContainment() {
        var documentAStatistics = new DocumentStatistics();
        documentAStatistics.setNGrams(new String[]{"abc", "bca", "ace", "dse"});

        var documentBStatistics = new DocumentStatistics();
        documentBStatistics.setNGrams(new String[]{"abc", "bca", "aee", "dde"});

        documentAStatistics.calculateContainment(documentBStatistics);

        Assert.assertEquals(Double.valueOf(0.5), documentAStatistics.getContainment());
    }
}