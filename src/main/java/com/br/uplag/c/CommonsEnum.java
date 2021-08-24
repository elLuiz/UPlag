package com.br.uplag.c;

public enum CommonsEnum {
    RECURSIVE_PARENTHESES_REGEX("(\\((?:[^()]++)*\\))"),
    VARIABLE_ASSIGNMENT_REGEX("([A-Za-z*]+\\s)?[*a-zA-Z\\[\\]\\d\\->]+\\s?"),
    VARIABLE_CASTING("\\([a-zA-Z*]+\\)");

    private final String regex;

    CommonsEnum(String regex) {
        this.regex = regex;
    }

    public String getRegex() {
        return regex;
    }
}
