package com.br.uplag.c.loops;

public enum LoopRegex {
    LOOP_REGEX("(while|for)", " L ");

    private final String regex;
    private final String token;

    LoopRegex(String regex, String token) {
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
