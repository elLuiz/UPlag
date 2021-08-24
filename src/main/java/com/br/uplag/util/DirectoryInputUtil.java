package com.br.uplag.util;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DirectoryInputUtil {
    private static final Logger LOGGER = Logger.getLogger(DirectoryInputUtil.class.getName());

    public static boolean isExistentDirectory(String directoryPath) {
        if (StringUtil.isValid(directoryPath) && Files.exists(Paths.get(directoryPath)))
            return true;
        else
            LOGGER.log(Level.SEVERE, "Unknown path: {0}", directoryPath);

        return false;
    }
}
