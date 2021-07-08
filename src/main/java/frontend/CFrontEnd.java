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
    public static final Logger LOGGER = Logger.getLogger(CFrontEnd.class.getName());
    @Override
    public void convertFunctionsOccurrencesToToken(String codeText) {
        Pattern pattern = Pattern.compile(FUNCTION_REGEX);
        Matcher matcher = pattern.matcher(codeText);
        while (matcher.find()) {
            System.out.println(matcher.group(0));
            System.out.println("New string: " + matcher.group(0).replaceAll(FUNCTION_REGEX, FUNCTION_TOKEN));
        }
        LOGGER.log(Level.INFO,"Number of functions {0}", matcher.groupCount());
    }


    @Override
    public void convertVariableOccurrencesToToken(String codeText) {
    }
}
