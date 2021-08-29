package com.br.uplag.c.operators;

public enum RelationOperatorRegex {
    EQ("\\=\\=", " \\= "),
    GT("(?<!\\-)\\>(?!\\=)", " \\> "),
    LT("\\<(?!\\=)", " \\< "),
    NE("\\!\\=", " \\- "),
    GE("\\>\\=", " \\~ "),
    LE("\\<\\=", " Q ");

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
