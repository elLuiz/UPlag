package com.br.uplag;

import com.br.uplag.index.InvertedIndex;
import com.br.uplag.ngram.NGram;
import com.br.uplag.reader.Reader;
import com.br.uplag.weight.Weight;

import java.util.List;
import java.util.Map;

public class CodeProcessor {
    private Reader reader;
    private List<String> programs;
    private InvertedIndex invertedIndex;
    private Weight weight;

    public CodeProcessor(Reader reader) {
        this.reader = reader;
        this.invertedIndex = new InvertedIndex();
    }

    public Map<String, Map<String, Integer>> createInvertedIndex() {
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
        }
    }

    public Map<String, String> getFilesCodeText() {
        return reader.startReadingInputFiles(programs);
    }

    public void setPrograms(List<String> programs) {
        this.programs = programs;
    }
}
