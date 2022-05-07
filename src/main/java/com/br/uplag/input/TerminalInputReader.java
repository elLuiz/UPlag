package com.br.uplag.input;

import com.br.uplag.parameters.ParametersInputRegex;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;

import static com.br.uplag.parameters.ParametersInputRegex.DICE;
import static com.br.uplag.parameters.ParametersInputRegex.TF_IDF;

@Getter
public class TerminalInputReader {
    protected List<String> arguments;
    protected String[] args;
    protected String directory;
    protected String language = "c";
    protected String weightingTechnique = TF_IDF.getParameter();
    protected List<String> programs;
    protected String similarityMeasure = DICE.getParameter();
    protected Integer similarityThreshold = null;

    public TerminalInputReader() {
    }

    public TerminalInputReader(String[] arguments) {
        this.arguments = Arrays.asList(arguments);
        this.args = arguments;
    }

    protected boolean argumentContainsProperty(ParametersInputRegex parametersInputRegex) {
        return arguments.contains(parametersInputRegex.getParameter());
    }
}
