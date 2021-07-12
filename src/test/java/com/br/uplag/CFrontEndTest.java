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
            + "#define MAX 6 \n"
            + "// Recebe uma skilist e o valor que deseja-se saber se pertence a ela.\n"
            + "// Retorna 0 caso ela nao tenha sido alocada ou esteja vazia.\n"
            + "// Retorna 1 caso o elemento esteja na lista.\n"
            + "int buscaSkipList(skiplist *sk, int chave){\n\n"
            + "    // Verifica se a lista foi alocada e se est vazia.\n"
            + "	if(vaziaSkipList(sk)){\n"
            + "        printf(\"Lista nao alocada ou vazia.\\n\");\n"
            + "		return 0;\n"
            + "}\n" +
            "void liberaSkipList(skiplist *sk){\n" +
            "    if(sk != NULL){\n" +
            "        no *aux = sk->inicio->prox;\n" +
            "        no *auxLiberar;\n" +
            "\n" +
            "        while(aux != NULL){\n" +
            "            auxLiberar = aux->prox;\n" +
            "\n" +
            "            // Anda pro lado liberando os n�s\n" +
            "            while(auxLiberar != NULL){\n" +
            "                no *aux2 = auxLiberar;\n" +
            "                auxLiberar = auxLiberar->prox;\n" +
            "                free(aux2);\n" +
            "            }\n" +
            "\n" +
            "            // Apos liberar todos na mesma linha, anda pra baixo\n" +
            "            no *aux3 = aux;\n" +
            "            aux = aux->abaixo;\n" +
            "            free(aux3);\n" +
            "        }\n" +
            "\n" +
            "        // Libera o aux e o ponteiro que apontava pro cabe�alho\n" +
            "        aux = sk->inicio;\n" +
            "        free(aux);\n" +
            "        free(sk);\n" +
            "        sk = NULL; // Coloca null para corrigir possiveis erros quando for checar se est� vazia.\n" +
            "    }\n" +
            "}";
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

    @Test
    public void shouldConvertFunctionCallsToAppropriateToken() {
        final String functionCallRegex = CFrontEnd.FUNCTION_CALL_REGEX;
        final String substitute = "F_CALL";
        final Pattern pattern = Pattern.compile(functionCallRegex, Pattern.MULTILINE);
        final Matcher matcher = pattern.matcher(code);
        final String result = matcher.replaceAll(substitute);
        Assert.assertEquals(true, result.contains(substitute));
    }

    @Test
    public void shouldConvertImportToImportToken() {
        String importRegex = CFrontEnd.IMPORT_REGEX;
        String substitute = "IMPORT";
        Pattern pattern = Pattern.compile(importRegex, Pattern.MULTILINE);
        Matcher matcher = pattern.matcher(code);
        String result = matcher.replaceAll(substitute);
        Assert.assertEquals(true, result.contains(substitute));
    }

    @Test
    public void shouldConvertDefinitionToConstToken() {
        String definitionREGEX = CFrontEnd.DEFINE_REGEX;
        String substitute = "CONST";
        Pattern pattern = Pattern.compile(definitionREGEX, Pattern.MULTILINE);
        Matcher matcher = pattern.matcher(code);
        String result = matcher.replaceAll(substitute);
        Assert.assertEquals(true, result.contains(substitute));
    }

    @Test
    public void shouldGetAllIfConditions() {
        String conditionREGEX = "(if|switch)\\s?\\(.*\\)";
        String insideConditionRegex = "(?<=if|switch)(.*?)(?=\\{)";
        String substitute = "COND";
        Pattern pattern = Pattern.compile(conditionREGEX, Pattern.MULTILINE);
        Matcher matcher = pattern.matcher(code);
        while (matcher.find()) {
            System.out.println(matcher.group());
        }
    }

    @Test
    public void shouldGetConditionalReturn() {
        String conditionalReturnREGEX = "(return)\\s[a-zA-Z\\d?:=&!<>\\s]+";
        String code = "return code >= 5";
        String substitute = "COND";
        Pattern pattern = Pattern.compile(conditionalReturnREGEX);
        Matcher matcher = pattern.matcher(code);
        String result = matcher.replaceAll(substitute);
        Assert.assertEquals("COND", result);
    }
}
