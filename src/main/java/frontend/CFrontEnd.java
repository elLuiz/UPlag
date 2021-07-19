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
    private static final Logger LOGGER = Logger.getLogger(CFrontEnd.class.getName());

    // Foremost - Convert the text to lowercase & break lines
    // First phase tokens - Remove style stuff from the code
    public static final String DOUBLE_SLASH_COMMENT_REGEX = "(\\/\\/[a-zA-ZÀ-Ùà-ùá-ú0-9?�Ã-ã,-\\\\p{Punct} ]+)";
    public static final String DOUBLE_ASTERISK_COMMENT_REGEX = "((\\/\\*)(.*)(\\\\*\\/)$)";
    public static final String IMPORT_REGEX = "#(include)[a-zA-Zp{Punct}\\s<\"].+";
    public static final String DEFINE_REGEX = "#(define)\\s?[A-Z-a-z\\d \\S]+";
    // Second phase tokens - Convert functions to their appropriate tokens
    public static final String FUNCTION_REGEX = "[a-zA-Z]+\\*?\\s\\*?[a-zA-Z]+\\s?\\(.*\\)\\s?";
    // Better to convert functions calls to their token
    public static final String FOR_LOOP_REGEX = "for[()[\\]a-zA-z-\\s.=<>!+\\d;]+\\{?";
    public static final String WHILE_LOOP_REGEX = "(while|for)(\\((?:[^()]++)*\\))";
    // Third phase tokens - Convert variable definitions to their token
    public static final String VARIABLE_ASSIGNMENT_REGEX = "[ *a-zA-Z\\[\\]\\d\\->]+\\s?[+\\-*%\\/]?\\=\\s?[a-z-A-Z\\[\\]( )->]+;";
    public static final String VARIABLE_ASSIGNMENT_INCREMENT = "([a-z->]+(\\-\\-))|([a-z->]+(\\+\\+))";
    // FOURTH PHASE TOKEN - Convert relational & logical operator to a common token
    public static final String RELATIONAL_OPERATOR_REGEX = "((\\=\\=)|(\\&\\&)|(\\!\\=)|(\\<\\=)|(\\<)|(\\>\\=)|(\\>))(?<!\\-\\>)";
    public static final String LOGICAL_OPERATOR_REGEX = "(\\&\\&)|(\\|\\|)|(\\!)";
    // Fifth Phase - Convert functions call to token
    public static final String FUNCTION_CALL_REGEX = "[a-zA-Z0-9]+(\\((.*)\\))\\;";
    // Sixth Phase - Convert normal statements to its form
    public static final String NORMAL_RETURN_REGEX = "(return)\\s[a-zA-Z0-9]+(\\(?(.*)\\)?)\\;";
    public static final String BREAK_STATEMENT_REGEX = "break";
    // Seventh Phase - Convert if, else, switch, conditional return to a commmon form
    public static final String CONDITIONAL_IF_OPERATOR_REGEX = "(if|else|else if)\\s?";
    public static final String CONDITIONAL_SWITCH_CASE_REGEX = "(switch|case|default)\\s?\\(?[a-zA-Z_\\d: ]+\\)?";
    public static final String CONDITIONAL_RETURN_REGEX = "(return)\\s[a-zA-Z\\d?:=&!<>\\s]+";


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
