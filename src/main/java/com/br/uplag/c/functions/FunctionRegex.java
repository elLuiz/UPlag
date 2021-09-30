package com.br.uplag.c.functions;

public enum FunctionRegex {
    // It must come after conditionals
    FUNCTION_CREATION_REGEX("[a-z]+\\*?\\s\\*?[a-z_\\d]+\\s?\\(.*?\\)\\s?\\{", "F { "),
    FUNCTION_CALL_REGEX("(?:[a-z*_\\d{1,1000}\\\\p{Punct}]+\\(.*\\));", " X "),
    FUNCTION_CALL_BETWEEN_PARENTHESES("(?<=\\()[\\w]+\\(.*\\)+(?=\\))", " X "),
    FUNCTION_CALL_BETWEEN_PARENTHESES_AND_OPERATORS("(?<=\\w\\s)[\\w]+\\(.*\\)+(?=\\))", " X ");
    private final String regex;
    private final String token;

    FunctionRegex(String regex, String token) {
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
