package com.br.uplag.util;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@RunWith(JUnit4.class)
public class DoubleListUtilTest {
    private List<Double> prob = Arrays.asList(39.3, 23.3, 20.2, 40.3);
    @Test
    public void shouldGetSubList() {
        Assert.assertEquals(2, DoubleListUtil.getSubListList(prob, 0, 2).size());
    }

    @Test
    public void shouldGetOnlyOneElementWhenStartPositionIsGreaterThanIndex() {
        Assert.assertEquals(Collections.singletonList(40.3), DoubleListUtil.getSubListList(prob, 4, 3));
    }

    @Test
    public void shouldSumList() {
        Assert.assertEquals(Double.valueOf(123.1), DoubleListUtil.sumList(prob));
    }

    @Test
    public void shouldMultiplyListOfProbabilities() {
        Assert.assertEquals(Double.valueOf(184.6), DoubleListUtil.multiplyListOfProbabilities(prob));
    }

    @Test
    public void shouldCalculateMean() {
        Assert.assertEquals(Double.valueOf(1.4995938261575954), DoubleListUtil.calculateMean(prob, 123.1));
    }

    @Test
    public void shouldCalculateVariance() {
        Assert.assertEquals(Double.valueOf(1.5432573492388943), DoubleListUtil.calculateVariance(prob, 1.499593826, 123.1));
    }
}
