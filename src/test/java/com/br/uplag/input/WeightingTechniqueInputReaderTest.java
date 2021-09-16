package com.br.uplag.input;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class WeightingTechniqueInputReaderTest {
    @Test
    public void shouldDefineParameterWhenNIDFIsSpecified() {
        String[] args = new String[2];
        args[0] = "java";
        args[1] = "-nidf";
        WeightingTechniqueInputReader terminalInputReader = new WeightingTechniqueInputReader(args);
        terminalInputReader.defineParameter();
        Assert.assertEquals("-nidf", terminalInputReader.getWeightingTechnique());
    }
}
