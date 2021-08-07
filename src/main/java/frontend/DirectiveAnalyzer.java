package frontend;

import c.directive.DirectiveRegex;

import java.util.regex.Pattern;

public class DirectiveAnalyzer extends FrontEND {
    public String convertImportsToToken(String codeText) {
        pattern = Pattern.compile(DirectiveRegex.IMPORT.getRegex(), Pattern.MULTILINE);
        matcher = pattern.matcher(codeText);
        if (stringMatchesPattern(matcher))
            return matcher.replaceAll(DirectiveRegex.IMPORT.getToken());

        return codeText;
    }

    public String convertDefineToToken(String codeText) {
        pattern = Pattern.compile(DirectiveRegex.DEFINE.getRegex(), Pattern.MULTILINE);
        matcher = pattern.matcher(codeText);
        if (stringMatchesPattern(matcher))
            return matcher.replaceAll(DirectiveRegex.DEFINE.getToken());

        return codeText;
    }

    public String removeStructOccurrences(String codeText) {
        pattern = Pattern.compile(DirectiveRegex.STRUCT.getRegex(), Pattern.MULTILINE);
        matcher = pattern.matcher(codeText);
        if (stringMatchesPattern(matcher))
            return matcher.replaceAll(DirectiveRegex.STRUCT.getToken());

        return codeText;
    }
}
