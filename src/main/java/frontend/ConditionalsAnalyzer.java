package frontend;

import c.conditionals.ConditionalsRegex;

import java.util.regex.Pattern;

import static c.conditionals.ConditionalsRegex.*;

public class ConditionalsAnalyzer extends FrontEND {
    private static ConditionalsAnalyzer conditionalsAnalyzer;
    private ConditionalsAnalyzer(){}

    public static ConditionalsAnalyzer getInstance() {
        if (conditionalsAnalyzer == null);
            conditionalsAnalyzer = new ConditionalsAnalyzer();
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
