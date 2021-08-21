package com.br.uplag;

import index.InvertedIndex;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import reader.CReader;
import reader.Reader;
import util.FileInputUtil;

import java.util.List;
import java.util.Map;

@RunWith(JUnit4.class)
public class InvertedIndexTest {
    @Test
    public void shouldCreateInvertedIndex() {
        List<String> codePaths = FileInputUtil.walkTroughDirectory("src/test/resources/", "c");
        Reader reader = new CReader();
        Map<String, String> filesContent = reader.startReadingInputFiles(codePaths);
        InvertedIndex invertedIndex = new InvertedIndex();
        invertedIndex.createInvertedIndex(filesContent);
        Assert.assertEquals(false, invertedIndex.getInvertedIndex().isEmpty());
    }
}