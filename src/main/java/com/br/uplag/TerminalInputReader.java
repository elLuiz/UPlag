package com.br.uplag;

import com.br.uplag.parameters.ParametersInputRegex;
import com.br.uplag.reader.CReader;
import com.br.uplag.util.FileInputUtil;
import com.br.uplag.util.PropertyInputUtil;
import com.br.uplag.weight.NormalizedWeight;
import com.br.uplag.weight.TfIdfWeight;
import com.br.uplag.weight.Weight;

import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

// args 0 - -d args 1 directory
// args 2 - -l args 3 language
// args 4 - -tf-idf/nidf
// args 5 - -p args 6 programs
public class TerminalInputReader {
    private static final Logger LOGGER = Logger.getLogger(TerminalInputReader.class.getSimpleName());
    private String directory;
    private String language;
    private String weightingTechnique;
    private List<String> programs;


    public static void main(String ...args) {
        TerminalInputReader terminalInputReader = new TerminalInputReader();
        terminalInputReader.readTerminalInput(args);
        terminalInputReader.startReadingCodeFiles();
    }

    public void readTerminalInput(String ...args) {
        if (PropertyInputUtil.verifyIfPropertyIsListed(args[0], ParametersInputRegex.DIRECTORY))
            directory = args[1];
        if (PropertyInputUtil.verifyIfPropertyIsListed(args[2], ParametersInputRegex.LANGUAGE))
            language = args[3];
        if (PropertyInputUtil.verifyIfPropertyIsListed(args[4], ParametersInputRegex.NIDF) || PropertyInputUtil.verifyIfPropertyIsListed(args[4], ParametersInputRegex.TF_IDF))
            weightingTechnique = args[4];
        if (PropertyInputUtil.verifyIfPropertyIsListed(args[5], ParametersInputRegex.PROGRAMS)) {
            programs = FileInputUtil.getAllFilesPath(directory, args);
        }
    }

    public void startReadingCodeFiles() {
        if ("c".equalsIgnoreCase(language)) {
            CodeProcessor codeProcessor = new CodeProcessor(new CReader());
            codeProcessor.setPrograms(programs);
            Map<String, Map<String, Integer>> invertedIndex = codeProcessor.createInvertedIndex();
            Weight weight = defineTermWeightingTechnique(invertedIndex);
            Map<String, Map<String, Double>> documentsWeightMap = weight.calculateTermWeight();
        } else {
            LOGGER.info("Invalid language");
            LOGGER.info("Available languages: c");
        }
    }

    private Weight defineTermWeightingTechnique(Map<String, Map<String, Integer>> invertedIndex) {
        if (ParametersInputRegex.TF_IDF.getParameter().equals(weightingTechnique))
            return new TfIdfWeight(invertedIndex, programs.size());
        else
            return new NormalizedWeight(invertedIndex, programs.size());
    }
}