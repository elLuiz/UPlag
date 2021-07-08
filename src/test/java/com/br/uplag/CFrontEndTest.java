package com.br.uplag;

import frontend.CFrontEnd;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RunWith(JUnit4.class)
public class CFrontEndTest {
    private final String code = "/* Just a test */ /\n\n"
            + "#include<stdio.h>\n"
            + "#include<stdlib.h>\n"
            + "#include<math.h>\n"
            + "#include \"SkipList.h\"\n\n"
            + "// Recebe uma skilist e o valor que deseja-se saber se pertence a ela.\n"
            + "// Retorna 0 caso ela nao tenha sido alocada ou esteja vazia.\n"
            + "// Retorna 1 caso o elemento esteja na lista.\n"
            + "int buscaSkipList(skiplist *sk, int chave){\n\n"
            + "    // Verifica se a lista foi alocada e se est vazia.\n"
            + "	if(vaziaSkipList(sk)){\n"
            + "        printf(\"Lista nao alocada ou vazia.\\n\");\n"
            + "		return 0;\n"
            + "}\n";
    @Test
    public void shouldRemoveComments() {
        final String regex = CFrontEnd.DOUBLE_ASTERISK_COMMENT_REGEX;
        final String subst = "";

        final Pattern pattern = Pattern.compile(regex, Pattern.MULTILINE);
        final Matcher matcher = pattern.matcher(code);

        final String result = matcher.replaceAll(subst);
        Assert.assertEquals(false, result.contains("/**/"));
    }

    @Test
    public void shouldRemoveDoubleSlashComments() {
        final String slashRegex = CFrontEnd.DOUBLE_SLASH_COMMENT_REGEX;
        final String substitute = "";
        final Pattern pattern = Pattern.compile(slashRegex, Pattern.MULTILINE);
        final Matcher matcher = pattern.matcher(code);
        final String result = matcher.replaceAll(substitute);
        Assert.assertEquals(false, result.contains("//"));
    }

    @Test
    public void shouldConvertFunctionOccurrencesToProperToken() {
        final String functionRegex = CFrontEnd.FUNCTION_REGEX;
        final String substitute = "FUN";
        final Pattern pattern = Pattern.compile(functionRegex, Pattern.MULTILINE);
        final Matcher matcher = pattern.matcher(code);
        final String result = matcher.replaceAll(substitute);
        Assert.assertEquals(true, result.contains(substitute));
    }
}
