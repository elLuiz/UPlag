package com.br.uplag.threshold.otsu;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

public class OtsuThresholdTest {
    private OtsuThreshold otsuThreshold;

    @Test
    public void shouldGetMaxThresholdPosition() {
        List<Double> classVariances = Arrays.asList(7.0, 1.0, 0.0, 2.0, 8.0);
        otsuThreshold = new OtsuThreshold(classVariances);

        Assert.assertEquals(Double.valueOf(40.0), otsuThreshold.findMaxThresholdValue());
    }

    @Test
    public void shouldGetPositionsAverageWhenArrayContainsRepetitiveValues() {
        List<Double> classVariances = Arrays.asList(7.0, 8.0, 0.0, 8.0, 8.0);
        otsuThreshold = new OtsuThreshold(classVariances);

        Assert.assertEquals(Double.valueOf(26.67), otsuThreshold.findMaxThresholdValue());
    }
}