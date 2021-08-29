package com.br.uplag.c.operations;

public enum VariableOperationsRegex {
    INCREMENT_REGEX("([->a-z_]+\\s?\\+{2}|\\s?\\+{2}[->\\w]+);?", " A + D "),
    DECREMENT_REGEX("([->a-z_]+\\s?\\-{2}|\\s?\\-{2}[->\\w_]+);?", " A - D "),
    NORMAL_ASSIGNMENT_REGEX("[\\da-z*]{0,}\\s?[*a-z_\\d\\->]+(\\[.*\\])?\\s?\\=", " A "),
    ASSIGNMENT_WITH_CASTING("ASSIGN\\s+\\(.+?\\)", " A j "),
    ASSIGNMENT_ADD("\\+\\=", " A + "),
    ASSIGNMENT_SUB("\\-\\=", " A - "),
    ASSIGNMENT_DIV("\\/\\=", " A / "),
    ASSIGNMENT_MULT("\\*\\=", " A * ");


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
