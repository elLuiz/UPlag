package com.br.uplag.c.operators;

public enum ArithmeticOperatorsRegex {
    ADD("(?<!\\+)\\+(?!\\+)", " O "),
    SUB("(?<!\\-)\\-(?!\\>)", " P "),
    // PROBLEM: WHAT IS COMMENT OR MULTIPLICATION
    MULT("\\*", " M "),
    DIV("\\/", " N "),
    MOD("\\%", " C ");

    private final String regex;
    private final String token;

    ArithmeticOperatorsRegex(String regex, String token) {
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
