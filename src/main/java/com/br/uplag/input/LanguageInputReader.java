package com.br.uplag.input;

import com.br.uplag.parameters.ParametersInputRegex;

public class LanguageInputReader extends TerminalInputReader implements InputReader{
    public LanguageInputReader() {
    }

    public LanguageInputReader(String[] arguments) {
        super(arguments);
    }

    @Override
    public void defineParameter() {
        if (argumentContainsProperty(ParametersInputRegex.LANGUAGE)) {
            int index = arguments.indexOf(ParametersInputRegex.LANGUAGE.getParameter());
            if (index > 0) {
                language = arguments.get(index + 1);
            }
        }
    }
}
