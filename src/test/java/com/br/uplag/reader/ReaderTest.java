package com.br.uplag.reader;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import com.br.uplag.util.FileInputUtil;

import java.util.List;

@RunWith(JUnit4.class)
public class ReaderTest {
    @Test
    public void shouldReadFile() {
        String[] args = new String[6];
        args[0] = "-d";
        args[1] = "src/test/resources/";
        args[2] = "-l";
        args[3] = "com/br/uplag/c";
        args[4] = "-p";
        args[5] = ".com.br.uplag.c";
        List<String> files = FileInputUtil.getAllFilesPath(args[1], args);
        Reader reader = new CReader();
        Assert.assertEquals(false, reader.startReadingInputFiles(files).isEmpty());
    }
}

