package com.br.uplag.c.operations;

public enum DigitsRegex {
    // MAY BE DIFFERENCIATE BETWEEN DOUBLE AND INTEGER, POSITIVE AND NEGATIVE NUMBERS
    DIGITS_REGEX("[+-]?([0-9]*[.])?[0-9]+", " D ");

    private final String regex;
    private final String token;

    DigitsRegex(String regex, String token) {
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
