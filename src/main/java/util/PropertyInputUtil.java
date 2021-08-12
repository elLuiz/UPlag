package util;

import parameters.ParametersInputRegex;

import java.util.logging.Logger;

public class PropertyInputUtil {
    private static final Logger LOGGER = Logger.getLogger(PropertyInputUtil.class.getName());

    public static boolean verifyIfPropertyIsListed(String input, ParametersInputRegex inputRegex) {
        return inputRegex.getParameter().equals(input);
    }
}
