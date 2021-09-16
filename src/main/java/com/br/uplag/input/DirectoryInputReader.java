package com.br.uplag.input;

import com.br.uplag.parameters.ParametersInputRegex;

import java.util.logging.Logger;

public class DirectoryInputReader extends TerminalInputReader implements InputReader{
    private static final Logger LOGGER = Logger.getLogger(DirectoryInputReader.class.getSimpleName());

    public DirectoryInputReader() {
    }

    public DirectoryInputReader(String[] arguments) {
        super(arguments);
    }

    @Override
    public void defineParameter() {
        if (argumentContainsProperty(ParametersInputRegex.DIRECTORY)) {
            int index = arguments.indexOf(ParametersInputRegex.DIRECTORY.getParameter());
            if (index >= 0)
                directory = arguments.get(index + 1);
        } else {
            LOGGER.severe("You must specify a path.");
            System.exit(1);
        }
    }
}