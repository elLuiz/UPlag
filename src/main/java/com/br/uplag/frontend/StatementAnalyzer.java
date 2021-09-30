package com.br.uplag.frontend;

import com.br.uplag.c.statements.StatementsRegex;

public class StatementAnalyzer extends FrontEnd {
    private static StatementAnalyzer statementAnalyzer;
    private StatementAnalyzer() {}

    public static StatementAnalyzer getInstance() {
        if (statementAnalyzer == null)
            statementAnalyzer = new StatementAnalyzer();
        return statementAnalyzer;
    }

    public String tokenizeStatementsOccurrences(String codeText, StatementsRegex statementsRegex) {
        return compileMatcher(codeText, statementsRegex.getRegex(), statementsRegex.getToken());
    }
}
