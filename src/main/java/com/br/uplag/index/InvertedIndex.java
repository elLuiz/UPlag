package com.br.uplag.index;

import com.br.uplag.util.StringUtil;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class InvertedIndex {
    private final Map<String, Map<String, Integer>> invertedIndexMap;

    public InvertedIndex() {
        this.invertedIndexMap = new LinkedHashMap<>();
    }

    public void createInvertedIndex(Map<String, String> fileContent) {
        for (Map.Entry<String, String> fileEntry : fileContent.entrySet()) {
            String filename = StringUtil.getFileNameAfterLastSlash(fileEntry.getKey());
            for (String token : getTokens(fileEntry)) {
                if (!token.isEmpty() && invertedIndexMap.get(token) == null) {
                    addNewEntryToInvertedIndex(filename, token);
                } else if (invertedIndexMap.get(token) != null) {
                    incrementInvertedIndexEntry(filename, token);
                }
            }
        }
    }

    private String[] getTokens(Map.Entry<String, String> fileEntry) {
        return fileEntry.getValue().split(" ");
    }

    private void addNewEntryToInvertedIndex(String filename, String token) {
        Map<String, Integer> documentOccurrences = new HashMap<>();
        documentOccurrences.put(filename, 1);
        invertedIndexMap.put(token, documentOccurrences);
    }

    private void incrementInvertedIndexEntry(String filename, String token) {
        Map<String, Integer> tokenOccurrencesInDocument = invertedIndexMap.get(token);
        tokenOccurrencesInDocument.merge(filename, 1, Integer::sum);
    }

    public Map<String, Map<String, Integer>> getInvertedIndex() {
        return invertedIndexMap;
    }
}
