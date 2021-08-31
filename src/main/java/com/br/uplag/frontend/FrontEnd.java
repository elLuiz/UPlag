package com.br.uplag.frontend;

import lombok.Getter;
import lombok.Setter;

import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Getter
@Setter
public class FrontEnd {
    protected static final Logger LOGGER = Logger.getLogger(FrontEnd.class.getName());
    protected Pattern pattern;
    protected Matcher matcher;
    protected String codeText;

    public boolean stringMatchesPattern(Matcher matcher) {
        return matcher.find();
    }

    protected String compileMatcher(String code,String regex, String token) {
        pattern = Pattern.compile(regex);
        matcher = pattern.matcher(code);
        if (stringMatchesPattern(matcher))
            return matcher.replaceAll(token);

        return code;
    }
}
