package com.br.uplag.frontend;

import com.br.uplag.c.operators.ArithmeticOperatorsRegex;

public class ArithmeticAnalyzer extends FrontEnd {
    private static ArithmeticAnalyzer arithmeticAnalyzer;
    private ArithmeticAnalyzer() {}

    public static ArithmeticAnalyzer getInstance() {
        if (arithmeticAnalyzer == null)
            arithmeticAnalyzer = new ArithmeticAnalyzer();
        return arithmeticAnalyzer;
    }

    public String tokenizeArithmeticOccurrences(String codeText, ArithmeticOperatorsRegex arithmetic) {
        return compileMatcher(codeText, arithmetic.getRegex(), arithmetic.getToken());
    }
}
