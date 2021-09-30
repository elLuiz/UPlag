package com.br.uplag.input;

import com.br.uplag.parameters.ParametersInputRegex;

public class ThresholdInputReader extends TerminalInputReader implements InputReader {
    public ThresholdInputReader() {
    }

    public ThresholdInputReader(String[] arguments) {
        super(arguments);
    }

    @Override
    public void defineParameter() {
        if (argumentContainsProperty(ParametersInputRegex.SIMILARITY)) {
            int index = arguments.indexOf(ParametersInputRegex.SIMILARITY.getParameter());
            convertSimilarityInput(arguments.get(index + 1));
        }
    }

    public void convertSimilarityInput(String input) {
        try {
            similarityThreshold = Integer.valueOf(input);
        } catch (NumberFormatException numberFormatException) {
            similarityThreshold = null;
        }
    }
}
