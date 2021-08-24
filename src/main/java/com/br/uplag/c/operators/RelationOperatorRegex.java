package com.br.uplag.c.operators;

public enum RelationOperatorRegex {
    EQ("\\=\\=", " EQ "),
    GT("(?<!\\-)\\>(?!\\=)", " GT "),
    LT("\\<(?!\\=)", " LT "),
    NE("\\!\\=", " NE "),
    GE("\\>\\=", " GE "),
    LE("\\<\\=", " LE ");

    private final String regex;
    private final String token;

    RelationOperatorRegex(String regex, String token) {
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
