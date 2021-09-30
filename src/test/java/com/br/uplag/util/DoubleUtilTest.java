package com.br.uplag.util;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.Arrays;
import java.util.List;

@RunWith(JUnit4.class)
public class DoubleUtilTest {
    @Test
    public void shouldPrettifyDouble() {
        Double value = 50.3049039343;
        Assert.assertEquals(Double.valueOf(50.31), DoubleUtil.prettifyDouble(value, 2));
    }

    @Test
    public void shouldConvertListOfDoubleToPrimitiveArray() {
        List<Double> doubles = Arrays.asList(2.0, 40.0, 10.1);
        Assert.assertEquals(3, DoubleUtil.convertListOfDoubleToPrimitiveArray(doubles).length);
    }
}
