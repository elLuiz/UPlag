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
    private static final Logger LOGGER = Logger.getLogger(FileInputUtil.class.getSimpleName());
    private static final String FILE_EXTENSION_REGEX = "\\.[a-z]+";

    private FileInputUtil() {
    }

    public static List<String> getAllFilesPath(String directory, int programsIndex, String... args) {
        List<String> files = new ArrayList<>();
        if (isTerminatedWithProgramExtension(args[programsIndex])) {
            getAllDirectoryFilesTerminatedWithExtension(directory, files, args[programsIndex]);
        } else {
            getSpecifiedFiles(directory, files, args);
        }
        Collections.sort(files);
        return files;
    }

    private static boolean isTerminatedWithProgramExtension(String args) {
        return args.matches(FILE_EXTENSION_REGEX);
    }

    private static void getAllDirectoryFilesTerminatedWithExtension(String directory, List<String> files, String args) {
        String extension = StringUtil.replaceByCharacter(args, "", ".");
        files.addAll(walkTroughDirectory(directory, extension));
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

    private static void getSpecifiedFiles(String directory, List<String> files, String[] args) {
        int upperBound = args.length;
        for (int i = 5; i < upperBound; i++) {
            if (isExistingFile(directory + args[i]))
                files.add(directory + args[i]);
            else
                LOGGER.log(Level.SEVERE, "File {0} not found", args[i]);
        }
    }

    public static boolean isExistingFile(String filePath) {
        return Files.exists(Paths.get(filePath));
    }

    // Fonte: Baeldung
    public static String readFromInputStream(String directory) {
        var stringBuilder = new StringBuilder();
        BufferedReader bufferedReader = null;
        try {
            bufferedReader = Files.newBufferedReader(Paths.get(directory), StandardCharsets.ISO_8859_1);
            String line;
            while ((line = bufferedReader.readLine()) != null)
                if (!line.isEmpty() || !line.isBlank())
                    stringBuilder.append(line).append("\n");
            bufferedReader.close();
        } catch (IOException ioException) {
            LOGGER.log(Level.SEVERE, "An exception occurred for {0}", directory);
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
}
