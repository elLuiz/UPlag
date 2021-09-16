package com.br.uplag.input;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class ProgramsInputReaderTest {
    @Test
    public void shouldDefinePrograms() {
        String[] args = new String[4];
        args[0] = "-d";
        args[1] = "src/test/resources/";
        args[2] = "-p";
        args[3] = ".c";
        ProgramsInputReader programsInputReader = new ProgramsInputReader(args);
        programsInputReader.setDirectory("src/test/resources/");
        programsInputReader.defineParameter();
        Assert.assertEquals(14, programsInputReader.getPrograms().size());

    }
}