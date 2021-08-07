package frontend;

import c.operators.LogicalOperatorsRegexEnum;
import c.operators.RelationOperatorRegex;

import java.util.regex.Pattern;

public class OperatorsAnalyzer extends FrontEND {
    public String tokenizeRelationalOperator(String codeText, RelationOperatorRegex relationalOperator) {
        LOGGER.info("Tokenizing relational operators");
        pattern = Pattern.compile(relationalOperator.getRegex(), Pattern.MULTILINE);
        matcher = pattern.matcher(codeText);
        if (stringMatchesPattern(matcher))
            return matcher.replaceAll(relationalOperator.getToken());

        return codeText;
    }

    public String tokenizeLogicalOperators(String codeText, LogicalOperatorsRegexEnum logicalOperatorsRegexEnum) {
        LOGGER.info("Tokenizing logical operators");
        pattern = Pattern.compile(logicalOperatorsRegexEnum.getRegex(), Pattern.MULTILINE);
        matcher = pattern.matcher(codeText);
        if (stringMatchesPattern(matcher))
            return matcher.replaceAll(logicalOperatorsRegexEnum.getToken());
        return codeText;
    }
}
