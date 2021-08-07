package frontend;

import c.conditionals.ConditionalsRegex;

import java.util.regex.Pattern;

public class ConditionalsAnalyzer extends FrontEND {
    public String convertConditionsToItsToken(String codeText) {
        codeText = convertSwitchCaseConditions(codeText);
        codeText = convertIfToCondition(codeText);
        return codeText;
    }

    public String convertSwitchCaseConditions(String codeText) {
        pattern = Pattern.compile(ConditionalsRegex.SWITCH_CONDITIONAL_STATEMENT.getRegex(), Pattern.MULTILINE);
        matcher = pattern.matcher(codeText);
        if (stringMatchesPattern(matcher))
            return matcher.replaceAll(ConditionalsRegex.SWITCH_CONDITIONAL_STATEMENT.getToken());

        return codeText;
    }

    public String convertIfToCondition(String codeText) {
        pattern = Pattern.compile(ConditionalsRegex.IF_CONDITIONAL_STATEMENT.getRegex(), Pattern.MULTILINE);
        matcher = pattern.matcher(codeText);
        if (stringMatchesPattern(matcher))
            return matcher.replaceAll(ConditionalsRegex.IF_CONDITIONAL_STATEMENT.getToken());

        return codeText;
    }
}
