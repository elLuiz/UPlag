package com.br.uplag;

import com.br.uplag.input.*;
import com.br.uplag.parameters.ParametersInputRegex;
import com.br.uplag.reader.CReader;
import com.br.uplag.result.SimilarityResult;
import com.br.uplag.similarity.CodeSimilarity;
import com.br.uplag.similarity.CosineSimilarity;
import com.br.uplag.similarity.DiceSimilarity;
import com.br.uplag.weight.SublinearTFIDFWeightCalculator;
import com.br.uplag.weight.TfIdfWeightCalculator;
import com.br.uplag.weight.TermWeightCalculator;

import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

// args 0 - -d args 1 directory
// args 2 - -l args 3 language
// args 4 - -tf-idf/nidf
// args 5 - -p args 6 programs
public class YouPlag {
    private static final Logger LOGGER = Logger.getLogger(YouPlag.class.getSimpleName());
    private String language;
    private String weightingTechnique;
    private List<String> programs;
    private String similarityMeasure;
    private Integer similarityThreshold;

    public static void main(String ...args) {
        YouPlag youPlag = new YouPlag();
        youPlag.readTerminalInput(args);
        youPlag.startReadingCodeFiles();
    }

    public void readTerminalInput(String ...args) {
        defineLanguage(args);
        defineWeightingTechnique(args);
        getSimilarityMeasure(args);
        getThreshold(args);
        readPrograms(getDirectory(args), args);
    }

    private void defineLanguage(String[] args) {
        LanguageInputReader languageInputReader = new LanguageInputReader(args);
        languageInputReader.defineParameter();
        language = languageInputReader.getLanguage();
    }

    private void defineWeightingTechnique(String[] args) {
        WeightingTechniqueInputReader weightingTechniqueInputReader = new WeightingTechniqueInputReader(args);
        weightingTechniqueInputReader.defineParameter();
        weightingTechnique = weightingTechniqueInputReader.getWeightingTechnique();
    }

    private void getSimilarityMeasure(String[] args) {
        SimilarityMeasureInputReader similarityMeasureInputReader = new SimilarityMeasureInputReader(args);
        similarityMeasureInputReader.defineParameter();
        similarityMeasure = similarityMeasureInputReader.getSimilarityMeasure();
    }

    private void getThreshold(String[] args) {
        ThresholdInputReader thresholdInputReader = new ThresholdInputReader(args);
        thresholdInputReader.defineParameter();
        similarityThreshold = thresholdInputReader.getSimilarityThreshold();
    }

    private void readPrograms(String directory, String[] args) {
        ProgramsInputReader programsInputReader = new ProgramsInputReader(args);
        programsInputReader.setDirectory(directory);
        programsInputReader.defineParameter();
        programs = programsInputReader.getPrograms();
    }

    private String getDirectory(String[] args) {
        DirectoryInputReader directoryInputReader = new DirectoryInputReader(args);
        directoryInputReader.defineParameter();
        return directoryInputReader.getDirectory();
    }

    public void startReadingCodeFiles() {
        if ("c".equalsIgnoreCase(language)) {
            CodeProcessor codeProcessor = new CodeProcessor(new CReader());
            processSimilarityMetrics(codeProcessor);
        } else {
            LOGGER.info("Invalid language");
            LOGGER.info("Available languages: c");
        }
    }

    private void processSimilarityMetrics(CodeProcessor codeProcessor) {
        Map<String, Map<String, Integer>> invertedIndex = codeProcessor.createInvertedIndex(programs);
        TermWeightCalculator termWeightCalculator = defineTermWeightingTechnique(invertedIndex);
        Map<String, Map<String, Double>> documentsWeightMap = termWeightCalculator.calculateTermWeight();
        CodeSimilarity codeSimilarity = defineCodeSimilarityMethod(documentsWeightMap);
        Map<String, Double> stringDoubleMap = codeSimilarity.calculateDocumentSimilarity();
        Double threshold = similarityThreshold == null ? null : Double.valueOf(similarityThreshold);
        SimilarityResult similarityResult = new SimilarityResult(stringDoubleMap, codeProcessor.getDocumentStatisticsMap(), threshold);
        similarityResult.displaySimilarityResults();
    }

    private TermWeightCalculator defineTermWeightingTechnique(Map<String, Map<String, Integer>> invertedIndex) {
        if (ParametersInputRegex.TF_IDF.getParameter().equals(weightingTechnique))
            return new TfIdfWeightCalculator(invertedIndex, programs);
        else
            return new SublinearTFIDFWeightCalculator(invertedIndex, programs);
    }

    private CodeSimilarity defineCodeSimilarityMethod(Map<String, Map<String, Double>> documentsWeightMap) {
        if (ParametersInputRegex.DICE.getParameter().equalsIgnoreCase(similarityMeasure))
            return new DiceSimilarity(documentsWeightMap);
        else
            return new CosineSimilarity(documentsWeightMap);
    }
}