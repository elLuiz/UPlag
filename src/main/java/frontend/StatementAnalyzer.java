package frontend;

import c.statements.StatementsRegex;

import java.util.regex.Pattern;

public class StatementAnalyzer extends FrontEND{
    public String tokenizeStatementsOccurrences(String codeText, StatementsRegex statementsRegex) {
        LOGGER.info("Tokenizing statements occurrences");
        pattern = Pattern.compile(statementsRegex.getRegex(), Pattern.MULTILINE);
        matcher = pattern.matcher(codeText);
        if (stringMatchesPattern(matcher))
            return matcher.replaceAll(statementsRegex.getToken());

        return codeText;
    }
}
