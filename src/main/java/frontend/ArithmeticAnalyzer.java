package frontend;

import c.operators.ArithmeticOperatorsRegex;

import java.util.regex.Pattern;

public class ArithmeticAnalyzer extends FrontEND{
    public String tokenizeArithmeticOccurrences(String codeText, ArithmeticOperatorsRegex arithmetic) {
        pattern = Pattern.compile(arithmetic.getRegex(), Pattern.MULTILINE);
        matcher = pattern.matcher(codeText);
        if (stringMatchesPattern(matcher))
            return matcher.replaceAll(arithmetic.getToken());

        return codeText;
    }
}
