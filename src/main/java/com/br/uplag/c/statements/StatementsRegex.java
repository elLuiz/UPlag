package com.br.uplag.c.statements;

public enum StatementsRegex {
    BREAK("break;", "B"),
    JUMP("return;", "B"),
    CONTINUE("continue;", "B"),
    RETURN("return(?!\\;)", " R ");

    private final String regex;
    private final String token;

    StatementsRegex(String regex, String token) {
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
