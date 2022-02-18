package com.br.uplag.ngram;

public class NGram {
    private final Integer windowSize;

    public NGram(Integer windowSize) {
        this.windowSize = windowSize;
    }


    public String createNGrams(String content) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < content.length() - windowSize + 1; i++) {
            stringBuilder.append(content, i, i + windowSize).append(" ");
        }

        return stringBuilder.toString();
    }
}
