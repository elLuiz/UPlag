package com.br.uplag.c.operators;

public enum BuiltInFunctionsRegex {
    SIZEOF("sizeof(.*)", " U "),
    PRINTF("printf(.*)", " O "),
    FREE("free(.*)", " G ");

    private final String regex;
    private final String token;

    BuiltInFunctionsRegex(String regex, String token) {
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
