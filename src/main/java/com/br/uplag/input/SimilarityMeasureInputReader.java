package com.br.uplag.input;

import com.br.uplag.parameters.ParametersInputRegex;

public class SimilarityMeasureInputReader extends TerminalInputReader implements InputReader{
    public SimilarityMeasureInputReader() {
    }

    public SimilarityMeasureInputReader(String[] arguments) {
        super(arguments);
    }

    @Override
    public void defineParameter() {
        if (argumentContainsProperty(ParametersInputRegex.COSINE)) {
            similarityMeasure = ParametersInputRegex.COSINE.getParameter();
        }
    }
}