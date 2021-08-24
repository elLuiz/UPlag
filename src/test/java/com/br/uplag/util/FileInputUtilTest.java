package com.br.uplag.util;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.Arrays;
import java.util.List;

@RunWith(JUnit4.class)
public class FileInputUtilTest {
    @Test
    public void shouldGetAllFilesPath() {
        String []args = new String[7];
        args[0] = "-d";
        args[1] = "/home/luiz/Downloads/fontes/";
        args[2] = "-l";
        args[3] = "com/br/uplag/c";
        args[4] = "-p";
        args[5] = "fonte01.c";
        args[6] = "fonte02.c";
        List<String> files = Arrays.asList("/home/luiz/Downloads/fontes/fonte01.com.br.uplag.c", "/home/luiz/Downloads/fontes/fonte02.com.br.uplag.c");
        Assert.assertEquals(files, FileInputUtil.getAllFilesPath(args[1], args));
    }

    @Test
    public void shouldGetAllFilesWithExtension() {
        String []args = new String[6];
        args[0] = "-d";
        args[1] = "/home/luiz/Downloads/fontes/";
        args[2] = "-l";
        args[3] = "com/br/uplag/c";
        args[4] = "-p";
        args[5] = ".com.br.uplag.c";
        Assert.assertEquals(true, !FileInputUtil.getAllFilesPath(args[1], args).isEmpty());
    }

    @Test
    public void shouldWalkThroughDirectory() {
        String directory = "src/test/resources/";
        String fileExtension = "com/br/uplag/c";
        Assert.assertEquals(1, FileInputUtil.walkTroughDirectory(directory, fileExtension).size());
    }

    @Test
    public void shouldReadFile() {
        String directory = "src/test/resources/code.com.br.uplag.c";
        String code = FileInputUtil.readFromInputStream(directory);
        Assert.assertEquals(false, code.isEmpty());
    }

    @Test
    public void shouldThrowExceptionAndReturnEmptyStringWhenPathDoesNotExist() {
        String directory = "src/test/resources/unknown/path";
        Assert.assertEquals(true, FileInputUtil.readFromInputStream(directory).isEmpty());
    }
}
