package frontend;

import lombok.Getter;
import lombok.Setter;

import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Getter
@Setter
public class CFrontEnd extends FrontEnd{
    public static final String FUNCTION_REGEX = "[a-zA-Z]+(\\*?)+\\s(\\*?)[a-zA-Z]+\\s?\\(.*\\)\\s?\\{";
    public static final String DOUBLE_SLASH_COMMENT_REGEX = "(\\/\\/[a-zA-ZÀ-Ùà-ùá-ú0-9?�Ã-ã\\p{Punct} ]+)";
    public static final String DOUBLE_ASTERISK_COMMENT_REGEX = "((\\/\\*)(.*)(\\\\*\\/)$)";
    public static final String FUNCTION_CALL_REGEX = "[a-zA-Z0-9]+(\\((.*)\\))\\;";
    public static final String VARIABLE_ASSIGNMENT_REGEX = "[*a-zA-Z\\d\\-> ]+\\s?[+\\-*%\\/]?\\s?\\=\\s?([*a-zA\\d\\-> ]|[a-zA-Z+%\\-*\\/]+|[[a-zA-Z0-9]+(\\((.*)\\))\\;])+\\;";
    public static final String IMPORT_REGEX = "#(include)[a-zA-Zp{Punct}\\s<\"].+";
    public static final String DEFINE_REGEX = "#(define)\\s?[A-Z-a-z\\d \\S]+";
    public static final String RELATIONAL_OPERATOR_REGEX = "(\\=\\=)|(\\&\\&)|(\\!\\=)|(\\<\\=)|(\\<)|(\\>\\=)";
    public static final String LOGICAL_OPERATOR_REGEX = "(\\&\\&)|(\\|\\|)|(\\!)";
    public static final String CONDITIONAL_OPERATOR_REGEX = "(if|switch|else|else if|case|default)\\s?\\(?[a-zA-Z_\\d: ]+\\)?";
    public static final String BREAK_STATEMENT_REGEX = "break";
    public static final String CONDITIONAL_RETURN_REGEX = "(return)\\s[a-zA-Z\\d?:=&!<>\\s]+";
    public static final String NORMAL_RETURN_REGEX = "(return)\\s[a-zA-Z0-9]+(\\(?(.*)\\)?)\\;";

    public static final Logger LOGGER = Logger.getLogger(CFrontEnd.class.getName());
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
