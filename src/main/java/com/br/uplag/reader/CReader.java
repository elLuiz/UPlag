package com.br.uplag.reader;

import com.br.uplag.exception.UPlagException;
import com.br.uplag.frontend.FrontEndFacade;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CReader extends Reader {
    private static final Logger LOGGER = Logger.getLogger(CReader.class.getSimpleName());
    private static final String CHARACTERS_TO_REMOVE_REGEX = "[\n\t ]";
    private final FrontEndFacade frontEndFacade;
    public CReader() {
        frontEndFacade = new FrontEndFacade();
    }

    @Override
    public Map<String, String> createFilesContentMap(List<String> codeFilePath) {
        Map<String, String> filenameToContent = new HashMap<>();
        for (String path : codeFilePath) {
            addFileContentToMap(filenameToContent, path);
        }
        return filenameToContent;
    }

    private void addFileContentToMap(Map<String, String> fileContentMap, String path) {
        try {
            String codeText = readFileContentAccordingToStandardCharset(path, StandardCharsets.ISO_8859_1);
            codeText = removeCharacters(frontEndFacade.createTokenSequence(codeText));
            if (!codeText.isEmpty()) {
                fileContentMap.put(path, codeText);
            }
        } catch (UPlagException uPlagException) {
            LOGGER.log(Level.SEVERE, "Exception while reading file at: {0}", path);
            LOGGER.log(Level.SEVERE, uPlagException.getMessage());
        }
    }

    private String removeCharacters(String token) {
        return token.replaceAll(CHARACTERS_TO_REMOVE_REGEX, "");
    }
}