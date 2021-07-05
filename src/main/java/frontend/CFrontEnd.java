package frontend;

import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CFrontEnd extends FrontEnd{
    private static final String FUNCTION_REGEX = "(function\\s[a-zA-Z]+\\s[a-zA-Z0-9]+\\((.*)\\))\\s?\\{";
    private static final Logger LOGGER = Logger.getLogger(CFrontEnd.class.getName());

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
