package com.br.uplag.input;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class ThresholdInputReaderTest {
    @Test
    public void shouldDefineThresholdSimilarity() {
        String []args = new String[4];
        args[0] = "-nidf";
        args[1] = "-cosine";
        args[2] = "-t";
        args[3] = "50";
        ThresholdInputReader thresholdInputReader = new ThresholdInputReader(args);
        thresholdInputReader.defineParameter();
        Assert.assertEquals(Integer.valueOf(50), thresholdInputReader.getSimilarityThreshold());
    }

    @Test
    public void shouldNotDefineThresholdValueWhenNumberIsInvalid() {
        String []args = new String[4];
        args[0] = "-nidf";
        args[1] = "-cosine";
        args[2] = "-t";
        args[3] = "5akjs0";
        ThresholdInputReader thresholdInputReader = new ThresholdInputReader(args);
        thresholdInputReader.defineParameter();
        Assert.assertEquals(null, thresholdInputReader.getSimilarityThreshold());
    }
}
