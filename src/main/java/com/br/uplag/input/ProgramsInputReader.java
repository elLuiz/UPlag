package com.br.uplag.input;

import com.br.uplag.parameters.ParametersInputRegex;
import com.br.uplag.util.FileInputUtil;

public class ProgramsInputReader extends TerminalInputReader implements InputReader{
    public ProgramsInputReader() {
    }

    public ProgramsInputReader(String[] arguments) {
        super(arguments);
    }

    @Override
    public void defineParameter() {
        if (argumentContainsProperty(ParametersInputRegex.PROGRAMS)) {
            int index = arguments.indexOf(ParametersInputRegex.PROGRAMS.getParameter());
            if (index > 0) {
                programs = FileInputUtil.getAllFilesPath(directory, index + 1, args);
            }
        }
    }

    public void setDirectory(String directory) {
        this.directory = directory;
    }
}
