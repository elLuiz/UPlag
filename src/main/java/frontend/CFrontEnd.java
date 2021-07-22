package frontend;

import frontendenum.CommonsEnum;
import lombok.Getter;
import lombok.Setter;

import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Getter
@Setter
public class CFrontEnd extends FrontEnd{
    private static final Logger LOGGER = Logger.getLogger(CFrontEnd.class.getName());

    public static final String DOUBLE_SLASH_COMMENT_REGEX = "(\\/\\/[a-zA-ZÀ-Ùà-ùá-ú0-9?�Ã-ã,-p{Punct} ]+)";
    public static final String DOUBLE_ASTERISK_COMMENT_REGEX = "((\\/\\*)(.*)(\\\\*\\/)$)";
    public static final String IMPORT_REGEX = "#(include)[a-zA-Zp{Punct}<.> \"]+$";
    public static final String DEFINE_REGEX = "#(define)\\s?[A-Z-a-z]+\\s[a-z]?\\s?(\\d{1,10})";
    public static final String FUNCTION_REGEX = "[a-zA-Z]+\\*?\\s\\*?[a-zA-Z]+\\s?\\(.*\\)\\s?";
    public static final String FOR_LOOP_REGEX = "for[()[\\]a-zA-z-\\s.=<>!+\\d;]+";
    public static final String WHILE_LOOP_REGEX = "(while)" + CommonsEnum.RECURSIVE_PARENTHESES_REGEX.getRegex();
    public static final String RELATIONAL_OPERATOR_REGEX = "((\\=\\=)|(\\&\\&)|(\\!\\=)|(\\<\\=)|(\\<)|(\\>\\=)|(\\>))(?<!\\-\\>)";
    public static final String LOGICAL_OPERATOR_REGEX = "(\\&\\&)|(\\|\\|)|(\\!)";
    public static final String VARIABLE_ASSIGNMENT_REGEX = CommonsEnum.VARIABLE_ASSIGNMENT_REGEX.getRegex() + "[+\\-*%\\/]?\\=\\s?[a-z-A-Z\\[\\]( )->]+;";
    public static final String VARIABLE_ASSIGNMENT_INCREMENT = "[a-z->]+\\s?+((\\-\\-))|(\\+\\+))";
    public static final String CONDITIONAL_VARIABLE_ASSIGNMENT = "([a-z]+\\s)?([*a-zA-Z\\[\\]\\d\\->]+\\s?\\=\\s?[!a-z\\[\\]A-Z->]+\\s" + RELATIONAL_OPERATOR_REGEX + "\\s?[a-zA-Z->:\\d\\s?+\\-%\\/]+);";
    // RESTRUCTURE THIS
    public static final String FUNCTION_CALL_REGEX = "[a-zA-Z0-9]+(\\((.*)\\))\\;";
    // if it is a conditional return don't exclude the rest of the condition, rather add for the first group the token COND + STATEMENT
    public static final String CONDITIONAL_RETURN_REGEX = "(return)\\s[a-zA-Z0-9]+(\\(?(.*)\\)?)\\;";
    public static final String NORMAL_RETURN_REGEX = "(return)\\s[a-zA-Z0-9->]+(\\(?(.*)\\))*\\;";
    public static final String BREAK_STATEMENT_REGEX = "break;";
    public static final String CONDITIONAL_IF_OPERATOR_REGEX = "(if|else|else if)\\s?";
    public static final String CONDITIONAL_SWITCH_CASE_REGEX = "(switch|case|default)\\s?\\(?[a-zA-Z_\\d: ]+\\)?";
    public static final String LEFT_CURLY_BRACES_REGEX = "\\{";
    public static final String RIGHT_CURLY_BRACES_REGEX = "\\}";
    public static final String LEFT_PARENTHESIS_REGEX = "\\(";
    public static final String RIGHT_PARENTHESIS_REGEX = "\\)";
    public static final String SEMICOLON_REGEX_REGEX = "\\;";

    @Override
    public void convertFunctionsOccurrencesToToken(String codeText) {
        Pattern pattern = Pattern.compile(FUNCTION_REGEX);
        Matcher matcher = pattern.matcher(codeText);
        while (matcher.find()) {
            LOGGER.info(matcher.group(0));
        }
        LOGGER.log(Level.INFO,"Number of functions {0}", matcher.groupCount());
    }

    @Override
    public void convertVariableOccurrencesToToken(String codeText) {
    }
}
