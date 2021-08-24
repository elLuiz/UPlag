package com.br.uplag.index;

import com.br.uplag.util.StringUtil;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class InvertedIndex {
    private Map<String, Map<String, Integer>> invertedIndexMap;

    public InvertedIndex() {
        this.invertedIndexMap = new LinkedHashMap<>();
    }

    public void createInvertedIndex(Map<String, String> fileContent) {
        for (Map.Entry<String, String> fileEntry : fileContent.entrySet()) {
            String filename = StringUtil.getFileNameAfterLastSlash(fileEntry.getKey());
            String[] tokens = fileEntry.getValue().split(" ");
            for (String token : tokens) {
                if (!token.isEmpty() && invertedIndexMap.get(token) == null) {
                    Map<String, Integer> documentOccurrences = new HashMap<>();
                    documentOccurrences.put(filename, 1);
                    invertedIndexMap.put(token, documentOccurrences);
                } else if (invertedIndexMap.get(token) != null) {
                    Map<String, Integer> tokenOccurrencesInDocument = invertedIndexMap.get(token);
                    tokenOccurrencesInDocument.merge(filename, 1, Integer::sum);
                }
            }
        }
    }

    public Map<String, Map<String, Integer>> getInvertedIndex() {
        return invertedIndexMap;
    }
}
