package com.br.uplag.input;

import com.br.uplag.parameters.ParametersInputRegex;

public class WeightingTechniqueInputReader extends TerminalInputReader implements InputReader{
    public WeightingTechniqueInputReader() {
    }

    public WeightingTechniqueInputReader(String[] arguments) {
        super(arguments);
    }

    @Override
    public void defineParameter() {
        if (argumentContainsProperty(ParametersInputRegex.NIDF)) {
            weightingTechnique = ParametersInputRegex.NIDF.getParameter();
        }
    }
}
