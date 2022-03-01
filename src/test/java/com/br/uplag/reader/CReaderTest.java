package com.br.uplag.reader;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.Map;

public class CReaderTest {
    private Reader reader;

    @Before
    public void setUp() {
        this.reader = new CReader();
    }

    @Test
    public void shouldReadFileContent() {
        Map<String, String> result = reader.createFilesContentMap(List.of("src/test/resources/exercise01.c"));
        Assert.assertTrue(result.containsKey("src/test/resources/exercise01.c"));
        String expectedCode = result.get("src/test/resources/exercise01.c");
        Assert.assertFalse(expectedCode.isEmpty());
    }

    @Test
    public void shouldThrowExceptionWhenFileDoesNotExist() {
        Map<String, String> result = reader.createFilesContentMap(List.of("src/test/resources/exercise-that-does-not-exist-01.c"));
        Assert.assertTrue(result.isEmpty());
    }
}