package com.br.uplag.frontend;

import com.br.uplag.c.operations.VariableOperationsRegex;


public class VariableAnalyzer extends FrontEnd {
    private static VariableAnalyzer variableAnalyzer;
    private VariableAnalyzer() {}

    public static VariableAnalyzer getInstance() {
        if (variableAnalyzer == null)
            variableAnalyzer = new VariableAnalyzer();
        return variableAnalyzer;
    }
    public String tokenizeVariable(String codeText, VariableOperationsRegex variableTokens) {
        return compileMatcher(codeText, variableTokens.getRegex(), variableTokens.getToken());
    }
}