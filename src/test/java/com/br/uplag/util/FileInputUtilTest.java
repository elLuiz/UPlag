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
    public void shouldGetAllFilesWhenJustTheExtensionIsSpecified() {
        String directory = "src/test/resources/";
        int programIndex = 1;
        String[] args = new String[]{"-p", ".c"};
        Assert.assertFalse(FileInputUtil.getAllFilesPath(directory, programIndex, args).isEmpty());
    }

    @Test
    public void shouldGetSpecifiedFiles() {
        String directory = "src/test/resources/";
        int programIndex = 1;
        String[] args = new String[]{"-d", directory, "-l", "c","-p", "exercise01.c", "exercise02.c"};
        Assert.assertEquals(2, FileInputUtil.getAllFilesPath(directory, programIndex, args).size());
    }

    @Test
    public void shouldWalkThroughDirectory() {
        String directory = "src/test/resources/";
        String fileExtension = "c";
        Assert.assertFalse(FileInputUtil.walkTroughDirectory(directory, fileExtension).isEmpty());
    }

    @Test
    public void shouldReadFile() {
        String directory = "src/test/resources/exercise01.c";
        String code = FileInputUtil.readFromInputStream(directory);
        Assert.assertFalse(code.isEmpty());
    }

    @Test
    public void shouldThrowExceptionAndReturnEmptyStringWhenPathDoesNotExist() {
        String directory = "src/test/resources/unknown/path";
        Assert.assertTrue(FileInputUtil.readFromInputStream(directory).isEmpty());
    }
}
