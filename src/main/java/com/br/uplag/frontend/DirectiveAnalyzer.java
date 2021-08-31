package com.br.uplag.frontend;

import com.br.uplag.c.operations.DigitsRegex;

import static com.br.uplag.c.directive.DirectiveRegex.*;

public class DirectiveAnalyzer extends FrontEnd {
    private static DirectiveAnalyzer directiveAnalyzer;
    private DirectiveAnalyzer() {}

    public static DirectiveAnalyzer getInstance() {
        if (directiveAnalyzer == null)
            directiveAnalyzer = new DirectiveAnalyzer();

        return directiveAnalyzer;
    }

    public String tokenize(String code) {
        String result = convertImportsToToken(code);
        result = convertDefineToToken(result);
        result = removeStructOccurrences(result);
        result = convertDigitsOccurrences(result);
        result = convertNullOccurrences(result);
        return result;
    }

    public String convertImportsToToken(String code) {
        return compileMatcher(code, IMPORT.getRegex(), IMPORT.getToken());
    }

    public String convertDefineToToken(String code) {
        return compileMatcher(code, DEFINE.getRegex(), DEFINE.getToken());
    }

    public String removeStructOccurrences(String code) {
       return compileMatcher(code, STRUCT.getRegex(), STRUCT.getToken());
    }

    public String convertDigitsOccurrences(String code) {
        return compileMatcher(code, DigitsRegex.DIGITS_REGEX.getRegex(), DigitsRegex.DIGITS_REGEX.getToken());
    }

    public String convertNullOccurrences(String code) {
        return compileMatcher(code, NULL.getRegex(), NULL.getToken());
    }
}
