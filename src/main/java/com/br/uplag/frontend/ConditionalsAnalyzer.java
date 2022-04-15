package com.br.uplag.frontend;

import static com.br.uplag.c.conditionals.ConditionalsRegex.*;

public class ConditionalsAnalyzer extends FrontEnd {
    private static ConditionalsAnalyzer conditionalsAnalyzer;
    private ConditionalsAnalyzer(){}

    public static ConditionalsAnalyzer getInstance() {
        if (conditionalsAnalyzer == null) {
            conditionalsAnalyzer = new ConditionalsAnalyzer();
        }
        return conditionalsAnalyzer;
    }

    public String tokenize(String codeText) {
        codeText = convertSwitchCaseConditions(codeText);
        codeText = convertIfToCondition(codeText);
        return codeText;
    }

    public String convertSwitchCaseConditions(String codeText) {
        return compileMatcher(codeText, SWITCH_CONDITIONAL_STATEMENT.getRegex(), SWITCH_CONDITIONAL_STATEMENT.getToken());
    }

    public String convertIfToCondition(String codeText) {
        return compileMatcher(codeText, IF_CONDITIONAL_STATEMENT.getRegex(), IF_CONDITIONAL_STATEMENT.getToken());
    }
}
