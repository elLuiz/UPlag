package processor;

import index.InvertedIndex;
import reader.Reader;

import java.util.List;
import java.util.Map;

public class CodeProcessor {
    private Reader reader;
    private List<String> programs;
    private InvertedIndex invertedIndex;

    public CodeProcessor(Reader reader) {
        this.reader = reader;
        this.invertedIndex = new InvertedIndex();
    }

    public void createInvertedIndex() {
        Map<String, String> filesCodeText = getFilesCodeText();
        invertedIndex.createInvertedIndex(filesCodeText);
    }

    public Map<String, String> getFilesCodeText() {
        return reader.startReadingInputFiles(programs);
    }

    public void setPrograms(List<String> programs) {
        this.programs = programs;
    }
}
