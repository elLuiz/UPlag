package com.br.uplag.c.statements;

public enum JumpStatementsRegex {
    BREAK("break;", "JUMP"),
    RETURN("return;", "JUMP"),
    CONTINUE("continue;", "JUMP");

    private final String regex;
    private final String token;

    JumpStatementsRegex(String regex, String token) {
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

