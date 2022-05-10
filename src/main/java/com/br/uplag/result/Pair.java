package com.br.uplag.result;

public class Pair {
    private final String documentOne;
    private final String documentTwo;

    public Pair(String pair) {
        var pairSplit = getPair(pair);
        documentOne = getDocumentOfPair(pairSplit, 0);
        documentTwo = getDocumentOfPair(pairSplit, 1);
    }

    private String[] getPair(String pair) {
        return pair.split(", ");
    }

    private String getDocumentOfPair(String[] pairSplit, int order) {
        return pairSplit[order].replaceAll("[()]", "");
    }

    public String getDocumentOne() {
        return documentOne;
    }

    public String getDocumentTwo() {
        return documentTwo;
    }

    public static String buildPairsTemplate(String document1Name, String document2Name) {
        return "(" + document1Name + ", " + document2Name + ")";
    }
}