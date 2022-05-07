package com.br.uplag.parameters;

public enum ParametersInputRegex {
    DIRECTORY("-d"),
    LANGUAGE("-l"),
    TF_IDF("-tfidf"),
    WF_IDF("-wfidf"),
    DICE("-dice"),
    COSINE("-cosine"),
    SIMILARITY("-t"),
    PROGRAMS("-p");

    private final String parameter;

    ParametersInputRegex(String parameter) {
        this.parameter = parameter;
    }

    public String getParameter() {
        return parameter;
    }
}
