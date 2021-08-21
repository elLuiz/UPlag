package frontend;

import c.misc.CommentsRegex;

public class CommentsAnalyzer extends FrontEND {
    private static CommentsAnalyzer commentsAnalyzer;
    private CommentsAnalyzer() {

    }

    public static CommentsAnalyzer getInstance() {
        if (commentsAnalyzer == null)
           commentsAnalyzer = new CommentsAnalyzer();
        return commentsAnalyzer;
    }

    public String tokenize(String code) {
        String result = convertDoubleSlashCommentsToToken(code);
        result = convertAsterisksCommentsToToken(result);
        return result;
    }

    public String convertDoubleSlashCommentsToToken(String code) {
        return compileMatcher(code, CommentsRegex.DOUBLE_SLASH_COMMENTS.getRegex(), CommentsRegex.DOUBLE_SLASH_COMMENTS.getToken());
    }

    public String convertAsterisksCommentsToToken(String code) {
        return compileMatcher(code, CommentsRegex.DOUBLE_ASTERISK_COMMENT_REGEX.getRegex(), CommentsRegex.DOUBLE_ASTERISK_COMMENT_REGEX.getToken());
    }
}
