package c.misc;

public enum CommentsRegex {
    DOUBLE_SLASH_COMMENTS("\\/\\/.*", ""),
    DOUBLE_ASTERISK_COMMENT_REGEX("(?<=\\/\\*)[\\S\\s]*(?=\\*\\/)", ""),
    SLASH_ASTERISK("\\/\\*\\*\\/", "");

    private final String regex;
    private final String token;

    CommentsRegex(String regex, String token) {
        this.regex = regex;
        this.token = token;
    }

    public String getRegex() {
        return regex;
    }

    public String getToken() {
        return token;
    }
}
