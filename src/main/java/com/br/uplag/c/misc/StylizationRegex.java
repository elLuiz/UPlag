package com.br.uplag.c.misc;

public enum StylizationRegex {
    LEFT_CURLY_BRACES("\\{", " LC "),
    RIGHT_CURLY_BRACES("\\}", " RC "),
    LEFT_PARENTHESES("\\(", " LP "),
    RIGHT_PARENTHESES("\\)", " RP "),
    COMMA("\\,", ""),
    SEMICOLON("\\;", "");

    private final String regex;
    private final String token;

    StylizationRegex(String regex, String token) {
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
