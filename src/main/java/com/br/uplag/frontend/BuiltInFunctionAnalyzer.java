package com.br.uplag.frontend;

import com.br.uplag.c.operators.BuiltInFunctionsRegex;

public class BuiltInFunctionAnalyzer extends FrontEnd {
    public String tokenize(String codeText) {
        codeText = tokenizePrintfCalls(codeText);
        return tokenizeFreeCalls(codeText);
    }

    public String tokenizePrintfCalls(String codeText) {
        return compileMatcher(codeText, BuiltInFunctionsRegex.PRINTF.getRegex(), BuiltInFunctionsRegex.PRINTF.getToken());
    }

    public String tokenizeFreeCalls(String codeText) {
        return compileMatcher(codeText, BuiltInFunctionsRegex.FREE.getRegex(), BuiltInFunctionsRegex.FREE.getToken());
    }


}
