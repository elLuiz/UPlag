package com.br.uplag.histogram;

import com.br.uplag.threshold.otsu.Histogram;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.ArrayList;
import java.util.Collection;

@RunWith(JUnit4.class)
public class HistogramTest {
    private Histogram histogram;

    @Before
    public void setUp() {
        this.histogram = new Histogram();
    }

    @Test
    public void shouldCreateHistogram() {
        histogram.createHistogram(createPercentageList());
        Assert.assertEquals(Integer.valueOf(2), histogram.getHistogramMap().get(5).getOccurrences());
    }

    private Collection<Double> createPercentageList() {
        Collection<Double> doubles = new ArrayList<>();
        doubles.add(59.2);
        doubles.add(100.0);
        doubles.add(34.4);
        doubles.add(0.595959);
        doubles.add(5.929);
        doubles.add(50.0);
        return doubles;
    }
}
