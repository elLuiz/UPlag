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
            getSimilarityThreshold(arguments.get(index + 1));
        }
    }

    public void getSimilarityThreshold(String input) {
        similarityThreshold = getThreshold(input);
    }

    // If the value is not compliant with the limits, then UPlag uses Otsu Threshol
    private Integer getThreshold(String input) {
        try {
            int threshold = Integer.parseInt(input);
            return threshold >= 0 && threshold <= 100 ? threshold : null;
        } catch (NumberFormatException numberFormatException) {
            return null;
        }
    }
}
