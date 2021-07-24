package frontend;

import token.VariableTokens;


public class AssignmentLexicalAnalyzer {
    public String analyzeCommonVariableAssignment(String code, String regex, VariableTokens variableToken) {
        return code.replaceAll(regex, variableToken.getToken());
    }
}
