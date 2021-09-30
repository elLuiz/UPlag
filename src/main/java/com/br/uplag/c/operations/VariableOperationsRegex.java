package com.br.uplag.c.operations;

public enum VariableOperationsRegex {
    INCREMENT_REGEX("([->a-z_]+\\s?\\+{2}|\\s?\\+{2}[->\\w]+);?", " A O D "),
    DECREMENT_REGEX("([->a-z_]+\\s?\\-{2}|\\s?\\-{2}[->\\w_]+);?", " A P D "),
    NORMAL_ASSIGNMENT_REGEX("[\\da-z*]{0,}\\s?[*a-z_\\d\\->]+(\\[.*\\])?\\s?\\=", " A "),
    ASSIGNMENT_WITH_CASTING("ASSIGN\\s+\\(.+?\\)", " A j "),
    ASSIGNMENT_ADD("\\+\\=", " A O "),
    ASSIGNMENT_SUB("\\-\\=", " A P "),
    ASSIGNMENT_DIV("\\/\\=", " A N "),
    ASSIGNMENT_MULT("\\*\\=", " A M ");


    private final String regex;
    private final String token;

    VariableOperationsRegex(String regex, String token) {
        this.regex = regex;
        this.token = token;
    }

    public String getRegex() {
        return regex;
    }

    public String getToken() {
        return token;
    }
}
