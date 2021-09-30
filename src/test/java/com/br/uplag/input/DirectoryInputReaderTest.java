package com.br.uplag.input;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class DirectoryInputReaderTest {
    @Test
    public void shouldDefineParameter() {
        String[] args = new String[3];
        args[0] = "-d";
        args[1] = "/home/whater";
        DirectoryInputReader directoryInputReader = new DirectoryInputReader(args);
        directoryInputReader.defineParameter();
        Assert.assertEquals("/home/whater", directoryInputReader.getDirectory());
    }

    @Test
    public void shouldNotDefineDirectoryWhenFlagIsNotSet() {
        String[] args = new String[3];
        args[0] = "";
        args[1] = "/home/whater";
        DirectoryInputReader directoryInputReader = new DirectoryInputReader(args);
        directoryInputReader.defineParameter();
        Mockito.verify(directoryInputReader).defineParameter();
    }
}
