package com.br.uplag.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FileInputUtil {
    private static final Logger LOGGER = Logger.getLogger("File input");

    public static List<String> getAllFilesPath(String directory, String ...args) {
        List<String> files = new ArrayList<>();
        int upperBound = args.length;
        if (isValidFileInputSize(upperBound)) {
            boolean filesTerminatedWith = args[6].matches("\\.[a-z]+");
            if (filesTerminatedWith) {
                String extension = StringUtil.replaceBy(".", "", args[6]);
                files.addAll(walkTroughDirectory(directory, extension));
            } else {
                for (int i = 5; i < upperBound; i++) {
                    if (isExistingFile(directory + args[i]))
                        files.add(directory + args[i]);
                    else
                        LOGGER.log(Level.SEVERE, "File {0} not found", args[i]);
                }
            }
        }

        return files;
    }

    public static List<String> walkTroughDirectory(String directory, String fileExtension) {
        try (Stream<Path> walk = Files.walk(Paths.get(directory))) {
            return walk
                    .map(Path::toString)
                    .filter(file -> file.endsWith(fileExtension))
                    .collect(Collectors.toList());
        } catch (IOException ioException) {
            LOGGER.severe(ioException.getMessage());
        }

        return Collections.emptyList();
    }

    public static boolean isExistingFile(String filePath) {
        return Files.exists(Paths.get(filePath));
    }

    // @Font: Baeldung
    public static String readFromInputStream(String directory) {
        StringBuilder stringBuilder = new StringBuilder();
        BufferedReader bufferedReader = null;
        try {
            bufferedReader = Files.newBufferedReader(Paths.get(directory), StandardCharsets.ISO_8859_1);
            String line;
            while ((line = bufferedReader.readLine()) != null)
                if (!line.isEmpty() || !line.isBlank())
                    stringBuilder.append(line).append("\n");
            bufferedReader.close();
        } catch (IOException ioException) {
            LOGGER.log(Level.SEVERE, "An com.br.uplag.exception occurred for {0}", directory);
            LOGGER.severe(ioException.getMessage());
            return "";
        } finally {
            closeBufferReader(bufferedReader);

        }

        return stringBuilder.toString().toLowerCase();
    }

    private static void closeBufferReader(BufferedReader bufferedReader) {
        if (bufferedReader != null) {
            try {
                bufferedReader.close();
            } catch (IOException ioException) {
                LOGGER.severe(ioException.getMessage());
            }
        }
    }

    private static boolean isValidFileInputSize(int upperBound) {
        if (upperBound < 5) {
            LOGGER.severe("Invalid number of files");
            return false;
        }

        return true;
    }
}
