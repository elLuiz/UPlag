package com.br.uplag.frontend;

import static com.br.uplag.c.functions.FunctionRegex.*;

public class FunctionsAnalyzer extends FrontEND {
    private static FunctionsAnalyzer functionsAnalyzer;
    private FunctionsAnalyzer(){}

    public static FunctionsAnalyzer getInstance() {
        if (functionsAnalyzer == null)
            functionsAnalyzer = new FunctionsAnalyzer();
        return functionsAnalyzer;
    }

    public String tokenize(String code) {
        String result = convertFunctionsCreationToItsToken(code);
        result = convertInlineFunctionsCalls(result);
        result = convertFunctionCallsInsideParenthesis(result);
        result = convertFunctionBetweenOperatorsAndParenthesis(result);
        return result;
    }

    public String convertFunctionsCreationToItsToken(String codeText) {
       return compileMatcher(codeText, FUNCTION_CREATION_REGEX.getRegex(), FUNCTION_CREATION_REGEX.getToken());
    }

    public String convertInlineFunctionsCalls(String codeText) {
       return compileMatcher(codeText, FUNCTION_CALL_REGEX.getRegex(), FUNCTION_CALL_REGEX.getToken());
    }

    public String convertFunctionCallsInsideParenthesis(String codeText) {
        return compileMatcher(codeText, FUNCTION_CALL_BETWEEN_PARENTHESES.getRegex(), FUNCTION_CREATION_REGEX.getToken());
    }

    public String convertFunctionBetweenOperatorsAndParenthesis(String codeText) {
        return compileMatcher(codeText, FUNCTION_CALL_BETWEEN_PARENTHESES_AND_OPERATORS.getRegex(), FUNCTION_CALL_BETWEEN_PARENTHESES_AND_OPERATORS.getToken());
    }
}
