package com.br.uplag.frontend;

import com.br.uplag.c.misc.StylizationRegex;
import com.br.uplag.c.operations.DigitsRegex;
import lombok.Getter;
import lombok.Setter;

import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Getter
@Setter
public class FrontEND {
    protected static final Logger LOGGER = Logger.getLogger(FrontEND.class.getName());
    protected Pattern pattern;
    protected Matcher matcher;
    protected String codeText;

    public boolean stringMatchesPattern(Matcher matcher) {
        return matcher.find();
    }

    public String tokenizeDigits(String codeText) {
        LOGGER.info("Tokenizing digits");
        pattern = Pattern.compile(DigitsRegex.DIGITS_REGEX.getRegex());
        matcher = pattern.matcher(codeText);
        return matcher.replaceAll(DigitsRegex.DIGITS_REGEX.getToken());
    }

    public String tokenizeStylization(String codeText, StylizationRegex stylizationRegex) {
        LOGGER.info("Tokenizing curly braces and parenthesis");
        pattern = Pattern.compile(stylizationRegex.getRegex(), Pattern.MULTILINE);
        matcher = pattern.matcher(codeText);
        return matcher.replaceAll(stylizationRegex.getToken());
    }

    protected String compileMatcher(String code,String regex, String token) {
        pattern = Pattern.compile(regex);
        matcher = pattern.matcher(code);
        if (stringMatchesPattern(matcher))
            return matcher.replaceAll(token);

        return code;
    }
}
