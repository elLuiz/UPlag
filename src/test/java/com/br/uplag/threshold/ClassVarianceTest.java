package com.br.uplag.threshold;

import com.br.uplag.threshold.otsu.ClassVariance;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.Arrays;
import java.util.Collection;

@RunWith(JUnit4.class)
public class ClassVarianceTest {
    private ClassVariance classVariance;

    @Before
    public void init() {
        Collection<Double> similarity = Arrays.asList(0.9, 0.1, 0.2, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.2, 2.0, 3.0, 1.0, 1.1, 1.0, 28.0, 27.0, 26.0, 22.0,
                31.0, 32.0, 34.0, 35.0, 31.0, 40.0, 42.0, 43.0, 45.0, 41.0, 45.0, 46.0, 41.0, 53.0, 54.0, 51.0, 50.0);
        classVariance = new ClassVariance(similarity);
    }

    @Test
    public void shouldGetPredictedThreshold() {
        classVariance.calculateBetweenClassVariance();
        Assert.assertEquals(Double.valueOf(0.0), classVariance.getPredictedThreshold());
    }
}
