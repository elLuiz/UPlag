package frontend;

import c.misc.CommentsRegex;

import java.util.regex.Pattern;

public class CommentsAnalyzer extends FrontEND {
    public String convertDoubleSlashCommentsToToken(String codeText) {
        LOGGER.info("Analyzing double slash comments occurrences");
        pattern = Pattern.compile(CommentsRegex.DOUBLE_SLASH_COMMENTS.getRegex(), Pattern.MULTILINE);
        matcher = pattern.matcher(codeText);
        if (stringMatchesPattern(matcher))
            return matcher.replaceAll(CommentsRegex.DOUBLE_SLASH_COMMENTS.getToken());

        return codeText;
    }

    public String convertAsterisksCommentsToToken(String codeText) {
        LOGGER.info("Analyzing asterisk comments occurrences");
        pattern = Pattern.compile(CommentsRegex.DOUBLE_ASTERISK_COMMENT_REGEX.getRegex());
        matcher = pattern.matcher(codeText);
        if (stringMatchesPattern(matcher))
            return matcher.replaceAll(CommentsRegex.DOUBLE_ASTERISK_COMMENT_REGEX.getToken());

        return codeText;
    }
}
