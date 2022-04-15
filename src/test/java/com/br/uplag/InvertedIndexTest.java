package com.br.uplag;

import com.br.uplag.index.InvertedIndex;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import com.br.uplag.reader.CReader;
import com.br.uplag.reader.Reader;
import com.br.uplag.util.FileInputUtil;

import java.util.List;
import java.util.Map;

@RunWith(JUnit4.class)
public class InvertedIndexTest {
    @Test
    public void shouldCreateInvertedIndex() {
        List<String> codePaths = FileInputUtil.walkTroughDirectory("src/test/resources/", "c");
        Reader reader = new CReader();
        Map<String, String> filesContent = reader.createFilesContentMap(codePaths);
        InvertedIndex invertedIndex = new InvertedIndex();
        invertedIndex.createInvertedIndex(filesContent);
        Assert.assertFalse(invertedIndex.getInvertedIndex().isEmpty());
    }
}