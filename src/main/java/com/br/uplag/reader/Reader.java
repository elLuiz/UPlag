package com.br.uplag.reader;

import java.util.List;
import java.util.Map;

public interface Reader {
  Map<String, String> startReadingInputFiles(List<String> codeFilesInput);
}
