package frontend;

import c.operations.VariableOperationsRegex;

import java.util.regex.Pattern;

public class VariableAnalyzer extends FrontEND{
    public String tokenizeVariable(String codeText, VariableOperationsRegex variableTokens) {
        LOGGER.info("Tokenizing variable assignments");
        pattern = Pattern.compile(variableTokens.getRegex(), Pattern.MULTILINE);
        matcher = pattern.matcher(codeText);
        if (stringMatchesPattern(matcher))
            return matcher.replaceAll(variableTokens.getToken());

        return codeText;
    }
}