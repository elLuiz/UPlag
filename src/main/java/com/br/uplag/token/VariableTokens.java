package com.br.uplag.token;

public enum VariableTokens {
    CAST("ASSIGN CAST"),
    ASSIGN("ASSIGN");

    private final String token;

    VariableTokens(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }
}
