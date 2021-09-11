package com.br.uplag.input;

import com.br.uplag.util.FileInputUtil;

import java.util.Arrays;
import java.util.List;

import static com.br.uplag.parameters.ParametersInputRegex.*;
import static com.br.uplag.util.PropertyInputUtil.verifyIfPropertyIsListed;

public class TerminalInputReader {
    private String directory;
    private String language = "c";
    private String weightingTechnique = TF_IDF.getParameter();
    private List<String> programs;
    private String similarityMeasure = DICE.getParameter();
    private Integer similarityThreshold = 50;

    public void defineYouPlagDataInput(String ...args) {
        for (int i = 0; i < args.length; i++) {
            defineDirectory(args[0], args[1]);
            defineLanguage(args[2], args[3]);
            defineWeightingTechnique(args[4]);
            defineSimilarityMethod(args[5]);
            List<String> input = Arrays.asList(args);
        }
    }

    public void defineDirectory(String parameter, String input) {
        if (verifyIfPropertyIsListed(parameter, DIRECTORY))
            directory = input;
    }

    public void defineLanguage(String parameter, String input) {
        if (verifyIfPropertyIsListed(parameter, LANGUAGE))
            language = input;
    }

    public void defineWeightingTechnique(String input) {
        if (verifyIfPropertyIsListed(input, NIDF) || verifyIfPropertyIsListed(input, TF_IDF))
            weightingTechnique = input;
    }

    public void defineSimilarityMethod(String input) {
        if (verifyIfPropertyIsListed(input, DICE) || verifyIfPropertyIsListed(input, COSINE))
            similarityMeasure = input;
    }

    public void defineSimilarityThreshold(String parameter, String input) {
        if (verifyIfPropertyIsListed(parameter, SIMILARITY))
            similarityThreshold = Integer.parseInt(input);
    }

    public void getPrograms(String parameter, String ...inputs) {
        if (verifyIfPropertyIsListed(parameter, PROGRAMS))
            programs = FileInputUtil.getAllFilesPath(directory, inputs);
    }
}
