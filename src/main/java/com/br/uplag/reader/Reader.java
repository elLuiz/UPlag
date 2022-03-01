package com.br.uplag.reader;

import com.br.uplag.exception.UPlagException;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

public abstract class Reader {
  private static final Logger LOGGER = Logger.getLogger(Reader.class.getSimpleName());

  // @Reference: Baeldung
  protected String readFileContentAccordingToStandardCharset(String directory, Charset charset) throws UPlagException {
    BufferedReader bufferedReader = null;
    try {
      bufferedReader = Files.newBufferedReader(Paths.get(directory), charset);
      return readFile(bufferedReader);
    } catch (IOException ioException) {
      throw new UPlagException(ioException);
    } finally {
      closeBufferReader(bufferedReader);
    }
  }

  private String readFile(BufferedReader bufferedReader) throws IOException {
    String line;
    var stringBuilder = new StringBuilder();
    while ((line = bufferedReader.readLine()) != null) {
      if (!line.isEmpty() || !line.isBlank())
        stringBuilder.append(line).append("\n");
    }
    return stringBuilder.toString().toLowerCase();
  }

  private void closeBufferReader(BufferedReader bufferedReader) {
    if (bufferedReader != null) {
      try {
        bufferedReader.close();
      } catch (IOException ioException) {
        LOGGER.severe(ioException.getMessage());
      }
    }
  }

  public abstract Map<String, String> createFilesContentMap(List<String> codeFilesInput);
}
