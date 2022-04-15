package com.br.uplag.util;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class DirectoryInputUtilTest {
    @Test
    public void shouldReturnTrueForValidTest() {
        String path = "src/test/resources";
        Assert.assertTrue(DirectoryInputUtil.isExistentDirectory(path));
    }

    @Test
    public void shouldReturnTrueWhenPathDoesNotExist() {
        String path = "/home/luiz/Downloads/fontes/2092/";
        Assert.assertFalse(DirectoryInputUtil.isExistentDirectory(path));
    }
}
