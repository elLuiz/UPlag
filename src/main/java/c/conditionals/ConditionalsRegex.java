package c.conditionals;

public enum ConditionalsRegex {
    IF_CONDITIONAL_STATEMENT("(if|else if|else)\\s?", " COND "),
    SWITCH_CONDITIONAL_STATEMENT("(switch|case|default)\\s?\\(?[a-zA-Z_\\d: ]+\\)?", " COND "),
    QUESTION_MARK_CONDITIONAL("\\?", " COND "),
    DOUBLE_POINTS_CONDITIONAL("\\:", " COND ");

    private final String regex;
    private final String token;

    ConditionalsRegex(String regex, String token) {
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
