package frontend;

import c.loops.LoopRegex;

import java.util.regex.Pattern;

public class LoopAnalyzer extends FrontEND {
    public String convertLoopsToCommonToken(String codeText) {
        LOGGER.info("Tokenizing loops");
        pattern = Pattern.compile(LoopRegex.LOOP_REGEX.getRegex(), Pattern.MULTILINE);
        matcher = pattern.matcher(codeText);
        if (stringMatchesPattern(matcher))
            return matcher.replaceAll(LoopRegex.LOOP_REGEX.getToken());
        return codeText;
    }
}
