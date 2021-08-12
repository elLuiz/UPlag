package reader;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import static util.FileInputUtil.readFromInputStream;

public class Reader {
    private static final Logger LOGGER = Logger.getLogger(Reader.class.getName());
    private final List<String> codeFilePath;

    public Reader(List<String> codeFilePath) {
        this.codeFilePath = codeFilePath;
    }

    public void startReadingInputFiles() {
        LOGGER.log(Level.INFO,"Started reading {0} files", codeFilePath.size());
        for (String path : codeFilePath) {
            String codeText = readFromInputStream(path);
            System.out.println(codeText);
        }
    }
}
