package util;

import java.io.BufferedReader;
import java.io.IOException;
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
            boolean filesTerminatedWith = args[5].matches("\\.[a-z]+");
            if (filesTerminatedWith) {
                String extension = StringUtil.replaceBy(".", "", args[5]);
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
        try {
            BufferedReader bufferedReader = Files.newBufferedReader(Paths.get(directory));
            String line;
            while ((line = bufferedReader.readLine()) != null)
                stringBuilder.append(line).append("\n");
        } catch (IOException ioException) {
            LOGGER.log(Level.SEVERE, "An exception occurred for {0}", directory);
            return "";
        }

        return stringBuilder.toString();
    }
    private static boolean isValidFileInputSize(int upperBound) {
        if (upperBound < 5) {
            LOGGER.severe("Invalid number of files");
            return false;
        }

        return true;
    }
}
