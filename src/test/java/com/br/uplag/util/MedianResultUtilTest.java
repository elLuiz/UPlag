package com.br.uplag.util;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.Arrays;
import java.util.List;

@RunWith(JUnit4.class)
public class MedianResultUtilTest {
    @Test
    public void shouldDisplayMean() {
        List<Double> doubles = Arrays.asList(1.0, 2.0, 3.0, 4.0, 5.0);
        MedianResultUtil.displayMedianValue(doubles);
    }

    @Test
    public void shouldDisplayMeanWhenListIsEven() {
        List<Double> doubles = Arrays.asList(1.0, 2.0, 3.0, 4.0, 5.0, 6.0);
        MedianResultUtil.displayMedianValue(doubles);
    }
}
