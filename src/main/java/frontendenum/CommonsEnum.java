package frontendenum;

public enum CommonsEnum {
    RECURSIVE_PARENTHESES_REGEX("(\\((?:[^()]++)*\\))"),
    VARIABLE_ASSIGNMENT_REGEX("([A-Za-z*]+\\s)?[*a-zA-Z\\[\\]\\d\\->]+\\s?");

    private final String regex;

    CommonsEnum(String regex) {
        this.regex = regex;
    }

    public String getRegex() {
        return regex;
    }
}
