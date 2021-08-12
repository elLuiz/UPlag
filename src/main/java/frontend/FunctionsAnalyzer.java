package frontend;

import c.functions.FunctionRegex;

import java.util.regex.Pattern;

public class FunctionsAnalyzer extends FrontEND {
    public String convertFunctionsCreationToItsToken(String codeText) {
        LOGGER.info("Running functions creations phase");
        pattern = Pattern.compile(FunctionRegex.FUNCTION_CREATION_REGEX.getRegex(), Pattern.MULTILINE);
        matcher = pattern.matcher(codeText);
        if (stringMatchesPattern(matcher))
            return matcher.replaceAll(FunctionRegex.FUNCTION_CREATION_REGEX.getToken());

        return codeText;
    }

    public String convertInlineFunctionsCalls(String codeText) {
        pattern = Pattern.compile(FunctionRegex.FUNCTION_CALL_REGEX.getRegex(), Pattern.MULTILINE);
        matcher = pattern.matcher(codeText);
        if (stringMatchesPattern(matcher))
            return matcher.replaceAll(FunctionRegex.FUNCTION_CALL_REGEX.getToken());

        return codeText;
    }

    public String convertFunctionCallsInsideParenthesis(String codeText) {
        pattern = Pattern.compile(FunctionRegex.FUNCTION_CALL_BETWEEN_PARENTHESES.getRegex(), Pattern.MULTILINE);
        matcher = pattern.matcher(codeText);
        if (stringMatchesPattern(matcher))
            return matcher.replaceAll(FunctionRegex.FUNCTION_CALL_BETWEEN_PARENTHESES.getToken());

        return codeText;
    }

    public String convertFunctionBetweenOperatorsAndParenthesis(String codeText) {
        pattern = Pattern.compile(FunctionRegex.FUNCTION_CALL_BETWEEN_PARENTHESES_AND_OPERATORS.getRegex(), Pattern.MULTILINE);
        matcher = pattern.matcher(codeText);
        if (stringMatchesPattern(matcher))
            return matcher.replaceAll(FunctionRegex.FUNCTION_CALL_BETWEEN_PARENTHESES_AND_OPERATORS.getToken());

        return codeText;
    }
}
