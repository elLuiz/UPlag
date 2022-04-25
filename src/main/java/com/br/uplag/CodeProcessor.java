package com.br.uplag;

import com.br.uplag.index.InvertedIndex;
import com.br.uplag.ngram.NGram;
import com.br.uplag.reader.Reader;
import com.br.uplag.result.DocumentStatistics;
import com.br.uplag.util.StringUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CodeProcessor {
    private final Reader reader;
    private final Map<String, DocumentStatistics> documentStatisticsMap = new HashMap<>();

    public CodeProcessor(Reader reader) {
        this.reader = reader;
    }

    public Map<String, Map<String, Integer>> createInvertedIndex(List<String> programsPath) {
        InvertedIndex invertedIndex = new InvertedIndex();
        Map<String, String> filesCodeText = reader.createFilesContentMap(programsPath);
        createNGrams(filesCodeText);
        invertedIndex.createInvertedIndex(filesCodeText);
        return invertedIndex.getInvertedIndex();
    }

    public void createNGrams(Map<String, String> filesContent) {
        NGram nGram = new NGram(4);
        for (Map.Entry<String, String> fileEntry : filesContent.entrySet()) {
            String content = fileEntry.getValue();
            String filename = fileEntry.getKey();
            filesContent.put(filename, nGram.createNGrams(content));
            calculateDocumentStatistics(filename, filesContent.get(filename));
        }
    }

    public void calculateDocumentStatistics(String document, String nGrams) {
        DocumentStatistics documentStatistics = new DocumentStatistics();
        documentStatistics.setNgramsInfo(nGrams);
        documentStatisticsMap.put(StringUtil.getFileNameAfterLastSlash(document), documentStatistics);
    }

    public Map<String, DocumentStatistics> getDocumentStatisticsMap() {
        return documentStatisticsMap;
    }
}
