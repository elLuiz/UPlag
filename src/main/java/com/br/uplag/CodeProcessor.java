package com.br.uplag;

import com.br.uplag.index.InvertedIndex;
import com.br.uplag.ngram.NGram;
import com.br.uplag.reader.Reader;
import com.br.uplag.result.DocumentStatistics;
import com.br.uplag.util.StringUtil;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class CodeProcessor {
    private final Reader reader;
    private List<String> programs;
    private final Map<String, DocumentStatistics> documentStatisticsMap = new LinkedHashMap<>();

    public CodeProcessor(Reader reader) {
        this.reader = reader;
    }

    public Map<String, Map<String, Integer>> createInvertedIndex() {
        InvertedIndex invertedIndex = new InvertedIndex();
        Map<String, String> filesCodeText = getFilesCodeText();
        createNGrams(filesCodeText);
        invertedIndex.createInvertedIndex(filesCodeText);
        return invertedIndex.getInvertedIndex();
    }

    public void createNGrams(Map<String, String> filesContent) {
        NGram nGram = new NGram(4);
        for (Map.Entry<String, String> fileEntry : filesContent.entrySet()) {
            String content = fileEntry.getValue();
            String key = fileEntry.getKey();
            filesContent.put(key, nGram.createNGrams(content));
            calculateDocumentStatistics(key, filesContent.get(key));
        }
    }

    public void calculateDocumentStatistics(String document, String nGrams) {
        DocumentStatistics documentStatistics = new DocumentStatistics();
        documentStatistics.setNGrams(nGrams.split(" "));
        documentStatistics.defineTotalNumberOfTokens(nGrams);
        documentStatisticsMap.put(StringUtil.getFileNameAfterLastSlash(document), documentStatistics);
    }

    public Map<String, String> getFilesCodeText() {
        return reader.startReadingInputFiles(programs);
    }

    public void setPrograms(List<String> programs) {
        this.programs = programs;
    }

    public Map<String, DocumentStatistics> getDocumentStatisticsMap() {
        return documentStatisticsMap;
    }
}
