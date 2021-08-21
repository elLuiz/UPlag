package reader;

import frontend.FrontEndFacade;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static util.FileInputUtil.readFromInputStream;

public class CReader implements Reader{
    private final FrontEndFacade frontEndFacade;
    public CReader() {
        frontEndFacade = new FrontEndFacade();
    }

    @Override
    public Map<String, String> startReadingInputFiles(List<String> codeFilePath) {
        Map<String, String> fileContentMap = new HashMap<>();
        for (String path : codeFilePath) {
            String codeText = readFromInputStream(path);
            codeText = frontEndFacade.createTokenSequence(codeText);
            fileContentMap.put(path, codeText);
        }

        return fileContentMap;
    }
}
