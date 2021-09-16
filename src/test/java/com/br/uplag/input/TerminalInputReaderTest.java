package com.br.uplag.input;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class TerminalInputReaderTest {
    @Test
    public void shouldDefineYouPlagParameters() {
        String[]args = new String[5];
        args[0] = "-d";
        args[1] = "src/test/resources";
        args[2] = "-nidf";
        args[3] = "-p";
        args[4] = ".c";
        TerminalInputReader terminalInputReader = new TerminalInputReader(args);
        terminalInputReader.defineYouPlagDataInput();
    }
}
