package com.br.uplag;

import com.br.uplag.index.InvertedIndex;
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
        invertedIndex.createInvertedIndex(filesCodeText);
        return invertedIndex.getInvertedIndex();
    }

    public Map<String, String> getFilesCodeText() {
        return reader.startReadingInputFiles(programs);
    }

    public void setPrograms(List<String> programs) {
        this.programs = programs;
    }
}
