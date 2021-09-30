package com.br.uplag;

import com.br.uplag.chart.SimilarityChartGenerator;
import com.br.uplag.input.*;
import com.br.uplag.parameters.ParametersInputRegex;
import com.br.uplag.reader.CReader;
import com.br.uplag.result.SimilarityResult;
import com.br.uplag.similarity.CodeSimilarity;
import com.br.uplag.similarity.CosineSimilarity;
import com.br.uplag.similarity.OverlapSimilarity;
import com.br.uplag.util.DoubleUtil;
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
public class YouPlag {
    private static final Logger LOGGER = Logger.getLogger(YouPlag.class.getSimpleName());
    private String directory;
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
        DirectoryInputReader directoryInputReader = new DirectoryInputReader(args);
        directoryInputReader.defineParameter();
        directory = directoryInputReader.getDirectory();
        LanguageInputReader languageInputReader = new LanguageInputReader(args);
        languageInputReader.defineParameter();
        language = languageInputReader.getLanguage();
        WeightingTechniqueInputReader weightingTechniqueInputReader = new WeightingTechniqueInputReader(args);
        weightingTechniqueInputReader.defineParameter();
        weightingTechnique = weightingTechniqueInputReader.getWeightingTechnique();
        SimilarityMeasureInputReader similarityMeasureInputReader = new SimilarityMeasureInputReader(args);
        similarityMeasureInputReader.defineParameter();
        similarityMeasure = similarityMeasureInputReader.getSimilarityMeasure();
        ThresholdInputReader thresholdInputReader = new ThresholdInputReader(args);
        thresholdInputReader.defineParameter();
        similarityThreshold = thresholdInputReader.getSimilarityThreshold();
        ProgramsInputReader programsInputReader = new ProgramsInputReader(args);
        programsInputReader.setDirectory(directory);
        programsInputReader.defineParameter();
        programs = programsInputReader.getPrograms();
    }

    public void startReadingCodeFiles() {
        if ("c".equalsIgnoreCase(language)) {
            CodeProcessor codeProcessor = new CodeProcessor(new CReader());
            codeProcessor.setPrograms(programs);
            Map<String, Map<String, Integer>> invertedIndex = codeProcessor.createInvertedIndex();
            Weight weight = defineTermWeightingTechnique(invertedIndex);
            Map<String, Map<String, Double>> documentsWeightMap = weight.calculateTermWeight();
            CodeSimilarity codeSimilarity = defineCodeSimilarityMethod(documentsWeightMap);
            Map<String, Double> stringDoubleMap = codeSimilarity.calculateSimilarity();
            Double threshold = similarityThreshold == null ? null : Double.valueOf(similarityThreshold);
            SimilarityResult similarityResult = new SimilarityResult(stringDoubleMap, codeProcessor.getDocumentStatisticsMap(), threshold);
            similarityResult.displaySimilarityResults();
            SimilarityChartGenerator similarityChartGenerator = new SimilarityChartGenerator(directory);
            similarityChartGenerator.createBarChart(DoubleUtil.convertListOfDoubleToPrimitiveArray(stringDoubleMap.values()));
        } else {
            LOGGER.info("Invalid language");
            LOGGER.info("Available languages: c");
        }
    }

    private Weight defineTermWeightingTechnique(Map<String, Map<String, Integer>> invertedIndex) {
        if (ParametersInputRegex.TF_IDF.getParameter().equals(weightingTechnique))
            return new TfIdfWeight(invertedIndex, programs);
        else
            return new NormalizedWeight(invertedIndex, programs);
    }

    private CodeSimilarity defineCodeSimilarityMethod(Map<String, Map<String, Double>> documentsWeightMap) {
        if (ParametersInputRegex.DICE.getParameter().equalsIgnoreCase(similarityMeasure))
            return new CosineSimilarity(documentsWeightMap);
        else if (ParametersInputRegex.OVERLAP.getParameter().equalsIgnoreCase(similarityMeasure))
            return new OverlapSimilarity(documentsWeightMap);
        else
            return new CosineSimilarity(documentsWeightMap);
    }
}