import parameters.ParametersInputRegex;
import processor.CodeProcessor;
import reader.CReader;
import util.FileInputUtil;
import util.PropertyInputUtil;

import java.util.ArrayList;
import java.util.List;

// args 0 - directory
// args 2 - language
// args 4 - programs
public class LexicalAnalysis {
    public static void main(String ...args) {
        String directoryPath = "";
        String language = "";
        List<String> programs = new ArrayList<>();
        if (PropertyInputUtil.verifyIfPropertyIsListed(args[0], ParametersInputRegex.DIRECTORY))
            directoryPath = args[1];
        if (PropertyInputUtil.verifyIfPropertyIsListed(args[2], ParametersInputRegex.LANGUAGE))
            language = args[3];
        if (PropertyInputUtil.verifyIfPropertyIsListed(args[4], ParametersInputRegex.PROGRAMS)) {
            programs = FileInputUtil.getAllFilesPath(directoryPath, args);
        }

        if ("c".equalsIgnoreCase(language)) {
            CodeProcessor codeProcessor = new CodeProcessor(new CReader());
            codeProcessor.setPrograms(programs);
            codeProcessor.createInvertedIndex();
        }
    }
}