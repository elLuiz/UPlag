package com.br.uplag.input;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class LanguageInputReaderTest {
    @Test
    public void shouldDefineLanguage() {
        String[] args = new String[5];
        args[0] = "-d";
        args[1] = "/home/whater";
        args[2] = "-l";
        args[3] = "java";
        LanguageInputReader inputReader = new LanguageInputReader(args);
        inputReader.defineParameter();
        Assert.assertEquals("java", inputReader.getLanguage());
    }

    @Test
    public void shouldSetDefaultLanguageWhenFlagIsMissing() {
        String[] args = new String[5];
        args[0] = "-d";
        args[1] = "/home/whater";
        args[2] = "-tfidf";
        args[3] = "-t";
        args[4] = "60";
        LanguageInputReader inputReader = new LanguageInputReader(args);
        inputReader.defineParameter();
        Assert.assertEquals("c", inputReader.getLanguage());
    }
}
