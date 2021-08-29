package com.br.uplag.c.operators;

public enum LogicalOperatorsRegexEnum {
    AND("\\&\\&", " \\& "),
    OR("\\|\\|", " \\| "),
    NOT("\\!", " \\! ");

    private final String regex;
    private final String token;

    LogicalOperatorsRegexEnum(String regex, String token) {
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
